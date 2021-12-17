package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Eternal Stella Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.EternalStellaItem
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
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stackInSlot = player.getInventory().getItem(i);

            if (stackInSlot.isEmpty() || stackInSlot.getDamageValue() == 0) {
                continue;
            }

            CompoundTag compound = stackInSlot.getOrCreateTag();
            compound.putBoolean("Repair", true);
        }

        if (!player.getAbilities().instabuild) {
            this.setRemainingUses(stack, this.getRemainingUses(stack) - 1);
        }

        if (this.getRemainingUses(stack) == 0) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".remaining_uses").append(": " + this.getRemainingUses(stack)).withStyle(ChatFormatting.GRAY));
    }

    private int getRemainingUses(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();

        if (!tag.contains("RemainingUses")) {
            return ItemConfig.ETERNAL_STELLA_USES.get();
        } else {
            return tag.getInt("RemainingUses");
        }
    }

    private void setRemainingUses(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt("RemainingUses", uses);
    }
}
