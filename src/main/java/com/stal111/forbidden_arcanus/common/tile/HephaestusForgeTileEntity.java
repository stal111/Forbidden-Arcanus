package com.stal111.forbidden_arcanus.common.tile;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer;
import com.stal111.forbidden_arcanus.common.container.InputType;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Hephaestus Forge Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.HephaestusForgeTileEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-18
 */
public class HephaestusForgeTileEntity extends LockableTileEntity implements ITickableTileEntity, IInventory {

    private HephaestusForgeLevel level = HephaestusForgeLevel.ONE;

    private NonNullList<ItemStack> inventoryContents = NonNullList.withSize(9, ItemStack.EMPTY);
    private final List<BlockPos> pedestals = new ArrayList<>();

    private final IIntArray hephaestusForgeData;

    private int aureal = 0;
    private int corruption = 0;
    private int souls = 0;
    private int blood = 0;
    private int experience = 0;

    public HephaestusForgeTileEntity() {
        super(ModTileEntities.HEPHAESTUS_FORGE.get());
        this.hephaestusForgeData = new IIntArray() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0: return HephaestusForgeTileEntity.this.getLevel().getIndex();
                    case 1: return HephaestusForgeTileEntity.this.getAureal();
                    case 2: return HephaestusForgeTileEntity.this.getCorruption();
                    case 3: return HephaestusForgeTileEntity.this.getSouls();
                    case 4: return HephaestusForgeTileEntity.this.getBlood();
                    case 5: return HephaestusForgeTileEntity.this.getExperience();
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: HephaestusForgeTileEntity.this.setLevel(HephaestusForgeLevel.getFromIndex(value)); break;
                    case 1: HephaestusForgeTileEntity.this.setAureal(value); break;
                    case 2: HephaestusForgeTileEntity.this.setCorruption(value); break;
                    case 3: HephaestusForgeTileEntity.this.setSouls(value); break;
                    case 4: HephaestusForgeTileEntity.this.setBlood(value); break;
                    case 5: HephaestusForgeTileEntity.this.setExperience(value); break;
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
            ItemStack stack = inventoryContents.get(i);

            if (!stack.isEmpty()) {
                InputType inputType = this.getInputTypeFromSlot(i);

                if (inputType != null && canInput(inputType)) {
                    stack.shrink(1);

                    this.fillWith(inputType, stack);

                    this.markDirty();
                }
            }
        }

        if (this.world.getGameTime() % 80L == 0L) {
            ((HephaestusForgeBlock) this.getBlockState().getBlock()).updateState(this.getBlockState(), this.world, this.pos);
        }
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

    private boolean canInput(InputType inputType) {
        HephaestusForgeLevel level = this.getLevel();

        switch (inputType) {
            case AUREAL: return this.getAureal() < level.getMaxAureal();
            case SOULS: return this.getSouls() < level.getMaxSouls();
            case BLOOD: return this.getBlood() < level.getMaxBlood();
            case EXPERIENCE: return this.getExperience() < level.getMaxExperience();
            default: return false;
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

    public int getAureal() {
        return this.aureal;
    }

    public void setAureal(int aureal) {
        this.aureal = aureal;
    }

    public void increaseAureal(int aureal) {
        if (this.getAureal() + aureal >= this.getLevel().getMaxAureal()) {
            this.setAureal(this.getLevel().getMaxAureal());
            return;
        }
        this.setAureal(this.getAureal() + aureal);
    }

    public int getCorruption() {
        return this.corruption;
    }

    public void setCorruption(int corruption) {
        this.corruption = corruption;
    }

    public void increaseCorruption(int corruption) {
        if (this.getCorruption() + corruption >= this.getLevel().getMaxCorruption()) {
            this.setCorruption(this.getLevel().getMaxCorruption());
            return;
        }
        this.setCorruption(this.getCorruption() + corruption);
    }

    public int getSouls() {
        return this.souls;
    }

    public void setSouls(int souls) {
        this.souls = souls;
    }

    public void increaseSouls(int souls) {
        if (this.getSouls() + souls >= this.getLevel().getMaxSouls()) {
            this.setSouls(this.getLevel().getMaxSouls());
            return;
        }
        this.setSouls(this.getSouls() + souls);
    }

    public int getBlood() {
        return this.blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public void increaseBlood(int blood) {
        if (this.getBlood() + blood >= this.getLevel().getMaxBlood()) {
            this.setBlood(this.getLevel().getMaxBlood());
            return;
        }
        this.setBlood(this.getBlood() + blood);
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void increaseExperience(int experience) {
        if (this.getExperience() + experience >= this.getLevel().getMaxExperience()) {
            this.setExperience(this.getLevel().getMaxExperience());
            return;
        }
        this.setExperience(this.getExperience() + experience);
    }

    public void fillWith(InputType inputType, ItemStack stack) {
        switch (inputType) {
            case AUREAL: this.increaseAureal(1); break;
            case SOULS: this.increaseSouls(1); break;
            case BLOOD: this.increaseBlood(1); break;
            case EXPERIENCE: this.increaseExperience(1); break;
        }
    }

    public List<BlockPos> getPedestals() {
        return pedestals;
    }

    public void addPedestal(BlockPos pos) {
        this.pedestals.add(pos);
    }

    public void removePedestal(BlockPos pos) {
        this.pedestals.remove(pos);
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);

        compound.putString("Level", this.getLevel().getName());
        ItemStackHelper.saveAllItems(compound, this.inventoryContents);

        if (!this.getPedestals().isEmpty()) {
            compound.putLongArray("pedestals", Lists.transform(this.getPedestals(), BlockPos::toLong));
        }

        compound.putInt("Aureal", this.getAureal());
        compound.putInt("Corruption", this.getCorruption());
        compound.putInt("Souls", this.getSouls());
        compound.putInt("Blood", this.getBlood());
        compound.putInt("Experience", this.getExperience());

        return compound;
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
        super.read(state, compound);
        this.setLevel(HephaestusForgeLevel.getFromName(compound.getString("Level")));

        this.inventoryContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventoryContents);

        if (compound.contains("pedestals")) {
            this.pedestals.clear();
            long[] pedestals = compound.getLongArray("pedestals");

            for (long pedestal : pedestals) {
                this.addPedestal(BlockPos.fromLong(pedestal));
            }
            System.out.println(this.pedestals);
        }

        this.setAureal(compound.getInt("Aureal"));
        this.setCorruption(compound.getInt("Corruption"));
        this.setSouls(compound.getInt("Souls"));
        this.setBlood(compound.getInt("Blood"));
        this.setExperience(compound.getInt("Experience"));
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
