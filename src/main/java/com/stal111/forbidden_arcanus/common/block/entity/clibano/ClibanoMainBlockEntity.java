package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.block.clibano.AbstractClibanoFrameBlock;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.ClibanoAccessor;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.ClibanoSmeltLogic;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.DefaultSmeltLogic;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.DoubleSmeltLogic;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerAccessor;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.MultiplySoulDurationEffect;
import com.stal111.forbidden_arcanus.common.network.clientbound.SetClibanoResiduesPayload;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import net.valhelsia.valhelsia_core.api.common.block.entity.neoforge.ValhelsiaContainerBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Clibano Main Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoMainBlockEntity extends ValhelsiaContainerBlockEntity<ClibanoMainBlockEntity> implements RecipeCraftingHolder, ClibanoAccessor {

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
    public static final int DATA_IS_DOUBLE_RECIPE = 9;

    public static final int DATA_COUNT = 10;

    public static final RecipeType<ClibanoRecipe> RECIPE_TYPE = ModRecipeTypes.CLIBANO_COMBUSTION.get();

    private final ResiduesStorage residuesStorage = new ResiduesStorage(() -> this.level);
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final CachedRecipeCheck quickCheck;

    private int soulTime;
    private int burnTime;
    private int burnDuration;

    private ClibanoFireType fireType = ClibanoFireType.FIRE;
    private ClibanoFireType nextFireType = ClibanoFireType.FIRE;

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            ClibanoMainBlockEntity blockEntity = ClibanoMainBlockEntity.this;

            return switch (index) {
                case DATA_SOUL_TIME -> blockEntity.soulTime;
                case DATA_BURN_TIME -> blockEntity.burnTime;
                case DATA_BURN_DURATION -> blockEntity.burnDuration;
                case DATA_COOKING_PROGRESS_FIRST -> blockEntity.logic.cookingProgress[0];
                case DATA_COOKING_PROGRESS_SECOND -> blockEntity.logic.cookingProgress[1];
                case DATA_COOKING_DURATION_FIRST -> blockEntity.logic.cookingDuration[0];
                case DATA_COOKING_DURATION_SECOND -> blockEntity.logic.cookingDuration[1];
                case DATA_FIRE_TYPE -> blockEntity.fireType.ordinal();
                case DATA_RESIDUE_FULLNESS -> blockEntity.residuesStorage.getTotalAmount();
                case DATA_IS_DOUBLE_RECIPE -> blockEntity.logic instanceof DoubleSmeltLogic ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            ClibanoMainBlockEntity blockEntity = ClibanoMainBlockEntity.this;

            switch (index) {
                case DATA_SOUL_TIME -> blockEntity.setSoulTime(value);
                case DATA_BURN_TIME -> blockEntity.burnTime = value;
                case DATA_BURN_DURATION -> blockEntity.burnDuration = value;
                case DATA_COOKING_PROGRESS_FIRST -> blockEntity.logic.cookingProgress[0] = value;
                case DATA_COOKING_PROGRESS_SECOND -> blockEntity.logic.cookingProgress[1] = value;
                case DATA_COOKING_DURATION_FIRST -> blockEntity.logic.cookingDuration[0] = value;
                case DATA_COOKING_DURATION_SECOND -> blockEntity.logic.cookingDuration[1] = value;
                case DATA_FIRE_TYPE -> blockEntity.fireType = ClibanoFireType.values()[value];
                case DATA_RESIDUE_FULLNESS -> blockEntity.residuesStorage.setTotalAmount(value);
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    private Direction frontDirection = Direction.NORTH;
    private boolean wasLit = false;

    private ClibanoSmeltLogic logic = new DefaultSmeltLogic(this, null, null);
    private @Nullable EnhancerDefinition enhancer;

    public ClibanoMainBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLIBANO_MAIN.get(), pos, state, ClibanoMenu.SLOT_COUNT, (slot, stack) -> {
            if (slot == ClibanoMenu.SOUL_SLOT) {
                return ClibanoFireType.fromItem(stack) != ClibanoFireType.FIRE;
            } else if (slot == ClibanoMenu.FUEL_SLOT) {
                return stack.getBurnTime(RecipeType.BLASTING) > 0 || FurnaceFuelSlot.isBucket(stack);
            }

            return !slot.equals(ClibanoMenu.RESULT_SLOTS.getFirst()) && !slot.equals(ClibanoMenu.RESULT_SLOTS.getSecond());
        });
        this.quickCheck = new CachedRecipeCheck(() -> this.enhancer != null ? List.of(this.enhancer) : Collections.emptyList());
    }

    @Override
    public void onLoad() {
        this.nextFireType = this.getFireTypeFromInput();
        this.enhancer = this.updateEnhancer();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ClibanoMainBlockEntity blockEntity) {
        Container combinedContainer = new SimpleContainer(blockEntity.getStack(ClibanoMenu.INPUT_SLOTS.getFirst()), blockEntity.getStack(ClibanoMenu.INPUT_SLOTS.getSecond()));

        Container firstSlot = new SimpleContainer(blockEntity.getStack(ClibanoMenu.INPUT_SLOTS.getFirst()));
        Container secondSlot = new SimpleContainer(blockEntity.getStack(ClibanoMenu.INPUT_SLOTS.getSecond()));

        List<RecipeHolder<ClibanoRecipe>> recipeHolders = new ArrayList<>();

        blockEntity.quickCheck.getAlloyRecipe(combinedContainer, level).ifPresentOrElse(recipeHolder -> {
            recipeHolders.add(recipeHolder);

            if (!(blockEntity.logic instanceof DoubleSmeltLogic)) {
                blockEntity.logic = new DoubleSmeltLogic(blockEntity, recipeHolder);
            }
        }, () -> {
            RecipeHolder<ClibanoRecipe> firstRecipe = blockEntity.quickCheck.getRecipeFor(firstSlot, level).orElse(null);
            RecipeHolder<ClibanoRecipe> secondRecipe = blockEntity.quickCheck.getRecipeFor(secondSlot, level).orElse(null);

            recipeHolders.add(firstRecipe);
            recipeHolders.add(secondRecipe);

            if (!(blockEntity.logic instanceof DefaultSmeltLogic)) {
                blockEntity.logic = new DefaultSmeltLogic(blockEntity, firstRecipe, secondRecipe);
            }
        });

        blockEntity.logic.updateRecipes(recipeHolders);

        boolean isLit = blockEntity.burnTime > 0;
        boolean canSmelt = blockEntity.logic.canSmelt();
        ItemStack fuel = blockEntity.getStack(ClibanoMenu.FUEL_SLOT);

        blockEntity.residuesStorage.tick(blockEntity);

        if (blockEntity.soulTime != 0) {
            blockEntity.soulTime--;

            if (blockEntity.soulTime == 0) {
                blockEntity.changeFireType(level, ClibanoFireType.FIRE);
            }
        } else if (canSmelt && (isLit || !fuel.isEmpty()) && blockEntity.nextFireType != ClibanoFireType.FIRE) {
            blockEntity.consumeSoul(level);
        }

        blockEntity.logic.tick(isLit);

        if (isLit) {
            blockEntity.burnTime--;
        } else {
            if (canSmelt) {
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

            if (blockEntity.wasLit) {
                blockEntity.updateAppearance(level);
            }

            blockEntity.wasLit = false;

            return;
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
     * @param inputSlot     the input slot to check
     * @return true if the recipe can be used
     */
    @Override
    public boolean canSmelt(@Nullable RecipeHolder<ClibanoRecipe> recipe, ClibanoInputSlot inputSlot) {
        if (recipe == null || this.level == null || inputSlot.apply(slot -> {
            return this.getStack(slot).isEmpty();
        })) {
            return false;
        }

        ItemStack stack = recipe.value().getResultItem(this.level.registryAccess());

        if (stack.isEmpty() || (this.soulTime == 0 ? this.nextFireType : this.fireType).ordinal() < recipe.value().getRequiredFireType().ordinal()) {
            return false;
        }

        ItemStack resultStack = this.getStack(ClibanoMenu.RESULT_SLOTS.getFirst());
        ItemStack secondResultStack = this.getStack(ClibanoMenu.RESULT_SLOTS.getSecond());

        if (resultStack.isEmpty() || secondResultStack.isEmpty()) {
            return true;
        } else if (!ItemStack.isSameItem(resultStack, stack) && !ItemStack.isSameItem(secondResultStack, stack)) {
            return false;
        } else if (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= this.getMaxStackSize() && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
            return true;
        } else if (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= this.getMaxStackSize() && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
            return true;
        }

        return (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= stack.getMaxStackSize()) || (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= stack.getMaxStackSize());
    }

    /**
     * Finishes the given recipe.
     * The result is added to one of the result slots and the input slot is cleared. The cooking progress is reset.
     *
     * @param recipe the recipe to finish
     * @param inputSlot   the slot where the recipe input was placed in
     */
    @Override
    public void finishRecipe(RecipeHolder<ClibanoRecipe> recipe, ClibanoInputSlot inputSlot) {
        if (this.level == null) {
            return;
        }

        ItemStack stack = recipe.value().getResultItem(this.level.registryAccess());

        inputSlot.apply(slot -> {
            this.getStack(slot).shrink(1);
        });

        for (int i : inputSlot.getIndex()) {
            this.logic.resetCookingProgress(i);
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

        this.addResidue(recipe.value(), this.level.getRandom());

        this.setRecipeUsed(recipe);
    }

    @Override
    public int getCookingTime(RecipeHolder<ClibanoRecipe> recipe) {
        return recipe.value().getCookingTime(this.fireType);
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

        ResidueChance chance = recipe.getResidueChance();

        if (chance != null && random.nextDouble() < chance.chance()) {
            this.residuesStorage.increaseType(chance.type(), 1);

            PacketDistributor.TRACKING_CHUNK.with(this.level.getChunkAt(this.worldPosition)).send(new SetClibanoResiduesPayload(this.residuesStorage.getResidueTypeAmountMap()));
        }
    }

    /**
     * Changes the current {@link ClibanoFireType} of the clibano and updates the cooking durations accordingly.
     *
     * @param level        the level the clibano is in
     * @param fireType     the new ClibanoFireType
     */
    private void changeFireType(Level level, ClibanoFireType fireType) {
        this.fireType = fireType;

        this.logic.onFireTypeChange(fireType);

        this.updateAppearance(level);

        this.setChanged();
    }

    /**
     * Updates the appearance of the clibano frame blocks around the main clibano block.
     *
     * @param level the level the clibano is in
     */
    private void updateAppearance(Level level) {
        BlockPos front = this.worldPosition.relative(this.frontDirection);

        this.updateAppearance(level, front);

        for (Direction direction : Direction.values()) {
            if (direction.getAxis() != this.frontDirection.getAxis()) {
                this.updateAppearance(level, front.relative(direction));
            }
        }
    }

    /**
     * Updates the appearance of the clibano frame block at the given position.
     *
     * @param level the level the clibano is in
     * @param pos   the position of the block
     */
    private void updateAppearance(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof AbstractClibanoFrameBlock clibanoFrameBlock) {
            level.setBlockAndUpdate(pos, clibanoFrameBlock.updateAppearance(state, this.burnTime > 0, this.fireType));
        }
    }

    /**
     * Consumes a soul from the {@link ClibanoMenu#SOUL_SLOT} and updates the fire type of the clibano.
     *
     * @param level the level the clibano is in
     */
    private void consumeSoul(Level level) {
        this.soulTime = SOUL_DURATION;

        if (this.enhancer != null) {
            this.enhancer.getEffects(EnhancerTarget.CLIBANO).forEach(enhancerEffect -> {
                if (enhancerEffect instanceof MultiplySoulDurationEffect effect) {
                    this.soulTime = effect.getModifiedValue(this.soulTime);
                }
            });
        }

        this.changeFireType(level, this.nextFireType);

        this.getStack(ClibanoMenu.SOUL_SLOT).shrink(1);
        this.onSlotChanged(ClibanoMenu.SOUL_SLOT);
    }

    public void setFrontDirection(Direction direction) {
        this.frontDirection = direction;
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.forbidden_arcanus.clibano");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull MenuCreationContext context) {
        return new ClibanoMenu(containerId, this.getItemStackHandler(), this.containerData, this.residuesStorage.getResidueTypeAmountMap(), context);
    }

    @Override
    protected void onSlotChanged(int slot) {
        if (slot == ClibanoMenu.SOUL_SLOT) {
            this.nextFireType = this.getFireTypeFromInput();
        } else if (slot == ClibanoMenu.ENHANCER_SLOT) {
            this.enhancer = this.updateEnhancer();
        }
    }

    private @Nullable EnhancerDefinition updateEnhancer() {
        return EnhancerCache.get(this.getStack(ClibanoMenu.ENHANCER_SLOT).getItem()).orElse(null);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        this.saveInventory(tag);

        tag.putInt("soul_time", this.soulTime);
        tag.putInt("burn_time", this.burnTime);

        tag.putIntArray("cooking_times", this.logic.cookingProgress);
        tag.putIntArray("cooking_durations", this.logic.cookingDuration);

        tag.putString("fire_type", this.fireType.getSerializedName());

        tag.putString("front_direction", this.frontDirection.getName());

        CompoundTag recipesUsed = new CompoundTag();

        this.recipesUsed.forEach((resourceLocation, integer) -> {
            recipesUsed.putInt(resourceLocation.toString(), integer);
        });

        tag.put("recipes_used", recipesUsed);

        if (this.residuesStorage.shouldBeSaved() && this.level != null) {
            this.residuesStorage.save(tag);
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        this.loadInventory(tag);

        this.soulTime = tag.getInt("soul_time");
        this.burnTime = tag.getInt("burn_time");
        this.burnDuration = this.getBurnDuration(this.getStack(ClibanoMenu.FUEL_SLOT));

        this.logic.cookingProgress = tag.getIntArray("cooking_times");
        this.logic.cookingDuration = tag.getIntArray("cooking_durations");

        this.fireType = ClibanoFireType.CODEC.byName(tag.getString("fire_type"), ClibanoFireType.FIRE);

        this.frontDirection = Direction.byName(tag.getString("front_direction"));

        CompoundTag recipesUsed = tag.getCompound("recipes_used");

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

        return fuel.getBurnTime(RECIPE_TYPE);
    }

    public ResiduesStorage getResiduesStorage() {
        return this.residuesStorage;
    }

    @Override
    @Nullable
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe == null) {
            return;
        }

        this.recipesUsed.addTo(recipe.id(), 1);
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        player.awardRecipes(this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position()));

        this.recipesUsed.clear();
    }

    public Collection<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<RecipeHolder<?>> list = new ArrayList<>();

        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                ClibanoMainBlockEntity.createExperience(level, position, entry.getIntValue(), ((ClibanoRecipe) recipe.value()).getExperience());
            });
        }

        return list;
    }

    public static class CachedRecipeCheck implements RecipeManager.CachedCheck<Container, ClibanoRecipe> {

        @Nullable
        private RecipeHolder<ClibanoRecipe> lastAlloyRecipe;
        private final Queue<RecipeHolder<ClibanoRecipe>> lastRecipes = new LinkedList<>();

        private final EnhancerAccessor accessor;

        public CachedRecipeCheck(EnhancerAccessor accessor) {
            this.accessor = accessor;
        }

        @Override
        public @NotNull Optional<RecipeHolder<ClibanoRecipe>> getRecipeFor(@NotNull Container container, @NotNull Level level) {
            while (!this.lastRecipes.isEmpty()) {
                Optional<RecipeHolder<ClibanoRecipe>> optional = this.checkRecipe(this.lastRecipes.poll(), container, level);

                if (optional.isPresent()) {
                    return optional;
                }
            }

            Optional<RecipeHolder<ClibanoRecipe>> optional = level.getRecipeManager().getAllRecipesFor(RECIPE_TYPE).stream().filter(recipe -> !recipe.value().isDoubleRecipe() && recipe.value().matches(container, level, this.accessor.getEnhancers())).findFirst();

            optional.ifPresent(this.lastRecipes::add);

            return optional;
        }

        public Optional<RecipeHolder<ClibanoRecipe>> getAlloyRecipe(Container container, Level level) {
            return Optional.ofNullable(this.checkRecipe(this.lastAlloyRecipe, container, level).orElseGet(() -> {
                for (RecipeHolder<ClibanoRecipe> recipe : level.getRecipeManager().getAllRecipesFor(RECIPE_TYPE)) {
                    if (recipe.value().isDoubleRecipe() && recipe.value().matches(container, level, this.accessor.getEnhancers())) {
                        this.lastAlloyRecipe = recipe;

                        return recipe;
                    }

                    this.lastRecipes.add(recipe);
                }

                return null;
            }));
        }

        private Optional<RecipeHolder<ClibanoRecipe>> checkRecipe(@Nullable RecipeHolder<ClibanoRecipe> recipeHolder, Container container, Level level) {
            if (recipeHolder != null && recipeHolder.value().matches(container, level, this.accessor.getEnhancers())) {
                return Optional.of(recipeHolder);
            }

            return Optional.empty();
        }
    }
}
