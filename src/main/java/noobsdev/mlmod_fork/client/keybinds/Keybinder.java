package noobsdev.mlmod_fork.client.keybinds;

import lombok.Getter;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public abstract class Keybinder {
    private static final Map<String, KeyBinding.Category> CATEGORIES = new HashMap<>();

    @Getter
    public KeyBinding bind;

    private final int key;
    private final String localeKey;
    private final KeyBinding.Category category;

    protected Keybinder(int key, String localeKey, String categoryName) {
        this.key = key;
        this.localeKey = localeKey;

        this.category = CATEGORIES.computeIfAbsent(categoryName, name ->
                KeyBinding.Category.create(Identifier.of(name))
        );

        register();
    }

    private void register() {
        this.bind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                this.localeKey,
                InputUtil.Type.KEYSYM,
                this.key,
                this.category
        ));
    }

    public abstract void bind();
}