package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.other.ModPOITypes;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdatePedestalPacket;
import com.stal111.forbidden_arcanus.network.UpdateRitualPacket;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.server.ServerWorld;

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

    private final List<BlockPos> cachedPedestals = new ArrayList<>();
    private Ritual activeRitual;
    private int counter;
    private int lightningCounter;

    public RitualManager(HephaestusForgeTileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public HephaestusForgeTileEntity getTileEntity() {
        return tileEntity;
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

    public void tryStartRitual() {
        List<ItemStack> list = new ArrayList<>();

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> list.add(pedestalTileEntity.getStack()), true);

        System.out.println(list);

        for (Ritual ritual : RitualLoader.getRituals()) {
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
        ServerWorld world = this.getWorld();

        if (!this.isRitualActive()) {
            return;
        }

        BlockPos pos = this.getTileEntity().getPos();
        Random random = world.getRandom();

        int time = this.getActiveRitual().getTime();

        this.counter++;
        this.updateCachedPedestals();

        if (this.lightningCounter != 0) {
            this.lightningCounter++;

            if (this.lightningCounter == 300) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> list.add(pedestalTileEntity.getStack()));

                if (!this.getActiveRitual().checkIngredients(list, this.tileEntity)) {
                    this.failRitual(world);

                    NetworkHandler.sentToTrackingChunk(world.getChunkAt(pos), new UpdateRitualPacket(pos, this.activeRitual));
                    return;
                }

                this.lightningCounter = 0;
            }
        }

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
            BlockPos pedestalPos = pedestalTileEntity.getPos();

            if (pedestalTileEntity.getItemHeight() != 130) {
                int height = pedestalTileEntity.getItemHeight() + 1;
                pedestalTileEntity.setItemHeight(height);

                NetworkHandler.sentToTrackingChunk(world.getChunkAt(pedestalPos), new UpdatePedestalPacket(pedestalPos, pedestalTileEntity.getStack(), height));
            }

            this.addItemParticles(world, pedestalPos, pedestalTileEntity.getItemHeight(), pedestalTileEntity.getStack());
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

        NetworkHandler.sentToTrackingChunk(world.getChunkAt(pos), new UpdateRitualPacket(pos, this.activeRitual));
    }

    public void finishRitual(ServerWorld world) {
        this.tileEntity.setInventorySlotContents(4, this.getActiveRitual().getResult());
        this.reset();

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getPos()), new UpdatePedestalPacket(pedestalTileEntity.getPos(), ItemStack.EMPTY, 110));
        });
    }

    public void failRitual(ServerWorld world) {
        ItemStack stack = this.tileEntity.getStackInSlot(4);
        BlockPos pos = this.tileEntity.getPos();

        this.reset();

        if (!stack.isEmpty()) {
            world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack));

            this.tileEntity.setInventorySlotContents(4, ItemStack.EMPTY);
        }

        this.forEachPedestal(PedestalTileEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();
            this.tileEntity.getEssenceManager().increaseCorruption(2);

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getPos()), new UpdatePedestalPacket(pedestalTileEntity.getPos(), ItemStack.EMPTY, 110));
        });

        world.spawnParticle(ModParticles.HUGE_MAGIC_EXPLOSION.get(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F) * 0.7F);
    }

    private void addItemParticles(ServerWorld world, BlockPos pedestalPos, int itemHeight, ItemStack stack) {
        BlockPos pos = this.tileEntity.getPos();

        double posX = pedestalPos.getX() + 0.5D;
        double posY = pedestalPos.getY() + 0.1D + itemHeight / 100.0F;
        double posZ = pedestalPos.getZ() + 0.5D;
        double xSpeed = 0.1D * (pos.getX() - pedestalPos.getX());
        double ySpeed = 0.22D;
        double zSpeed = 0.1D * (pos.getZ() - pedestalPos.getZ());

        if (world.getRandom().nextDouble() < 0.6D) {
            world.spawnParticle(new ItemParticleData(ParticleTypes.ITEM, stack), posX, posY, posZ, 0, xSpeed, ySpeed, zSpeed, 0.9D);
        }
    }

    private void reset() {
        this.counter = 0;
        this.lightningCounter = 0;
        this.setActiveRitual(null);
    }

    public ServerWorld getWorld() {
        return (ServerWorld) this.tileEntity.getWorld();
    }

    public double getFailureChance() {
        System.out.println((this.getTileEntity().getEssenceManager().getCorruption() / (float) this.getTileEntity().getLevel().getMaxCorruption()) / 2);
        return (this.getTileEntity().getEssenceManager().getCorruption() / (float) this.getTileEntity().getLevel().getMaxCorruption()) / 2;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
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
        if (compound.contains("ActiveRitual")) {
            this.setActiveRitual(RitualLoader.getRitual(new ResourceLocation(compound.getString("ActiveRitual"))));
            this.counter = compound.getInt("Counter");

            if (this.counter != 0) {
                this.tileEntity.getMagicCircle().setRotation(this.counter);
            }

            if (compound.contains("LightningCounter")) {
                this.lightningCounter = compound.getInt("LightningCounter");
            }
        }
    }

    public void updateCachedPedestals() {
        PointOfInterestManager manager = this.getWorld().getPointOfInterestManager();

        this.cachedPedestals.clear();
        manager.func_219146_b(poiType -> poiType == ModPOITypes.PEDESTAL.get(), this.tileEntity.getPos(), 4, PointOfInterestManager.Status.ANY).forEach(pointOfInterest -> this.cachedPedestals.add(pointOfInterest.getPos()));
    }

    public void forEachPedestal(Predicate<PedestalTileEntity> predicate, Consumer<PedestalTileEntity> consumer) {
        this.forEachPedestal(predicate, consumer, false);
    }

    public void forEachPedestal(Predicate<PedestalTileEntity> predicate, Consumer<PedestalTileEntity> consumer, boolean updatePedestals) {
        if (updatePedestals) {
            this.updateCachedPedestals();
        }
        System.out.println(this.cachedPedestals.size());
        this.cachedPedestals.stream().map(pos -> (PedestalTileEntity) this.getWorld().getTileEntity(pos)).filter(predicate).forEach(consumer);
    }
}
