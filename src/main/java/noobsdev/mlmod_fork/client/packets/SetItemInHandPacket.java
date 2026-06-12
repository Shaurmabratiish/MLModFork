package noobsdev.mlmod_fork.client.packets;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

public class SetItemInHandPacket {

    public SetItemInHandPacket() {}

    public void send(ClientPlayerEntity player, int slot, ItemStack item) {
        player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + slot, item));
    }

}
