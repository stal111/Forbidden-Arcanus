package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.block.clibano.AbstractClibanoFrameBlock;
import com.stal111.forbidden_arcanus.common.block.clibano.ClibanoMainPartBlock;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedSlotState;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.EssenceInputResourceHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.FuelItemHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.ResultSlotItemHandler;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorages;
import com.stal111.forbidden_arcanus.common.inventory.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.input.ClibanoAlloyingRecipeInput;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.network.clientbound.InsertMoltenMaterialPayload;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.UnaryOperator;

/**
 * Clibano Main Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoMainBlockEntity extends BlockEntity implements MenuProvider, EssenceAccess {

    public static final int ECTOPLASM_DURATION = 150;
    public static final int RESULT_TIME = 20;

    public static final int DATA_LIT_TIME_REMAINING = 0;
    public static final int DATA_LIT_TOTAL_TIME = 1;
    public static final int DATA_COOKING_TIME_1 = 2;
    public static final int DATA_COOKING_TIME_2 = 3;
    public static final int DATA_COOKING_TOTAL_TIME_1 = 4;
    public static final int DATA_COOKING_TOTAL_TIME_2 = 5;
    public static final int DATA_ECTOPLASM_AMOUNT = 6;
    public static final int DATA_ECTOPLASM_TIME_REMAINING = 7;
    public static final int DATA_RESULT_PROGRESS = 8;
    public static final int DATA_RESULT_DURATION = 9;
    public static final int DATA_FIRE_TYPE = 10;

    public static final int DATA_COUNT = 11;

    public static final RecipeType<ClibanoMeltingRecipe> RECIPE_TYPE = ModRecipeTypes.CLIBANO_MELTING.get();

    private static final Component NAME = Component.translatable("container.forbidden_arcanus.clibano");

    private final RecipeManager.CachedCheck<SingleRecipeInput, ClibanoMeltingRecipe> quickCheck = RecipeManager.createCheck(RECIPE_TYPE);

    private final FuelItemHandler fuelInventory = new FuelItemHandler(stack -> getBurnDuration(stack, this.level) > 0, _ -> this.setChanged());
    private final ItemStacksResourceHandler inputInventory = new ItemStacksResourceHandler(2) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            ClibanoMainBlockEntity.this.onInputChange(index, previousContents);
        }
    };
    private final EssenceInputResourceHandler essenceInputInventory = new EssenceInputResourceHandler(EssenceType.ECTOPLASM);
    private final ResultSlotItemHandler resultInventory = new ResultSlotItemHandler(_ -> this.setChanged());

    public MaterialStorage storedMaterials = MaterialStorage.createEmpty();
    private EssenceStorage essenceStorage = EssenceStorages.CLIBANO_ECTOPLASM_EMPTY;
    private final SelectedSlotState selectedSlotState = new SelectedSlotState(() -> {
        this.resultProgress = 0;
        this.setChanged();
    });

    private int litTimeRemaining;
    private int litTotalTime;

    private int[] cookingTimes = new int[2];
    private int[] cookingTotalTimes = new int[2];

    private int ectoplasmTimeRemaining = 0;
    private int resultProgress = 0;
    private int resultDuration = 0;

    private ClibanoFireType fireType = ClibanoFireType.FIRE;

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
                case DATA_ECTOPLASM_TIME_REMAINING -> blockEntity.ectoplasmTimeRemaining;
                case DATA_RESULT_PROGRESS -> blockEntity.resultProgress;
                case DATA_RESULT_DURATION -> blockEntity.resultDuration;
                case DATA_FIRE_TYPE -> blockEntity.fireType.ordinal();
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
                case DATA_ECTOPLASM_TIME_REMAINING -> blockEntity.ectoplasmTimeRemaining = value;
                case DATA_RESULT_PROGRESS -> blockEntity.resultProgress = value;
                case DATA_RESULT_DURATION -> blockEntity.resultDuration = value;
                case DATA_FIRE_TYPE -> blockEntity.fireType = ClibanoFireType.values()[value];
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    private Direction frontDirection = Direction.NORTH;

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
//        this.enhancer = this.updateEnhancer();
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            ClibanoMainPartBlock.dismantle(this.level, pos);
        }
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, ClibanoMainBlockEntity blockEntity) {
        ClibanoFireType nextFireType = blockEntity.fireType;
        boolean wasLit = blockEntity.isLit();

        blockEntity.essenceInputInventory.tick(blockEntity);

        if (blockEntity.isLit()) {
            blockEntity.litTimeRemaining--;
        }

        if (blockEntity.ectoplasmTimeRemaining > 0) {
            blockEntity.ectoplasmTimeRemaining--;

            if (blockEntity.ectoplasmTimeRemaining == 0) {
                nextFireType = ClibanoFireType.FIRE;
            }
        }

        ItemStack fuel = blockEntity.fuelInventory.getStack();
        boolean canSmelt = Arrays.stream(blockEntity.cookingTotalTimes).anyMatch(time -> time != 0);

        if (!blockEntity.isLit() && !fuel.isEmpty() && canSmelt) {
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
            if (blockEntity.ectoplasmTimeRemaining == 0 && !blockEntity.essenceStorage.isEmpty() && canSmelt) {
                blockEntity.ectoplasmTimeRemaining = ECTOPLASM_DURATION;
                nextFireType = ClibanoFireType.SOUL_FIRE;

                blockEntity.addEssence(EssenceType.ECTOPLASM, -1);
            }

            for (int i = 0; i < blockEntity.cookingTimes.length; i++) {
                if (blockEntity.cookingTotalTimes[i] != 0 && blockEntity.cookingTimes[i] < blockEntity.cookingTotalTimes[i]) {
                    blockEntity.cookingTimes[i]++;

                    if (blockEntity.cookingTimes[i] >= blockEntity.cookingTotalTimes[i]) {
                        blockEntity.finishRecipe(i);
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

        blockEntity.selectedSlotState.getSelected().ifPresent(either -> {
            either.ifLeft(type -> blockEntity.tryCreateResult(level, type));
            either.ifRight(resourceKey -> blockEntity.tryCreateAlloy(level, resourceKey));
        });

        if (wasLit != blockEntity.isLit() || nextFireType != blockEntity.fireType) {
            blockEntity.changeFireType(level, nextFireType);
        }
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

    private boolean isLit() {
        return this.litTimeRemaining > 0;
    }

    private void onInputChange(int index, ItemStack oldStack) {
        ItemStack stack = ItemUtil.getStack(this.inputInventory, index);

        if (!ItemStack.isSameItemSameComponents(stack, oldStack) && this.level instanceof ServerLevel serverLevel) {
            this.updateRecipe(serverLevel, index, true);
        }

        this.setChanged();
    }

    public void finishRecipe(int index) {
        ItemStack stack = ItemUtil.getStack(this.inputInventory, index);

        if (this.level instanceof ServerLevel serverLevel) {
            this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), serverLevel).ifPresent(recipeHolder -> {
                MoltenMaterial moltenMaterial = recipeHolder.value().result();

                this.insertMaterial(serverLevel, moltenMaterial);
            });

            ItemStack input = stack.copy();
            input.shrink(1);

            this.inputInventory.set(index, ItemResource.of(input), input.getCount());

            for (int i = 0; i < this.cookingTotalTimes.length; i++) {
                this.updateRecipe(serverLevel, i, i == index);
            }
        }
    }

    private void updateRecipe(ServerLevel level, int index, boolean resetProgress) {
        ItemStack stack = ItemUtil.getStack(this.inputInventory, index);

        RecipeHolder<ClibanoMeltingRecipe> recipe = this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), level).orElse(null);
        boolean canSmelt = recipe != null && this.storedMaterials.canFit(recipe.value().result());

        this.cookingTotalTimes[index] = canSmelt ? recipe.value().getCookingTime(this.fireType) : 0;

        if (!canSmelt || resetProgress) {
            this.cookingTimes[index] = 0;
        }
    }

    /**
     * Changes the current {@link ClibanoFireType} of the clibano and updates the cooking durations accordingly.
     *
     * @param level    the level the clibano is in
     * @param fireType the new ClibanoFireType
     */
    private void changeFireType(ServerLevel level, ClibanoFireType fireType) {
        for (int i = 0; i < this.cookingTimes.length; i++) {
            ItemStack stack = ItemUtil.getStack(this.inputInventory, i);
            int oldDuration = this.cookingTotalTimes[i];

            if (this.cookingTotalTimes[i] != 0) {
                RecipeHolder<ClibanoMeltingRecipe> recipe = this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), level).orElse(null);

                if (recipe != null) {
                    this.cookingTotalTimes[i] = recipe.value().getCookingTime(fireType);
                    this.cookingTimes[i] = (int) (((float) this.cookingTimes[i] / oldDuration) * this.cookingTotalTimes[i]);
                }
            }
        }

        this.fireType = fireType;

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

    public void setFrontDirection(Direction direction) {
        this.frontDirection = direction;
    }

    private void tryCreateResult(ServerLevel level, Holder<MoltenMaterialType> type) {
        if (this.storedMaterials.getAmount(type) >= 9 && this.canCreateResult(type.value())) {
            ItemStack result = this.resultInventory.getStack();

            this.resultProgress++;
            this.resultDuration = RESULT_TIME;

            if (this.resultProgress >= this.resultDuration) {
                if (result.isEmpty()) {
                    this.resultInventory.setStack(type.value().result().create());
                } else if (ItemStack.isSameItemSameComponents(result, type.value().result())) {
                    result.grow(1);
                    this.resultInventory.setStack(result);
                }

                this.insertMaterial(level, new MoltenMaterial(type, -9));
                this.resultProgress = 0;
            }
        }
    }

    private boolean canCreateResult(MoltenMaterialType materialType) {
        ItemStack result = this.resultInventory.getStack();

        if (result.isEmpty()) {
            return true;
        } else if (!ItemStack.isSameItemSameComponents(result, materialType.result())) {
            return false;
        }

        return this.resultInventory.getStack().getCount() < result.getMaxStackSize();
    }

    private void tryCreateAlloy(ServerLevel level, ResourceKey<Recipe<?>> resourceKey) {
        level.recipeAccess().getRecipeFor(ModRecipeTypes.CLIBANO_ALLOYING.get(), new ClibanoAlloyingRecipeInput(this.storedMaterials.getAll()), level, resourceKey)
                .map(RecipeHolder::value)
                .ifPresent(recipe -> {
                    ItemStack result = this.resultInventory.getStack();

                    this.resultProgress++;
                    this.resultDuration = recipe.duration();

                    if (this.resultProgress >= this.resultDuration) {
                        if (result.isEmpty()) {
                            this.resultInventory.setStack(recipe.result().create());
                        } else if (ItemStack.isSameItemSameComponents(result, recipe.result())) {
                            result.grow(1);
                            this.resultInventory.setStack(result);
                        }

                        recipe.requiredMaterials().stream()
                                .map(material -> new MoltenMaterial(material.type(), -material.amount()))
                                .forEach(material -> this.insertMaterial(level, material));

                        this.resultProgress = 0;
                    }
                });
    }

    private void insertMaterial(ServerLevel level, MoltenMaterial material) {
        this.storedMaterials.insert(material);

        PacketDistributor.sendToPlayersTrackingChunk(level, ChunkPos.containing(this.getBlockPos()), new InsertMoltenMaterialPayload(material));
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
        this.fuelInventory.serialize(output.child("fuel"));
        this.inputInventory.serialize(output.child("input"));
        this.resultInventory.serialize(output.child("result"));

        output.store("stored_materials", MaterialStorage.CODEC, this.storedMaterials);
        output.store("ectoplasm", EssenceStorage.codec(EssenceType.ECTOPLASM).codec(), this.essenceStorage);
        this.selectedSlotState.serialize(output);

        output.putInt("lit_time_remaining", this.litTimeRemaining);
        output.putInt("lit_total_time", this.litTotalTime);

        output.putIntArray("cooking_times_spent", this.cookingTimes);
        output.putIntArray("cooking_total_times", this.cookingTotalTimes);

        output.putInt("ectoplasm_time_remaining", this.ectoplasmTimeRemaining);
        output.putInt("result_progress", this.resultProgress);

        output.store("fire_type", ClibanoFireType.CODEC, this.fireType);
        output.store("front_direction", Direction.CODEC, this.frontDirection);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.essenceInputInventory.deserialize(input.childOrEmpty("essence_inputs"));
        this.fuelInventory.deserialize(input.childOrEmpty("fuel"));
        this.inputInventory.deserialize(input.childOrEmpty("input"));
        this.resultInventory.deserialize(input.childOrEmpty("result"));

        this.storedMaterials = input.read("stored_materials", MaterialStorage.CODEC).orElse(MaterialStorage.createEmpty());
        this.essenceStorage = input.read("ectoplasm", EssenceStorage.codec(EssenceType.ECTOPLASM).codec()).orElse(EssenceStorages.CLIBANO_ECTOPLASM_EMPTY);
        this.selectedSlotState.deserialize(input);

        this.litTimeRemaining = input.getIntOr("lit_time_remaining", 0);
        this.litTotalTime = input.getIntOr("lit_total_time", 0);

        this.cookingTimes = input.getIntArray("cooking_times_spent").orElse(new int[2]);
        this.cookingTotalTimes = input.getIntArray("cooking_total_times").orElse(new int[2]);

        this.ectoplasmTimeRemaining = input.getIntOr("ectoplasm_time_remaining", 0);
        this.resultProgress = input.getIntOr("result_progress", 0);

        input.read("fire_type", ClibanoFireType.CODEC).ifPresent(fireType -> this.fireType = fireType);
        input.read("front_direction", Direction.CODEC).ifPresent(direction -> this.frontDirection = direction);
    }

    public static int getBurnDuration(ItemStack stack, Level level) {
        return stack.getBurnTime(RECIPE_TYPE, level.fuelValues());
    }

    public MaterialStorage getStoredMaterials() {
        return this.storedMaterials;
    }

    public SelectedSlotState getSelectedSlotState() {
        return this.selectedSlotState;
    }

    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ClibanoMenu(containerId, playerInventory, this.fuelInventory, this.inputInventory, this.essenceInputInventory, this.resultInventory, this.containerData, ContainerLevelAccess.create(this.level, this.getBlockPos()), this.storedMaterials, this.selectedSlotState);
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
