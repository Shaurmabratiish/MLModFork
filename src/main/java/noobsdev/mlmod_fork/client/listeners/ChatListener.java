package noobsdev.mlmod_fork.client.listeners;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.client.Mlmod_forkClient;
import noobsdev.mlmod_fork.integrations.config.ModConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener {

    private static final Pattern MINECRAFT_NAME_PATTERN = Pattern.compile("\\b([a-zA-Z0-9_]{3,16})\\b");

    public static void register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return true;

            // 1. ЖЕСТКАЯ ЗАЩИТА ОТ РЕКУРСИИ И БАГОВ МЕНЮ:
            // Если в сообщении (или в любом его куске) уже привязана наша команда — СРАЗУ пропускаем
            if (message.getStyle() != null && message.getStyle().getClickEvent() != null) {
                String command = message.getStyle().getClickEvent().getValue();
                if (command != null && (command.startsWith("/mlmod_internal_menu") || command.contains("mlmod_menu_marker"))) {
                    return true;
                }
            }

            String rawText = message.getString();

            // Дополнительная проверка по тексту (учитываем возможный \n в начале)
            if (rawText.contains("[MLMOD]")) return true;

            String fullPlayerName = extractPlayerName(rawText);

            if (fullPlayerName != null) {
                if (ModConfig.INSTANCE.isIgnorePlayersEnabled) {
                    if (isPlayerInConfig(fullPlayerName)) {
                        if (ModConfig.INSTANCE.ignorePlayersDebug) {
                            client.player.sendMessage(Text.literal("§c[MLMOD] Заблокировано сообщение от: " + fullPlayerName));
                        }
                        Mlmod_forkClient.LOGGER.info("§c[MLMOD] Заблокировано сообщение от: {}", fullPlayerName);
                        return false;
                    }
                }

                if (ModConfig.INSTANCE.isPlayerInteractionEnabled) {
                    // Создаем модифицированную копию сообщения с клик-ивентом
                    Text modifiedMessage = message.copy().styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mlmod_internal_menu " + fullPlayerName))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("§eНажмите, чтобы открыть меню игрока §b" + fullPlayerName)))
                    );

                    // Отправляем новое модифицированное сообщение
                    client.player.sendMessage(modifiedMessage, false);
                    return false; // Блокируем оригинальное
                }
            }

            return true;
        });
    }

    private static boolean isPlayerInConfig(String chatName) {
        ModConfig config = ModConfig.INSTANCE;
        if (config.ignoredPlayers == null || config.ignoredPlayers.isEmpty()) return false;

        for (String configuredWorld : config.ignoredPlayers) {
            if (chatName.equalsIgnoreCase(configuredWorld) ||
                    chatName.toLowerCase().contains(configuredWorld.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String extractPlayerName(String rawText) {
        if (rawText == null || rawText.isEmpty()) return null;

        String text = rawText.trim();

        if (text.startsWith("Креатив-чат »") ||
                text.startsWith("Донат-чат »") ||
                text.startsWith("Система »") ||
                text.startsWith("Donate-Chat »") ||
                text.startsWith("Друзья »")) {
            int arrowIndex = text.indexOf("»");
            if (arrowIndex != -1) {
                text = text.substring(arrowIndex + 1).trim();
            }
        }

        if (text.contains(":")) {
            text = text.split(":")[0].trim();
        } else if (text.contains("»")) {
            text = text.split("»")[0].trim();
        } else if (text.contains("...")) {
            text = text.split("\\.\\.\\.")[0].trim();
        }

        Matcher matcher = MINECRAFT_NAME_PATTERN.matcher(text);
        String lastFoundName = null;

        while (matcher.find()) {
            lastFoundName = matcher.group(1);
        }

        return lastFoundName;
    }
}
