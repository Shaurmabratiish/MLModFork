package noobsdev.mlmod_fork.client.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class HelpCommand extends MLModCommand{
    @Override
    public void run() {
        MinecraftClient.getInstance().player.sendMessage(Text.literal("it's a MLMod!"), false);
    }
}
