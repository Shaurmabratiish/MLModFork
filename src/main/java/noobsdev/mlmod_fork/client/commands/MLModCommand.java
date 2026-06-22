package noobsdev.mlmod_fork.client.commands;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public abstract class MLModCommand {
    public MLModCommand() {}

    public abstract void run(FabricClientCommandSource ctx);

}
