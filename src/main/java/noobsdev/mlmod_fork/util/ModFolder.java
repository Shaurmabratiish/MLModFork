package noobsdev.mlmod_fork.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import noobsdev.mlmod_fork.client.Mlmod_forkClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModFolder {

    private final File modDirectory;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ModFolder() {
        File gameDir = FabricLoader.getInstance().getGameDir().toFile();
        this.modDirectory = new File(gameDir, "mlmod");
        createModFolder();
    }

    private void createModFolder() {
        if (!modDirectory.exists()) {
            boolean success = modDirectory.mkdirs();
            if (success) {
                Mlmod_forkClient.LOGGER.info("Папка успешно создана по пути: {}", modDirectory.getAbsolutePath());
            } else {
                Mlmod_forkClient.LOGGER.error("Не удалось создать папку: {}", modDirectory.getAbsolutePath());
            }
        }
    }

    public File getOrCreateFile(String fileName) {
        File file = new File(modDirectory, fileName);
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (file.createNewFile()) {
                Mlmod_forkClient.LOGGER.info("Файл успешно создан: {}", file.getAbsolutePath());
            } else {
                Mlmod_forkClient.LOGGER.info("Файл уже существует: {}", file.getAbsolutePath());
            }
            return file;
        } catch (IOException e) {
            Mlmod_forkClient.LOGGER.error("Не удалось создать файл: {}", fileName, e);
            return null;
        }
    }

    public Map<String, CodeCommentData> readJson(String fileName) {
        File file = getOrCreateFile(fileName);
        if (file == null || file.length() == 0) {
            return new HashMap<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, CodeCommentData>>() {}.getType();
            Map<String, CodeCommentData> data = gson.fromJson(reader, type);

            return data != null ? data : new HashMap<>();
        } catch (IOException e) {
            Mlmod_forkClient.LOGGER.error("Ошибка при чтении JSON файла: " + fileName, e);
            return new HashMap<>();
        }
    }

    public void writeJson(String fileName, Map<String, CodeCommentData> data) {
        File file = getOrCreateFile(fileName);
        if (file == null) return;

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
            Mlmod_forkClient.LOGGER.info("Данные успешно сохранены в файл: {}", file.getName());
        } catch (IOException e) {
            Mlmod_forkClient.LOGGER.error("Ошибка при записи JSON файла: {}", fileName, e);
        }
    }

    public List<File> searchFiles(String query) {
        List<File> result = new ArrayList<>();
        File[] files = modDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().contains(query)) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    public static class CodeCommentData {
        public String Location;
        public String WorldID;
        public String Name;

        public CodeCommentData() {}

        public CodeCommentData(String location, String worldID, String name) {
            this.Location = location;
            this.WorldID = worldID;
            this.Name = name;
        }
    }

}
