package com.stal111.forbidden_arcanus.common.recipe;

import com.stal111.forbidden_arcanus.common.item.CapacityBucket;
import com.stal111.forbidden_arcanus.common.item.EdelwoodBucketItem;
import com.stal111.forbidden_arcanus.common.item.EdelwoodSoupBucketItem;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Increase Edelwood Bucket Fullness Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.recipe.IncreaseEdelwoodBucketFullnessRecipe
 *
 * @author stal111
 * @since 2021-12-09
 */
public class IncreaseEdelwoodBucketFullnessRecipe extends CustomRecipe {

    private int bucketSlot = 0;
    private int increasement = 0;

    public IncreaseEdelwoodBucketFullnessRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer container, @Nonnull Level level) {
        ItemStack bucket = ItemStack.EMPTY;
        CapacityBucket bucketCapacity = null;
        int increasement = 0;

        this.bucketSlot = 0;
        this.increasement = 0;

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);

            if (stack.getItem() instanceof CapacityBucket capacityBucket) {
                bucket = stack.copy();

                bucketCapacity = capacityBucket;
                this.bucketSlot = slot;

                break;
            }
        }

        if (bucket.isEmpty()) {
            return false;
        }

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);

            if (slot == this.bucketSlot) {
                continue;
            }

            if (this.isValidIncreasementItem(bucket, stack)) {
                if (stack.getItem() instanceof CapacityBucket capacityBucket && stack.is(bucket.getItem())) {
                    for (int j = 0; j < capacityBucket.getFullness(stack); j++) {
                        increasement++;
                    }
                } else {
                    increasement++;
                }
            }
        }

        if (Objects.requireNonNull(bucketCapacity).getFullness(bucket) + increasement > bucketCapacity.getCapacity()) {
            return false;
        }
        this.increasement = increasement;

        return increasement > 0;
    }

    private boolean isValidIncreasementItem(ItemStack bucket, ItemStack increasement) {
        if (bucket.getItem() instanceof EdelwoodBucketItem edelwoodBucketItem && increasement.getItem() instanceof BucketItem bucketItem) {
            return edelwoodBucketItem.getFluid() == bucketItem.getFluid();
        }

        if (bucket.getItem() instanceof EdelwoodSoupBucketItem soupBucketItem) {
            return increasement.is(soupBucketItem.getSoup()) || increasement.is(soupBucketItem);
        }

        return !bucket.isEmpty() && bucket.is(increasement.getItem());
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack stack = container.getItem(this.bucketSlot).copy();

        if (stack.getItem() instanceof CapacityBucket capacityBucket) {
            return capacityBucket.setFullness(stack, capacityBucket.getFullness(stack) + this.increasement);
        }
        return stack;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(@Nonnull CraftingContainer container) {
        NonNullList<ItemStack> list = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < list.size(); ++i) {
            ItemStack stack = container.getItem(i);

            if (stack.hasCraftingRemainingItem()) {
                list.set(i, stack.getCraftingRemainingItem());
            } else if (stack.getItem() instanceof BowlFoodItem) {
                list.set(i, new ItemStack(Items.BOWL));
            }
        }

        list.set(this.bucketSlot, ItemStack.EMPTY);

        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EDELWOOD_BUCKET_INCREASE_FULLNESS.get();
    }
}
