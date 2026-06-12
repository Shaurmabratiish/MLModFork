package noobsdev.mlmod_fork.client.listeners;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.client.Mlmod_forkClient;
import noobsdev.mlmod_fork.integrations.config.ModConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener {

    private static final Pattern MINECRAFT_NAME_PATTERN = Pattern.compile("\\b([a-zA-Z0-9_]{3,16})\\b");

    public static void register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            // ИСПРАВЛЕНО: Используем .getString() вместо .toString()
            String rawText = message.getString();

            // Проверяем, что игрок загрузился в мир
            if (MinecraftClient.getInstance().player == null) return true;

            // Извлекаем чистый ник через ваш парсер
            String playerName = extractPlayerName(rawText);

            if (playerName != null) {
                // Проверяем, находится ли найденный ник в черном списке вашего конфига
                if (isPlayerInConfig(playerName)) {
                    // ОТЛАДКА: выведет в чат, кого именно мы заблокировали
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("§c[MLMOD] Заблокировано сообщение от: " + playerName), false);

                    return true; // Полностью блокируем и скрываем сообщение
                }
            }

            return true; // Разрешаем показ всех остальных сообщений
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

        // 1. Отрезаем системные префиксы чатов в начале строки, чтобы они не мешали
        if (text.startsWith("Креатив-чат »") ||
                text.startsWith("Донат-чат »") ||
                text.startsWith("Система »") ||
                text.startsWith("Друзья »")) {
            int arrowIndex = text.indexOf("»");
            text = text.substring(arrowIndex + 1).trim();
        }

        // 2. Отсекаем само сообщение, оставляя только левую часть с ником и префиксами
        if (text.contains(":")) {
            text = text.split(":")[0].trim();
        } else if (text.contains("»")) {
            text = text.split("»")[0].trim();
        } else if (text.contains("...")) {
            text = text.split("\\.\\.\\.")[0].trim();
        }

        // Теперь в переменной text осталось что-то вроде "[VIP] Hero Mineland" или "▶ Mineland"
        // 3. Ищем самое последнее слово, которое подходит под правила ника Майнкрафт
        Matcher matcher = MINECRAFT_NAME_PATTERN.matcher(text);
        String lastFoundName = null;

        while (matcher.find()) {
            lastFoundName = matcher.group(1); // Запоминаем каждое совпадение
        }

        // Возвращаем самое последнее найденное слово (это гарантированно будет сам ник, а не элементы префиксов)
        return lastFoundName;
    }
}