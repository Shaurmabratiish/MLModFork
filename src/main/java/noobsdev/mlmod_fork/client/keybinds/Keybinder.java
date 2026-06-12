package noobsdev.mlmod_fork.client.keybinds;

import lombok.Getter;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public abstract class Keybinder {
    @Getter
    public KeyBinding bind;

    private final int key;
    private final String localeKey;
    private final String category;

    protected Keybinder(int key, String localeKey, String category) {
        this.key = key;
        this.localeKey = localeKey;
        this.category = category;

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
