package lio.playeranimatorapi.forge;

import com.google.common.collect.ImmutableMap;
import lio.liosmultiloaderutils.utils.Platform;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ModMixinPluginForge implements IMixinConfigPlugin {

    private static final Supplier<Boolean> TRUE = () -> true;
    private static boolean madeInterface = false;

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "lio.playeranimatorapi.forge.mixin.EmoteCraftClientInitMixinForge", () -> Platform.isModLoaded("emotecraft", "io.github.kosmx.emotes.forge.ForgeWrapper")
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!madeInterface) {
            madeInterface = true;
            checkAndDefineInterface();
        }
        return CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
    }

    private void checkAndDefineInterface() {
        if (!isModLoaded("liolib")) {
            defineInterface();
        }
    }

    private static boolean isModLoaded(String modId) {
        if (Platform.isModLoaded("liolib", "net.liopyu.liolib.LioLib")) {
            return true;
        }
        return false;
    }

    private static void defineInterface() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_INTERFACE, "lio/playeranimatorapi/GeoPlayer", null, "java/lang/Object", null);
        cw.visitEnd();
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
