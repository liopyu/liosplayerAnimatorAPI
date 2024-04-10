package zigy.playeranimatorapi;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import zigy.zigysmultiloaderutils.utils.Platform;

import java.util.List;
import java.util.Set;

public class ModMixinPlugin implements IMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith("_azureOnly") && !Platform.isModLoaded("azurelib", "mod.azure.azurelib.AzureLib")) {
            return false;
        }
        if (mixinClassName.equals("zigy.playeranimatorapi.mixin.LivingEntityRendererMixin") && Platform.isModLoaded("azurelib", "mod.azure.azurelib.AzureLib")) {
            return false;
        }
        return true;
    }

    //Boilerplate

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
