package noobsdev.mlmod_fork.client.keybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.client.packets.SetItemInHandPacket;
import noobsdev.mlmod_fork.integrations.config.ModConfig;
import org.lwjgl.glfw.GLFW;

public class ReverseReplaceTextKeybind extends Keybinder{
    public ReverseReplaceTextKeybind() {
        super(GLFW.GLFW_KEY_J, "keybind.mlmod_fork.reverse_replace_text", "keybind.mlmod_fork.title");
    }

    @Override
    public void bind() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        ItemStack item = client.player.getMainHandStack();
        int slot = client.player.getInventory().selectedSlot;
        if(!item.isEmpty()) {
            new SetItemInHandPacket().send(client.player, slot, parser(item));
        }
    }

    private ItemStack parser(ItemStack result) {

        String targetText = ModConfig.INSTANCE.sourceText;
        String sourceText = ModConfig.INSTANCE.targetText;

        if(result == null || !result.hasNbt()) return null;

        if(result.getName().contains(Text.of(targetText))) {
            result.setCustomName(Text.of(result.getName().getString().replace(sourceText, targetText)));
        }

        NbtCompound rootNbt = result.getOrCreateNbt();

        NbtCompound displayNbt;
        if (rootNbt.contains("display", 10)) {
            displayNbt = rootNbt.getCompound("display");
        } else { return null;}

        NbtCompound creativeNbt = null;

        if (rootNbt.contains("creative", 10)) {
            NbtCompound creative = rootNbt.getCompound("creative");
            if (creative.contains("value", 10)) {
                creativeNbt = creative.getCompound("value");
            }
        }

        if (creativeNbt == null) {
            return null;
        }
        if (displayNbt.contains("VV|Protocol1_12_2To1_13|Name")) {
            String text = displayNbt.getString("VV|Protocol1_12_2To1_13|Name");
            if (text.contains(sourceText)) {
                displayNbt.putString("VV|Protocol1_12_2To1_13|Name", text.replaceAll(sourceText, targetText));
            }
        }

        if (creativeNbt.contains("value")) {
            String text = creativeNbt.getString("value");
            if (text.contains(sourceText)) {
                creativeNbt.putString("value", text.replaceAll(sourceText, targetText));
            }
        }
        return result;
    }
}
