package lio.playeranimatorapi.mixin;


import net.liopyu.liolib.constant.DataTickets;
import net.liopyu.liolib.core.animatable.GeoAnimatable;
import net.liopyu.liolib.core.animation.AnimatableManager;
import net.liopyu.liolib.core.animation.AnimationProcessor;
import net.liopyu.liolib.core.animation.AnimationState;
import net.liopyu.liolib.model.GeoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import lio.playeranimatorapi.liolib.PlayerAnimationModel;
import lio.playeranimatorapi.playeranims.CustomModifierLayer;
import lio.playeranimatorapi.playeranims.PlayerAnimations;

@Mixin(GeoModel.class)
public abstract class GeoModelMixin_LioLibOnly<T extends GeoAnimatable> {

    @Shadow(remap = false) private long lastRenderedInstance;

    @Shadow(remap = false) private double lastGameTickTime;

    @Shadow(remap = false) private double animTime;

    @Shadow(remap = false) public abstract boolean crashIfBoneMissing();

    @Shadow(remap = false) public abstract AnimationProcessor<T> getAnimationProcessor();

    @Inject(method = "handleAnimations", at = @At("HEAD"), cancellable = true, remap = false)
    private void inject(T animatable, long instanceId, AnimationState<T> animationState, CallbackInfo ci) {
        if ((Object)this instanceof PlayerAnimationModel) {
            Minecraft mc = Minecraft.getInstance();
            AnimatableManager<T> animatableManager = animatable.getAnimatableInstanceCache().getManagerForId(instanceId);
            Double currentTick = animationState.getData(DataTickets.TICK);
            CustomModifierLayer modifierLayer = PlayerAnimations.getModifierLayer((AbstractClientPlayer) animatable);

            if (animatableManager.getFirstTickTime() == -1)
                animatableManager.startedAt(currentTick + mc.getFrameTime() - modifierLayer.animPlayer.getTick() - ((KeyframeAnimationPlayerAccessor)modifierLayer.animPlayer).getTickDelta());

            if (currentTick == null) {
                LivingEntity livingEntity = (LivingEntity) animatable;
                currentTick = (double) livingEntity.tickCount;
            }

            if (animatableManager.getFirstTickTime() == -1)
                animatableManager.startedAt(currentTick + mc.getFrameTime());

            double currentFrameTime = currentTick - animatableManager.getFirstTickTime();
            boolean isReRender = !animatableManager.isFirstTick() && currentFrameTime == animatableManager.getLastUpdateTime();

            if (isReRender && instanceId == this.lastRenderedInstance)
                return;

            if (!isReRender && (!mc.isPaused() || animatable.shouldPlayAnimsWhileGamePaused())) {
                animatableManager.updatedAt(currentFrameTime);

                double lastUpdateTime = animatableManager.getLastUpdateTime();
                this.animTime += lastUpdateTime - this.lastGameTickTime;
                this.lastGameTickTime = lastUpdateTime;
            }

            animationState.animationTick = this.animTime;
            AnimationProcessor<T> processor = getAnimationProcessor();

            processor.preAnimationSetup(animationState.getAnimatable(), this.animTime);

            if (!processor.getRegisteredBones().isEmpty())
                processor.tickAnimation(animatable, (GeoModel)(Object)this, animatableManager, this.animTime, animationState, crashIfBoneMissing());

            ci.cancel();
        }
    }
}
