package noobsdev.mlmod_fork.client.commands;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class HelpCommand extends MLModCommand{
    @Override
    public void run(FabricClientCommandSource ctx) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("it's a MLMod!"), false);
    }
}
