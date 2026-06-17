package noobsdev.mlmod_fork.client.commands;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import noobsdev.mlmod_fork.integrations.config.ModConfig;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandHandler {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("mlmod")
                .executes(ctx -> {
                    new HelpCommand().run(ctx.getSource());
                    return 1;
                })
                .then(literal("help")
                        .executes(ctx -> {
                            new HelpCommand().run(ctx.getSource());
                            return 1;
                        })
                )
                .then(literal("config")
                        .executes(ctx -> {
                            new ConfigCommand().run(ctx.getSource());
                            return 1;
                        })
                )
                .then(literal("ignore")
                        .executes(ctx -> {
                            new ConfigCommand().run(ctx.getSource());
                            return 1;
                        })
                        .then(ClientCommandManager.literal("add")
                                .then(ClientCommandManager.argument("playerName", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String target = StringArgumentType.getString(context, "playerName");
                                            new IgnoreCommand().addPlayer(context.getSource(),target);
                                            return 1;
                                        })
                                )
                        )
                        .then(ClientCommandManager.literal("remove")
                                .then(ClientCommandManager.argument("playerName", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String target = StringArgumentType.getString(context, "playerName");
                                            new IgnoreCommand().removePlayer(context.getSource(),target);
                                            return 1;
                                        })
                                )
                        )


                )
                .then(literal("ignore_world")
                        .executes(ctx -> {
                            new ConfigCommand().run(ctx.getSource());
                            return 1;
                        })
                        .then(ClientCommandManager.literal("add")
                                .then(ClientCommandManager.argument("worldName", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String target = StringArgumentType.getString(context, "worldName");
                                            new IgnoreCommand().addWorld(context.getSource(),target);
                                            return 1;
                                        })
                                )
                        )
                )
        ));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("mlmod_internal_menu")
                .then(ClientCommandManager.argument("targetPlayer", StringArgumentType.greedyString())
                        .executes(context -> {
                            String targetPlayer = StringArgumentType.getString(context, "targetPlayer");
                            var client = context.getSource().getClient();

                            if (client.player != null) {
                                MutableText menu = Text.literal("\n§7[MLMOD] Действия над §b" + targetPlayer + "§7: \n\n");

                                MutableText ignoreBtn;
                                boolean ignoredPlayersContains = ModConfig.INSTANCE.isIgnoredPlayersContains(targetPlayer);
                                if (!ignoredPlayersContains) {
                                    ignoreBtn = Text.literal("[").append(Text.translatable("text.mlmod_fork.player_interaction.ignoring")).append("]")
                                            .formatted(Formatting.RED)
                                            .styled(style -> style
                                                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mlmod ignore add " + targetPlayer)) // или ваша команда конфига
                                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("button.interaction_with_player.ignoring_add")))
                                            );
                                } else {
                                    ignoreBtn = Text.literal("[").append(Text.translatable("text.mlmod_fork.player_interaction.remove_ignore")).append("]")
                                            .formatted(Formatting.GREEN)
                                            .styled(style -> style
                                                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mlmod ignore remove " + targetPlayer)) // или ваша команда конфига
                                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("button.interaction_with_player.ignoring_remove")))
                                            );
                                }


                                MutableText msgBtn = Text.literal("[").append(Text.translatable("text.mlmod_fork.player_interaction.send_dm")).append("]")
                                        .formatted(Formatting.YELLOW)
                                        .styled(style -> style
                                                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + targetPlayer + " "))
                                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("button.interaction_with_player.send_dm")))
                                        );

                                MutableText friendBtn = Text.literal("[").append(Text.translatable("text.mlmod_fork.player_interaction.add_friend")).append("]")
                                        .formatted(Formatting.AQUA)
                                        .styled(style -> style
                                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f add " + targetPlayer))
                                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("button.interaction_with_player.add_friend")))
                                        );

                                MutableText reportBtn = Text.literal("[").append(Text.translatable("text.mlmod_fork.player_interaction.report")).append("]")
                                        .formatted(Formatting.RED)
                                        .styled(style -> style
                                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + targetPlayer))
                                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("button.interaction_with_player.report")))
                                        );

                                ModConfig config = ModConfig.INSTANCE;
                                if (config.playerInteractionIgnoring) {
                                    menu.append(ignoreBtn).append("   ");
                                }
                                if (config.playerInteractionAddFriend) {
                                    menu.append(friendBtn).append("   ");
                                }
                                if (config.playerInteractionSendDM) {
                                    menu.append(msgBtn).append("   ");
                                }
                                if (config.isPlayerInteractionReport) {
                                    menu.append(reportBtn).append("   ");
                                }
                                menu.append("\n");
                                client.player.sendMessage(menu, false);
                            }
                            return 1;
                        })

                )
        ));
    }

}
