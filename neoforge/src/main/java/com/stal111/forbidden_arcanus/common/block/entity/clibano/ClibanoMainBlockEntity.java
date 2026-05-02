package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.clibano.AbstractClibanoFrameBlock;
import com.stal111.forbidden_arcanus.common.block.clibano.ClibanoMainPartBlock;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.ClibanoAccessor;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.ClibanoSmeltLogic;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.logic.DefaultSmeltLogic;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.BuiltinMoltenMaterialTypes;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.EssenceInputResourceHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.FuelItemHandler;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorages;
import com.stal111.forbidden_arcanus.common.inventory.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenuOld;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.MultiplySoulDurationEffect;
import com.stal111.forbidden_arcanus.common.network.clientbound.SetClibanoResiduesPayload;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Clibano Main Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoMainBlockEntity extends BlockEntity implements MenuProvider, RecipeCraftingHolder, ClibanoAccessor, EssenceAccess {

    public static final int SOUL_DURATION = 2700;

    public static final int DATA_LIT_TIME_REMAINING = 0;
    public static final int DATA_LIT_TOTAL_TIME = 1;
    public static final int DATA_COOKING_TIME_1 = 2;
    public static final int DATA_COOKING_TIME_2 = 3;
    public static final int DATA_COOKING_TOTAL_TIME_1 = 4;
    public static final int DATA_COOKING_TOTAL_TIME_2 = 5;
    public static final int DATA_ECTOPLASM_AMOUNT = 6;

    public static final int DATA_COUNT = 10;

    public static final RecipeType<ClibanoRecipe> RECIPE_TYPE = ModRecipeTypes.CLIBANO_COMBUSTION.get();

    private static final Component NAME = Component.translatable("container.forbidden_arcanus.clibano");

    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);

    private final ResiduesStorage residuesStorage = new ResiduesStorage();
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, ClibanoRecipe> quickCheck = RecipeManager.createCheck(RECIPE_TYPE);

    private final FuelItemHandler fuelInventory = new FuelItemHandler(stack -> getBurnDuration(stack, this.level) > 0, _ -> this.setChanged());
    private final ItemStacksResourceHandler inputInventory = new ItemStacksResourceHandler(2) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            ClibanoMainBlockEntity.this.onInputChange(index, previousContents);
        }
    };
    private final EssenceInputResourceHandler essenceInputInventory = new EssenceInputResourceHandler(EssenceType.ECTOPLASM);

    public MaterialStorage storedMaterials = MaterialStorage.createEmpty();
    private EssenceStorage essenceStorage = EssenceStorages.CLIBANO_ECTOPLASM_EMPTY;

    private int litTimeRemaining;
    private int litTotalTime;

    private int[] cookingTimes = new int[2];
    private int[] cookingTotalTimes = new int[2];

    private int soulTime;

    private ClibanoFireType fireType = ClibanoFireType.FIRE;
    private ClibanoFireType nextFireType = ClibanoFireType.FIRE;

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            ClibanoMainBlockEntity blockEntity = ClibanoMainBlockEntity.this;

            return switch (index) {
                case DATA_LIT_TIME_REMAINING -> blockEntity.litTimeRemaining;
                case DATA_LIT_TOTAL_TIME -> blockEntity.litTotalTime;
                case DATA_COOKING_TIME_1 -> blockEntity.cookingTimes[0];
                case DATA_COOKING_TIME_2 -> blockEntity.cookingTimes[1];
                case DATA_COOKING_TOTAL_TIME_1 -> blockEntity.cookingTotalTimes[0];
                case DATA_COOKING_TOTAL_TIME_2 -> blockEntity.cookingTotalTimes[1];
                case DATA_ECTOPLASM_AMOUNT -> blockEntity.getEssenceAmount(EssenceType.ECTOPLASM);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            ClibanoMainBlockEntity blockEntity = ClibanoMainBlockEntity.this;

            switch (index) {
                case DATA_LIT_TIME_REMAINING -> blockEntity.litTimeRemaining = value;
                case DATA_LIT_TOTAL_TIME -> blockEntity.litTotalTime = value;
                case DATA_COOKING_TIME_1 -> blockEntity.cookingTimes[0] = value;
                case DATA_COOKING_TIME_2 -> blockEntity.cookingTimes[1] = value;
                case DATA_COOKING_TOTAL_TIME_1 -> blockEntity.cookingTotalTimes[0] = value;
                case DATA_COOKING_TOTAL_TIME_2 -> blockEntity.cookingTotalTimes[1] = value;
                case DATA_ECTOPLASM_AMOUNT -> blockEntity.setEssenceAmount(EssenceType.ECTOPLASM, value);
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    };

    private Direction frontDirection = Direction.NORTH;
    private boolean wasLit = false;

    private ClibanoSmeltLogic logic = new DefaultSmeltLogic(this, null, null);
    private @Nullable Holder<EnhancerDefinition> enhancer;

    public ClibanoMainBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLIBANO_MAIN.get(), pos, state
//                , ClibanoMenuOld.SLOT_COUNT
//                , (slot, stack) -> {
//            if (slot == ClibanoMenuOld.SOUL_SLOT) {
//                return ClibanoFireType.fromItem(stack) != ClibanoFireType.FIRE;
//            } else if (slot == ClibanoMenuOld.FUEL_SLOT) {
//                //TODO
//                return stack.getBurnTime(RecipeType.BLASTING, null) > 0 || FurnaceFuelSlot.isBucket(stack);
//            }
//
//            return !slot.equals(ClibanoMenuOld.RESULT_SLOTS.getFirst()) && !slot.equals(ClibanoMenuOld.RESULT_SLOTS.getSecond());
//        }
        );
    }

    @Override
    public void onLoad() {
//        this.nextFireType = this.getFireTypeFromInput();
//        this.enhancer = this.updateEnhancer();

        if (this.getLevel() != null) {
            RegistryAccess registryAccess = this.getLevel().registryAccess();

            this.storedMaterials.insert(registryAccess.holderOrThrow(BuiltinMoltenMaterialTypes.IRON), 10);
            this.storedMaterials.insert(registryAccess.holderOrThrow(BuiltinMoltenMaterialTypes.DIAMOND), 5);
            this.storedMaterials.insert(registryAccess.holderOrThrow(BuiltinMoltenMaterialTypes.GOLD), 5);
            this.storedMaterials.insert(registryAccess.holderOrThrow(BuiltinMoltenMaterialTypes.COPPER), 5);
            this.storedMaterials.insert(registryAccess.holderOrThrow(BuiltinMoltenMaterialTypes.LAPIS_LAZULI), 5);
            this.storedMaterials.insert(registryAccess.holderOrThrow(BuiltinMoltenMaterialTypes.EMERALD), 5);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            ClibanoMainPartBlock.dismantle(this.level, pos);
        }
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, ClibanoMainBlockEntity blockEntity) {
        blockEntity.essenceInputInventory.tick(blockEntity, level.registryAccess());

        if (blockEntity.isLit()) {
            blockEntity.litTimeRemaining--;
        }

        ItemStack fuel = blockEntity.fuelInventory.getStack();

        if (!blockEntity.isLit() && !fuel.isEmpty()) {
            blockEntity.litTimeRemaining = getBurnDuration(fuel, level);
            blockEntity.litTotalTime = blockEntity.litTimeRemaining;

            ItemStackTemplate remainder = fuel.getCraftingRemainder();

            if (remainder != null) {
                blockEntity.fuelInventory.setStack(remainder.create());
            } else {
                fuel.shrink(1);
            }
        }

        if (blockEntity.isLit()) {
            for (int i = 0; i < blockEntity.cookingTimes.length; i++) {
                if (blockEntity.cookingTotalTimes[i] != 0 && blockEntity.cookingTimes[i] < blockEntity.cookingTotalTimes[i]) {
                    blockEntity.cookingTimes[i]++;

                    if (blockEntity.cookingTimes[i] >= blockEntity.cookingTotalTimes[i]) {
                        blockEntity.cookingTimes[i] = 0;
                    }
                }
            }
        } else {
            for (int i = 0; i < blockEntity.cookingTimes.length; i++) {
                if (blockEntity.cookingTimes[i] != 0) {
                    blockEntity.cookingTimes[i] = Mth.clamp(blockEntity.cookingTimes[i] - 2, 0, blockEntity.cookingTotalTimes[i]);
                }
            }
        }


//        ClibanoRecipeInput combinedInput = new ClibanoRecipeInput(blockEntity.getItem(ClibanoMenuOld.INPUT_SLOTS.getFirst()), blockEntity.getItem(ClibanoMenuOld.INPUT_SLOTS.getSecond()));
//
//        ClibanoRecipeInput firstSlot = new ClibanoRecipeInput(blockEntity.getItem(ClibanoMenuOld.INPUT_SLOTS.getFirst()), ItemStack.EMPTY);
//        ClibanoRecipeInput secondSlot = new ClibanoRecipeInput(ItemStack.EMPTY, blockEntity.getItem(ClibanoMenuOld.INPUT_SLOTS.getSecond()));
//
//        List<RecipeHolder<ClibanoRecipe>> recipeHolders = new ArrayList<>();
//
//        if (blockEntity.burnDuration == 0) {
//            blockEntity.burnDuration = blockEntity.getBurnDuration(level.fuelValues(), blockEntity.getItem(ClibanoMenuOld.FUEL_SLOT));
//
//        }
//
//        blockEntity.quickCheck.getAlloyRecipe(combinedInput, level).ifPresentOrElse(recipeHolder -> {
//            recipeHolders.add(recipeHolder);
//
//            if (!(blockEntity.logic instanceof DoubleSmeltLogic)) {
//                blockEntity.logic = new DoubleSmeltLogic(blockEntity, recipeHolder);
//            }
//        }, () -> {
//            RecipeHolder<ClibanoRecipe> firstRecipe = blockEntity.quickCheck.getRecipeFor(firstSlot, level).orElse(null);
//            RecipeHolder<ClibanoRecipe> secondRecipe = blockEntity.quickCheck.getRecipeFor(secondSlot, level).orElse(null);
//
//            recipeHolders.add(firstRecipe);
//            recipeHolders.add(secondRecipe);
//
//            if (!(blockEntity.logic instanceof DefaultSmeltLogic)) {
//                blockEntity.logic = new DefaultSmeltLogic(blockEntity, firstRecipe, secondRecipe);
//            }
//        });
//
//        blockEntity.logic.updateRecipes(recipeHolders);
//
//        boolean isLit = blockEntity.burnTime > 0;
//        boolean canSmelt = blockEntity.logic.canSmelt();
//        ItemStack fuel = blockEntity.getItem(ClibanoMenuOld.FUEL_SLOT);
//
//        blockEntity.residuesStorage.tick(blockEntity);
//
//        if (blockEntity.soulTime != 0) {
//            blockEntity.soulTime--;
//
//            if (blockEntity.soulTime == 0) {
//                blockEntity.changeFireType(level, ClibanoFireType.FIRE);
//            }
//        } else if (canSmelt && (isLit || !fuel.isEmpty()) && blockEntity.nextFireType != ClibanoFireType.FIRE) {
//            blockEntity.consumeSoul(level);
//        }
//
//        blockEntity.logic.tick(isLit);
//
//        if (isLit) {
//            blockEntity.burnTime--;
//        } else {
//            if (canSmelt) {
//                blockEntity.burnDuration = 0;
//
//                if (!fuel.isEmpty()) {
//                    blockEntity.burnTime = blockEntity.getBurnDuration(level.fuelValues(), fuel);
//                    blockEntity.burnDuration = blockEntity.burnTime;
//
//                    fuel.shrink(1);
//
//                    if (!blockEntity.wasLit) {
//                        blockEntity.updateAppearance(level);
//                    }
//
//                    blockEntity.setChanged();
//                }
//            }
//
//            if (blockEntity.wasLit) {
//                blockEntity.updateAppearance(level);
//            }
//
//            blockEntity.wasLit = false;
//
//            return;
//        }
//
//        blockEntity.wasLit = true;
    }

    /**
     * @return The fire type that matches the item inputted in the {@link ClibanoMenuOld#SOUL_SLOT}.
     */
