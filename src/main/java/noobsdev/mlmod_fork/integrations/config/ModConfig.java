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
    public List<String> ignoredPlayers = new ArrayList<>();
    public boolean isIgnorePlayersEnabled = false;
    public boolean ignorePlayersDebug = false;
    public boolean isPlayerInteractionEnabled = true;

    public boolean playerInteractionIgnoring = true;
    public boolean playerInteractionAddFriend = true;
    public boolean playerInteractionSendDM = true;

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
}
