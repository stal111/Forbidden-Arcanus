package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.other.ModPOITypes;
import com.stal111.forbidden_arcanus.item.IRitualStarterItem;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdatePedestalPacket;
import com.stal111.forbidden_arcanus.network.UpdateRitualPacket;
import com.stal111.forbidden_arcanus.util.SavedData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Ritual Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class RitualManager implements SavedData {

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

    public void tryStartRitual(ItemStack stack) {
        IRitualStarterItem ritualStarterItem = (IRitualStarterItem) stack.getItem();
        List<ItemStack> list = new ArrayList<>();

        if (ritualStarterItem.getRemainingUses(stack) <= 0) {
            return;
        }

        this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalTileEntity -> list.add(pedestalTileEntity.getStack()), true);

        for (Ritual ritual : RitualLoader.getRituals()) {
            if (ritual.canStart(list, this.tileEntity)) {
                ritualStarterItem.setRemainingUses(stack, ritualStarterItem.getRemainingUses(stack) - 1);

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
        ServerLevel world = this.getWorld();

        if (!this.isRitualActive()) {
            return;
        }

        BlockPos pos = this.getTileEntity().getBlockPos();
        Random random = world.getRandom();

        int time = this.getActiveRitual().getTime();

        this.counter++;
        this.updateCachedPedestals();

        if (this.lightningCounter != 0) {
            this.lightningCounter++;

            if (this.lightningCounter == 300) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalTileEntity -> list.add(pedestalTileEntity.getStack()));

                if (!this.getActiveRitual().checkIngredients(list, this.tileEntity)) {
                    this.failRitual(world);

                    NetworkHandler.sentToTrackingChunk(world.getChunkAt(pos), new UpdateRitualPacket(pos, this.activeRitual));
                    return;
                }

                this.lightningCounter = 0;
            }
        }

        this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalTileEntity -> {
            BlockPos pedestalPos = pedestalTileEntity.getBlockPos();

            if (pedestalTileEntity.getItemHeight() != 130) {
                int height = pedestalTileEntity.getItemHeight() + 1;
                pedestalTileEntity.setItemHeight(height);

                NetworkHandler.sentToTrackingChunk(world.getChunkAt(pedestalPos), new UpdatePedestalPacket(pedestalPos, pedestalTileEntity.getStack(), height));
            }

            this.addItemParticles(world, pedestalPos, pedestalTileEntity.getItemHeight(), pedestalTileEntity.getStack());
        });

        if (this.counter == time / 2.0F && random.nextDouble() <= this.getFailureChance() * 2) {
            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), world);
            entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setVisualOnly(true);

            world.addFreshEntity(entity);

            this.lightningCounter++;

            this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalTileEntity -> {
                if (random.nextBoolean()) {
                    ItemStack stack = pedestalTileEntity.getStack().copy();
                    BlockPos pedestalPos = pedestalTileEntity.getBlockPos();

                    world.addFreshEntity(new ItemEntity(world, pedestalPos.getX() + 0.5, pedestalPos.getY() + pedestalTileEntity.getItemHeight() / 100.0F, pedestalPos.getZ() + 0.5, stack));
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

    public void finishRitual(ServerLevel world) {
        this.tileEntity.setItem(4, this.getActiveRitual().getResult());
        this.reset();

        this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getBlockPos()), new UpdatePedestalPacket(pedestalTileEntity.getBlockPos(), ItemStack.EMPTY, 110));
        });
    }

    public void failRitual(ServerLevel world) {
        ItemStack stack = this.tileEntity.getItem(4);
        BlockPos pos = this.tileEntity.getBlockPos();

        this.reset();

        if (!stack.isEmpty()) {
            world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack));

            this.tileEntity.setItem(4, ItemStack.EMPTY);
        }

        this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalTileEntity -> {
            pedestalTileEntity.clearStack();
            this.tileEntity.getEssenceManager().increaseCorruption(2);

            NetworkHandler.sentToTrackingChunk(world.getChunkAt(this.tileEntity.getBlockPos()), new UpdatePedestalPacket(pedestalTileEntity.getBlockPos(), ItemStack.EMPTY, 110));
        });

        world.sendParticles(ModParticles.HUGE_MAGIC_EXPLOSION.get(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F) * 0.7F);
    }

    private void addItemParticles(ServerLevel world, BlockPos pedestalPos, int itemHeight, ItemStack stack) {
        BlockPos pos = this.tileEntity.getBlockPos();

        double posX = pedestalPos.getX() + 0.5D;
        double posY = pedestalPos.getY() + 0.1D + itemHeight / 100.0F;
        double posZ = pedestalPos.getZ() + 0.5D;
        double xSpeed = 0.1D * (pos.getX() - pedestalPos.getX());
        double ySpeed = 0.22D;
        double zSpeed = 0.1D * (pos.getZ() - pedestalPos.getZ());

        if (world.getRandom().nextDouble() < 0.6D) {
            world.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), posX, posY, posZ, 0, xSpeed, ySpeed, zSpeed, 0.9D);
        }
    }

    private void reset() {
        this.counter = 0;
        this.lightningCounter = 0;
        this.setActiveRitual(null);
    }

    public ServerLevel getWorld() {
        return (ServerLevel) this.tileEntity.getLevel();
    }

    public double getFailureChance() {
        return ((this.getTileEntity().getEssenceManager().getCorruption() + 5) / (float) this.getTileEntity().getForgeLevel().getMaxCorruption()) / 2;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        if (this.isRitualActive()) {
            tag.putString("ActiveRitual", this.getActiveRitual().getName().toString());
            tag.putInt("Counter", this.counter);

            if (this.lightningCounter != 0) {
                tag.putInt("LightningCounter", this.lightningCounter);
            }
        }

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("ActiveRitual")) {
            this.setActiveRitual(RitualLoader.getRitual(new ResourceLocation(tag.getString("ActiveRitual"))));
            this.counter = tag.getInt("Counter");

            if (this.counter != 0) {
                this.tileEntity.getMagicCircle().setRotation(this.counter);
            }

            if (tag.contains("LightningCounter")) {
                this.lightningCounter = tag.getInt("LightningCounter");
            }
        }
    }

    public void updateCachedPedestals() {
        PoiManager manager = this.getWorld().getPoiManager();

        this.cachedPedestals.clear();
        manager.getInRange(poiType -> poiType == ModPOITypes.PEDESTAL.get(), this.tileEntity.getBlockPos(), 4, PoiManager.Occupancy.ANY).forEach(pointOfInterest -> this.cachedPedestals.add(pointOfInterest.getPos()));
    }

    public void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        this.forEachPedestal(predicate, consumer, false);
    }

    public void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer, boolean updatePedestals) {
        if (updatePedestals) {
            this.updateCachedPedestals();
        }
        this.cachedPedestals.stream().map(pos -> (PedestalBlockEntity) this.getWorld().getBlockEntity(pos)).filter(predicate).forEach(consumer);
    }
}
