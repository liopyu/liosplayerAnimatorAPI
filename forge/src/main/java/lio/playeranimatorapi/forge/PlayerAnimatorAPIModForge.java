package lio.playeranimatorapi.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.ModInitClient;

@Mod(ModInit.MOD_ID)
public class PlayerAnimatorAPIModForge {
    public PlayerAnimatorAPIModForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::ClientInit);

        ModInit.init();
    }

    private void ClientInit(final FMLClientSetupEvent event) {
        ModInitClient.init();
    }
}
