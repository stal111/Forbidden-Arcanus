package com.stal111.forbidden_arcanus.common.inventory.clibano;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Clibano Result Slot <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoResultSlot
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-06-10
 */
public class ClibanoResultSlot extends SlotItemHandler {

    private final Player player;
    private int removeCount;

    public ClibanoResultSlot(Player player, IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
        this.player = player;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }

        return super.remove(amount);
    }

    @Override
    public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(@Nonnull ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(@Nonnull ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);

        if (this.player instanceof ServerPlayer serverPlayer && this.container instanceof ClibanoMainBlockEntity blockEntity) {
            blockEntity.awardUsedRecipesAndPopExperience(serverPlayer);
        }

        this.removeCount = 0;
        net.minecraftforge.event.ForgeEventFactory.firePlayerSmeltedEvent(this.player, stack);
    }
}
