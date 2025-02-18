package lio.playeranimatorapi.fabric.mixin;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import io.github.kosmx.emotes.fabric.ClientInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.playeranims.CustomModifierLayer;
import lio.playeranimatorapi.playeranims.PlayerAnimations;

@Mixin(ClientInit.class)
public abstract class EmoteCraftClientInitMixinFabric {
    private static final ResourceLocation animationLayerId = new ResourceLocation(ModInit.MOD_ID, "factory");

    @Unique
    private static CustomModifierLayer animationContainer(AbstractClientPlayer player) {
        return (CustomModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(animationLayerId);
    }

    @Redirect(method = "lambda$initKeyBinding$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"), remap = false)
    private static void initKeybinding(Minecraft instance, @Nullable Screen guiScreen) {
        if (instance.player != null && PlayerAnimations.getModifierLayer(instance.player).isActive() && PlayerAnimations.getModifierLayer(instance.player).important) {
            instance.player.displayClientMessage(Component.translatable("warn.playeranimatorapi.cannotEmote"), true);
        }
    }
}
