package zigy.playeranimatorapi.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import zigy.playeranimatorapi.ModInit;
import zigy.playeranimatorapi.ModInitClient;

@Mod(ModInit.MOD_ID)
public class PlayerAnimatorAPIModForge {
    public PlayerAnimatorAPIModForge() {
        IEventBus modEventBus = NeoForge.EVENT_BUS;
        modEventBus.addListener(this::ClientInit);

        ModInit.init();
    }

    private void ClientInit(final FMLClientSetupEvent event) {
        ModInitClient.init();
    }
}
