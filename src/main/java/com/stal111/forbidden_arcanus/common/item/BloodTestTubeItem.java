package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Blood Test Tube Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-07-08
 */
public class BloodTestTubeItem extends Item {

    public static final int MAX_BLOOD = 3000;

    public BloodTestTubeItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public String getDescriptionId(@Nonnull ItemStack stack) {
        return ModItems.TEST_TUBE.get().getDescriptionId();
    }

    public static void collectBlood(Player player, float damage) {
        Inventory inventory = player.getInventory();
        int blood = (int) (20 * damage);

        ItemStack stack = null;

        //TODO: Fix Blood Test Tube
//        outer: for (NonNullList<ItemStack> nonNullList : inventory.compartments) {
//            for (ItemStack inventoryStack : nonNullList) {
//                if (inventoryStack.is(ModItems.TEST_TUBE.get()) && stack == null) {
//                    stack = inventoryStack;
//
//                } else if (inventoryStack.is(ModItems.BLOOD_TEST_TUBE.get()) && BloodTestTubeItem.getBlood(inventoryStack) != BloodTestTubeItem.MAX_BLOOD) {
//                    BloodTestTubeItem.addBlood(inventoryStack, blood);
//                    stack = null;
//
//                    break outer;
//                }
//            }
//        }

        if (stack != null) {
            ItemStack newStack = BloodTestTubeItem.setBlood(new ItemStack(ModItems.BLOOD_TEST_TUBE.get()), blood);
            int slot = inventory.findSlotMatchingItem(stack);

            stack.shrink(1);

            if (!stack.isEmpty()) {
                if (!player.addItem(newStack)) {
                    player.drop(newStack, false);
                }
                return;
            }

            if (slot == -1 && player.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
                player.setItemInHand(InteractionHand.OFF_HAND, newStack);
            } else {
                inventory.setItem(slot, newStack);
            }
        }
    }

    public static int getBlood(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Blood");
    }

    public static ItemStack setBlood(ItemStack stack, int blood) {
        CompoundTag compound = stack.getOrCreateTag();
        compound.putInt("Blood", blood);

        return stack;
    }

    public static void addBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.min(getBlood(stack) + blood, MAX_BLOOD));
    }

    public static ItemStack removeBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.max(getBlood(stack) - blood, 0));

        if (getBlood(stack) == 0) {
            stack = new ItemStack(ModItems.TEST_TUBE.get());
        }
        return stack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        tooltip.add(Component.translatable(ForbiddenArcanus.MOD_ID + ".essence.blood").append(": " + getBlood(stack) + "/" + MAX_BLOOD).withStyle(ChatFormatting.GRAY));
    }
}
