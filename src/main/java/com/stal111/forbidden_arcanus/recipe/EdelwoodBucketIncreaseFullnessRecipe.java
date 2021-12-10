package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EdelwoodBucketIncreaseFullnessRecipe extends CustomRecipe {

    public EdelwoodBucketIncreaseFullnessRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        AtomicInteger i = new AtomicInteger();
        List<Integer> list = new ArrayList<>();
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getContainerSize(); slot++) {
            ItemStack stackInSlot = inv.getItem(slot);
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
                if (getValidItems(finalStack).contains(inv.getItem(integer).getItem())) {
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
    public ItemStack assemble(CraftingContainer inv) {
        AtomicInteger i = new AtomicInteger();
        List<Integer> list = new ArrayList<>();
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getContainerSize(); slot++) {
            ItemStack stackInSlot = inv.getItem(slot);
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
                if (getValidItems(finalStack).contains(inv.getItem(integer).getItem())) {
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
        if (stack.getItem() == NewModItems.EDELWOOD_WATER_BUCKET.get()) {
            list.add(Items.WATER_BUCKET);
        } else if (stack.getItem() == NewModItems.EDELWOOD_LAVA_BUCKET.get()) {
            list.add(Items.LAVA_BUCKET);
        }
        return list;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventory) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);

        Map<Integer, ItemStack> slot = new HashMap<>();

        for(int i = 0; i < nonnulllist.size(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item.hasContainerItem()) {
                slot.put(i, item.getContainerItem());
            }
        }

        if (!slot.isEmpty()) {
            for (Map.Entry<Integer,ItemStack> entry : slot.entrySet()) {
                if (!entry.getValue().isEmpty() && !(entry.getValue().getItem() instanceof BucketItem))  {
                    slot.put(entry.getKey(), ItemStack.EMPTY);
                    break;
                }
            }
        }

        slot.forEach((integer, stack) -> {
            if (!stack.isEmpty()) {
                slot.forEach(nonnulllist::set);
            }
        });

        return nonnulllist;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EDELWOOD_BUCKET_INCREASE_FULLNESS.get();
    }
}
