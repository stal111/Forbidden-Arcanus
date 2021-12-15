package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Arcane Crystal Obelisk Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.ArcaneCrystalObeliskBlockEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-03
 */
public class ArcaneCrystalObeliskBlockEntity extends BlockEntity {

    public ArcaneCrystalObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCANE_CRYSTAL_OBELISK.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ArcaneCrystalObeliskBlockEntity blockEntity) {
        if (level.getGameTime() % 100 != 0) {
            return;
        }

        PoiManager manager = ((ServerLevel) level).getPoiManager();

        BlockPos forgePos = manager.getInRange(poiType -> poiType == ModPOITypes.HEPHAESTUS_FORGE.get(), pos, 4, PoiManager.Occupancy.ANY).map(PoiRecord::getPos).findFirst().orElse(null);

        if (forgePos != null && level.getBlockEntity(pos) instanceof HephaestusForgeTileEntity forgeBlockEntity) {
            forgeBlockEntity.getEssenceManager().increaseAureal(1);
        }
    }
}
