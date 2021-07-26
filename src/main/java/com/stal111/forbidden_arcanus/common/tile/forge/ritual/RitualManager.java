package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdatePedestalPacket;
import com.stal111.forbidden_arcanus.network.UpdateRitualPacket;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private int lightningCounter;

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
        World world = this.getWorld();

        if (!this.isRitualActive() || world.isRemote()) {
            return;
        }

        BlockPos pos = this.getTileEntity().getPos();
        Random random = world.getRandom();

        int time = this.getActiveRitual().getTime();

        this.counter++;

        if (this.lightningCounter != 0) {
            this.lightningCounter++;

            if (this.lightningCounter == 300) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> list.add(pedestalTileEntity.getStack()));

                if (!this.getActiveRitual().checkIngredients(list, this.tileEntity)) {
                    this.failRitual(world);

                    NetworkHandler.sentToTrackingChunk(world.getChunkAt(pos), new UpdateRitualPacket(pos, this.activeRitual, this.counter));
                    return;
                }

                this.lightningCounter = 0;
            }
        }

        this.forEachPedestal(pedestalTileEntity -> pedestalTileEntity.hasStack() && pedestalTileEntity.getItemHeight() != 130, pedestalTileEntity -> {
            int height = pedestalTileEntity.getItemHeight() + 1;
            pedestalTileEntity.setItemHeight(height);

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(pos), new UpdatePedestalPacket(pos, pedestalTileEntity.getStack(), height));
        });

        if (this.counter == time / 2.0F && random.nextDouble() <= this.getFailureChance() * 2) {
            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), world);
            entity.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setEffectOnly(true);

            world.addEntity(entity);

            this.lightningCounter++;

            this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
                if (random.nextBoolean()) {
                    ItemStack stack = pedestalTileEntity.getStack().copy();
                    BlockPos pedestalPos = pedestalTileEntity.getPos();

                    world.addEntity(new ItemEntity(world, pedestalPos.getX() + 0.5, pedestalPos.getY() + pedestalTileEntity.getItemHeight() / 100.0F, pedestalPos.getZ() + 0.5, stack));
                    pedestalTileEntity.clearStack();

                    NetworkHandler.sentToTrackingChunk(world.getChunkAt(pedestalPos), new UpdatePedestalPacket(pedestalPos, ItemStack.EMPTY, 110));
                }
            });
        }

        if (this.counter == time) {
            if (random.nextDouble() > this.getFailureChance()) {
                this.finishRitual(world);
            } else {
                this.failRitual(world);
            }
        }

        NetworkHandler.sentToTrackingChunk(world.getChunkAt(pos), new UpdateRitualPacket(pos, this.activeRitual, this.counter));
    }

    public void finishRitual(World world) {
        this.tileEntity.setInventorySlotContents(4, this.getActiveRitual().getResult());
        this.reset();

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getPos()), new UpdatePedestalPacket(pedestalTileEntity.getPos(), ItemStack.EMPTY, 110));
        });
    }

    public void failRitual(World world) {
        this.reset();

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getPos()), new UpdatePedestalPacket(pedestalTileEntity.getPos(), ItemStack.EMPTY, 110));
        });
    }

    private void reset() {
        this.counter = 0;
        this.lightningCounter = 0;
        this.setActiveRitual(null);
    }

    public World getWorld() {
        return this.tileEntity.getWorld();
    }

    public double getFailureChance() {
        System.out.println((this.getTileEntity().getEssenceManager().getCorruption() / (float) this.getTileEntity().getLevel().getMaxCorruption()) / 2);
        return (this.getTileEntity().getEssenceManager().getCorruption() / (float) this.getTileEntity().getLevel().getMaxCorruption()) / 2;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (!this.getPedestals().isEmpty()) {
            compound.putLongArray("Pedestals", Lists.transform(this.getPedestals(), BlockPos::toLong));
        }
        if (this.isRitualActive()) {
            compound.putString("ActiveRitual", this.getActiveRitual().getName().toString());
            compound.putInt("Counter", this.counter);

            if (this.lightningCounter != 0) {
                compound.putInt("LightningCounter", this.lightningCounter);
            }
        }

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("Pedestals")) {
            this.pedestals.clear();
            long[] pedestals = compound.getLongArray("Pedestals");

            for (long pedestal : pedestals) {
                this.addPedestal(BlockPos.fromLong(pedestal));
            }
        }
        if (compound.contains("ActiveRitual")) {
            this.setActiveRitual(RitualLoader.getRituals().get(new ResourceLocation(compound.getString("ActiveRitual"))));
            this.counter = compound.getInt("Counter");

            if (compound.contains("LightningCounter")) {
                this.lightningCounter = compound.getInt("LightningCounter");
            }
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
