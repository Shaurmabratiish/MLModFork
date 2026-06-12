package noobsdev.mlmod_fork.client.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.List;

public class RegisterKeybind {


    public List<Keybinder> binds;

    public RegisterKeybind() {
        this.binds = new ArrayList<>();
        ticker();
    }

    public void add(Keybinder bind) {
        if (!binds.contains(bind)) this.binds.add(bind);
    }

    private void ticker() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            for (Keybinder keyBinding : binds) {
                if (keyBinding.getBind().wasPressed()) {
                    keyBinding.bind();
                    break;
                }
            }
        });
    }

}
