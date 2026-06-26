package noobsdev.mlmod_fork.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class Development {

    public static boolean isItemContainsCreativeTag(ItemStack item) {
        if(item == null || !item.hasNbt()) return false;
        NbtCompound rootNbt = item.getOrCreateNbt();

        boolean value = false;

        if (rootNbt.contains("creative", 10)) {
            NbtCompound creative = rootNbt.getCompound("creative");
            if (creative.contains("value", 10)) {
                NbtCompound creativeNbt = creative.getCompound("value");
                value = creativeNbt.contains("value");
            }
        }

        return value;
    }

    public static VarInstance getVarType(ItemStack item) {

        if(isItemContainsCreativeTag(item)) {
            String varType = item.getOrCreateNbt().getCompound("creative").getCompound("value").getString("var_type");

            if (!item.getOrCreateNbt().getCompound("creative").getCompound("value").contains("value")) return null;

            String value = item.getOrCreateNbt().getCompound("creative").getCompound("value").getString("value");

            VarType type = VarType.fromString(varType);
            if (type == null) return null;

            return new VarInstance(type, value);
        }

        return null;

    }

    public enum VarType {
        TEXT, NUMBER, LOCATION, POTION, DYNAMIC_VARIABLE,
        GAVE_VALUE, PARTICLE, TEXT_COMPONENT, VECTOR;

        public static @Nullable VarType fromString(String name) {
            try {
                return VarType.valueOf(name);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public record VarInstance(VarType type, String value) {}

}
