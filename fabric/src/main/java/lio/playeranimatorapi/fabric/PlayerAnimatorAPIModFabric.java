package lio.playeranimatorapi.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.commands.PlayPlayerAnimationCommand;
import lio.playeranimatorapi.commands.StopPlayerAnimationCommand;

public class PlayerAnimatorAPIModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListenerFabric());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            PlayPlayerAnimationCommand.register(dispatcher);
            StopPlayerAnimationCommand.register(dispatcher);
        });

        ModInit.init();
    }
}
