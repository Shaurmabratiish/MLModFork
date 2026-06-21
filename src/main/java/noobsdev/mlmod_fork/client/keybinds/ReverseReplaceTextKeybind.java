package noobsdev.mlmod_fork.client.keybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.client.packets.SetItemInHandPacket;
import noobsdev.mlmod_fork.integrations.config.ModConfig;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class ReverseReplaceTextKeybind extends Keybinder{
    public ReverseReplaceTextKeybind() {
        super(GLFW.GLFW_KEY_J, "keybind.mlmod_fork.reverse_replace_text", "keybind.mlmod_fork.title");
    }

    @Override
    public void bind() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ItemStack item = client.player.getMainHandStack();
        int slot = client.player.getInventory().getSelectedSlot();

        if (!item.isEmpty()) {
            ItemStack parsedItem = parser(item);
            if (parsedItem != null) {
                new SetItemInHandPacket().send(client.player, slot, parsedItem);
            }
        }
    }

    private ItemStack parser(ItemStack item) {
        String targetText = ModConfig.INSTANCE.sourceText;
        String sourceText = ModConfig.INSTANCE.targetText;

        ItemStack result = item.copy();

        Text customName = result.get(DataComponentTypes.CUSTOM_NAME);
        if (customName != null && customName.getString().contains(sourceText)) {
            String newNameStr = customName.getString().replace(sourceText, targetText);
            result.set(DataComponentTypes.CUSTOM_NAME, Text.literal(newNameStr));
        }

        NbtComponent customDataComponent = result.get(DataComponentTypes.CUSTOM_DATA);
        if (customDataComponent == null) return null;

        Optional<NbtCompound> optionalNbt = customDataComponent.copyNbt().asCompound();
        if (optionalNbt.isEmpty()) return null;

        NbtCompound rootNbt = optionalNbt.get();
        boolean modified = false;

        if (rootNbt.contains("display")) {
            NbtCompound displayNbt = rootNbt.getCompoundOrEmpty("display");
            if (displayNbt.contains("VV|Protocol1_12_2To1_13|Name")) {
                String text = displayNbt.getString("VV|Protocol1_12_2To1_13|Name", null);
                if (text != null && text.contains(sourceText)) {
                    displayNbt.putString("VV|Protocol1_12_2To1_13|Name", text.replace(sourceText, targetText));
                    modified = true;
                }
            }
        }

        if (rootNbt.contains("creative")) {
            NbtCompound creative = rootNbt.getCompoundOrEmpty("creative");
            if (creative.contains("value")) {
                NbtCompound creativeValue = creative.getCompoundOrEmpty("value");

                if (creativeValue.contains("value")) {
                    String text = creativeValue.getString("value", null);
                    if (text != null && text.contains(sourceText)) {
                        creativeValue.putString("value", text.replace(sourceText, targetText));
                        modified = true;
                    }
                }
            }
        }

        if (modified) {
            result.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(rootNbt));
            return result;
        }

        return null;
    }
}
