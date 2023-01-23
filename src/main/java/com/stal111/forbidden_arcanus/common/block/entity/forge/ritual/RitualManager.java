package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.CreateValidRitualIndicatorPacket;
import com.stal111.forbidden_arcanus.common.network.clientbound.RemoveValidRitualIndicatorPacket;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateForgeRitualPacket;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdatePedestalPacket;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Ritual Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager
 *
 * @author stal111
 * @since 2021-07-09
 */
public class RitualManager implements NeedsStoring {

    public static final float DEFAULT_RITUAL_TIME = 500.0F;

    public static final Vec3i[] PEDESTAL_OFFSETS = {
            new Vec3i(-3, 0, 0),
            new Vec3i(-2, 0, -2),
            new Vec3i(0, 0, -3),
            new Vec3i(2, 0, -2),
            new Vec3i(3, 0, 0),
            new Vec3i(2, 0, 2),
            new Vec3i(0, 0, 3),
            new Vec3i(-2, 0, 2)
    };

    private ServerLevel level;
    private BlockPos pos;
    private final MainIngredientAccessor mainIngredientAccessor;

    private boolean loaded = false;

    private final HashMap<PedestalBlockEntity, ItemStack> cachedIngredients = new HashMap<>();
    private Ritual validRitual;
    private Ritual activeRitual;
    private int counter;
    private int lightningCounter;

    public RitualManager(MainIngredientAccessor accessor) {
        this.mainIngredientAccessor = accessor;
    }

    public void setup(ServerLevel level, BlockPos pos, EssencesStorage storage) {
        this.level = level;
        this.pos = pos;
    }

    private boolean canStart() {
        return this.validRitual != null;
    }

    public Ritual getActiveRitual() {
        return this.activeRitual;
    }

    public void setActiveRitual(Ritual ritual) {
        this.activeRitual = ritual;
    }

    public boolean isRitualActive() {
        return this.activeRitual != null;
    }

    public void tryStartRitual(EssencesStorage storage, BooleanConsumer started) {
        if (this.canStart()) {
            this.startRitual(storage, this.validRitual);

            started.accept(true);

            return;
        }

        started.accept(false);
    }

    public void updateIngredient(PedestalBlockEntity pedestal, ItemStack stack, EssencesStorage storage) {
        if (stack.isEmpty()) {
            this.cachedIngredients.remove(pedestal);
        } else {
            this.cachedIngredients.put(pedestal, stack);
        }

        this.validRitual = this.tryFindValidRitual(storage);
    }

    public Ritual tryFindValidRitual(EssencesStorage storage) {
        boolean oldValue = this.validRitual != null;

        for (Ritual ritual : RitualLoader.getRituals()) {
            if (this.canStartRitual(ritual, storage)) {
                this.validRitual = ritual;

                if (!oldValue) {
                    NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(this.pos), new CreateValidRitualIndicatorPacket(this.pos));
                }

                return ritual;
            }
        }

        this.validRitual = null;

