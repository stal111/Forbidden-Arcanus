package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author stal111
 * @since 2023-02-05
 */
public record UpgradeTierResult(HephaestusForgeLevel resultTier) implements RitualResult {

    public static final MapCodec<UpgradeTierResult> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            HephaestusForgeLevel.CODEC.fieldOf("result_tier").forGetter(UpgradeTierResult::resultTier)
    ).apply(instance, UpgradeTierResult::new));

    @Override
    public ItemStack getResultItem(ItemStack mainInput) {
        return ItemStack.EMPTY;
    }

    @Override
    public void executeLevelEffect(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        level.setBlockAndUpdate(pos, state.setValue(ModBlockStateProperties.FORGE_TIER, this.resultTier));

        CrimsonLightningBoltEntity entity = ModEntities.CRIMSON_LIGHTNING_BOLT.get().create(level, EntitySpawnReason.EVENT);

        if (entity != null) {
            entity.snapTo(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setVisualOnly(true);

            level.addFreshEntity(entity);
        }
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.UPGRADE_TIER.get();
    }
}
