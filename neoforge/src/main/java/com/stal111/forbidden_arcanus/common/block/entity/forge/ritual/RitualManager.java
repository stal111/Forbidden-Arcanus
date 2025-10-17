package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ForgeDataCache;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.SingleItemResourceHandler;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import com.stal111.forbidden_arcanus.common.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.common.network.clientbound.AdvancedBlockEventPayload;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.Nullable;

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

    private final MagicCircleController magicCircleController;
    private final SingleItemResourceHandler mainIngredientInventory;

    private ForgeDataCache dataCache;

    private ServerLevel level;
    private BlockPos pos;
    private int forgeTier;

    private @Nullable Holder<Ritual> validRitual;
    private @Nullable ActiveRitualData activeRitualData;

    public RitualManager(MagicCircleController circleController, SingleItemResourceHandler mainIngredientInventory, int forgeTier, ForgeDataCache dataCache) {
        this.magicCircleController = circleController;
        this.mainIngredientInventory = mainIngredientInventory;
        this.forgeTier = forgeTier;
        this.dataCache = dataCache;
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

    public Optional<ActiveRitualData> getActiveRitualData() {
        return Optional.ofNullable(this.activeRitualData);
    }

    private void setActiveRitual(@Nullable Holder<Ritual> ritual, @Nullable ServerPlayer startedBy) {
        this.activeRitualData = ritual != null ? ActiveRitualData.create(ritual, startedBy) : null;

        int duration = ritual != null ? ritual.value().duration() : 0;

        PacketDistributor.sendToPlayersTrackingChunk(this.level, new ChunkPos(pos), new AdvancedBlockEventPayload(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_DURATION, duration));
    }

    public boolean isRitualActive() {
        return this.level != null && this.getActiveRitualData().isPresent();
    }

    public void onDataChanged(ForgeDataCache dataCache, HephaestusForgeState state) {
        this.dataCache = dataCache;

        if (this.activeRitualData != null) {
            if (this.activeRitualData.isStillValid(state.mainItem(), state.pedestalItems())) {
                return;
            }

            this.failRitual();
        }

        this.updateValidRitual(state.essenceSet());
    }

    private void updateValidRitual(EssenceSet definition) {
        boolean oldValue = this.validRitual != null;

        for (Holder<Ritual> ritual : this.level.registryAccess().lookupOrThrow(FARegistries.RITUAL).listElements().toList()) {
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

    private boolean canStartRitual(Ritual ritual, EssenceSet definition) {
        List<EssenceModifier> modifiers = this.dataCache.getEnhancers().stream()
                .flatMap(enhancerDefinition -> enhancerDefinition.value().getEffects(EnhancerTarget.HEPHAESTUS_FORGE))
                .filter(effect -> effect instanceof EssenceModifier)
                .map(effect -> (EssenceModifier) effect)
                .toList();

        EssenceSet updatedEssences = ritual.requirements().essences().applyModifiers(modifiers);

        return definition.hasMoreThan(updatedEssences) && ritual.canStart(this.dataCache, this.mainIngredientInventory.getStack(), this.forgeTier);
    }

    public boolean startRitual(ServerLevel level, BlockPos pos, ServerPlayer player, EssenceAccess essenceAccess) {
        return this.getValidRitual().map(ritual -> {
            this.setActiveRitual(ritual, player);

            this.magicCircleController.createMagicCircle(level, pos, ritual.value().magicCircleType());

            essenceAccess.removeEssences(ritual.value().requirements().essences());

            this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> {
                blockEntity.setItemHeightTarget(PEDESTAL_ITEM_HEIGHT);
            });

            return true;
        }).orElse(false);
    }

    public void finishRitual(ActiveRitualData data) {
        this.reset();

        ItemStack result = data.finish(this.level, this.pos, this.mainIngredientInventory.getStack());

        this.mainIngredientInventory.setStack(result);
    }

    private void failRitual() {
        ItemStack stack = this.mainIngredientInventory.getStack();

        this.reset();

        if (!stack.isEmpty()) {
            this.level.addFreshEntity(new ItemEntity(this.level, this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5, stack));
        }

        this.level.sendParticles(ModParticles.HUGE_MAGIC_EXPLOSION.get(), this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        this.level.playSound(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0F, (1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F) * 0.7F);

        this.mainIngredientInventory.setStack(ItemStack.EMPTY);
    }

    private void clearPedestals() {
        this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> blockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED));

        this.dataCache.cachedIngredients().clear();
    }

    private void reset() {
        this.validRitual = null;
        this.setActiveRitual(null, null);
        this.magicCircleController.removeMagicCircle(this.level, this.pos);
        this.updateRitualIndicator(false);
        this.clearPedestals();
    }

    public void save(ValueOutput output) {
        //TODO
//        this.getActiveRitualData().flatMap(data -> {
//            return ActiveRitualData.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), this.activeRitualData).result();
//        }).ifPresent(dataTag -> {
//            tag.put(TAG_ACTIVE_RITUAL, dataTag);
//        });
    }

    public void load(ValueInput input) {
//        if (tag.contains(TAG_ACTIVE_RITUAL)) {
//            this.activeRitualData = ActiveRitualData.CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), tag.get(TAG_ACTIVE_RITUAL)).resultOrPartial(System.err::println).orElse(null);
//        }
    }

    private void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        for (ForgeDataCache.IngredientEntry entry : this.dataCache.cachedIngredients()) {
            if (this.level.getBlockEntity(entry.pos()) instanceof PedestalBlockEntity blockEntity && predicate.test(blockEntity)) {
                consumer.accept(blockEntity);
            }
        }
    }

    private void updateRitualIndicator(boolean show) {
        if (this.level != null) {
            this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_INDICATOR, BooleanUtils.toInteger(show));
        }
    }
}
