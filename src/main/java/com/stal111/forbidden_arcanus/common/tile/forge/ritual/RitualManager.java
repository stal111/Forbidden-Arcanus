package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdatePedestalPacket;
import com.stal111.forbidden_arcanus.network.UpdateRitualPacket;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Ritual Manager
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class RitualManager implements ISavedData {

    private final HephaestusForgeTileEntity tileEntity;

    private final List<BlockPos> pedestals = new ArrayList<>();
    private Ritual activeRitual;
    private int counter;

    public RitualManager(HephaestusForgeTileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public HephaestusForgeTileEntity getTileEntity() {
        return tileEntity;
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

    public Ritual getActiveRitual() {
        return activeRitual;
    }

    public void setActiveRitual(Ritual ritual) {
        this.activeRitual = ritual;
    }

    public boolean isRitualActive() {
        return this.activeRitual != null;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void tryStartRitual() {
        List<ItemStack> list = new ArrayList<>();

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> list.add(pedestalTileEntity.getStack()));

        for (Ritual ritual : RitualLoader.getRituals().values()) {
            if (ritual.canStart(list, this.tileEntity)) {
                this.startRitual(ritual);
                return;
            }
        }
    }

    public void startRitual(Ritual ritual) {
        this.setActiveRitual(ritual);

        ritual.getEssences().reduceEssences(this.tileEntity);
    }

    public void tick() {
        if (this.isRitualActive() && this.counter < this.getActiveRitual().getTime()) {
            World world = this.getWorld();

            this.counter++;

            this.forEachPedestal(pedestalTileEntity -> pedestalTileEntity.hasStack() && pedestalTileEntity.getItemHeight() != 130, pedestalTileEntity -> {
                int height = pedestalTileEntity.getItemHeight() + 1;

                pedestalTileEntity.setItemHeight(height);

                if (!world.isRemote()) {
                    NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getPos()), new UpdatePedestalPacket(pedestalTileEntity.getPos(), pedestalTileEntity.getStack(), height));
                }
            });

            if (this.counter == this.getActiveRitual().getTime()) {
                this.finishRitual();
            }

            if (!Objects.requireNonNull(this.tileEntity.getWorld()).isRemote()) {
                NetworkHandler.sentToTrackingChunk(this.tileEntity.getWorld().getChunkAt(this.tileEntity.getPos()), new UpdateRitualPacket(this.tileEntity.getPos(), this.activeRitual, this.counter));
            }
        }
    }

    public void finishRitual() {
        World world = this.tileEntity.getWorld();

        if (world == null) {
            return;
        }

        this.counter = 0;
        this.tileEntity.setInventorySlotContents(4, this.getActiveRitual().getResult());
        this.setActiveRitual(null);

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();

            if(!world.isRemote()) {
                NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getPos()), new UpdatePedestalPacket(pedestalTileEntity.getPos(), ItemStack.EMPTY, 110));
            }
        });
    }

    public World getWorld() {
        return this.tileEntity.getWorld();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (!this.getPedestals().isEmpty()) {
            compound.putLongArray("pedestals", Lists.transform(this.getPedestals(), BlockPos::toLong));
        }
        if (this.isRitualActive()) {
            compound.putString("ActiveRitual", this.getActiveRitual().getName().toString());
            compound.putInt("Counter", this.counter);
        }

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("pedestals")) {
            this.pedestals.clear();
            long[] pedestals = compound.getLongArray("pedestals");

            for (long pedestal : pedestals) {
                this.addPedestal(BlockPos.fromLong(pedestal));
            }
        }
        if (compound.contains("ActiveRitual")) {
            this.setActiveRitual(RitualLoader.getRituals().get(new ResourceLocation(compound.getString("ActiveRitual"))));
            this.counter = compound.getInt("Counter");
        }
    }

    public void forEachPedestal(Predicate<PedestalTileEntity> predicate, Consumer<PedestalTileEntity> consumer) {
        World world = this.getWorld();

        for (BlockPos pos : this.getPedestals()) {
            if (!(world.getTileEntity(pos) instanceof PedestalTileEntity)) {
                continue;
            }

            PedestalTileEntity tileEntity = (PedestalTileEntity) world.getTileEntity(pos);

            if (predicate.test(tileEntity)) {
                consumer.accept(tileEntity);
            }
        }
    }
}
