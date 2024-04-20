package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesStorage;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerAccessor;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
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
import net.valhelsia.valhelsia_core.api.common.util.SerializableComponent;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Ritual Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager
 *
 * @author stal111
 * @since 2021-07-09
 */
public class RitualManager implements SerializableComponent {

    public static final int PEDESTAL_ITEM_HEIGHT = 140;
    public static final int LIGHTNING_COUNTER_THRESHOLD = 300;

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

    private final MainIngredientAccessor mainIngredientAccessor;
    private final EnhancerAccessor enhancerAccessor;
    private final MagicCircleController magicCircleController;

    private ServerLevel level;
    private BlockPos pos;
    private int forgeTier;

    private final HashMap<BlockPos, ItemStack> cachedIngredients = new HashMap<>();
    private @Nullable Ritual validRitual;
    private @Nullable Ritual activeRitual;
    private int counter;
    private int lightningCounter;

    public RitualManager(MainIngredientAccessor accessor, EnhancerAccessor enhancerAccessor, MagicCircleController circleController, int forgeTier) {
        this.mainIngredientAccessor = accessor;
        this.enhancerAccessor = enhancerAccessor;
        this.magicCircleController = circleController;
        this.forgeTier = forgeTier;
    }

    public void setup(ServerLevel level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public void setForgeTier(int forgeTier) {
        this.forgeTier = forgeTier;
    }

    private boolean canStart() {
        return this.validRitual != null;
    }

    public @Nullable Ritual getValidRitual() {
        return this.validRitual;
    }

    public Optional<Ritual> getActiveRitual() {
        return Optional.ofNullable(this.activeRitual);
    }

    public void setActiveRitual(@Nullable Ritual ritual) {
        this.activeRitual = ritual;

        int duration = ritual != null ? ritual.duration() : 0;

        this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_DURATION, duration);
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

    public void updateIngredient(BlockPos pos, ItemStack stack, EssencesDefinition definition) {
        this.cachedIngredients.put(pos, stack);

        if (this.isRitualActive()) {
            this.failRitual();
        }

        this.updateValidRitual(definition);
    }

    public void updateValidRitual(EssencesDefinition definition) {
        boolean oldValue = this.validRitual != null;

        for (Ritual ritual : this.level.registryAccess().registryOrThrow(FARegistries.RITUAL)) {
            if (this.canStartRitual(ritual, definition)) {
                if (!oldValue) {
                    this.updateRitualIndicator(true);
                }

                this.validRitual = ritual;

                return;
            }
        }

        this.validRitual = null;

        if (oldValue && !this.isRitualActive()) {
            this.updateRitualIndicator(false);
        }
    }

    public boolean canStartRitual(Ritual ritual, EssencesDefinition definition) {
        List<EssenceModifier> modifiers = this.enhancerAccessor.getEnhancers().stream()
                .flatMap(enhancerDefinition -> enhancerDefinition.getEffects(EnhancerTarget.HEPHAESTUS_FORGE))
                .filter(effect -> effect instanceof EssenceModifier)
                .map(effect -> (EssenceModifier) effect)
                .toList();

        EssencesDefinition updatedEssences = ritual.essences().applyModifiers(modifiers);
        Ritual.RitualStartContext context = Ritual.RitualStartContext.of(this.level, this.pos, this.forgeTier, this.cachedIngredients.values(), this.mainIngredientAccessor.get(), this.enhancerAccessor.getEnhancers());

        return definition.hasMoreThan(updatedEssences) && ritual.canStart(context);
    }

    public void startRitual(EssencesStorage storage, Ritual ritual) {
        this.setActiveRitual(ritual);

        this.magicCircleController.createMagicCircle(this.level, this.pos, ritual.magicCircleType());

        storage.reduce(ritual.essences());

        this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> {
            blockEntity.setItemHeightTarget(PEDESTAL_ITEM_HEIGHT);
        });
    }

    public void tick() {
        if (!this.isRitualActive()) {
            return;
        }

        RandomSource random = this.level.getRandom();
        float progress = this.calculateRitualProgress();

        this.counter++;

        this.handleLightningCounter();

        this.cachedIngredients.forEach((blockPos, stack) -> {
            this.addItemParticles(blockPos, Math.min(PedestalBlockEntity.DEFAULT_ITEM_HEIGHT + this.counter, PEDESTAL_ITEM_HEIGHT), stack);
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
                    pedestalBlockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED);
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
    }

    private void handleLightningCounter() {
        if (this.lightningCounter != 0) {
            this.lightningCounter++;

            if (this.lightningCounter == LIGHTNING_COUNTER_THRESHOLD) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> list.add(pedestalBlockEntity.getStack()));

                this.getActiveRitual().ifPresentOrElse(ritual -> {
                    if (!ritual.checkIngredients(list, this.mainIngredientAccessor.get())) {
                        this.failRitual();
                    }
                }, this::failRitual);
            }
        }
    }

    public void finishRitual() {
        this.activeRitual.result().apply(this.mainIngredientAccessor, this.level, this.pos);

        this.reset();

        this.clearPedestals();
    }

    public void failRitual() {
        ItemStack stack = this.mainIngredientAccessor.get();

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
        this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> blockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED));

        this.cachedIngredients.clear();

        this.validRitual = null;
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
        this.magicCircleController.removeMagicCircle(this.level, this.pos);
        this.updateRitualIndicator(false);
    }

    public double getFailureChance() {
        //TODO
        return 0.0D;
        //return ((this.getBlockEntity().getEssenceManager().getCorruption() + 5) / (float) this.getBlockEntity().getForgeLevel().getMaxCorruption()) / 2;
    }

    /**
     * Returns the progress of the active ritual as a ratio of the counter to the ritual's duration.
     * If no ritual is active, returns -1.0F.
     *
     * @return Ritual progress as a float between 0.0F and 1.0F, or -1.0F if no ritual is active.
     */
    public float calculateRitualProgress() {
        return this.getActiveRitual().map(ritual -> this.counter / (float) ritual.duration()).orElse(-1.0F);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        if (this.isRitualActive()) {
            tag.putString("ActiveRitual", this.level.registryAccess().registryOrThrow(FARegistries.RITUAL).getResourceKey(this.activeRitual).orElseThrow().toString());
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
            //TODO this breaks as level is null during load
            this.setActiveRitual(this.level.registryAccess().registryOrThrow(FARegistries.RITUAL).get(new ResourceLocation(tag.getString("ActiveRitual"))));
            this.counter = tag.getInt("Counter");

            if (tag.contains("LightningCounter")) {
                this.lightningCounter = tag.getInt("LightningCounter");
            }
        }
    }

    public void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        for (BlockPos pos : this.cachedIngredients.keySet()) {
            if (this.level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity && predicate.test(blockEntity)) {
                consumer.accept(blockEntity);
            }
        }
    }

    private void updateRitualIndicator(boolean show) {
        this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_INDICATOR, BooleanUtils.toInteger(show));
    }

    public interface MainIngredientAccessor {
        ItemStack get();
        void set(ItemStack stack);
    }
}
