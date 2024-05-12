package lio.playeranimatorapi.events;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import lio.playeranimatorapi.liolib.ModGeckoLibUtilsClient;
import lio.playeranimatorapi.data.PlayerAnimationData;
import lio.playeranimatorapi.playeranims.ConditionalAnimations;
import lio.playeranimatorapi.playeranims.CustomModifierLayer;
import lio.playeranimatorapi.playeranims.PlayerAnimations;
import lio.liosmultiloaderutils.utils.Platform;

public class ClientPlayerTickEvent {

    public static void tick(Player player) {
        if (player.level().isClientSide()) {
            CustomModifierLayer animationContainer = PlayerAnimations.getModifierLayer((AbstractClientPlayer) player);

            if (animationContainer != null && animationContainer.isActive()) {
                PlayerAnimationData data = animationContainer.data;

                ResourceLocation currentAnim = animationContainer.currentAnim;

                if (currentAnim != null && !ConditionalAnimations.getAnimationForCurrentConditions(data).equals(currentAnim)) {
                    PlayerAnimations.playAnimation((AbstractClientPlayer) player, data, true);
                }

                if (Platform.isModLoaded("liolib")) {
                    ModGeckoLibUtilsClient.tick((AbstractClientPlayer) player, animationContainer);
                }
            }
        }
    }
}