        if (oldValue && !this.isRitualActive()) {
            NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(this.pos), new RemoveValidRitualIndicatorPacket(this.pos));
        }

        return null;
    }

    public boolean canStartRitual(Ritual ritual, EssencesStorage storage) {
        return storage.hasMoreThan(ritual.getEssences()) && ritual.checkIngredients(this.cachedIngredients.values(), this.mainIngredientAccessor);
    }

    public void startRitual(EssencesStorage storage, Ritual ritual) {
        this.setActiveRitual(ritual);

        ritual.createMagicCircle(this.level, this.pos, 0);

        storage.reduce(ritual.getEssences());
    }

    public void tick(EssencesStorage storage) {
        if (!this.loaded && this.level != null) {
            for (Vec3i vec3i : PEDESTAL_OFFSETS) {
                BlockPos offsetPos = pos.offset(vec3i);

                if (level.getBlockEntity(offsetPos) instanceof PedestalBlockEntity blockEntity && !blockEntity.getStack().isEmpty()) {
                    this.updateIngredient(blockEntity, blockEntity.getStack(), storage);
                }
            }

            this.loaded = true;
        }

        if (!this.isRitualActive()) {
            return;
        }

        RandomSource random = this.level.getRandom();
        float progress = RitualManager.getRitualProgress(this.counter);

        this.counter++;

        if (this.lightningCounter != 0) {
            this.lightningCounter++;

            if (this.lightningCounter == 300) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> list.add(pedestalBlockEntity.getStack()));

                if (!this.getActiveRitual().checkIngredients(list, this.mainIngredientAccessor)) {
                    this.failRitual();

                    NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(pos), new UpdateForgeRitualPacket(pos, this.activeRitual));
                    return;
                }

                this.lightningCounter = 0;
            }
        }

        this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> {
            BlockPos pedestalPos = pedestalBlockEntity.getBlockPos();

            if (pedestalBlockEntity.getItemHeight() != 140) {
                int height = pedestalBlockEntity.getItemHeight() + 1;
                pedestalBlockEntity.setItemHeight(height);

                NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(pedestalPos), new UpdatePedestalPacket(pedestalPos, pedestalBlockEntity.getStack(), height));
            }

            this.addItemParticles(pedestalPos, pedestalBlockEntity.getItemHeight(), pedestalBlockEntity.getStack());
        });

        if (progress == 0.5F && random.nextDouble() <= this.getFailureChance() * 2) {
            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), this.level);
            entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setVisualOnly(true);

            this.level.addFreshEntity(entity);

            this.lightningCounter++;

            this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> {
                if (random.nextBoolean()) {
                    ItemStack stack = pedestalBlockEntity.getStack().copy();
                    BlockPos pedestalPos = pedestalBlockEntity.getBlockPos();

                    this.level.addFreshEntity(new ItemEntity(this.level, pedestalPos.getX() + 0.5, pedestalPos.getY() + pedestalBlockEntity.getItemHeight() / 100.0F, pedestalPos.getZ() + 0.5, stack));
                    pedestalBlockEntity.clearStack(this.level);
                }
            });
        }

        if (progress == 1.0F) {
            if (random.nextDouble() > this.getFailureChance()) {
                this.finishRitual();
            } else {
                this.failRitual();
            }
        }

        NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(pos), new UpdateForgeRitualPacket(pos, this.activeRitual));
    }

    public void finishRitual() {
        this.mainIngredientAccessor.set(this.getActiveRitual().getResult());

        this.activeRitual.removeMagicCircle(this.level, this.pos);

        this.reset();

        this.clearPedestals();
    }

    public void failRitual() {
        ItemStack stack = this.mainIngredientAccessor.get();

        this.activeRitual.removeMagicCircle(this.level, this.pos);

        this.reset();

        if (!stack.isEmpty()) {
            this.level.addFreshEntity(new ItemEntity(this.level, this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5, stack));

            this.mainIngredientAccessor.set(ItemStack.EMPTY);
        }

        this.clearPedestals();

        this.level.sendParticles(ModParticles.HUGE_MAGIC_EXPLOSION.get(), this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        this.level.playSound(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F) * 0.7F);
    }

    private void clearPedestals() {
        List<PedestalBlockEntity> pedestals = new ArrayList<>(this.cachedIngredients.keySet());

        pedestals.forEach(blockEntity -> {
            blockEntity.clearStack(this.level);
        });
    }

    private void addItemParticles(BlockPos pedestalPos, int itemHeight, ItemStack stack) {
        double posX = pedestalPos.getX() + 0.5D;
        double posY = pedestalPos.getY() + 0.1D + itemHeight / 100.0F;
        double posZ = pedestalPos.getZ() + 0.5D;
        double xSpeed = 0.1D * (this.pos.getX() - pedestalPos.getX());
        double ySpeed = 0.22D;
        double zSpeed = 0.1D * (this.pos.getZ() - pedestalPos.getZ());

        if (this.level.getRandom().nextDouble() < 0.6D) {
            this.level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), posX, posY, posZ, 0, xSpeed, ySpeed, zSpeed, 0.9D);
        }
    }

    private void reset() {
        this.counter = 0;
        this.lightningCounter = 0;
        this.validRitual = null;
        this.setActiveRitual(null);
    }

    public double getFailureChance() {
        //TODO
        return 0.0D;
        //return ((this.getBlockEntity().getEssenceManager().getCorruption() + 5) / (float) this.getBlockEntity().getForgeLevel().getMaxCorruption()) / 2;
    }

    /**
     * @return the progress of the currently active ritual. Between {@code 0.0F} and {@code 1.0F} if the ritual is finished.
     */
    public static float getRitualProgress(float counter) {
        return counter / DEFAULT_RITUAL_TIME;
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

            if (tag.contains("LightningCounter")) {
                this.lightningCounter = tag.getInt("LightningCounter");
            }
        }
    }

    public void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        for (PedestalBlockEntity blockEntity : this.cachedIngredients.keySet()) {
            if (predicate.test(blockEntity)) {
                consumer.accept(blockEntity);
            }
        }
    }

    public interface MainIngredientAccessor {
        ItemStack get();
        void set(ItemStack stack);
    }
}
