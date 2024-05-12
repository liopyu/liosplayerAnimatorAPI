package lio.playeranimatorapi;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import lio.playeranimatorapi.geckolib.ModGeckoLibUtils;
import lio.playeranimatorapi.misc.ModEntityDataSerializers;
import lio.liosmultiloaderutils.MultiloaderUtils;
import lio.liosmultiloaderutils.utils.Platform;

public class ModInit {
    public static final String MOD_ID = "liosplayeranimatorapi";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean liosplayeranimationapiloaded = false;
    private static void isLiosPlayerAnimatorApiLoaded() {
        liosplayeranimationapiloaded = true;
    }
    public static void init() {

        ModEntityDataSerializers.init();
        MultiloaderUtils.forceClientToHaveMod(MOD_ID, Platform.getModVersion(MOD_ID));

        if (Platform.isModLoaded("geckolib", "software.bernie.geckolib.GeckoLib")) {
            ModGeckoLibUtils.init();
            ModInit.isLiosPlayerAnimatorApiLoaded();
        }
    }
}
