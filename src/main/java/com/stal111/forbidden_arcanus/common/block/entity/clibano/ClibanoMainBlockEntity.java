package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.block.properties.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.ClibanoSideType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import net.valhelsia.valhelsia_core.api.common.block.entity.forge.ValhelsiaContainerBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Clibano Main Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoMainBlockEntity extends ValhelsiaContainerBlockEntity<ClibanoMainBlockEntity> implements RecipeHolder {

    public static final int SOUL_DURATION = 2700;

    public static final int DATA_SOUL_TIME = 0;
    public static final int DATA_BURN_TIME = 1;
    public static final int DATA_BURN_DURATION = 2;
    public static final int DATA_COOKING_PROGRESS_FIRST = 3;
    public static final int DATA_COOKING_PROGRESS_SECOND = 4;
    public static final int DATA_COOKING_DURATION_FIRST = 5;
    public static final int DATA_COOKING_DURATION_SECOND = 6;
    public static final int DATA_FIRE_TYPE = 7;
    public static final int DATA_RESIDUE_FULLNESS = 8;

    public static final int BASE_DATA_COUNT = 9;
    public static final int FULL_DATA_COUNT = BASE_DATA_COUNT + ResiduesStorage.RESIDUE_TYPES.size();

    public static final RecipeType<ClibanoRecipe> RECIPE_TYPE = ModRecipes.CLIBANO_COMBUSTION.get();

    private final ResiduesStorage residuesStorage = new ResiduesStorage();
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private int soulTime;
    private int burnTime;
    private int burnDuration;
    private int cookingProgressFirst;
    private int cookingProgressSecond;
    private int cookingDurationFirst;
    private int cookingDurationSecond;
    private ClibanoFireType fireType = ClibanoFireType.FIRE;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            ClibanoMainBlockEntity blockEntity = ClibanoMainBlockEntity.this;

            return switch (index) {
                case DATA_SOUL_TIME -> blockEntity.soulTime;
                case DATA_BURN_TIME -> blockEntity.burnTime;
                case DATA_BURN_DURATION -> blockEntity.burnDuration;
                case DATA_COOKING_PROGRESS_FIRST -> blockEntity.cookingProgressFirst;
                case DATA_COOKING_PROGRESS_SECOND -> blockEntity.cookingProgressSecond;
                case DATA_COOKING_DURATION_FIRST -> blockEntity.cookingDurationFirst;
                case DATA_COOKING_DURATION_SECOND -> blockEntity.cookingDurationSecond;
                case DATA_FIRE_TYPE -> blockEntity.fireType.ordinal();
                case DATA_RESIDUE_FULLNESS -> blockEntity.residuesStorage.getTotalAmount();
                default -> {
                    ResidueType type = ResiduesStorage.RESIDUE_TYPES.get(index - BASE_DATA_COUNT);

                    yield blockEntity.residuesStorage.getResidueTypeAmountMap().getOrDefault(type, 0);
                }
            };
        }

        @Override
        public void set(int index, int value) {
            ClibanoMainBlockEntity blockEntity = ClibanoMainBlockEntity.this;

            switch (index) {
                case DATA_SOUL_TIME -> blockEntity.setSoulTime(value);
                case DATA_BURN_TIME -> blockEntity.burnTime = value;
                case DATA_BURN_DURATION -> blockEntity.burnDuration = value;
                case DATA_COOKING_PROGRESS_FIRST -> blockEntity.cookingProgressFirst = value;
                case DATA_COOKING_PROGRESS_SECOND -> blockEntity.cookingProgressSecond = value;
                case DATA_COOKING_DURATION_FIRST -> blockEntity.cookingDurationFirst = value;
                case DATA_COOKING_DURATION_SECOND -> blockEntity.cookingDurationSecond = value;
                case DATA_FIRE_TYPE -> blockEntity.fireType = ClibanoFireType.values()[value];
                case DATA_RESIDUE_FULLNESS -> blockEntity.residuesStorage.setTotalAmount(value);
                default -> {
                    blockEntity.residuesStorage.getResidueTypeAmountMap().put(ResiduesStorage.RESIDUE_TYPES.get(index - BASE_DATA_COUNT), value);
                }
            }
        }

        @Override
        public int getCount() {
            return FULL_DATA_COUNT;
        }
    };
    private Direction frontDirection = Direction.NORTH;
    private boolean wasLit = false;

    private final LazyOptional<IItemHandler> topInputHandler = LazyOptional.of(() -> new ClibanoItemHandler(this.getItemStackHandler(), Direction.UP));;
    private final LazyOptional<IItemHandler> sideInputHandler = LazyOptional.of(() -> new ClibanoItemHandler(this.getItemStackHandler(), Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    private final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> new ClibanoItemHandler(this.getItemStackHandler(), Direction.DOWN));

    public ClibanoMainBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLIBANO_MAIN.get(), pos, state, ClibanoMenu.SLOT_COUNT, (slot, stack) -> {
            if (slot == ClibanoMenu.SOUL_SLOT) {
                return ClibanoFireType.fromItem(stack) != ClibanoFireType.FIRE;
            } else if (slot == ClibanoMenu.FUEL_SLOT) {
                return ForgeHooks.getBurnTime(stack, RecipeType.BLASTING) > 0 || FurnaceFuelSlot.isBucket(stack);
            }

            return !slot.equals(ClibanoMenu.RESULT_SLOTS.getFirst()) && !slot.equals(ClibanoMenu.RESULT_SLOTS.getSecond());
        });
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ClibanoMainBlockEntity blockEntity) {
        Container firstSlot = new SimpleContainer(blockEntity.getStack(ClibanoMenu.INPUT_SLOTS.getFirst()));
        Container secondSlot = new SimpleContainer(blockEntity.getStack(ClibanoMenu.INPUT_SLOTS.getSecond()));

        ClibanoRecipe firstRecipe = level.getRecipeManager().getRecipeFor(RECIPE_TYPE, firstSlot, level).orElse(null);
        ClibanoRecipe secondRecipe = level.getRecipeManager().getRecipeFor(RECIPE_TYPE, secondSlot, level).orElse(null);

        boolean isLit = blockEntity.burnTime > 0;

        ClibanoFireType newFireType = blockEntity.getFireTypeFromInput();
        ClibanoFireType currentHighestType = newFireType.ordinal() > blockEntity.fireType.ordinal() ? newFireType : blockEntity.fireType;

        RegistryAccess registryAccess = level.registryAccess();

        boolean canSmeltFirst = firstRecipe != null && blockEntity.canBurn(registryAccess, firstRecipe, currentHighestType, blockEntity.getMaxStackSize(), ClibanoMenu.INPUT_SLOTS.getFirst());
        boolean canSmeltSecond = secondRecipe != null && blockEntity.canBurn(registryAccess, secondRecipe, currentHighestType, blockEntity.getMaxStackSize(), ClibanoMenu.INPUT_SLOTS.getSecond());

        RandomSource random = level.getRandom();

        blockEntity.residuesStorage.tick(level, blockEntity);

        if (blockEntity.soulTime != 0) {
            blockEntity.soulTime--;

            if (blockEntity.soulTime == 0) {
                blockEntity.changeFireType(level, ClibanoFireType.FIRE, firstRecipe, secondRecipe);
            }
        } else if ((canSmeltFirst || canSmeltSecond) && newFireType != ClibanoFireType.FIRE) {
            blockEntity.soulTime = SOUL_DURATION;

            blockEntity.changeFireType(level, newFireType, firstRecipe, secondRecipe);
            blockEntity.getStack(ClibanoMenu.SOUL_SLOT).shrink(1);
        }

        if (isLit) {
            blockEntity.burnTime--;
        } else {
            if (canSmeltFirst || canSmeltSecond) {
                ItemStack fuel = blockEntity.getStack(ClibanoMenu.FUEL_SLOT);

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
                blockEntity.finishRecipe(registryAccess, firstRecipe, random, ClibanoMenu.INPUT_SLOTS.getFirst());
            }

            blockEntity.setChanged();
        } else {
            blockEntity.cookingProgressFirst = 0;
        }

        if (canSmeltSecond) {
            blockEntity.cookingDurationSecond = secondRecipe.getCookingTime(blockEntity.fireType);

            blockEntity.cookingProgressSecond++;

            if (blockEntity.cookingProgressSecond == blockEntity.cookingDurationSecond) {
                blockEntity.finishRecipe(registryAccess, secondRecipe, random, ClibanoMenu.INPUT_SLOTS.getSecond());
            }

            blockEntity.setChanged();
        } else {
            blockEntity.cookingProgressSecond = 0;
        }

        blockEntity.wasLit = true;
    }

    /**
     * @return The fire type that matches the item inputted in the {@link ClibanoMenu#SOUL_SLOT}.
     */
    private ClibanoFireType getFireTypeFromInput() {
        ItemStack soul = this.getStack(ClibanoMenu.SOUL_SLOT);

        if (!soul.isEmpty()) {
            return ClibanoFireType.fromItem(soul);
        }

        return ClibanoFireType.FIRE;
    }

    private static void createExperience(ServerLevel level, Vec3 position, int count, float experience) {
        int i = Mth.floor(count * experience);
        float f = Mth.frac(count * experience);

        if (f != 0.0F && Math.random() < f) {
            i++;
        }

        ExperienceOrb.award(level, position, i);
    }

    /**
     * Checks if the given recipe can be used at the moment.
     * To be considered usable one of the result slots must be empty or the result of the recipe must fit into one of the existing stacks.
     *
     * @param recipe   the recipe to check
     * @param fireType the {@link ClibanoFireType} that will be active when the recipe gets started
     * @param maxCount the maximum stack size of the result
     * @param slot     the input slot to check
     * @return true if the recipe can be used
     */
    private boolean canBurn(RegistryAccess registryAccess, ClibanoRecipe recipe, ClibanoFireType fireType, int maxCount, int slot) {
        if (this.getStack(slot).isEmpty()) {
            return false;
        }

        ItemStack stack = recipe.getResultItem(registryAccess);

        if (stack.isEmpty() || fireType.ordinal() < recipe.getRequiredFireType().ordinal()) {
            return false;
        }

        ItemStack resultStack = this.getStack(ClibanoMenu.RESULT_SLOTS.getFirst());
        ItemStack secondResultStack = this.getStack(ClibanoMenu.RESULT_SLOTS.getSecond());

        if (resultStack.isEmpty() || secondResultStack.isEmpty()) {
            return true;
        } else if (!ItemStack.isSameItem(resultStack, stack) && !ItemStack.isSameItem(secondResultStack, stack)) {
            return false;
        } else if (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= maxCount && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
            return true;
        } else if (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= maxCount && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
            return true;
        }

        return (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= stack.getMaxStackSize()) || (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= stack.getMaxStackSize());
    }

    /**
     * Finishes the given recipe.
     * The result is added to one of the result slots and the input slot is cleared. The cooking progress is reset.
     *
     * @param recipe the recipe to finish
     * @param random the random instance
     * @param slot   the slot where the recipe input was placed in
     */
    private void finishRecipe(RegistryAccess registryAccess, ClibanoRecipe recipe, RandomSource random, int slot) {
        ItemStack stack = recipe.getResultItem(registryAccess);

        this.getStack(slot).shrink(1);

        if (slot == ClibanoMenu.INPUT_SLOTS.getFirst()) {
            this.cookingProgressFirst = 0;
        } else {
            this.cookingProgressSecond = 0;
        }

        if (stack.isEmpty()) {
            return;
        }

        ItemStack resultStack = this.getStack(ClibanoMenu.RESULT_SLOTS.getFirst());
        ItemStack secondResultStack = this.getStack(ClibanoMenu.RESULT_SLOTS.getSecond());

        if (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
            resultStack.grow(stack.getCount());
        } else if (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
            secondResultStack.grow(stack.getCount());
        } else if (resultStack.isEmpty()) {
            this.setStack(ClibanoMenu.RESULT_SLOTS.getFirst(), stack.copy());
        } else if (secondResultStack.isEmpty()) {
            this.setStack(ClibanoMenu.RESULT_SLOTS.getSecond(), stack.copy());
        }

        this.addResidue(recipe, random);

        this.setRecipeUsed(recipe);
    }

    /**
     * Adds residues to the {@link ResiduesStorage} of the clibano if the recipe can generate residues. <br>
     * Residues only get generated if the fire type is not the default one.
     *
     * @param recipe the current recipe
     */
    private void addResidue(ClibanoRecipe recipe, RandomSource random) {
        if (this.fireType == ClibanoFireType.FIRE) {
            return;
        }

        ClibanoRecipe.ResidueInfo residueInfo = recipe.getResidueInfo();

        if (residueInfo == ClibanoRecipe.ResidueInfo.NONE) {
            return;
        }

        if (random.nextDouble() < residueInfo.chance()) {
            this.residuesStorage.increaseType(residueInfo.getType(), 1);
        }
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
            centerType = this.fireType.getCenterType();
            sideType = this.fireType.getSideType();
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
        return Component.translatable("container.forbidden_arcanus.clibano");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull MenuCreationContext context) {
        return new ClibanoMenu(containerId, this.getItemStackHandler(), this.containerData, context);
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);

        this.saveInventory(tag);

        tag.putInt("SoulTime", this.soulTime);
        tag.putInt("BurnTime", this.burnTime);
        tag.putInt("CookingProgressFirst", this.cookingProgressFirst);
        tag.putInt("CookingProgressSecond", this.cookingProgressSecond);
        tag.putInt("CookingDurationFirst", this.cookingDurationFirst);
        tag.putInt("CookingDurationSecond", this.cookingDurationSecond);

        tag.putString("FireType", this.fireType.getSerializedName());

        tag.putString("FrontDirection", this.frontDirection.getName());

        CompoundTag recipesUsed = new CompoundTag();

        this.recipesUsed.forEach((resourceLocation, integer) -> {
            recipesUsed.putInt(resourceLocation.toString(), integer);
        });

        tag.put("RecipesUsed", recipesUsed);

        if (this.residuesStorage.shouldBeSaved()) {
            this.residuesStorage.save(tag);
        }
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);

        this.loadInventory(tag);

        this.soulTime = tag.getInt("SoulTime");
        this.burnTime = tag.getInt("BurnTime");
        this.burnDuration = this.getBurnDuration(this.getStack(ClibanoMenu.FUEL_SLOT));
        this.cookingProgressFirst = tag.getInt("CookingProgressFirst");
        this.cookingProgressSecond = tag.getInt("CookingProgressSecond");
        this.cookingDurationFirst = tag.getInt("CookingDurationFirst");
        this.cookingDurationSecond = tag.getInt("CookingDurationSecond");

        this.fireType = ClibanoFireType.byName(tag.getString("FireType")).orElse(ClibanoFireType.FIRE);

        this.frontDirection = Direction.byName(tag.getString("FrontDirection"));

        CompoundTag recipesUsed = tag.getCompound("RecipesUsed");

        for (String recipe : recipesUsed.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(recipe), tag.getInt(recipe));
        }

        this.residuesStorage.load(tag);
    }

    public void setSoulTime(int duration) {
        this.soulTime = duration;
    }

    protected int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }

        return ForgeHooks.getBurnTime(fuel, RECIPE_TYPE);
    }

    @Override
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe == null) {
            return;
        }

        this.recipesUsed.addTo(recipe.getId(), 1);
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        player.awardRecipes(this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position()));

        this.recipesUsed.clear();
    }

    public Collection<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<Recipe<?>> list = new ArrayList<>();

        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                ClibanoMainBlockEntity.createExperience(level, position, entry.getIntValue(), ((ClibanoRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
        if (this.remove || this.level == null) {
            return super.getCapability(cap, side);
        }

        if (cap.equals(ForgeCapabilities.ITEM_HANDLER) && side != null) {
            if (side == Direction.DOWN) {
                return this.outputHandler.cast();
            } else if (side == Direction.UP) {
                return this.topInputHandler.cast();
            } else {
                return this.sideInputHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }
}
