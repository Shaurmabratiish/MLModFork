package noobsdev.mlmod_fork.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import noobsdev.mlmod_fork.integrations.config.ModConfig;
import noobsdev.mlmod_fork.util.Development;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class GuiGraphicsMixin {
    @Shadow
    public abstract int drawText(TextRenderer textRenderer, @Nullable Text text, int x, int y, int color, boolean shadow);

    @Shadow
    public abstract void fill(RenderLayer layer, int startX, int startY, int endX, int endY, int color);

    @Inject(
            method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At("TAIL")
    )
    public void renderItemDecorations(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {

        if (stack == null || stack.isEmpty()) return;

        if (!Development.isItemContainsCreativeTag(stack)) return;

        Development.VarInstance varType = Development.getVarType(stack);
        if (varType == null) return;

        if (!ModConfig.INSTANCE.isDecoratorsEnabled || !varType.type().isEnabled()) return;

        String rawText = varType.value();
        String textStr = Formatting.strip(rawText);
        int color = varType.type().getColor();

        int charLimit = varType.type().getCharLimit();

        if (textStr != null && textStr.length() > charLimit) {
            textStr = textStr.substring(0, charLimit);
        } else if (textStr == null) {
            textStr = "NULL";
            color = 0xFF0000;
        }

        DrawContext context = (DrawContext) (Object) this;
        float scale = ModConfig.INSTANCE.DecoratorsSize;

        context.getMatrices().push();

        context.getMatrices().translate(x, y, 200.0F);

        context.getMatrices().scale(scale, scale, 1.0f);

        int textWidth = textRenderer.getWidth(textStr);

        context.fill(RenderLayer.getGui(), -4, -2, -3 + textWidth, textRenderer.fontHeight, 0x88000000);
        context.drawText(textRenderer, Text.literal(textStr), -3, -1, color, true);

        context.getMatrices().pop();
    }
}
