package noobsdev.mlmod_fork.client.listeners;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
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

            String fullPlayerName = new ChatMessageParser().getNameByText(rawText);
            String clanID = new ChatMessageParser().getClanIDByText(rawText);

            if (clanID != null && ModConfig.INSTANCE.isClanIgnoreEnabled && ModConfig.INSTANCE.isIgnoredClansContains(clanID)) {

                if (ModConfig.INSTANCE.ignoreClansDebug) {
                    client.player.sendMessage(Text.literal("§c[MLMOD] Заблокировано сообщение от клана: " + clanID), false);
                }

                Mlmod_forkClient.LOGGER.info("§c[MLMOD] 1Заблокировано сообщение от клана: {}", clanID);
                return false;
            }

            if (fullPlayerName != null && ModConfig.INSTANCE.isIgnorePlayersEnabled && ModConfig.INSTANCE.isIgnoredPlayersContains(fullPlayerName)) {

                if (ModConfig.INSTANCE.ignorePlayersDebug) {
                    client.player.sendMessage(Text.literal("§c[MLMOD] Заблокировано сообщение от: " + fullPlayerName), false);
                }

                Mlmod_forkClient.LOGGER.info("§c[MLMOD] Заблокировано сообщение от: {}", fullPlayerName);
                return false;
            }

            if (ModConfig.INSTANCE.isPlayerInteractionEnabled && fullPlayerName != null) {
                Text modifiedMessage = message.copy().styled(style -> style
                        .withClickEvent(new ClickEvent.RunCommand("/mlmod_internal_menu " + fullPlayerName))
                        .withHoverEvent(new HoverEvent.ShowText(Text.literal("§eНажмите, чтобы открыть меню игрока §b" + fullPlayerName)))
                );
                client.player.sendMessage(modifiedMessage, false);
                return false;
            }

            return true;
        });
    }

    private static @Nullable String getString(Text message) {
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
