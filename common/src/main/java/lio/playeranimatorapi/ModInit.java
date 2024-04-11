package lio.playeranimatorapi;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import lio.playeranimatorapi.azure.ModAzureUtils;
import lio.playeranimatorapi.misc.ModEntityDataSerializers;
import lio.liosmultiloaderutils.MultiloaderUtils;
import lio.liosmultiloaderutils.utils.Platform;
public class ModInit {
    public static final String MOD_ID = "liosplayeranimatorapi";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ModEntityDataSerializers.init();
        MultiloaderUtils.forceClientToHaveMod(MOD_ID, Platform.getModVersion(MOD_ID));

        if (Platform.isModLoaded("azurelib", "mod.azure.azurelib.AzureLib")) {
            ModAzureUtils.init();
        }
    }
}
