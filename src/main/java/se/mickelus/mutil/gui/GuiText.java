package se.mickelus.mutil.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class GuiText extends GuiElement {

    Font fontRenderer;

    String string;
    int color = 0xffffff;

    public GuiText(int x, int y, int width, String string) {
        super(x, y, width ,0);

        fontRenderer = Minecraft.getInstance().font;
        setString(string);
    }

    public void setString(String string) {
        this.string = string.replace("\\n", "\n");

        height = fontRenderer.wordWrapHeight(this.string, width);
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void draw(final GuiGraphics graphics, int refX, int refY, int screenWidth, int screenHeight, int mouseX, int mouseY, float opacity) {
        renderText(graphics, fontRenderer, string, refX + x, refY + y, width, color, opacity);

        super.draw(graphics, refX, refY, screenWidth, screenHeight, mouseX, mouseY, opacity);
    }

    protected static void renderText(GuiGraphics graphics, Font fontRenderer, String string, int x, int y, int width, int color, float opacity) {
        // todo 1.20: font rendering changed, test that it works
        List<FormattedCharSequence> list = fontRenderer.split(Component.literal(string), width);
        for(FormattedCharSequence line : list) {
            graphics.drawString(fontRenderer, line, x, y, colorWithOpacity(color, opacity));
            y += 9;
//            float lineX = (float) x;
//            MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
//                        if (fontRenderer.getBidiFlag()) {
//                int i = fontRenderer.getStringWidth(fontRenderer.bidiReorder(line.));
//                lineX += (float)(width - i);
//            }
//            fontRenderer.drawInBatch(line, lineX, (float)y, colorWithOpacity(color, opacity), true, matrix, buffer, false, 0, 15728880);
//            buffer.endBatch();
        }
    }
}
