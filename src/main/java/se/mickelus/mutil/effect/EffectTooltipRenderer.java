package se.mickelus.mutil.effect;

import com.mojang.blaze3d.platform.Window;
import io.github.fabricators_of_create.porting_lib.entity.client.MobEffectRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectTooltipRenderer implements MobEffectRenderer {
    private final Function<MobEffectInstance, String> constructEffectTooltip;

    public EffectTooltipRenderer(Function<MobEffectInstance, String> constructEffectTooltip) {
        this.constructEffectTooltip = constructEffectTooltip;
    }

    @Environment(EnvType.CLIENT)
    public static void renderInventoryEffectTooltip(GuiGraphics graphics, int x, int y, Supplier<Component> tooltip) {
        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();

        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        int mouseX = (int) (mc.mouseHandler.xpos() * width / window.getScreenWidth());
        int mouseY = (int) (mc.mouseHandler.ypos() * height / window.getScreenHeight());

        if (x < mouseX && mouseX < x + 120 && y < mouseY && mouseY < y + 32) {
            graphics.renderTooltip(mc.font, tooltip.get(), mouseX, mouseY);
        }
    }

    @Override
    public boolean renderInventoryIcon(final MobEffectInstance instance, final EffectRenderingInventoryScreen<?> screen,
            final GuiGraphics graphics, final int x, final int y, final int blitOffset) {
        renderInventoryEffectTooltip(graphics, x, y, () -> Component.literal(constructEffectTooltip.apply(instance)));
        return false;
    }
}
