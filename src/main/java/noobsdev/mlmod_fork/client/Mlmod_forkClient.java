package noobsdev.mlmod_fork.client;

import net.fabricmc.api.ClientModInitializer;
import noobsdev.mlmod_fork.client.keybinds.OpenConfigBind;
import noobsdev.mlmod_fork.client.keybinds.RegisterKeybind;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mlmod_forkClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitializeClient() {
        RegisterKeybind keybinds = new RegisterKeybind();
        keybinds.add(new OpenConfigBind());
    }
}
