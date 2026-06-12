package noobsdev.mlmod_fork.client.commands;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.Screen;
import noobsdev.mlmod_fork.integrations.config.ConfigScreenProvider;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigCommand extends MLModCommand {

    @Override
    public void run() {
        AtomicBoolean a = new AtomicBoolean(true);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (a.get()) {
                Screen configScreen = ConfigScreenProvider.createConfigScreen(client.currentScreen);
                client.setScreen(configScreen);
                a.set(false);
            }
        });
    }

}
