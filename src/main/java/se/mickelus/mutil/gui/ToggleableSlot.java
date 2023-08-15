package se.mickelus.mutil.gui;

import io.github.fabricators_of_create.porting_lib.transfer.item.SlotItemHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ToggleableSlot extends SlotItemHandler {

    private boolean isEnabled = true;
    private int realX, realY;

    public ToggleableSlot(SlottedStackStorage stackStorage, int index, int xPosition, int yPosition) {
        super(stackStorage, index, xPosition, yPosition);

        realX = xPosition;
        realY = yPosition;
    }

    public void toggle(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return isEnabled;
    }

    @Override
    public boolean mayPlace(@Nullable ItemStack stack) {
        return isEnabled;
    }
}
