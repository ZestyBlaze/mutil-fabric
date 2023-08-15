package se.mickelus.mutil.util;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemHandlerWrapper implements Container {

    protected final ItemStackHandler inv;

    public ItemHandlerWrapper(ItemStackHandler inv) {
        this.inv = inv;
    }

    /**
     * Returns the size of this inventory.
     */
    @Override
    public int getContainerSize() {
        return inv.getSlotCount();
    }

    /**
     * Returns the stack in this slot.  This stack should be a modifiable reference, not a copy of a stack in your inventory.
     */
    @Override
    public ItemStack getItem(int slot) {
        return inv.getStackInSlot(slot);
    }

    /**
     * Attempts to remove n items from the specified slot.  Returns the split stack that was removed.  Modifies the inventory.
     */
    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack stack = inv.getStackInSlot(slot);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
    }

    /**
     * Sets the contents of this slot to the provided stack.
     */
    @Override
    public void setItem(int slot, ItemStack stack) {
        try (Transaction t = TransferUtil.getTransaction()) {
            inv.insertSlot(slot, ItemVariant.of(stack), 64, t);
        }
    }

    /**
     * Removes the stack contained in this slot from the underlying handler, and returns it.
     */
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack s = getItem(index);
        if(s.isEmpty()) return ItemStack.EMPTY;
        setItem(index, ItemStack.EMPTY);
        return s;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < inv.getSlotCount(); i++) {
            if(!inv.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return inv.isItemValid(slot, ItemVariant.of(stack));
    }

    @Override
    public void clearContent() {
        for(int i = 0; i < inv.getSlotCount(); i++) {
            try (Transaction t = TransferUtil.getTransaction()) {
                inv.extractSlot(i, ItemVariant.of(inv.getSlot(i).getStack()), 64, t);
            }
        }
    }

    //The following methods are never used by vanilla in crafting.  They are defunct as mods need not override them.
    @Override
    public int getMaxStackSize() { return 0; }
    @Override
    public void setChanged() {}
    @Override
    public boolean stillValid(Player player) { return false; }
    @Override
    public void startOpen(Player player) {}
    @Override
    public void stopOpen(Player player) {}
}
