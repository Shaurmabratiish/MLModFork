package noobsdev.mlmod_fork.integrations.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static noobsdev.mlmod_fork.client.Mlmod_forkClient.LOGGER;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "mlmod_fork-config.json");


    public boolean isTextReplaceEnabled = true;
    public String sourceText = "%player%";
    public String targetText = "%selected%";
    public boolean reverseMode = true;

    public List<String> ignoringPlayers = new ArrayList<>();
    public boolean isIgnorePlayersEnabled = false;
    public boolean ignorePlayersDebug = false;

    public boolean isPlayerInteractionEnabled = true;
    public boolean playerInteractionIgnoring = true;
    public boolean playerInteractionAddFriend = true;
    public boolean playerInteractionSendDM = true;
    public boolean isPlayerInteractionReport = true;

    public List<String> clansIgnoring = new ArrayList<>();
    public boolean isClanIgnoreEnabled = false;
    public boolean ignoreClansDebug = false;

    public boolean isFlyBoostEnabled = false;
    public int flyBoost = 10;

    public List<String> worldsIgnoring = new ArrayList<>();
    public boolean isWorldIgnoreEnabled = false;
    public boolean ignoreWorldsDebug = false;


    // -----------------Декораторы------------------------

    public boolean isDecoratorsEnabled = false;
    public float DecoratorsSize = 1.0f;

    public String textDecoratorColor = "#55FFFF";
    public boolean isTextDecoratorEnabled = true;
    public int textDecoratorCharLimit = 3;

    public String numDecoratorColor = "#FF5555";
    public boolean isNumDecoratorEnabled = true;
    public int numDecoratorCharLimit = 3;

    public String locationDecoratorColor = "#55FF55";
    public boolean isLocationDecoratorEnabled = true;
    public int locationDecoratorCharLimit = 3;

    public String potionDecoratorColor = "#AA00AA";
    public boolean isPotionDecoratorEnabled = true;
    public int potionDecoratorCharLimit = 3;

    public String varDecoratorColor = "#FF5555";
    public boolean isVarDecoratorEnabled = true;
    public int varDecoratorCharLimit = 3;

    public String gameValueDecoratorColor = "#55FF55";
    public boolean isGameValueDecoratorEnabled = true;
    public int gameValueDecoratorCharLimit = 3;

    public String particleDecoratorColor = "#55FF55";
    public boolean isParticleDecoratorEnabled = true;
    public int particleDecoratorCharLimit = 3;

    public String textComponentDecoratorColor = "#5555FF";
    public boolean isTextComponentDecoratorEnabled = true;
    public int textComponentDecoratorCharLimit = 3;

    public String vectorDecoratorColor = "#00AAAA";
    public boolean isVectorDecoratorEnabled = true;
    public int vectorDecoratorCharLimit = 3;


    public static ModConfig INSTANCE = load();

    public static ModConfig load() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                ModConfig config = GSON.fromJson(reader, ModConfig.class);
                return config != null ? config : new ModConfig();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        return new ModConfig();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public boolean isIgnoredPlayersContains(String name) {
        if (ModConfig.INSTANCE.ignoringPlayers == null || ModConfig.INSTANCE.ignoringPlayers.isEmpty()) return false;

        for (String ignoredPlayers : ModConfig.INSTANCE.ignoringPlayers) {
            if (name.contains(ignoredPlayers) ||
                    name.toLowerCase().contains(ignoredPlayers.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isIgnoredClansContains(String name) {
        if (clansIgnoring == null || clansIgnoring.isEmpty()) return false;

        for (String clansIgnoring : clansIgnoring) {
            if (name.contains(clansIgnoring) ||
                    name.toLowerCase().contains(clansIgnoring.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isIgnoredWorldsContains (String name) {
        if (worldsIgnoring == null || worldsIgnoring.isEmpty()) return false;

        for (String worldsIgnoring : worldsIgnoring) {
            if (name.contains(worldsIgnoring) ||
                    name.toLowerCase().contains(worldsIgnoring.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
