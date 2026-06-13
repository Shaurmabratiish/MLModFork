package noobsdev.mlmod_fork.client.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.integrations.config.ModConfig;

import java.util.ArrayList;

public class IgnoreCommand extends MLModCommand{

    // TODO: перевод
    @Override
    public void run(FabricClientCommandSource ctx) {
    }

    public void add(FabricClientCommandSource ctx, String target) {
        var player = ctx.getClient().player;
        if (player == null) return;

        if (ModConfig.INSTANCE.ignoredPlayers == null) {
            ModConfig.INSTANCE.ignoredPlayers = new ArrayList<>();
        }

        boolean alreadyContains = ModConfig.INSTANCE.ignoredPlayers.stream()
                .anyMatch(p -> p.equalsIgnoreCase(target));

        if (alreadyContains) {
            player.sendMessage(Text.literal("§c[MLMOD] Игрок §b" + target + " §cуже находится в игноре."), false);
        } else {
            ModConfig.INSTANCE.ignoredPlayers.add(target);
            player.sendMessage(Text.literal("§a[MLMOD] Игрок §b" + target + " §aуспешно добавлен в игнор."), false);
            ModConfig.INSTANCE.save();
        }
    }

    public void remove(FabricClientCommandSource ctx, String target) {
        var player = ctx.getClient().player;
        if (player == null) return;

        if (ModConfig.INSTANCE.ignoredPlayers == null || ModConfig.INSTANCE.ignoredPlayers.isEmpty()) {
            player.sendMessage(Text.literal("§c[MLMOD] Список игнорируемых игроков пуст."), false);
            return;
        }

        boolean removed = ModConfig.INSTANCE.ignoredPlayers.removeIf(p -> p.equalsIgnoreCase(target));
        if (removed) {
            player.sendMessage(Text.literal("§a[MLMOD] Игрок §b" + target + " §aудален из игнора."), false);
            ModConfig.INSTANCE.save();
        } else {
            player.sendMessage(Text.literal("§c[MLMOD] Игрок §b" + target + " §cне найден в списке игнора."), false);
        }
    }

}
