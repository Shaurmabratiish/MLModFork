package noobsdev.mlmod_fork.client.commands;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.integrations.config.ModConfig;

import java.util.ArrayList;

public class IgnoreCommand extends MLModCommand{

    // TODO: перевод
    @Override
    public void run(FabricClientCommandSource ctx) {
    }

    public void addPlayer(FabricClientCommandSource ctx, String target) {
        var player = ctx.getClient().player;
        if (player == null) return;

        if (ModConfig.INSTANCE.ignoringPlayers == null) {
            ModConfig.INSTANCE.ignoringPlayers = new ArrayList<>();
        }

        boolean alreadyContains = ModConfig.INSTANCE.ignoringPlayers.stream()
                .anyMatch(p -> p.equalsIgnoreCase(target));

        if (alreadyContains) {
            player.sendMessage(Text.literal("§c[MLMOD] Игрок §b" + target + " §cуже находится в игноре."), false);
        } else {
            ModConfig.INSTANCE.ignoringPlayers.add(target);
            player.sendMessage(Text.literal("§a[MLMOD] Игрок §b" + target + " §aуспешно добавлен в игнор."), false);
            ModConfig.INSTANCE.save();
        }
    }

    public void removePlayer(FabricClientCommandSource ctx, String target) {
        var player = ctx.getClient().player;
        if (player == null) return;

        if (ModConfig.INSTANCE.ignoringPlayers == null || ModConfig.INSTANCE.ignoringPlayers.isEmpty()) {
            player.sendMessage(Text.literal("§c[MLMOD] Список игнорируемых игроков пуст."), false);
            return;
        }

        boolean removed = ModConfig.INSTANCE.ignoringPlayers.removeIf(p -> p.equalsIgnoreCase(target));
        if (removed) {
            player.sendMessage(Text.literal("§a[MLMOD] Игрок §b" + target + " §aудален из игнора."), false);
            ModConfig.INSTANCE.save();
        } else {
            player.sendMessage(Text.literal("§c[MLMOD] Игрок §b" + target + " §cне найден в списке игнора."), false);
        }
    }

    public void addWorld(FabricClientCommandSource ctx, String target) {
        var player = ctx.getClient().player;
        if (player == null) return;

        if (ModConfig.INSTANCE.worldsIgnoring == null) {
            ModConfig.INSTANCE.worldsIgnoring = new ArrayList<>();
        }

        boolean alreadyContains = ModConfig.INSTANCE.worldsIgnoring.stream()
                .anyMatch(p -> p.equalsIgnoreCase(target));

        if (alreadyContains) {
            player.sendMessage(Text.literal("§c[MLMOD] Мир §b" + target + " §cуже находится в игноре."), false);
        } else {
            ModConfig.INSTANCE.worldsIgnoring.add(target);
            player.sendMessage(Text.literal("§a[MLMOD] Мир §b" + target + " §aуспешно добавлен в игнор."), false);
            ModConfig.INSTANCE.save();
        }
    }

}
