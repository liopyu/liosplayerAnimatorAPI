package zigy.playeranimatorapi.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zigy.playeranimatorapi.PlayerAnimatorAPIMod;
import zigy.playeranimatorapi.data.PlayerAnimationData;
import zigy.playeranimatorapi.data.PlayerParts;
import zigy.playeranimatorapi.utils.CommonPlayerLookup;
import zigy.playeranimatorapi.utils.NetworkManager;

/**
 * Class for playing player animations from server side.
 * Look at the GitHub wiki for information on what the method parameters do.
 */
public class PlayerAnimAPI {

    public static final ResourceLocation playerAnimPacket = new ResourceLocation(PlayerAnimatorAPIMod.MOD_ID, "player_anim");
    public static final ResourceLocation playerAnimStopPacket = new ResourceLocation(PlayerAnimatorAPIMod.MOD_ID, "player_anim_stop");

    private static final Logger logger = LogManager.getLogger(PlayerAnimatorAPIMod.class);

    public static Gson gson = new GsonBuilder().setLenient().serializeNulls().create();

    //For emotes.
    public static void playPlayerAnim(ServerLevel level, Player player, ResourceLocation animationID) {
        playPlayerAnim(level, player, animationID, null, null, PlayerParts.allEnabled,
                -1, -1, -1, false, false, false);
    }

    //For gameplay like player animations for items.
    public static void playPlayerAnim(ServerLevel level, Player player, ResourceLocation normalAnimationID, ResourceLocation crouchedAnimationID,
                                      ResourceLocation swimmingAnimationID, PlayerParts parts, boolean firstPersonEnabled) {
        playPlayerAnim(level, player, normalAnimationID, crouchedAnimationID, swimmingAnimationID, parts, -1,
                -1, -1, firstPersonEnabled, true, true);
    }

    //Play player animations with the PlayerAnimationData class.
    public static void playPlayerAnim(ServerLevel level, Player player, PlayerAnimationData data) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());

        buf.writeUtf(gson.toJson(PlayerAnimationData.CODEC.encodeStart(JsonOps.INSTANCE, data).getOrThrow(true, logger::warn)));
        NetworkManager.sendToPlayers(CommonPlayerLookup.tracking(level, player.chunkPosition()), playerAnimPacket, buf);
    }

    //Play player animations with full customizability.
    public static void playPlayerAnim(ServerLevel level, Player player, ResourceLocation normalAnimationID, ResourceLocation crouchedAnimationID,
                                      ResourceLocation swimmingAnimationID, PlayerParts parts, int fadeLength, float desiredLength,
                                      int easeID, boolean firstPersonEnabled, boolean shouldMirror, boolean shouldFollowPlayerView) {

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        PlayerAnimationData data = new PlayerAnimationData(player.getUUID(), normalAnimationID, crouchedAnimationID, swimmingAnimationID,
                parts, fadeLength, desiredLength, easeID, firstPersonEnabled, shouldMirror, shouldFollowPlayerView);

        buf.writeUtf(gson.toJson(PlayerAnimationData.CODEC.encodeStart(JsonOps.INSTANCE, data).getOrThrow(true, logger::warn)));
        NetworkManager.sendToPlayers(CommonPlayerLookup.tracking(level, player.chunkPosition()), playerAnimPacket, buf);
    }

    //Stop a player animation
    public static void stopPlayerAnim(ServerLevel level, Player player, ResourceLocation animationID) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeUUID(player.getUUID());
        buf.writeResourceLocation(animationID);
        NetworkManager.sendToPlayers(CommonPlayerLookup.tracking(level, player.chunkPosition()), playerAnimStopPacket, buf);
    }
}
