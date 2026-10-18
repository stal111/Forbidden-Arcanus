package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.ActiveRitualData;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.HephaestusForgeState;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import com.stal111.forbidden_arcanus.common.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HephaestusForgeServerData {

    public static final int PEDESTAL_ITEM_HEIGHT = 140;

    public static final MapCodec<HephaestusForgeServerData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityReference.<ServerPlayer>codec().optionalFieldOf("ritual_initiator").forGetter(data -> Optional.ofNullable(data.ritualInitiator))
    ).apply(instance, ritualInitiator -> new HephaestusForgeServerData(ritualInitiator.orElse(null))));

    private final Map<BlockPos, ItemStack> pedestalItems = new HashMap<>();
    private @Nullable EntityReference<ServerPlayer> ritualInitiator;

    public HephaestusForgeServerData() {
    }

    public HephaestusForgeServerData(@Nullable EntityReference<ServerPlayer> ritualInitiator) {
        this.ritualInitiator = ritualInitiator;
    }

    private void setActiveRitual(@Nullable Holder<Ritual> ritual, @Nullable ServerPlayer initiator, HephaestusForgeSharedData sharedData) {
        this.ritualInitiator = initiator != null ? EntityReference.of(initiator) : null;
        sharedData.setActiveRitualData(ritual != null ? ActiveRitualData.create(ritual) : null);
    }

    public Map<BlockPos, ItemStack> getPedestalItems() {
        return Collections.unmodifiableMap(this.pedestalItems);
    }

    public void updatePedestalStack(BlockPos pos, ItemStack stack) {
        if (stack.isEmpty()) {
            this.pedestalItems.remove(pos);
        } else {
            this.pedestalItems.put(pos, stack);
        }
    }

    public void onDataChanged(ServerLevel level, BlockPos pos, HephaestusForgeState state, HephaestusForgeSharedData sharedData) {
        sharedData.getActiveRitualData().ifPresent(activeRitualData -> {
            if (!activeRitualData.isStillValid(state.mainItem(), state.pedestalItems())) {
                this.failRitual(level, pos, sharedData);
            }
        });

        this.updateValidRitual(level, state, sharedData);
    }

    private void updateValidRitual(ServerLevel level, HephaestusForgeState state, HephaestusForgeSharedData sharedData) {
        if (sharedData.getActiveRitualData().isPresent()) {
            sharedData.setValidRitual(null);
            return;
        }

        List<EssenceModifier> essenceModifiers = EnhancerHelper.getEssenceModifiers(state.enhancers(), EnhancerTarget.HEPHAESTUS_FORGE);

        for (Holder<Ritual> ritual : level.registryAccess().lookupOrThrow(FARegistries.RITUAL).listElements().toList()) {
            if (ritual.value().canStart(state, essenceModifiers)) {
                sharedData.setValidRitual(ritual);
                return;
            }
        }

        sharedData.setValidRitual(null);
    }

    public boolean startRitual(ServerLevel level, BlockPos pos, ServerPlayer player, HephaestusForgeSharedData sharedData) {
        if (sharedData.getActiveRitualData().isPresent()) {
            return false;
        }

        return sharedData.getValidRitual().map(ritual -> {
            this.setActiveRitual(ritual, player, sharedData);

            sharedData.removeEssences(ritual.value().requirements().essences());

            this.forEachPedestal(level, PedestalBlockEntity::hasStack, blockEntity -> {
                blockEntity.setItemHeightTarget(PEDESTAL_ITEM_HEIGHT);
            });

            return true;
        }).orElse(false);
    }

    public void finishRitual(ServerLevel level, BlockPos pos, ActiveRitualData data, HephaestusForgeSharedData sharedData) {
        ServerPlayer initiator = EntityReference.get(this.ritualInitiator, level, ServerPlayer.class);

        this.resetRitual(level, sharedData);

        ItemStack result = data.finish(level, pos, initiator, sharedData.getMainSlotInventory().getStack());
        sharedData.getMainSlotInventory().setStack(result);

        level.sendParticles(ModParticles.MAGIC_EXPLOSION.get(), pos.getX() + 0.5D, pos.getY() + 1.4D, pos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private void failRitual(ServerLevel level, BlockPos pos, HephaestusForgeSharedData sharedData) {
        ItemStack stack = sharedData.getMainSlotInventory().getStack();

        this.resetRitual(level, sharedData);

        if (!stack.isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack));
        }

        level.sendParticles(ModParticles.HUGE_MAGIC_EXPLOSION.get(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        level.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0F, (1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F) * 0.7F);

        sharedData.getMainSlotInventory().setStack(ItemStack.EMPTY);
    }

    private void clearPedestals(ServerLevel level) {
        this.forEachPedestal(level, PedestalBlockEntity::hasStack, blockEntity -> {
            blockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED);

            BlockPos pedestalPos = blockEntity.getBlockPos();
            level.sendParticles(ParticleTypes.POOF, pedestalPos.getX() + 0.5D, pedestalPos.getY() + 1.4D, pedestalPos.getZ() + 0.5D, 6, 0.12D, 0.12D, 0.12D, 0.02D);
        });

        this.pedestalItems.clear();
    }

    private void resetRitual(ServerLevel level, HephaestusForgeSharedData sharedData) {
        sharedData.setValidRitual(null);
        this.setActiveRitual(null, null, sharedData);
        this.clearPedestals(level);
    }

    private void forEachPedestal(ServerLevel level, Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        for (BlockPos pedestalPos : this.pedestalItems.keySet()) {
            if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity blockEntity && predicate.test(blockEntity)) {
                consumer.accept(blockEntity);
            }
        }
    }

    void set(HephaestusForgeServerData from) {
        this.ritualInitiator = from.ritualInitiator;
    }
}
