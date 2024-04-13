package lio.playeranimatorapi.mixin;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.geckolib.ModGeckoLibUtilsClient;

@Mixin(Player.class)
public abstract class PlayerMixin_GeckoLibOnly implements GeoEntity {

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, ModInit.MOD_ID, state -> PlayState.STOP).setOverrideEasingTypeFunction((player) -> ModGeckoLibUtilsClient.getEasingTypeForID((Player)(Object)this)));
    }

    @Unique
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
