package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class UpgradeTierResult extends RitualResult {

    public static final Codec<UpgradeTierResult> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("required_tier").forGetter(result -> {
                return result.requiredTier;
            }),
            Codec.INT.fieldOf("upgraded_tier").forGetter(result -> {
                return result.upgradedTier;
            })
    ).apply(instance, UpgradeTierResult::new));

    private final int requiredTier;
    private final int upgradedTier;

    public UpgradeTierResult(int requiredTier, int upgradedTier) {
        this.requiredTier = requiredTier;
        this.upgradedTier = upgradedTier;
    }

    @Override
    public void apply(RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        level.setBlockAndUpdate(pos, state.setValue(HephaestusForgeBlock.TIER, this.upgradedTier));

        accessor.set(ItemStack.EMPTY);

        CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), level);
        entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
        entity.setVisualOnly(true);

        level.addFreshEntity(entity);
    }

    @Override
    public boolean checkConditions(RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos) {
        return level.getBlockState(pos).getValue(HephaestusForgeBlock.TIER) == this.requiredTier;
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.UPGRADE_TIER.get();
    }
}
