package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Eternal Stella Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.EternalStellaItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
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

//            CompoundTag compound = stackInSlot.getOrCreateTag();
//            compound.putBoolean("Repair", true);
        }

        if (!player.getAbilities().instabuild) {
            this.setRemainingUses(stack, this.getRemainingUses(stack) - 1);
        }

        if (this.getRemainingUses(stack) == 0) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

//    @Override
//    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
//        super.appendHoverText(stack, world, tooltip, flag);
//
//        tooltip.add(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + ".remaining_uses").append(": " + this.getRemainingUses(stack)).withStyle(ChatFormatting.GRAY));
//    }

    private int getRemainingUses(ItemStack stack) {
//        CompoundTag tag = stack.getOrCreateTag();
//
//        if (!tag.contains("RemainingUses")) {
//            return ItemConfig.ETERNAL_STELLA_USES.get();
//        } else {
//            return tag.getInt("RemainingUses");
//        }

        return 0;
    }

    private void setRemainingUses(ItemStack stack, int uses) {
//        stack.getOrCreateTag().putInt("RemainingUses", uses);
    }
}
