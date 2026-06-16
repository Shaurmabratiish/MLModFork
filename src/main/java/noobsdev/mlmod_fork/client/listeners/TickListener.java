package noobsdev.mlmod_fork.client.listeners;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import noobsdev.mlmod_fork.integrations.config.ModConfig;

public class TickListener {

    private static final float DEFAULT_FLY_SPEED = 0.05F;

    public void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            ClientPlayerEntity player = client.player;

            if (player == null) {
                return;
            }

            float boost = (float) ModConfig.INSTANCE.flyBoost / 10;

            if (player.isCreative()
                    && player.getAbilities().flying
                    && client.options.sprintKey.isPressed()
                    && ModConfig.INSTANCE.isFlyBoostEnabled) {

                player.getAbilities().setFlySpeed(DEFAULT_FLY_SPEED * boost);

                Vec3d velocity = player.getVelocity();

                if (client.options.sneakKey.isPressed()) {
                    player.setVelocity(
                            velocity.add(0.0, -0.15D * boost, 0.0)
                    );
                }

                if (client.options.jumpKey.isPressed()) {
                    player.setVelocity(
                            player.getVelocity().add(0.0, 0.15D * boost, 0.0)
                    );
                }

            } else {
                if (player.getAbilities().getFlySpeed() != DEFAULT_FLY_SPEED) {
                    player.getAbilities().setFlySpeed(DEFAULT_FLY_SPEED);
                }
            }
        });
    }
}