package lio.playeranimatorapi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.liopyu.liolib.core.animation.AnimatableManager;

@Mixin(AnimatableManager.class)
public interface AnimatableManagerAccessor_LioLibOnly {
    @Invoker(remap = false)
    void callFinishFirstTick();
}
