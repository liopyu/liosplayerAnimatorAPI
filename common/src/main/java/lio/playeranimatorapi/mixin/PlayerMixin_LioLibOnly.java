package lio.playeranimatorapi.mixin;

import net.liopyu.liolib.animatable.GeoEntity;
import net.liopyu.liolib.core.animatable.instance.AnimatableInstanceCache;
import net.liopyu.liolib.core.animation.AnimatableManager;
import net.liopyu.liolib.core.animation.AnimationController;
import net.liopyu.liolib.core.object.PlayState;
import net.liopyu.liolib.util.GeckoLibUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.liolib.ModGeckoLibUtilsClient;

@Mixin(Player.class)
public abstract class PlayerMixin_LioLibOnly implements GeoEntity {

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
