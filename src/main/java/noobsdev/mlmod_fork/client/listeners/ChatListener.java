package noobsdev.mlmod_fork.client.listeners;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import noobsdev.mlmod_fork.client.Mlmod_forkClient;
import noobsdev.mlmod_fork.integrations.config.ModConfig;
import noobsdev.mlmod_fork.util.ChatMessageParser;
import org.jetbrains.annotations.Nullable;

public class ChatListener {

    public ChatListener() {
    }

    public void register() {

        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null)
                return true;

            ChatMessageParser parser = new ChatMessageParser();

            String rawText = message.getString();

            String player = parser.getNameByText(rawText);
            String clan = parser.getClanIDByText(rawText);
            String world = parser.getWorldID(message);


            if (ignoreWorlds(world, client))
                return false;

            if (ignoreClans(clan, client))
                return false;

            return !ignorePlayers(player, client);
        });


        ClientReceiveMessageEvents.MODIFY_GAME.register(
                (message, overlay) -> modifyMessage(message)
        );
    }

    private boolean ignoreClans(String clanID, MinecraftClient client) {
        if (clanID != null && ModConfig.INSTANCE.isClanIgnoreEnabled && ModConfig.INSTANCE.isIgnoredClansContains(clanID)) {

            if (ModConfig.INSTANCE.ignoreClansDebug) {
                assert client.player != null;
                client.player.sendMessage(Text.literal("§7§o[MLMOD] Заблокировано сообщение от клана: " + clanID), false);
            }

            Mlmod_forkClient.LOGGER.info("§7§o[MLMOD] 1Заблокировано сообщение от клана: {}", clanID);
            return true;
        }

        return false;
    }

    private boolean ignorePlayers(String fullPlayerName, MinecraftClient client) {
        if (fullPlayerName != null && ModConfig.INSTANCE.isIgnorePlayersEnabled && ModConfig.INSTANCE.isIgnoredPlayersContains(fullPlayerName)) {

            if (ModConfig.INSTANCE.ignorePlayersDebug) {
                assert client.player != null;
                client.player.sendMessage(Text.literal("§7§o[MLMOD] Заблокировано сообщение от: " + fullPlayerName), false);
            }

            Mlmod_forkClient.LOGGER.info("§7§o[MLMOD] Заблокировано сообщение от: {}", fullPlayerName);
            return true;
        }
        return false;
    }
    private boolean ignoreWorlds(String world, MinecraftClient client) {
        if (world == null)
            return false;
        if (!ModConfig.INSTANCE.isWorldIgnoreEnabled)
            return false;
        if (!ModConfig.INSTANCE.isIgnoredWorldsContains(world))
            return false;
        if (ModConfig.INSTANCE.ignoreWorldsDebug) {
            assert client.player != null;
            client.player.sendMessage(
                    Text.literal(
                            "[MLMOD] Мир "
                                    +world
                                    +" заблокирован"
                    ),
                    false
            );
        }
        return true;
    }

    private Text addIgnoreButton(Text text,String world) {

        return text.copy().append(
                Text.literal(" [Добавить в игнор]")
                        .formatted(Formatting.RED)
                        .styled(style->style
                                .withClickEvent(
                                        new ClickEvent.RunCommand(
                                                "/mlmod ignore_world add "
                                                        +world
                                        )
                                )
                                .withHoverEvent(
                                        new HoverEvent.ShowText(

                                                Text.literal(
                                                        "Добавить мир в игнор"
                                                )
                                        )
                                )
                        )
        );
    }


    private Text addPlayerMenu(Text text, String player) {
        return text.copy().styled(style -> style
                .withClickEvent(
                        new ClickEvent.RunCommand(
                                "/mlmod_internal_menu " + player
                        )
                )


                .withHoverEvent(
                        new HoverEvent.ShowText(
                                Text.literal(
                                        "§eОткрыть меню §b"+player
                                )
                        )
                )

        );
    }

    private Text modifyMessage(Text message) {

        String raw = message.getString();

        if (raw.contains("[MLMOD]"))
            return message;
        
        ChatMessageParser parser = new ChatMessageParser();

        String player = parser.getNameByText(raw);
        String world = parser.getWorldID(message);


        Text result = message;


        if (world != null)
            result = addIgnoreButton(result, world);


        if (player != null &&
                ModConfig.INSTANCE.isPlayerInteractionEnabled)
            result = addPlayerMenu(result, player);


        return result;
    }

}
