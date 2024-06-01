package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;

import java.util.Map;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class UpgradeTierResult extends RitualResult {

    //TODO
    private static final Map<Integer, BlockRegistryEntry<HephaestusForgeBlock>> FORGE_TIERS = ImmutableMap.of(
            1, ModBlocks.HEPHAESTUS_FORGE_TIER_1,
            2, ModBlocks.HEPHAESTUS_FORGE_TIER_2,
            3, ModBlocks.HEPHAESTUS_FORGE_TIER_3,
            4, ModBlocks.HEPHAESTUS_FORGE_TIER_4,
            5, ModBlocks.HEPHAESTUS_FORGE_TIER_5
    );

    public static final UpgradeTierResult INSTANCE = new UpgradeTierResult();

    public static final MapCodec<UpgradeTierResult> CODEC = MapCodec.unit(INSTANCE);

    private UpgradeTierResult() {
    }

    @Override
    public ItemStack apply(Level level, BlockPos pos, int forgeTier) {
        BlockState state = level.getBlockState(pos);

        level.setBlockAndUpdate(pos, FORGE_TIERS.get(forgeTier + 1).get().defaultBlockState().setValue(HephaestusForgeBlock.ACTIVATED, state.getValue(HephaestusForgeBlock.ACTIVATED)).setValue(HephaestusForgeBlock.WATERLOGGED, state.getValue(HephaestusForgeBlock.WATERLOGGED)));

        CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), level);
        entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
        entity.setVisualOnly(true);

        level.addFreshEntity(entity);

        return ItemStack.EMPTY;
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.UPGRADE_TIER.get();
    }
}
