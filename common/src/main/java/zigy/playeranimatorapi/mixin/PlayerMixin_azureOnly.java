package zigy.playeranimatorapi.mixin;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import zigy.playeranimatorapi.ModInit;
import zigy.playeranimatorapi.azure.ModAzureUtilsClient;

@Mixin(Player.class)
public abstract class PlayerMixin_azureOnly implements GeoEntity {

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, ModInit.MOD_ID, state -> PlayState.STOP).setOverrideEasingTypeFunction((player) -> ModAzureUtilsClient.getEasingTypeForID((Player)(Object)this)));
    }

    @Unique
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
