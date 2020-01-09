package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EdelwoodBucketIncreaseFullnessRecipe extends SpecialRecipe {

    public EdelwoodBucketIncreaseFullnessRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        AtomicInteger i = new AtomicInteger();
        List<Integer> list = new ArrayList<>();
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stackInSlot = inv.getStackInSlot(slot);
            if (stack.isEmpty()) {
                if (stackInSlot.getItem() instanceof ICapacityBucket) {
                    stack = stackInSlot.copy();
                } else {
                    list.add(slot);
                }
            } else {
                if (!getValidItems(stack).isEmpty()) {
                    if (getValidItems(stack).contains(stackInSlot.getItem())) {
                        i.getAndIncrement();
                    } else if (stackInSlot.getItem() == stack.getItem()) {
                        for (int j = 0; j < ICapacityBucket.getFullness(stackInSlot); j++) {
                            i.getAndIncrement();
                        }
                    }
                }
            }
        }

        if (!stack.isEmpty()) {
            ItemStack finalStack = stack;
            list.forEach((integer -> {
                if (getValidItems(finalStack).contains(inv.getStackInSlot(integer).getItem())) {
                    i.getAndIncrement();
                }
            }));
        }
        if (!stack.isEmpty()) {
            if (!((ICapacityBucket.getFullness(stack) + i.get()) > ((ICapacityBucket) stack.getItem()).getCapacity())) {
                return i.get() > 0;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        AtomicInteger i = new AtomicInteger();
        List<Integer> list = new ArrayList<>();
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stackInSlot = inv.getStackInSlot(slot);
            if (stack.isEmpty()) {
                if (stackInSlot.getItem() instanceof ICapacityBucket) {
                    stack = stackInSlot.copy();
                } else {
                    list.add(slot);
                }
            } else {
                if (!getValidItems(stack).isEmpty()) {
                    if (getValidItems(stack).contains(stackInSlot.getItem())) {
                        i.getAndIncrement();
                    } else if (stackInSlot.getItem() == stack.getItem()) {
                        for (int j = 0; j < ICapacityBucket.getFullness(stackInSlot); j++) {
                            i.getAndIncrement();
                        }
                    }
                }
            }
        }

        if (!stack.isEmpty()) {
            ItemStack finalStack = stack;
            list.forEach((integer -> {
                if (getValidItems(finalStack).contains(inv.getStackInSlot(integer).getItem())) {
                    i.getAndIncrement();
                }
            }));
        }

        if (!((ICapacityBucket.getFullness(stack) + i.get()) > ((ICapacityBucket) stack.getItem()).getCapacity())) {
            ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + i.get());
        }
        return stack;
    }

    private List<Item> getValidItems(ItemStack stack) {
        List<Item> list = new ArrayList<>();
        if (stack.getItem() == ModItems.EDELWOOD_WATER_BUCKET.getItem()) {
            list.add(Items.WATER_BUCKET.getItem());
        } else if (stack.getItem() == ModItems.EDELWOOD_LAVA_BUCKET.getItem()) {
            list.add(Items.LAVA_BUCKET.getItem());
        } else if (stack.getItem() == ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.getItem()) {
            list.add(Items.MUSHROOM_STEW.getItem());
        } else if (stack.getItem() == ModItems.EDELWOOD_BEETROOT_SOUP_BUCKET.getItem()) {
            list.add(Items.BEETROOT_SOUP.getItem());
        } else if (stack.getItem() == ModItems.EDELWOOD_BAT_SOUP_BUCKET.getItem()) {
            list.add(ModItems.BAT_SOUP.getItem());
        }
        return list;
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

        slots.forEach(pair -> {
            if (pair.getValue().getItem() != ModItems.EDELWOOD_BUCKET.getItem()) {
                slots.set(slots.indexOf(pair), new Pair<>(pair.getKey(), ItemStack.EMPTY));
            }
        });

        List<Pair<Integer, ItemStack>> newList = new ArrayList<>();
        for (Pair<Integer, ItemStack> slot : slots) {
            if (!slot.getValue().isEmpty()) {
                newList.add(slot);
            }
        }

        if (slots.size() != 0) {
            nonnulllist.set(newList.get(newList.size() - 1).getKey(), ItemStack.EMPTY);
        }
        return nonnulllist;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EDELWOOD_BUCKET_INCREASE_FULLNESS.getRecipe();
    }
}
