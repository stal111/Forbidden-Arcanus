package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.block.properties.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.ClibanoSideType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Clibano Main Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public class ClibanoMainBlockEntity extends BaseContainerBlockEntity implements RecipeHolder {

    public static final int SOUL_DURATION = 2700;

    public static final int DATA_SOUL_TIME = 0;
    public static final int DATA_BURN_TIME = 1;
    public static final int DATA_BURN_DURATION = 2;
    public static final int DATA_COOKING_PROGRESS_FIRST = 3;
    public static final int DATA_COOKING_PROGRESS_SECOND = 4;
    public static final int DATA_COOKING_DURATION_FIRST = 5;
    public static final int DATA_COOKING_DURATION_SECOND = 6;
    public static final int DATA_FIRE_TYPE = 7;

    public static final int DATA_COUNT = 8;

    public static final RecipeType<ClibanoRecipe> RECIPE_TYPE = ModRecipes.CLIBANO_COMBUSTION.get();

    public static final Function<ItemStack, Optional<ClibanoFireType>> ITEM_TO_FIRE_TYPE = stack -> {
        if (stack.is(ModItems.SOUL.get()) || stack.is(ModItems.DARK_SOUL.get())) {
            return Optional.of(ClibanoFireType.BLUE_FIRE);
        }
        return Optional.empty();
    };

    private final NonNullList<ItemStack> inventoryContents = NonNullList.withSize(9, ItemStack.EMPTY);

    private int soulTime;
    private int burnTime;
    private int burnDuration;
    private int cookingProgressFirst;
    private int cookingProgressSecond;
    private int cookingDurationFirst;
    private int cookingDurationSecond;

    private ClibanoFireType fireType = ClibanoFireType.FIRE;

    private Direction frontDirection = Direction.NORTH;

    private boolean wasLit = false;

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_SOUL_TIME -> ClibanoMainBlockEntity.this.soulTime;
                case DATA_BURN_TIME -> ClibanoMainBlockEntity.this.burnTime;
                case DATA_BURN_DURATION -> ClibanoMainBlockEntity.this.burnDuration;
                case DATA_COOKING_PROGRESS_FIRST -> ClibanoMainBlockEntity.this.cookingProgressFirst;
                case DATA_COOKING_PROGRESS_SECOND -> ClibanoMainBlockEntity.this.cookingProgressSecond;
                case DATA_COOKING_DURATION_FIRST -> ClibanoMainBlockEntity.this.cookingDurationFirst;
                case DATA_COOKING_DURATION_SECOND -> ClibanoMainBlockEntity.this.cookingDurationSecond;
                case DATA_FIRE_TYPE -> ClibanoMainBlockEntity.this.fireType.ordinal();
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_SOUL_TIME -> ClibanoMainBlockEntity.this.setSoulTime(value);
                case DATA_BURN_TIME -> ClibanoMainBlockEntity.this.burnTime = value;
                case DATA_BURN_DURATION -> ClibanoMainBlockEntity.this.burnDuration = value;
                case DATA_COOKING_PROGRESS_FIRST -> ClibanoMainBlockEntity.this.cookingProgressFirst = value;
                case DATA_COOKING_PROGRESS_SECOND -> ClibanoMainBlockEntity.this.cookingProgressSecond = value;
                case DATA_COOKING_DURATION_FIRST -> ClibanoMainBlockEntity.this.cookingDurationFirst = value;
                case DATA_COOKING_DURATION_SECOND -> ClibanoMainBlockEntity.this.cookingDurationSecond = value;
                case DATA_FIRE_TYPE -> ClibanoMainBlockEntity.this.fireType = ClibanoFireType.values()[value];
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public ClibanoMainBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CLIBANO_MAIN.get(), worldPosition, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ClibanoMainBlockEntity blockEntity) {
        Container firstSlot = new SimpleContainer(blockEntity.inventoryContents.get(ClibanoMenu.INPUT_SLOTS.getFirst()));
        Container secondSlot = new SimpleContainer(blockEntity.inventoryContents.get(ClibanoMenu.INPUT_SLOTS.getSecond()));

        ClibanoRecipe firstRecipe = level.getRecipeManager().getRecipeFor(RECIPE_TYPE, firstSlot, level).orElse(null);
        ClibanoRecipe secondRecipe = level.getRecipeManager().getRecipeFor(RECIPE_TYPE, secondSlot, level).orElse(null);

        boolean isLit = blockEntity.burnTime > 0;

        boolean canSmeltFirst = firstRecipe != null && blockEntity.canBurn(firstRecipe, blockEntity.getMaxStackSize(), ClibanoMenu.INPUT_SLOTS.getFirst());
        boolean canSmeltSecond = secondRecipe != null && blockEntity.canBurn(secondRecipe, blockEntity.getMaxStackSize(), ClibanoMenu.INPUT_SLOTS.getSecond());

        if (blockEntity.soulTime != 0) {
            blockEntity.soulTime--;

            if (blockEntity.soulTime == 0) {
                blockEntity.changeFireType(level, ClibanoFireType.FIRE, firstRecipe, secondRecipe);
            }
        } else if (canSmeltFirst || canSmeltSecond) {
            ItemStack soul = blockEntity.getItem(ClibanoMenu.SOUL_SLOT);

            if (!soul.isEmpty()) {
                blockEntity.soulTime = SOUL_DURATION;

                blockEntity.changeFireType(level, ITEM_TO_FIRE_TYPE.apply(soul).orElse(ClibanoFireType.FIRE), firstRecipe, secondRecipe);

                soul.shrink(1);
            }
        }

        if (isLit) {
            blockEntity.burnTime--;
        } else {
            if (canSmeltFirst || canSmeltSecond) {
                ItemStack fuel = blockEntity.getItem(ClibanoMenu.FUEL_SLOT);

                blockEntity.burnDuration = 0;

                if (!fuel.isEmpty()) {
                    blockEntity.burnTime = blockEntity.getBurnDuration(fuel);
                    blockEntity.burnDuration = blockEntity.burnTime;

                    fuel.shrink(1);

                    if (!blockEntity.wasLit) {
                        blockEntity.updateAppearance(level);
                    }

                    blockEntity.setChanged();
                }
            }

            if (blockEntity.cookingProgressFirst != 0 || blockEntity.cookingProgressSecond != 0) {
                blockEntity.cookingProgressFirst = Math.max(0, blockEntity.cookingProgressFirst - 2);
                blockEntity.cookingProgressSecond = Math.max(0, blockEntity.cookingProgressSecond - 2);

                blockEntity.setChanged();
            }

            if (blockEntity.wasLit) {
                blockEntity.updateAppearance(level);
            }

            blockEntity.wasLit = false;

            return;
        }

        if (canSmeltFirst) {
            blockEntity.cookingDurationFirst = firstRecipe.getCookingTime(blockEntity.fireType);

            blockEntity.cookingProgressFirst++;

            if (blockEntity.cookingProgressFirst == blockEntity.cookingDurationFirst) {
                blockEntity.finishRecipe(firstRecipe, ClibanoMenu.INPUT_SLOTS.getFirst());
            }

            blockEntity.setChanged();
        } else {
            blockEntity.cookingProgressFirst = 0;
        }

        if (canSmeltSecond) {
            blockEntity.cookingDurationSecond = secondRecipe.getCookingTime(blockEntity.fireType);

            blockEntity.cookingProgressSecond++;

            if (blockEntity.cookingProgressSecond == blockEntity.cookingDurationSecond) {
                blockEntity.finishRecipe(secondRecipe, ClibanoMenu.INPUT_SLOTS.getSecond());
            }

            blockEntity.setChanged();
        } else {
            blockEntity.cookingProgressSecond = 0;
        }

        blockEntity.wasLit = true;
    }

    /**
     * Checks if the given recipe can be used at the moment.
     * To be considered usable one of the result slots must be empty or the result of the recipe must fit into one of the existing stacks.
     *
     * @param recipe   the recipe to check
     * @param maxCount the maximum stack size of the result
     * @param slot     the input slot to check
     * @return true if the recipe can be used
     */
    private boolean canBurn(ClibanoRecipe recipe, int maxCount, int slot) {
        NonNullList<ItemStack> items = this.inventoryContents;

        if (items.get(slot).isEmpty()) {
            return false;
        }

        ItemStack stack = recipe.getResultItem();

        if (stack.isEmpty()) {
            return false;
        }

        ItemStack resultStack = items.get(ClibanoMenu.RESULT_SLOTS.getFirst());
        ItemStack secondResultStack = items.get(ClibanoMenu.RESULT_SLOTS.getSecond());

        if (resultStack.isEmpty() || secondResultStack.isEmpty()) {
            return true;
        } else if (!resultStack.sameItem(stack) && !secondResultStack.sameItem(stack)) {
            return false;
        } else if (resultStack.getCount() + stack.getCount() <= maxCount && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
            return true;
        } else if (secondResultStack.getCount() + stack.getCount() <= maxCount && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
            return true;
        }

        return resultStack.getCount() + stack.getCount() <= stack.getMaxStackSize() || secondResultStack.getCount() + stack.getCount() <= stack.getMaxStackSize();
    }

    /**
     * Finishes the given recipe.
     * The result is added to one of the result slots and the input slot is cleared. The cooking progress is reset.
     *
     * @param recipe the recipe to finish
     * @param slot   the slot where the recipe input was placed in
     */
    private void finishRecipe(ClibanoRecipe recipe, int slot) {
        NonNullList<ItemStack> items = this.inventoryContents;
        ItemStack stack = recipe.getResultItem();

        items.get(slot).shrink(1);

        if (slot == ClibanoMenu.INPUT_SLOTS.getFirst()) {
            this.cookingProgressFirst = 0;
        } else {
            this.cookingProgressSecond = 0;
        }

        if (stack.isEmpty()) {
            return;
        }

        ItemStack resultStack = items.get(ClibanoMenu.RESULT_SLOTS.getFirst());
        ItemStack secondResultStack = items.get(ClibanoMenu.RESULT_SLOTS.getSecond());

        if (resultStack.sameItem(stack) && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
            resultStack.grow(stack.getCount());
        } else if (secondResultStack.sameItem(stack) && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
            secondResultStack.grow(stack.getCount());
        } else if (resultStack.isEmpty()) {
            items.set(ClibanoMenu.RESULT_SLOTS.getFirst(), stack.copy());
        } else if (secondResultStack.isEmpty()) {
            items.set(ClibanoMenu.RESULT_SLOTS.getSecond(), stack.copy());
        }

        this.setRecipeUsed(recipe);
    }

    /**
     * Changes the current {@link ClibanoFireType} of the clibano and updates the cooking durations accordingly.
     *
     * @param level        the level the clibano is in
     * @param fireType     the new ClibanoFireType
     * @param firstRecipe  the recipe for the first input slot
     * @param secondRecipe the recipe for the second input slot
     */
    private void changeFireType(Level level, ClibanoFireType fireType, @Nullable ClibanoRecipe firstRecipe, @Nullable ClibanoRecipe secondRecipe) {
        this.fireType = fireType;

        if (firstRecipe != null) {
            int oldDuration = this.cookingDurationFirst;

            this.cookingDurationFirst = firstRecipe.getCookingTime(fireType);
            this.cookingProgressFirst = (int) (((float) this.cookingProgressFirst / oldDuration) * this.cookingDurationFirst);
        }

        if (secondRecipe != null) {
            int oldDuration = this.cookingDurationSecond;

            this.cookingDurationSecond = secondRecipe.getCookingTime(fireType);
            this.cookingProgressSecond = (int) (((float) this.cookingProgressSecond / oldDuration) * this.cookingDurationSecond);
        }

        this.updateAppearance(level);

        this.setChanged();
    }

    /**
     * Updates the clibano's appearance to resemble the current fire type.
     *
     * @param level the level the clibano is in
     */
    private void updateAppearance(Level level) {
        ClibanoCenterType centerType = ClibanoCenterType.FRONT_OFF;
        ClibanoSideType sideType = ClibanoSideType.OFF;

        if (this.burnTime > 0) {
            if (this.fireType == ClibanoFireType.FIRE) {
                centerType = ClibanoCenterType.FRONT_FIRE;
                sideType = ClibanoSideType.FIRE;
            } else if (this.fireType == ClibanoFireType.BLUE_FIRE) {
                centerType = ClibanoCenterType.FRONT_BLUE_FIRE;
                sideType = ClibanoSideType.BLUE_FIRE;
            }
        }

        BlockPos frontCenterPos = this.worldPosition.relative(this.frontDirection);
        BlockState center = level.getBlockState(frontCenterPos).setValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE, centerType);

        level.setBlockAndUpdate(frontCenterPos, center);

        for (Direction direction : Direction.values()) {
            if (direction.getAxis() == this.frontDirection.getAxis()) {
                continue;
            }

            BlockState side = level.getBlockState(frontCenterPos.relative(direction)).setValue(ModBlockStateProperties.CLIBANO_SIDE_TYPE, sideType);

            level.setBlockAndUpdate(frontCenterPos.relative(direction), side);
        }
    }

    public void setFrontDirection(Direction direction) {
        this.frontDirection = direction;
    }

    @Nonnull
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.forbidden_arcanus.clibano");
    }

    @Nonnull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @Nonnull Inventory inventory) {
        return new ClibanoMenu(containerId, this, inventory, this.containerData);
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);

        ContainerHelper.saveAllItems(tag, this.inventoryContents);

        tag.putInt("SoulTime", this.soulTime);
        tag.putInt("BurnTime", this.burnTime);
        tag.putInt("CookingProgressFirst", this.cookingProgressFirst);
        tag.putInt("CookingProgressSecond", this.cookingProgressSecond);
        tag.putInt("CookingDurationFirst", this.cookingDurationFirst);
        tag.putInt("CookingDurationSecond", this.cookingDurationSecond);

        tag.putString("FireType", this.fireType.getSerializedName());

        tag.putString("FrontDirection", this.frontDirection.getName());

        this.recipesUsed.forEach((resourceLocation, integer) -> {
            tag.putInt(resourceLocation.toString(), integer);
        });
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);

        this.inventoryContents.clear();
        ContainerHelper.loadAllItems(tag, this.inventoryContents);

        this.soulTime = tag.getInt("SoulTime");
        this.burnTime = tag.getInt("BurnTime");
        this.burnDuration = this.getBurnDuration(this.inventoryContents.get(ClibanoMenu.FUEL_SLOT));
        this.cookingProgressFirst = tag.getInt("CookingProgressFirst");
        this.cookingProgressSecond = tag.getInt("CookingProgressSecond");
        this.cookingDurationFirst = tag.getInt("CookingDurationFirst");
        this.cookingDurationSecond = tag.getInt("CookingDurationSecond");

        this.fireType = ClibanoFireType.byName(tag.getString("FireType")).orElse(ClibanoFireType.FIRE);

        this.frontDirection = Direction.byName(tag.getString("FrontDirection"));

        for(String recipe : tag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(recipe), tag.getInt(recipe));
        }
    }

    public void setSoulTime(int duration) {
        this.soulTime = duration;
    }

    @Override
    public int getContainerSize() {
        return this.inventoryContents.size();
    }

    protected int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }

        return ForgeHooks.getBurnTime(fuel, RECIPE_TYPE);
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe == null) {
            return;
        }

        this.recipesUsed.addTo(recipe.getId(), 1);
    }

    @Override
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(@Nonnull Player player) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        player.awardRecipes(this.getRecipesToAwardAndPopExperience(player.getLevel(), player.position()));

        this.recipesUsed.clear();
    }

    public Collection<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<Recipe<?>> list = new ArrayList<>();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                ClibanoMainBlockEntity.createExperience(level, position, entry.getIntValue(), ((ClibanoRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(ServerLevel level, Vec3 position, int count, float experience) {
        int i = Mth.floor(count * experience);
        float f = Mth.frac(count * experience);

        if (f != 0.0F && Math.random() < f) {
            i++;
        }

        ExperienceOrb.award(level, position, i);
    }

    @Override
    public boolean isEmpty() {
        return this.inventoryContents.stream().allMatch(ItemStack::isEmpty);
    }

    @Nonnull
    @Override
    public ItemStack getItem(int index) {
        return this.inventoryContents.get(index);
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = ContainerHelper.removeItem(this.inventoryContents, index, count);

        if (!stack.isEmpty()) {
            this.setChanged();
        }

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.inventoryContents, index);
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        this.inventoryContents.set(index, stack);

        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        if (this.getLevel() == null || this.getLevel().getBlockEntity(this.worldPosition) != this) {
            return false;
        }

        return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clearContent() {
        this.inventoryContents.clear();
    }
}
