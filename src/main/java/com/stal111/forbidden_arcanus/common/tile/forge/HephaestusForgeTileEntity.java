package com.stal111.forbidden_arcanus.common.tile.forge;

import com.stal111.forbidden_arcanus.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer;
import com.stal111.forbidden_arcanus.common.container.InputType;
import com.stal111.forbidden_arcanus.common.container.input.HephaestusForgeInputs;
import com.stal111.forbidden_arcanus.common.container.input.IHephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
public class HephaestusForgeTileEntity extends LockableTileEntity implements ITickableTileEntity, IInventory {

    private HephaestusForgeLevel level = HephaestusForgeLevel.ONE;

    private NonNullList<ItemStack> inventoryContents = NonNullList.withSize(9, ItemStack.EMPTY);
    private final IIntArray hephaestusForgeData;

    private final RitualManager ritualManager = new RitualManager(this);
    private final EssenceManager essenceManager = new EssenceManager(this);

    private final MagicCircle magicCircle = new MagicCircle(this.ritualManager);

    private List<LivingEntity> entities = new ArrayList<>();

    public HephaestusForgeTileEntity() {
        super(ModTileEntities.HEPHAESTUS_FORGE.get());
        this.hephaestusForgeData = new IIntArray() {
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
            public int size() {
                return 6;
            }
        };
    }

    @Override
    public void tick() {
        if (this.world == null) {
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

                    this.markDirty();
                }
            }
        }

        if (this.world.getGameTime() % 80 == 0) {
            ((HephaestusForgeBlock) this.getBlockState().getBlock()).updateState(this.getBlockState(), this.world, this.pos);
        }

        if (this.world.getGameTime() % 20 == 0) {
            this.entities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).grow(5, 5, 5));

            this.essenceManager.tick();
        }
        this.ritualManager.tick();
        this.magicCircle.tick();
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
        HephaestusForgeLevel level = this.getLevel();
        EssenceManager manager = this.getEssenceManager();

        switch (inputType) {
            case AUREAL: return manager.getAureal() >= level.getMaxAureal();
            case SOULS: return manager.getSouls() >= level.getMaxSouls();
            case BLOOD: return manager.getBlood() >= level.getMaxBlood();
            case EXPERIENCE: return manager.getExperience() >= level.getMaxExperience();
            default: return true;
        }
    }

    public HephaestusForgeLevel getLevel() {
        return level;
    }

    public void setLevel(HephaestusForgeLevel level) {
        this.level = level;
    }

    public IIntArray getHephaestusForgeData() {
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
        int value = input.getInputValue(inputType, stack, Objects.requireNonNull(this.getWorld()).getRandom());
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

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);

        compound.putString("Level", this.getLevel().getName());
        ItemStackHelper.saveAllItems(compound, this.inventoryContents);

        compound.put("Ritual", this.getRitualManager().write(new CompoundNBT()));
        compound.put("Essences", this.getEssenceManager().write(new CompoundNBT()));

        return compound;
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
        super.read(state, compound);
        this.setLevel(HephaestusForgeLevel.getFromName(compound.getString("Level")));

        this.inventoryContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventoryContents);

        this.getRitualManager().read(compound.getCompound("Ritual"));
        this.getEssenceManager().read(compound.getCompound("Essences"));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet) {
        if (this.world != null) {
            this.read(this.world.getBlockState(packet.getPos()), packet.getNbtCompound());
        }
    }

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.forbidden_arcanus.hephaestus_forge");
    }

    @Nonnull
    @Override
    protected Container createMenu(int id, @Nonnull PlayerInventory player) {
        return new HephaestusForgeContainer(id, player, this);
    }

    @Override
    public int getSizeInventory() {
        return this.inventoryContents.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventoryContents.stream().allMatch(ItemStack::isEmpty);
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventoryContents.get(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(this.inventoryContents, index, count);
        if (!stack.isEmpty()) {
            this.markDirty();
        }

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventoryContents, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        this.inventoryContents.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        if (this.getWorld() == null || this.getWorld().getTileEntity(this.pos) != this) {
            return false;
        }

        return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clear() {
        this.inventoryContents.clear();
    }
}
