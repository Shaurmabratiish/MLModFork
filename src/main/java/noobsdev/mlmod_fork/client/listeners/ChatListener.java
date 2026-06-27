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
            if (client.player == null) return true;



            if (message.getStyle() != null && message.getStyle().getClickEvent() != null) {
                String command = getString(message);
                if (command != null && (command.startsWith("/mlmod_internal_menu") || command.contains("mlmod_menu_marker"))) {
                    return true;
                }
            }

            String rawText = message.getString();
            if (rawText.contains("[MLMOD]")) return true;

            ChatMessageParser parser = new ChatMessageParser();

            String fullPlayerName = parser.getNameByText(rawText);
            String clanID = parser.getClanIDByText(rawText);
            String worldID = parser.getWorldID(message);

            if (ignoreWorlds(worldID, client, message)) return false;

            if (ignoreClans(clanID, client)) return false;

            if (ignorePlayers(fullPlayerName, client)) return false;

            return !interactionMenu(fullPlayerName, message, client);
        });
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

    private boolean interactionMenu(String fullPlayerName, Text message, MinecraftClient client) {
        if (ModConfig.INSTANCE.isPlayerInteractionEnabled && fullPlayerName != null) {
            Text modifiedMessage = message.copy().styled(style -> style
                    .withClickEvent(new ClickEvent.RunCommand("/mlmod_internal_menu " + fullPlayerName))
                    .withHoverEvent(new HoverEvent.ShowText(Text.literal("§eНажмите, чтобы открыть меню игрока §b" + fullPlayerName)))
            );
            assert client.player != null;
            client.player.sendMessage(modifiedMessage, false);
            return true;
        }
        return false;
    }

    private boolean ignoreWorlds(String worldID, MinecraftClient client, Text message) {
        if (worldID != null && ModConfig.INSTANCE.isWorldIgnoreEnabled && ModConfig.INSTANCE.isIgnoredWorldsContains(worldID)) {
            if (ModConfig.INSTANCE.ignoreWorldsDebug) {
                assert client.player != null;
                client.player.sendMessage(Text.literal("§7§o[MLMOD] Заблокирована реклама мира: " + worldID),false);
            }
            Mlmod_forkClient.LOGGER.info("§7§o[MLMOD] Заблокирована реклама мира: {}", worldID);
            return true;

        }
        if (worldID != null) {
            Text modified = addIgnoreButton(message, worldID);
            assert client.player != null;
            client.player.sendMessage(modified, false);
            return true;
        }

        return false;

    }

    private Text addIgnoreButton(Text original, String worldId) {
        return original.copy().append(
                Text.literal(" [Добавить в игнор]")
                        .styled(style -> style
                                .withFormatting(Formatting.RED)
                                .withClickEvent(new ClickEvent.RunCommand(
                                        "/mlmod ignore_world add " + worldId
                                ))
                                .withHoverEvent(new HoverEvent.ShowText(
                                        Text.literal("Добавить мир в игнор")
                                ))
                        )
        );
    }

    public static @Nullable String getString(Text message) {
        ClickEvent clickEvent = message.getStyle().getClickEvent();

        return switch (clickEvent) {
            case ClickEvent.RunCommand caseEvent -> caseEvent.command();
            case ClickEvent.SuggestCommand caseEvent -> caseEvent.command();
            case ClickEvent.OpenUrl caseEvent -> String.valueOf(caseEvent.uri());
            case ClickEvent.CopyToClipboard caseEvent -> caseEvent.value();
            case ClickEvent.OpenFile caseEvent -> caseEvent.path();
            case null, default -> null;
        };
    }

}
