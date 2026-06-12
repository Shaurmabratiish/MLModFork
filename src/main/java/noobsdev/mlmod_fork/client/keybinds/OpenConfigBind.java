package noobsdev.mlmod_fork.client.keybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import noobsdev.mlmod_fork.integrations.config.ConfigScreenProvider;
import org.lwjgl.glfw.GLFW;

public class OpenConfigBind extends Keybinder{
    public OpenConfigBind() {
        super(GLFW.GLFW_KEY_O, "keybind.mlmod_fork.open_config", "keybind.mlmod_fork.title");
    }

    @Override
    public void bind() {
        MinecraftClient client = MinecraftClient.getInstance();
        Screen configScreen = ConfigScreenProvider.createConfigScreen(client.currentScreen);
        client.setScreen(configScreen);
    }
}
