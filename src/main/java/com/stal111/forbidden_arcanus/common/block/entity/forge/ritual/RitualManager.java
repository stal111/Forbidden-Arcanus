package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ForgeDataCache;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesStorage;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
public class RitualManager {

    public static final String TAG_ACTIVE_RITUAL = "active_ritual";

    public static final int PEDESTAL_ITEM_HEIGHT = 140;
    public static final int LIGHTNING_COUNTER_THRESHOLD = 300;

    private final MagicCircleController magicCircleController;

    private ForgeDataCache dataCache = ForgeDataCache.EMPTY;

    private ServerLevel level;
    private BlockPos pos;
    private int forgeTier;

    private @Nullable Holder<Ritual> validRitual;
    private @Nullable ActiveRitualData activeRitualData;

    public RitualManager(MagicCircleController circleController, int forgeTier) {
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

    public Optional<Holder<Ritual>> getValidRitual() {
        return Optional.ofNullable(this.validRitual);
    }

    private Optional<ActiveRitualData> getActiveRitualData() {
        return Optional.ofNullable(this.activeRitualData);
    }

    private void setActiveRitual(@Nullable Holder<Ritual> ritual) {
        this.activeRitualData = ritual != null ? ActiveRitualData.create(ritual) : null;

        int duration = ritual != null ? ritual.value().duration() : 0;

        this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_DURATION, duration);
    }

    public boolean isRitualActive() {
        return this.level != null && this.getActiveRitualData().isPresent();
    }

    public void onDataChanged(ForgeDataCache dataCache, EssencesDefinition essencesDefinition) {
        this.dataCache = dataCache;

        if (this.isRitualActive()) {
            this.failRitual();
        }

        this.updateValidRitual(essencesDefinition);
    }

    public void updateValidRitual(EssencesDefinition definition) {
        boolean oldValue = this.validRitual != null;

        for (Holder<Ritual> ritual : this.level.registryAccess().registryOrThrow(FARegistries.RITUAL).holders().toList()) {
            if (this.canStartRitual(ritual.value(), definition)) {
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

    private boolean canStartRitual(Ritual ritual, EssencesDefinition definition) {
        List<EssenceModifier> modifiers = this.dataCache.getEnhancers().stream()
                .flatMap(enhancerDefinition -> enhancerDefinition.value().getEffects(EnhancerTarget.HEPHAESTUS_FORGE))
                .filter(effect -> effect instanceof EssenceModifier)
                .map(effect -> (EssenceModifier) effect)
                .toList();

        EssencesDefinition updatedEssences = ritual.requirements().essences().applyModifiers(modifiers);
        Ritual.RitualStartContext context = Ritual.RitualStartContext.of(this.level, this.pos, this.forgeTier, this.dataCache);

        return definition.hasMoreThan(updatedEssences) && ritual.canStart(context);
    }

    public boolean startRitual(EssencesStorage storage) {
        return this.getValidRitual().map(ritual -> {
            this.setActiveRitual(ritual);

            this.magicCircleController.createMagicCircle(this.level, this.pos, ritual.value().magicCircleType());

            storage.reduce(ritual.value().requirements().essences());

            this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> {
                blockEntity.setItemHeightTarget(PEDESTAL_ITEM_HEIGHT);
            });

            return true;
        }).orElse(false);
    }

    public Optional<ItemStack> tick() {
        ActiveRitualData data = this.getActiveRitualData().orElse(null);

        if (data == null) {
            return Optional.empty();
        }

        RandomSource random = this.level.getRandom();
        float progress = data.calculateRitualProgress();

        data.incrementCounter();

        this.handleLightningCounter(data);

        this.dataCache.cachedIngredients().forEach((blockPos, stack) -> {
            this.addItemParticles(blockPos, Math.min(PedestalBlockEntity.DEFAULT_ITEM_HEIGHT + data.getCounter(), PEDESTAL_ITEM_HEIGHT), stack);
        });

        if (progress == 0.5F && random.nextDouble() <= this.getFailureChance() * 2) {
            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), level);
            entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setVisualOnly(true);

            level.addFreshEntity(entity);

            data.incrementLightningCounter();

            this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> {
                if (random.nextBoolean()) {
                    ItemStack stack = pedestalBlockEntity.getStack().copy();
                    BlockPos pedestalPos = pedestalBlockEntity.getBlockPos();

                    level.addFreshEntity(new ItemEntity(level, pedestalPos.getX() + 0.5, pedestalPos.getY() + pedestalBlockEntity.getItemHeight() / 100.0F, pedestalPos.getZ() + 0.5, stack));
                    pedestalBlockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED);
                }
            });
        }

        if (progress == 1.0F) {
            if (random.nextDouble() > this.getFailureChance()) {
                return Optional.of(this.finishRitual(data));
            } else {
                return Optional.of(this.failRitual());
            }
        }

        return Optional.empty();
    }

    private void handleLightningCounter(ActiveRitualData data) {
        if (data.getLightningCounter() != 0) {
            data.incrementLightningCounter();

            if (data.getLightningCounter() == LIGHTNING_COUNTER_THRESHOLD) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> list.add(pedestalBlockEntity.getStack()));

                if (!data.getRitual().checkIngredients(list, this.dataCache.mainIngredient())) {
                    this.failRitual();
                }
            }
        }
    }

    private ItemStack finishRitual(ActiveRitualData data) {
        this.reset();

        return data.getRitual().result().apply(this.level, this.pos, this.forgeTier);
    }

    private ItemStack failRitual() {
        ItemStack stack = this.dataCache.mainIngredient();

        this.reset();

        if (!stack.isEmpty()) {
            this.level.addFreshEntity(new ItemEntity(this.level, this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5, stack));
        }

        this.level.sendParticles(ModParticles.HUGE_MAGIC_EXPLOSION.get(), this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        this.level.playSound(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0F, (1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F) * 0.7F);

        return ItemStack.EMPTY;
    }

    private void clearPedestals() {
        this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> blockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED));

        this.dataCache.cachedIngredients().clear();
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
        this.validRitual = null;
        this.setActiveRitual(null);
        this.magicCircleController.removeMagicCircle(this.level, this.pos);
        this.updateRitualIndicator(false);
        this.clearPedestals();
    }

    private double getFailureChance() {
        //TODO
        return 0.0D;
        //return ((this.getBlockEntity().getEssenceManager().getCorruption() + 5) / (float) this.getBlockEntity().getForgeLevel().getMaxCorruption()) / 2;
    }

    public CompoundTag save(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.getActiveRitualData().flatMap(data -> {
            return ActiveRitualData.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), this.activeRitualData).result();
        }).ifPresent(dataTag -> {
            tag.put(TAG_ACTIVE_RITUAL, dataTag);
        });

        return tag;
    }

    public void load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        if (tag.contains(TAG_ACTIVE_RITUAL)) {
            this.activeRitualData = ActiveRitualData.CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), tag).resultOrPartial(System.err::println).orElse(null);
        }
    }

    private void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        for (BlockPos pos : this.dataCache.cachedIngredients().keySet()) {
            if (this.level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity && predicate.test(blockEntity)) {
                consumer.accept(blockEntity);
            }
        }
    }

    private void updateRitualIndicator(boolean show) {
        this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_INDICATOR, BooleanUtils.toInteger(show));
    }
}
