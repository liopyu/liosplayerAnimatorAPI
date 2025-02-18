package lio.playeranimatorapi.forge;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.commands.PlayPlayerAnimationCommand;
import lio.playeranimatorapi.commands.StopPlayerAnimationCommand;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = ModInit.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ModEventListener {

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            PlayPlayerAnimationCommand.register(event.getDispatcher());
            StopPlayerAnimationCommand.register(event.getDispatcher());
        }
    }
}
