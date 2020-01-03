package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import com.stal111.forbidden_arcanus.util.ModUtils;
import javafx.util.Pair;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EdelwoodWaterBucketIncreaseFullnessRecipe extends SpecialRecipe {

    public EdelwoodWaterBucketIncreaseFullnessRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int i = 0;
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stackInSlot = inv.getStackInSlot(slot);
            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getItem() == ModItems.EDELWOOD_WATER_BUCKET.getItem()) {
                    if (stack.isEmpty()) {
                        stack = stackInSlot;
                    } else {
                        for (int j = 0; j < ICapacityBucket.getFullness(stackInSlot); j++) {
                            i++;
                        }
                    }
                } else if (stackInSlot.getItem() == Items.WATER_BUCKET) {
                    i++;
                } else {
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            if (!((ICapacityBucket.getFullness(stack) + i) > ((ICapacityBucket) stack.getItem()).getCapacity())) {
                return i > 0;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        int i = 0;
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stackInSlot = inv.getStackInSlot(slot);
            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getItem() == ModItems.EDELWOOD_WATER_BUCKET.getItem()) {
                    if (stack.isEmpty()) {
                        stack = stackInSlot.copy();
                    } else {
                        for (int j = 0; j < ICapacityBucket.getFullness(stackInSlot); j++) {
                            i++;
                        }
                    }
                } else if (stackInSlot.getItem() == Items.WATER_BUCKET) {
                    i++;
                }
            }
        }

        if (!((ICapacityBucket.getFullness(stack) + i) > ((ICapacityBucket) stack.getItem()).getCapacity())) {
            ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + i);
        }
        return stack;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inventory) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

        List<Pair<Integer, ItemStack>> slots = new ArrayList<>();

        for(int i = 0; i < nonnulllist.size(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
            if (item.hasContainerItem()) {
                nonnulllist.set(i, item.getContainerItem());
                slots.add(new Pair<>(i, item.getContainerItem()));
            }
        }
        List<Pair<Integer, ItemStack>> newList = slots;

        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getValue().getItem() != ModItems.EDELWOOD_BUCKET.getItem()) {
                newList.remove(i);
            }
        }
        nonnulllist.set(newList.get(newList.size() - 1).getKey(), ItemStack.EMPTY);
        return nonnulllist;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EDELWOOD_WATER_BUCKET_INCREASE_FULLNESS.getRecipe();
    }
}
