package lio.playeranimatorapi.fabric;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import lio.playeranimatorapi.ModInit;
import lio.playeranimatorapi.ResourceReloadListener;

public class ResourceReloadListenerFabric extends ResourceReloadListener implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(ModInit.MOD_ID, "my_resources");
    }
}
