package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Eternal Stella Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.EternalStellaItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class EternalStellaItem extends Item {

    public EternalStellaItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stackInSlot = player.inventory.getStackInSlot(i);

            if (stackInSlot.isEmpty() || stackInSlot.getDamage() == 0) {
                continue;
            }

            CompoundNBT compound = stackInSlot.getOrCreateTag();
            compound.putBoolean("Repair", true);
        }

        if (!player.abilities.isCreativeMode) {
            this.setRemainingUses(stack, this.getRemainingUses(stack) - 1);
        }

        if (this.getRemainingUses(stack) == 0) {
            stack.shrink(1);
        }

        return ActionResult.func_233538_a_(stack, world.isRemote());
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".remaining_uses").appendString(": " + this.getRemainingUses(stack)).mergeStyle(TextFormatting.GRAY));
    }

    private int getRemainingUses(ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();

        if (!compound.contains("RemainingUses")) {
            return ItemConfig.ETERNAL_STELLA_USES.get();
        } else {
            return compound.getInt("RemainingUses");
        }
    }

    private void setRemainingUses(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt("RemainingUses", uses);
    }
}
