package noobsdev.mlmod_fork.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import noobsdev.mlmod_fork.integrations.config.ModConfig;
import org.jetbrains.annotations.Nullable;

public class Development {

    public static boolean isItemContainsCreativeTag(ItemStack item) {
        if (item == null || item.isEmpty()) return false;

        NbtComponent nbtComponent = item.get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) return false;

        NbtCompound rootNbt = nbtComponent.copyNbt();

        boolean value = false;

        if (rootNbt.contains("creative")) {
            NbtCompound creative = rootNbt.getCompoundOrEmpty("creative");
            if (creative.contains("value")) {
                NbtCompound creativeNbt = creative.getCompoundOrEmpty("value");
                value = creativeNbt.contains("value");
            }
        }

        return value;
    }

    public static VarInstance getVarType(ItemStack item) {
        if (item == null || item.isEmpty()) return null;

        NbtComponent nbtComponent = item.get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) return null;

        NbtCompound rootNbt = nbtComponent.copyNbt();

        if (isItemContainsCreativeTag(item)) {
            NbtCompound creativeValue = rootNbt.getCompoundOrEmpty("creative").getCompoundOrEmpty("value");
            String varType = creativeValue.getString("var_type", null);

            if (!creativeValue.contains("value")) return null;

            String value = creativeValue.getString("value", null);

            VarType type = VarType.fromString(varType);
            if (type == null) return null;

            return new VarInstance(type, value);
        }

        return null;
    }

    public enum VarType {
        TEXT,
        NUMBER,
        LOCATION,
        POTION,
        DYNAMIC_VARIABLE,
        GAME_VALUE,
        PARTICLE,
        TEXT_COMPONENT,
        VECTOR;

        public int getColor() {
            return switch (this) {
                case TEXT -> parseHexToInt(ModConfig.INSTANCE.textDecoratorColor);
                case NUMBER -> parseHexToInt(ModConfig.INSTANCE.numDecoratorColor);
                case LOCATION -> parseHexToInt(ModConfig.INSTANCE.locationDecoratorColor);
                case POTION -> parseHexToInt(ModConfig.INSTANCE.potionDecoratorColor);
                case DYNAMIC_VARIABLE -> parseHexToInt(ModConfig.INSTANCE.varDecoratorColor);
                case GAME_VALUE -> parseHexToInt(ModConfig.INSTANCE.gameValueDecoratorColor);
                case PARTICLE -> parseHexToInt(ModConfig.INSTANCE.particleDecoratorColor);
                case TEXT_COMPONENT -> parseHexToInt(ModConfig.INSTANCE.textComponentDecoratorColor);
                case VECTOR -> parseHexToInt(ModConfig.INSTANCE.vectorDecoratorColor);
            };
        }

        public boolean isEnabled() {
            return switch (this) {
                case TEXT -> ModConfig.INSTANCE.isTextDecoratorEnabled;
                case NUMBER -> ModConfig.INSTANCE.isNumDecoratorEnabled;
                case LOCATION -> ModConfig.INSTANCE.isLocationDecoratorEnabled;
                case POTION -> ModConfig.INSTANCE.isPotionDecoratorEnabled;
                case DYNAMIC_VARIABLE -> ModConfig.INSTANCE.isVarDecoratorEnabled;
                case GAME_VALUE -> ModConfig.INSTANCE.isGameValueDecoratorEnabled;
                case PARTICLE -> ModConfig.INSTANCE.isParticleDecoratorEnabled;
                case TEXT_COMPONENT -> ModConfig.INSTANCE.isTextComponentDecoratorEnabled;
                case VECTOR -> ModConfig.INSTANCE.isVectorDecoratorEnabled;
            };
        }

        public int getCharLimit() {
            return switch (this) {
                case TEXT -> ModConfig.INSTANCE.textDecoratorCharLimit;
                case NUMBER -> ModConfig.INSTANCE.numDecoratorCharLimit;
                case LOCATION -> ModConfig.INSTANCE.locationDecoratorCharLimit;
                case POTION -> ModConfig.INSTANCE.potionDecoratorCharLimit;
                case DYNAMIC_VARIABLE -> ModConfig.INSTANCE.varDecoratorCharLimit;
                case GAME_VALUE -> ModConfig.INSTANCE.gameValueDecoratorCharLimit;
                case PARTICLE -> ModConfig.INSTANCE.particleDecoratorCharLimit;
                case TEXT_COMPONENT -> ModConfig.INSTANCE.textComponentDecoratorCharLimit;
                case VECTOR -> ModConfig.INSTANCE.vectorDecoratorCharLimit;
            };
        }

        public static @Nullable VarType fromString(String name) {
            try {
                return VarType.valueOf(name);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public static int parseHexToInt(String hexStr) {
        int defaultColor = 0xFF0000;
        if (hexStr == null || hexStr.isEmpty()) {
            return defaultColor;
        }
        try {
            String cleanHex = hexStr.replace("#", "").trim();
            return Integer.parseInt(cleanHex, 16);
        } catch (NumberFormatException e) {
            return defaultColor;
        }
    }

    public record VarInstance(VarType type, String value) {}
}
