package noobsdev.mlmod_fork.api;

import lombok.Getter;
import lombok.Setter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static noobsdev.mlmod_fork.client.Mlmod_forkClient.LOGGER;

@Getter
@Setter
public abstract class Properties {

    private List<String> values;
    private String fileName;
    private File file;

    public Properties(String fileName) {
        this.fileName = fileName;
        this.file = getOrCreateFile();
        this.values = loadValues();
    }

    private List<String> loadValues() {
        if (file == null || !Files.exists(file.toPath())) {
            return new ArrayList<>();
        }
        try {
            return new ArrayList<>(Files.readAllLines(file.toPath()));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private File getOrCreateFile() {
        File gameDir = FabricLoader.getInstance().getGameDir().toFile();
        File myFolder = new File(gameDir, "mlmod_fork");

        if (!myFolder.exists()) {
            boolean ignored = myFolder.mkdirs();
        }

        File myFile = new File(myFolder, fileName);
        try {
            if (!myFile.exists()) {
                boolean ignored = myFile.createNewFile();
            }
            return myFile;
        } catch (IOException e) {
            return null;
        }
    }

    public void save() {
        if (file == null) return;
        try {
            Files.write(file.toPath(), values);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public void add(String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        if (this.values.contains(value)) {
            return;
        }

        this.values.add(value);
        this.save();
    }

    public void remove(String value) {
        if (value == null) {
            return;
        }

        boolean removed = this.values.remove(value);
        if (removed) {
            this.save();
        }
    }
}
