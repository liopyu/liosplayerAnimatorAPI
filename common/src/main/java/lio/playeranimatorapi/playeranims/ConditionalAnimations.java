package lio.playeranimatorapi.playeranims;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import lio.playeranimatorapi.data.PlayerAnimationData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static lio.playeranimatorapi.playeranims.PlayerAnimations.animationLayerId;

@Environment(EnvType.CLIENT)
public class ConditionalAnimations {

    private static Map<String, Function<PlayerAnimationData, ResourceLocation>> perModConditions = new HashMap<>();

    public static void addModConditions(String namespace, Function<PlayerAnimationData, ResourceLocation> function) {
        perModConditions.put(namespace, function);
    }

    public static ResourceLocation getAnimationForCurrentConditions(PlayerAnimationData data) {

        if (perModConditions.containsKey(data.animationID().getNamespace())) {
            return perModConditions.get(data.animationID().getNamespace()).apply(data);
        }

        AbstractClientPlayer player = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(data.playerUUID());
        CustomModifierLayer animationContainer = (CustomModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(animationLayerId);
        ResourceLocation currentAnim = animationContainer.currentAnim;

        ResourceLocation baseAnim = data.animationID();
        ResourceLocation original = data.animationID();
        String newPath = original.getPath() + "_run";
        ResourceLocation runningAnim = new ResourceLocation(original.getNamespace(), newPath);
        String originalPath = data.animationID().getPath();
        String crouchedPath = originalPath + "_crouch";
        String crawlingPath = originalPath + "_crawl";
        String swimmingPath = originalPath + "_swim";

        ResourceLocation crouchedAnim = new ResourceLocation(data.animationID().getNamespace(), crouchedPath);
        ResourceLocation crawlingAnim = new ResourceLocation(data.animationID().getNamespace(), crawlingPath);
        ResourceLocation swimmingAnim = new ResourceLocation(data.animationID().getNamespace(), swimmingPath);
        Map<ResourceLocation, KeyframeAnimation> animations = PlayerAnimationRegistry.getAnimations();

        if (player.isCrouching() && currentAnim != crawlingAnim && animations.containsKey(crouchedAnim)) {
            return crouchedAnim;
        } else if (player.isVisuallyCrawling() && currentAnim != crawlingAnim && animations.containsKey(crawlingAnim)) {
            return crawlingAnim;
        } else if (player.isVisuallySwimming() && currentAnim != swimmingAnim && animations.containsKey(swimmingAnim)) {
            return swimmingAnim;
        } else if (player.isSprinting() && currentAnim != runningAnim && animations.containsKey(runningAnim)) {
            return runningAnim;
        }

        return baseAnim;
    }
}
