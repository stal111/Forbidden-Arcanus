package com.stal111.forbidden_arcanus.common.block.pedestal.effect;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 02.04.2024
 */
public class UpdateForgeIngredientsEffect extends PedestalEffect {

    public UpdateForgeIngredientsEffect() {
        super(PedestalEffectTrigger.PLAYER_PLACE_ITEM, PedestalEffectTrigger.PLAYER_REMOVE_ITEM, PedestalEffectTrigger.ENTITY_SUMMONED, PedestalEffectTrigger.MAGNETIZED_PICKUP, PedestalEffectTrigger.LOAD);
    }

    @Override
    public void execute(ServerLevel level, BlockPos pos, ItemStack stack) {
        findHephaestusForge(level, pos).ifPresent(forgePos -> {
            if (level.getBlockEntity(forgePos) instanceof HephaestusForgeBlockEntity blockEntity) {
                RitualManager ritualManager = blockEntity.getRitualManager();

                ritualManager.updateIngredient(pos, stack, blockEntity.getEssences());
            }
        });
    }

    private static Optional<BlockPos> findHephaestusForge(ServerLevel level, BlockPos pos) {
        return level.getPoiManager().getInRange(poiTypeHolder -> poiTypeHolder.value() == ModPOITypes.HEPHAESTUS_FORGE.get(), pos, 4, PoiManager.Occupancy.ANY).map(PoiRecord::getPos).findFirst();
    }
}
