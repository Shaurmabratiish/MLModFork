package noobsdev.mlmod_fork.util;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class PlayerCharacter {

    @Getter
    private ClientPlayerEntity player;

    public PlayerCharacter() {
    }

    public void register() {
        this.player = MinecraftClient.getInstance().player;
    }

}
