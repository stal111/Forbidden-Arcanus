package com.stal111.forbidden_arcanus.common.inventory.clibano;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;

import javax.annotation.Nonnull;

/**
 * Clibano Menu <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenuOld
 *
 * @author stal111
 * @since 2022-05-25
 */
public class ClibanoMenuOld extends AbstractContainerMenu {

    public static final int SLOT_COUNT = 7;
    public static final int BASE_DATA_COUNT = ClibanoMainBlockEntity.DATA_COUNT;
    public static final int ENHANCER_SLOT = 0;
    public static final int SOUL_SLOT = 1;
    public static final int FUEL_SLOT = 2;

    public static final Pair<Integer, Integer> INPUT_SLOTS = Pair.of(3, 4);
    public static final Pair<Integer, Integer> RESULT_SLOTS = Pair.of(5, 6);

    private final ContainerData containerData;

    private final MenuCreationContext<ClibanoMainBlockEntity, IItemHandler> context;

    public ClibanoMenuOld(int id, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(id, new ItemStackHandler(SLOT_COUNT), new SimpleContainerData(ClibanoMainBlockEntity.DATA_COUNT), MenuCreationContext.of(inventory));
    }

    public ClibanoMenuOld(int containerId, ItemStackHandler handler, ContainerData containerData, MenuCreationContext<ClibanoMainBlockEntity, IItemHandler> context) {
        super(ModMenuTypes.CLIBANO.get(), containerId);
        this.containerData = containerData;
        this.context = context;

        this.addSlots(handler);

        checkContainerDataCount(containerData, BASE_DATA_COUNT);
    }

    private void addSlots(ItemStackHandler handler) {
//        this.addSlot(new LockableSlot(handler, ENHANCER_SLOT, 18, 20));
        this.addSlot(new ClibanoSoulSlot(handler, SOUL_SLOT, 18, 56));
        this.addSlot(new ClibanoFuelSlot(this, handler, FUEL_SLOT, 53, 56));

        // Input Slots
        this.addSlot(new SlotItemHandler(handler, INPUT_SLOTS.getFirst(), 44, 20));
        this.addSlot(new SlotItemHandler(handler, INPUT_SLOTS.getSecond(), 62, 20));

        ClibanoMainBlockEntity blockEntity = this.context.getBlockEntity();

        // Result Slot
        this.addSlot(new ClibanoResultSlot(this.context.player(), handler, blockEntity, RESULT_SLOTS.getFirst(), 116, 32));
        this.addSlot(new ClibanoResultSlot(this.context.player(), handler, blockEntity, RESULT_SLOTS.getSecond(), 142, 28));

        // Inventory Slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(this.context.inventory(), j + i * 9 + 9, 8 + j * 18, 91 + i * 18));
            }
        }

        // Hotbar Slots
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new SlotItemHandler(this.context.inventory(), k, 8 + k * 18, 149));
        }

        this.addDataSlots(this.containerData);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return result;
        }

        ItemStack stack = slot.getItem();
        result = stack.copy();

        if (index == RESULT_SLOTS.getFirst() || index == RESULT_SLOTS.getSecond()) {
            if (!this.moveItemStackTo(stack, 7, 43, true)) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack, result);
        } else if (index < SLOT_COUNT) {
            if (!this.moveItemStackTo(stack, 7, 43, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (this.canSmelt(stack)) {
                if (!this.moveItemStackTo(stack, INPUT_SLOTS.getFirst(), INPUT_SLOTS.getSecond() + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.isFuel(stack)) {
                if (!this.moveItemStackTo(stack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.isSoul(stack)) {
                if (!this.moveItemStackTo(stack, SOUL_SLOT, SOUL_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (EnhancerHelper.getEnhancer(stack).isPresent()) {
                if (!this.moveItemStackTo(stack, ENHANCER_SLOT, ENHANCER_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 34) {
                if (!this.moveItemStackTo(stack, 34, 43, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 43 && !this.moveItemStackTo(stack, 7, 34, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == result.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);

        return result;
    }

    protected boolean canSmelt(ItemStack stack) {
        return this.context.levelAccess().evaluate((level, pos) -> {
            //TODO
//            for (RecipeHolder<ClibanoRecipe> recipe : level.recipeAccess().getAllRecipesFor(ModRecipeTypes.CLIBANO_COMBUSTION.get())) {
//                for (Ingredient ingredient : recipe.value().getIngredients()) {
//                    if (ingredient.test(stack)) {
//                        return true;
//                    }
//                }
//            }
            return false;
        }, false);
    }

    protected boolean isFuel(ItemStack stack) {
        //TODO
        return false;
//        return stack.getBurnTime(RecipeType.BLASTING, this.level.fuelValues()) > 0;
    }

    protected boolean isSoul(ItemStack stack) {
        return false;
    }

//    public boolean isSoulActive() {
//        return this.getSoulDuration() > 0;
//    }

//    public int getSoulDuration() {
//        return this.containerData.get(ClibanoMainBlockEntity.DATA_SOUL_TIME);
//    }

    public int getBurnTime() {
        return this.containerData.get(ClibanoMainBlockEntity.DATA_LIT_TIME_REMAINING);
    }

    public int getBurnDuration() {
        return this.containerData.get(ClibanoMainBlockEntity.DATA_LIT_TOTAL_TIME);
    }

//    public Pair<Integer, Integer> getCookingProgress() {
//        return Pair.of(this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_PROGRESS_FIRST), this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_PROGRESS_SECOND));
//    }
//
//    public Pair<Integer, Integer> getCookingDuration() {
//        return Pair.of(this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_DURATION_FIRST), this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_DURATION_SECOND));
//    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(this.context.levelAccess(), player, ModBlocks.CLIBANO_MAIN_PART.get());
    }

//    public int getFireType() {
//        return this.containerData.get(ClibanoMainBlockEntity.DATA_FIRE_TYPE);
//    }
//
//    public int getResidueFullness() {
//        return this.containerData.get(ClibanoMainBlockEntity.DATA_RESIDUE_FULLNESS);
//    }

//    public boolean isDoubleRecipe() {
//        return this.containerData.get(ClibanoMainBlockEntity.DATA_IS_DOUBLE_RECIPE) == 1;
//    }
}
