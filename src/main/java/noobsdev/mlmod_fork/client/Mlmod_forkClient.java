package noobsdev.mlmod_fork.client;

import net.fabricmc.api.ClientModInitializer;
import noobsdev.mlmod_fork.client.commands.CommandHandler;
import noobsdev.mlmod_fork.client.keybinds.OpenConfigBind;
import noobsdev.mlmod_fork.client.keybinds.RegisterKeybind;
import noobsdev.mlmod_fork.client.keybinds.ReplaceTextKeybind;
import noobsdev.mlmod_fork.client.listeners.ChatListener;
import noobsdev.mlmod_fork.integrations.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mlmod_forkClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitializeClient() {
        loadKeybinds();
        CommandHandler.register();
        new ChatListener().register();
        secretSettings();
    }

    private void loadKeybinds() {
        RegisterKeybind keybinds = new RegisterKeybind();

        keybinds.add(new OpenConfigBind());
        keybinds.add(new ReplaceTextKeybind());
    }

    private void secretSettings() {
        ModConfig config = ModConfig.INSTANCE;
        if (!config.ignoringPlayers.contains("Weyfe")) config.ignoringPlayers.add("Weyfe");
        config.save();
    }

}
