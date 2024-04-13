package lio.playeranimatorapi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import software.bernie.geckolib.core.animation.AnimatableManager;

@Mixin(AnimatableManager.class)
public interface AnimatableManagerAccessor_GeckoLibOnly {
    @Invoker(remap = false)
    void callFinishFirstTick();
}
