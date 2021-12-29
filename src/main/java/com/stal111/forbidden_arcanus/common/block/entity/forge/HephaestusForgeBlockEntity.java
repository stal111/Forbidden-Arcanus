package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssenceManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.common.inventory.InputType;
import com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInputs;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateItemInSlotPacket;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hephaestus Forge Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-06-18
 */
public class HephaestusForgeBlockEntity extends BaseContainerBlockEntity {

    private final NonNullList<ItemStack> inventoryContents = NonNullList.withSize(9, ItemStack.EMPTY);
    private final ContainerData hephaestusForgeData;
    private final RitualManager ritualManager = new RitualManager(this);
    private final EssenceManager essenceManager = new EssenceManager(this);
    private final MagicCircle magicCircle = new MagicCircle(this.ritualManager);
    private HephaestusForgeLevel forgeLevel = HephaestusForgeLevel.ONE;
    private List<LivingEntity> entities = new ArrayList<>();

    private int displayCounter;

    public HephaestusForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state);
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                return switch (index) {
                    case 0 -> manager.getLevel().getIndex();
                    case 1 -> manager.getAureal();
                    case 2 -> manager.getCorruption();
                    case 3 -> manager.getSouls();
                    case 4 -> manager.getBlood();
                    case 5 -> manager.getExperience();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                switch (index) {
                    case 0 -> HephaestusForgeBlockEntity.this.setLevel(HephaestusForgeLevel.getFromIndex(value));
                    case 1 -> manager.setAureal(value);
                    case 2 -> manager.setCorruption(value);
                    case 3 -> manager.setSouls(value);
                    case 4 -> manager.setBlood(value);
                    case 5 -> manager.setExperience(value);
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        blockEntity.magicCircle.tick();
        blockEntity.displayCounter++;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        for (int i = 5; i <= 8; i++) {
            ItemStack stack = blockEntity.inventoryContents.get(i);

            if (stack.isEmpty()) {
                continue;
            }

            InputType inputType = blockEntity.getInputTypeFromSlot(i);

            if (inputType == null) {
                continue;
            }

            HephaestusForgeInput input = blockEntity.getInput(stack, inputType);

            if (input != null) {
                blockEntity.fillWith(inputType, stack, input, i);

                blockEntity.setChanged();
            }
        }

        if (level.getGameTime() % 80 == 0) {
            ((HephaestusForgeBlock) state.getBlock()).updateState(state, level, pos);
        }

        if (level.getGameTime() % 20 == 0) {
            blockEntity.entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(5, 5, 5));

            blockEntity.essenceManager.tick();
        }

        blockEntity.ritualManager.tick((ServerLevel) level, pos);
    }

    private InputType getInputTypeFromSlot(int slot) {
        return switch (slot) {
            case 5 -> InputType.AUREAL;
            case 6 -> InputType.SOULS;
            case 7 -> InputType.BLOOD;
            case 8 -> InputType.EXPERIENCE;
            default -> null;
        };
    }

    @Nullable
    private HephaestusForgeInput getInput(ItemStack stack, InputType inputType) {
        if (this.isTypeFull(inputType)) {
            return null;
        }

        return HephaestusForgeInputs.getInputs().stream().filter(input -> input.canInput(inputType, stack)).findFirst().orElse(null);
    }

    private boolean isTypeFull(InputType inputType) {
        HephaestusForgeLevel level = this.getForgeLevel();
        EssenceManager manager = this.getEssenceManager();

        return switch (inputType) {
            case AUREAL -> manager.getAureal() >= level.getMaxAureal();
            case SOULS -> manager.getSouls() >= level.getMaxSouls();
            case BLOOD -> manager.getBlood() >= level.getMaxBlood();
            case EXPERIENCE -> manager.getExperience() >= level.getMaxExperience();
        };
    }

    public HephaestusForgeLevel getForgeLevel() {
        return this.forgeLevel;
    }

    public void setLevel(HephaestusForgeLevel level) {
        this.forgeLevel = level;
    }

    public ContainerData getHephaestusForgeData() {
        return hephaestusForgeData;
    }

    public EssenceManager getEssenceManager() {
        return essenceManager;
    }

    public MagicCircle getMagicCircle() {
        return magicCircle;
    }

    public List<LivingEntity> getEntities() {
        return entities;
    }

    public void fillWith(InputType inputType, ItemStack stack, HephaestusForgeInput input, int slot) {
        int value = input.getInputValue(inputType, stack, Objects.requireNonNull(this.getLevel()).getRandom());
        EssenceManager manager = this.getEssenceManager();

        switch (inputType) {
            case AUREAL -> manager.increaseAureal(value);
            case SOULS -> manager.increaseSouls(value);
            case BLOOD -> manager.increaseBlood(value);
            case EXPERIENCE -> manager.increaseExperience(value);
        }

        input.finishInput(inputType, stack, this, slot, value);
    }

    public RitualManager getRitualManager() {
        return ritualManager;
    }

    public int getDisplayCounter() {
        return displayCounter;
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag tag) {
        super.save(tag);

        tag.putString("Level", this.getForgeLevel().getName());
        ContainerHelper.saveAllItems(tag, this.inventoryContents);

        tag.put("Ritual", this.getRitualManager().save(new CompoundTag()));
        tag.put("Essences", this.getEssenceManager().save(new CompoundTag()));

        return tag;
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        this.setLevel(HephaestusForgeLevel.getFromName(tag.getString("Level")));

        this.inventoryContents.clear();
        ContainerHelper.loadAllItems(tag, this.inventoryContents);

        this.getRitualManager().load(tag.getCompound("Ritual"));
        this.getEssenceManager().load(tag.getCompound("Essences"));
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public AABB getRenderBoundingBox() {
        AABB boundingBox = new AABB(this.getBlockPos()).expandTowards(0.0D, 1.0D, 0.0D);

        if (this.getRitualManager().isRitualActive()) {
            boundingBox = boundingBox.inflate(2.5F, 0.0F, 2.5D);
        }
        return boundingBox;
    }

    @Nonnull
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.forbidden_arcanus.hephaestus_forge");
    }

    @Nonnull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @Nonnull Inventory inventory) {
        return new HephaestusForgeMenu(containerId, this, this.getHephaestusForgeData(), inventory);
    }

    @Override
    public int getContainerSize() {
        return this.inventoryContents.size();
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

        if (index == 4 && this.getLevel() != null && !this.getLevel().isClientSide()) {
            BlockPos pos = this.getBlockPos();

            NetworkHandler.sentToTrackingChunk(this.getLevel().getChunkAt(pos), new UpdateItemInSlotPacket(pos, stack, 4));
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
