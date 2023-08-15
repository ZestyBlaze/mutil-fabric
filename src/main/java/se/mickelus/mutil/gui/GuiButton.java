package se.mickelus.mutil.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import se.mickelus.mutil.gui.impl.GuiColors;

import java.util.Collections;
import java.util.List;

public class GuiButton extends GuiClickable {
    private final GuiStringOutline textElement;

    private boolean enabled = true;
    private Component disabledTooltip;

    public GuiButton(int x, int y, int width, int height, String text, Runnable onClick) {
        super(x, y, width, height, onClick);

        textElement = new GuiStringOutline(0, (height - 8) / 2, text);
        addChild(textElement);
    }

    public GuiButton(int x, int y, String text, Runnable onClick) {
        this(x, y, Minecraft.getInstance().font.width(text), 10, text, onClick);
    }

    public GuiButton(int x, int y, int width, int height, String text, Runnable onClick, Component disabledTooltip) {
        this(x, y, width, height, text, onClick);

        this.disabledTooltip = disabledTooltip;
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) {
        return enabled && super.onMouseClick(x, y, button);
    }

    private void updateColor() {
        if (!enabled) {
            textElement.setColor(GuiColors.muted);
        } else if (hasFocus()) {
            textElement.setColor(GuiColors.hover);
        } else {
            textElement.setColor(GuiColors.normal);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateColor();
    }

    public void setText(String text) {
        textElement.setString(text);
        setWidth(Minecraft.getInstance().font.width(text));
    }

    @Override
    protected void onFocus() {
        updateColor();
    }

    @Override
    protected void onBlur() {
        updateColor();
    }

    @Override
    public List<Component> getTooltipLines() {
        if (!enabled && disabledTooltip != null && hasFocus()) {
            return Collections.singletonList(disabledTooltip);
        }
        return null;
    }
}
