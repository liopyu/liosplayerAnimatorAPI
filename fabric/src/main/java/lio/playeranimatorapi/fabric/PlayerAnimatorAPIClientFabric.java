package lio.playeranimatorapi.fabric;

import net.fabricmc.api.ClientModInitializer;
import lio.playeranimatorapi.ModInitClient;

public class PlayerAnimatorAPIClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModInitClient.init();
    }
}