//    private ClibanoFireType getFireTypeFromInput() {
//        ItemStack soul = this.getItem(ClibanoMenuOld.SOUL_SLOT);
//
//        if (!soul.isEmpty()) {
//            return ClibanoFireType.fromItem(soul);
//        }
//
//        return ClibanoFireType.FIRE;
//    }

    private boolean isLit() {
        return this.litTimeRemaining > 0;
    }

    private void onInputChange(int index, ItemStack oldStack) {
        ItemStack stack = ItemUtil.getStack(this.inputInventory, index);

        if (!ItemStack.isSameItemSameComponents(stack, oldStack) && this.level instanceof ServerLevel serverLevel) {
            SingleRecipeInput input = new SingleRecipeInput(stack);

            this.quickCheck.getRecipeFor(input, serverLevel).ifPresent(recipeHolder -> {
                this.cookingTotalTimes[index] = recipeHolder.value().cookingTimes().get(this.fireType);
            });

            this.cookingTimes[index] = 0;
        }

        this.setChanged();
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
     * @param recipe    the recipe to check
     * @param inputSlot the input slot to check
     * @return true if the recipe can be used
     */
    @Override
    public boolean canSmelt(@Nullable RecipeHolder<ClibanoRecipe> recipe, ClibanoInputSlot inputSlot) {
        return false;
//        if (recipe == null || this.level == null || inputSlot.apply(slot -> {
//            return this.getItem(slot).isEmpty();
//        })) {
//            return false;
//        }
//
//        //TODO
//        ItemStack stack = recipe.value().assemble(null, this.level.registryAccess());
//
//        if (stack.isEmpty() || (this.soulTime == 0 ? this.nextFireType : this.fireType).ordinal() < recipe.value().requiredFireType().ordinal()) {
//            return false;
//        }
//
//        ItemStack resultStack = this.getItem(ClibanoMenuOld.RESULT_SLOTS.getFirst());
//        ItemStack secondResultStack = this.getItem(ClibanoMenuOld.RESULT_SLOTS.getSecond());
//
//        if (resultStack.isEmpty() || secondResultStack.isEmpty()) {
//            return true;
//        } else if (!ItemStack.isSameItem(resultStack, stack) && !ItemStack.isSameItem(secondResultStack, stack)) {
//            return false;
//        } else if (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= this.getMaxStackSize() && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
//            return true;
//        } else if (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= this.getMaxStackSize() && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
//            return true;
//        }
//
//        return (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= stack.getMaxStackSize()) || (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= stack.getMaxStackSize());
    }

    /**
     * Finishes the given recipe.
     * The result is added to one of the result slots and the input slot is cleared. The cooking progress is reset.
     *
     * @param recipe    the recipe to finish
     * @param inputSlot the slot where the recipe input was placed in
     */
    @Override
    public void finishRecipe(RecipeHolder<ClibanoRecipe> recipe, ClibanoInputSlot inputSlot) {
        if (this.level == null) {
            return;
        }

        //TODO
        ItemStack stack = recipe.value().assemble(null);

        inputSlot.apply(slot -> {
//            this.getItem(slot).shrink(1);
        });

        for (int i : inputSlot.getIndex()) {
            this.logic.resetCookingProgress(i);
        }

        if (stack.isEmpty()) {
            return;
        }

//        ItemStack resultStack = this.getItem(ClibanoMenuOld.RESULT_SLOTS.getFirst());
//        ItemStack secondResultStack = this.getItem(ClibanoMenuOld.RESULT_SLOTS.getSecond());
//
//        if (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
//            resultStack.grow(stack.getCount());
//        } else if (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
//            secondResultStack.grow(stack.getCount());
//        }
//        else if (resultStack.isEmpty()) {
//            this.setItem(ClibanoMenuOld.RESULT_SLOTS.getFirst(), stack.copy());
//        } else if (secondResultStack.isEmpty()) {
//            this.setItem(ClibanoMenuOld.RESULT_SLOTS.getSecond(), stack.copy());
//        }

        this.addResidue(recipe.value(), this.level.getRandom());

        this.setRecipeUsed(recipe);
    }

    @Override
    public int getCookingTime(RecipeHolder<ClibanoRecipe> recipe) {
        return recipe.value().cookingTimes().get(this.fireType);
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

        recipe.residueChance().ifPresent(chance -> {
            if (random.nextDouble() < chance.chance()) {
                this.residuesStorage.increaseType(chance.type(), 1);

                if (this.level instanceof ServerLevel serverLevel) {
                    PacketDistributor.sendToPlayersTrackingChunk(serverLevel, ChunkPos.containing(this.getBlockPos()), new SetClibanoResiduesPayload(this.residuesStorage));
                }
            }
        });
    }

    /**
     * Changes the current {@link ClibanoFireType} of the clibano and updates the cooking durations accordingly.
     *
     * @param level    the level the clibano is in
     * @param fireType the new ClibanoFireType
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
            level.setBlockAndUpdate(pos, clibanoFrameBlock.updateAppearance(state, this.isLit(), this.fireType));
        }
    }

    /**
     * Consumes a soul from the {@link ClibanoMenuOld#SOUL_SLOT} and updates the fire type of the clibano.
     *
     * @param level the level the clibano is in
     */
    private void consumeSoul(Level level) {
        this.soulTime = SOUL_DURATION;

        if (this.enhancer != null) {
            this.enhancer.value().getEffects(EnhancerTarget.CLIBANO).forEach(enhancerEffect -> {
                if (enhancerEffect instanceof MultiplySoulDurationEffect effect) {
                    this.soulTime = effect.getModifiedValue(this.soulTime);
                }
            });
        }

        this.changeFireType(level, this.nextFireType);

//        this.getItem(ClibanoMenuOld.SOUL_SLOT).shrink(1);
//        this.onSlotChanged(ClibanoMenuOld.SOUL_SLOT);
    }

    public void setFrontDirection(Direction direction) {
        this.frontDirection = direction;
    }

    //TODO
//    @Override
//    protected void onSlotChanged(int slot) {
//        if (slot == ClibanoMenuOld.SOUL_SLOT) {
//            this.nextFireType = this.getFireTypeFromInput();
//        } else if (slot == ClibanoMenuOld.ENHANCER_SLOT) {
//            this.enhancer = this.updateEnhancer();
//        }
//    }

//    private @Nullable Holder<EnhancerDefinition> updateEnhancer() {
//        return EnhancerHelper.getEnhancerHolder(this.getItem(ClibanoMenuOld.ENHANCER_SLOT)).orElse(null);
//    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        this.essenceInputInventory.serialize(output.child("essence_inputs"));

        output.store("stored_materials", MaterialStorage.CODEC, this.storedMaterials);
        output.store("ectoplasm", EssenceStorage.codec(EssenceType.ECTOPLASM).codec(), this.essenceStorage);

        output.putInt("lit_time_remaining", this.litTimeRemaining);
        output.putInt("lit_total_time", this.litTotalTime);

        output.putIntArray("cooking_times_spent", this.cookingTimes);
        output.putIntArray("cooking_total_times", this.cookingTotalTimes);

        //TODO
//        this.saveInventory(tag, lookupProvider);

        output.putInt("soul_time", this.soulTime);

        output.store("fire_type", ClibanoFireType.CODEC, this.fireType);
        output.store("front_direction", Direction.CODEC, this.frontDirection);

        output.store("RecipesUsed", RECIPES_USED_CODEC, this.recipesUsed);

        if (this.residuesStorage.shouldBeSaved() && this.level != null) {
            this.residuesStorage.save(output);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.essenceInputInventory.deserialize(input.childOrEmpty("essence_inputs"));

        this.storedMaterials = input.read("stored_materials", MaterialStorage.CODEC).orElse(MaterialStorage.createEmpty());
        this.essenceStorage = input.read("ectoplasm", EssenceStorage.codec(EssenceType.ECTOPLASM).codec()).orElse(EssenceStorages.CLIBANO_ECTOPLASM_EMPTY);

        this.litTimeRemaining = input.getIntOr("lit_time_remaining", 0);
        this.litTotalTime = input.getIntOr("lit_total_time", 0);

        this.cookingTimes = input.getIntArray("cooking_times_spent").orElse(new int[2]);
        this.cookingTotalTimes = input.getIntArray("cooking_total_times").orElse(new int[2]);

        //        this.loadInventory(tag, lookupProvider);

        this.soulTime = input.getIntOr("soul_time", 0);

        input.read("fire_type", ClibanoFireType.CODEC).ifPresent(fireType -> this.fireType = fireType);
        input.read("front_direction", Direction.CODEC).ifPresent(direction -> this.frontDirection = direction);

        this.recipesUsed.clear();
        this.recipesUsed.putAll(input.read("recipes_used", RECIPES_USED_CODEC).orElse(Map.of()));

        this.residuesStorage.load(input);
    }

    public void setSoulTime(int duration) {
        this.soulTime = duration;
    }

    public static int getBurnDuration(ItemStack stack, Level level) {
        return stack.getBurnTime(RECIPE_TYPE, level.fuelValues());
    }

    public ResiduesStorage getResiduesStorage() {
        return this.residuesStorage;
    }

    public MaterialStorage getStoredMaterials() {
        return this.storedMaterials;
    }

    @Override
    @Nullable
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            this.recipesUsed.addTo(recipe.id(), 1);
        }
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        player.awardRecipes(this.getRecipesToAwardAndPopExperience(player.level(), player.position()));

        this.recipesUsed.clear();
    }

    public Collection<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<RecipeHolder<?>> list = new ArrayList<>();

        for (Reference2IntMap.Entry<ResourceKey<Recipe<?>>> entry : this.recipesUsed.reference2IntEntrySet()) {
            level.recipeAccess().byKey(entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                ClibanoMainBlockEntity.createExperience(level, position, entry.getIntValue(), ((ClibanoRecipe) recipe.value()).getExperience());
            });
        }

        return list;
    }

    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ClibanoMenu(containerId, playerInventory, this.fuelInventory, this.inputInventory, this.essenceInputInventory, this.containerData, ContainerLevelAccess.create(this.level, this.getBlockPos()), this.storedMaterials);
    }

    @Override
    public EssenceStorage getEssence(EssenceType type) {
        return type == EssenceType.ECTOPLASM ? this.essenceStorage : EssenceStorage.createEmpty(type, 0);
    }

    @Override
    public void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        if (type == EssenceType.ECTOPLASM) {
            this.essenceStorage = updater.apply(this.essenceStorage);

            this.setChanged();
        }
    }
}
