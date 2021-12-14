package com.stal111.forbidden_arcanus.common.tile.forge;

import com.stal111.forbidden_arcanus.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer;
import com.stal111.forbidden_arcanus.common.container.InputType;
import com.stal111.forbidden_arcanus.common.container.input.HephaestusForgeInputs;
import com.stal111.forbidden_arcanus.common.container.input.IHephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdateItemInSlotPacket;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hephaestus Forge Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-18
 */
public class HephaestusForgeTileEntity extends BaseContainerBlockEntity implements Container {

    private HephaestusForgeLevel forgeLevel = HephaestusForgeLevel.ONE;

    private final NonNullList<ItemStack> inventoryContents = NonNullList.withSize(9, ItemStack.EMPTY);
    private final ContainerData hephaestusForgeData;

    private final RitualManager ritualManager = new RitualManager(this);
    private final EssenceManager essenceManager = new EssenceManager(this);

    private final MagicCircle magicCircle = new MagicCircle(this.ritualManager);

    private List<LivingEntity> entities = new ArrayList<>();

    private int displayCounter;

    public HephaestusForgeTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state);
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                EssenceManager manager = HephaestusForgeTileEntity.this.getEssenceManager();

                switch (index) {
                    case 0: return manager.getLevel().getIndex();
                    case 1: return manager.getAureal();
                    case 2: return manager.getCorruption();
                    case 3: return manager.getSouls();
                    case 4: return manager.getBlood();
                    case 5: return manager.getExperience();
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                EssenceManager manager = HephaestusForgeTileEntity.this.getEssenceManager();

                switch (index) {
                    case 0: HephaestusForgeTileEntity.this.setLevel(HephaestusForgeLevel.getFromIndex(value)); break;
                    case 1: manager.setAureal(value); break;
                    case 2: manager.setCorruption(value); break;
                    case 3: manager.setSouls(value); break;
                    case 4: manager.setBlood(value); break;
                    case 5: manager.setExperience(value); break;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

   // @Override
    public void tick() {
        if (this.level == null) {
            return;
        }

        for (int i = 5; i <= 8; i++) {
            ItemStack stack = this.inventoryContents.get(i);

            if (!stack.isEmpty()) {
                InputType inputType = this.getInputTypeFromSlot(i);

                if (inputType == null) {
                    return;
                }

                IHephaestusForgeInput input = this.getInput(stack, inputType);

                if (input != null) {
                    this.fillWith(inputType, stack, input, i);

                    this.setChanged();
                }
            }
        }

        if (this.level.getGameTime() % 80 == 0) {
            ((HephaestusForgeBlock) this.getBlockState().getBlock()).updateState(this.getBlockState(), this.level, this.worldPosition);
        }

        if (this.level.getGameTime() % 20 == 0) {
            this.entities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.worldPosition).inflate(5, 5, 5));

            this.essenceManager.tick();
        }

        if (!this.level.isClientSide()) {
            this.ritualManager.tick();
        }
        this.magicCircle.tick();

        this.displayCounter++;
    }

    private InputType getInputTypeFromSlot(int slot) {
        switch (slot) {
            case 5: return InputType.AUREAL;
            case 6: return InputType.SOULS;
            case 7: return InputType.BLOOD;
            case 8: return InputType.EXPERIENCE;
            default: return null;
        }
    }

    @Nullable
    private IHephaestusForgeInput getInput(ItemStack stack, InputType inputType) {
        if (this.isTypeFull(inputType)) {
            return null;
        }

        return HephaestusForgeInputs.getInputs().stream().filter(input -> input.canInput(inputType, stack)).findFirst().orElse(null);
    }

    private boolean isTypeFull(InputType inputType) {
        HephaestusForgeLevel level = this.getForgeLevel();
        EssenceManager manager = this.getEssenceManager();

        switch (inputType) {
            case AUREAL: return manager.getAureal() >= level.getMaxAureal();
            case SOULS: return manager.getSouls() >= level.getMaxSouls();
            case BLOOD: return manager.getBlood() >= level.getMaxBlood();
            case EXPERIENCE: return manager.getExperience() >= level.getMaxExperience();
            default: return true;
        }
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

    public void fillWith(InputType inputType, ItemStack stack, IHephaestusForgeInput input, int slot) {
        int value = input.getInputValue(inputType, stack, Objects.requireNonNull(this.getLevel()).getRandom());
        EssenceManager manager = this.getEssenceManager();

        switch (inputType) {
            case AUREAL: manager.increaseAureal(value); break;
            case SOULS: manager.increaseSouls(value); break;
            case BLOOD: manager.increaseBlood(value); break;
            case EXPERIENCE: manager.increaseExperience(value); break;
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
    public CompoundTag save(@Nonnull CompoundTag compound) {
        super.save(compound);

        compound.putString("Level", this.getForgeLevel().getName());
        ContainerHelper.saveAllItems(compound, this.inventoryContents);

        compound.put("Ritual", this.getRitualManager().save(new CompoundTag()));
        compound.put("Essences", this.getEssenceManager().save(new CompoundTag()));

        return compound;
    }

    @Override
    public void load(@Nonnull CompoundTag compound) {
        super.load(compound);
        this.setLevel(HephaestusForgeLevel.getFromName(compound.getString("Level")));

        this.inventoryContents.clear();
        ContainerHelper.loadAllItems(compound, this.inventoryContents);

        this.getRitualManager().load(compound.getCompound("Ritual"));
        this.getEssenceManager().load(compound.getCompound("Essences"));
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection networkManager, ClientboundBlockEntityDataPacket packet) {
        if (this.level != null) {
            this.load(packet.getTag());
        }
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
    protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory player) {
        return new HephaestusForgeContainer(id, player, this);
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
