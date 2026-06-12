package noobsdev.mlmod_fork.client.commands;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandHandler {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("mlmod")
                .executes(ctx -> {
                    new HelpCommand().run();
                    return 1;
                })
                .then(literal("help")
                        .executes(ctx -> {
                            new HelpCommand().run();
                            return 1;
                        })
                )
                .then(literal("config")
                        .executes(ctx -> {
                            new ConfigCommand().run();
                            return 1;
                        })
                )
        ));
    }

}
