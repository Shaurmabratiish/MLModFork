package noobsdev.mlmod_fork.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
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
    public abstract void fill(int startX, int startY, int endX, int endY, int color);

    @Inject(
            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At("TAIL")
    )
    public void renderItemDecorations(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {

        if (stack == null || stack.isEmpty()) return;

        if(!Development.isItemContainsCreativeTag(stack)) return;

        Development.VarInstance varType = Development.getVarType(stack);
        if(varType == null) return;

        String rawText = varType.value();
        String textStr = net.minecraft.util.Formatting.strip(rawText);
        int color = 0xFFFFFF;

        if (textStr != null && textStr.length() > 3) {
            textStr = textStr.substring(0, 3);
        } else if (textStr == null) {
            textStr = "NULL";
            color = 0xFF0000;
        }
        
        DrawContext context = (DrawContext) (Object) this;

        context.getMatrices().push();
        context.getMatrices().translate(0, 0, 200.0F);


        fill(x - 4, y - 2, x - 3 + textRenderer.getWidth(textStr), y + textRenderer.fontHeight, 0x88000000);
        drawText(textRenderer, Text.literal(textStr), x - 3, y - 1, color, true);

        context.getMatrices().pop();
    }
}
