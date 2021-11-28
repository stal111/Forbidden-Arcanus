package com.stal111.forbidden_arcanus.common.tile;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.init.other.ModPOITypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

/**
 * Arcane Crystal Obelisk Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.ArcaneCrystalObeliskTileEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-03
 */
public class ArcaneCrystalObeliskTileEntity extends BlockEntity {

    public ArcaneCrystalObeliskTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCANE_CRYSTAL_OBELISK.get(), pos, state);
    }

   // @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide() || this.level.getGameTime() % 100 != 0) {
            return;
        }

        PoiManager manager = ((ServerLevel) this.level).getPoiManager();

        BlockPos pos = manager.getInRange(poiType -> poiType == ModPOITypes.HEPHAESTUS_FORGE.get(), this.worldPosition, 4, PoiManager.Occupancy.ANY).map(PoiRecord::getPos).findFirst().orElse(null);

        if (pos != null) {
            HephaestusForgeTileEntity tileEntity = (HephaestusForgeTileEntity) level.getBlockEntity(pos);

            Objects.requireNonNull(tileEntity).getEssenceManager().increaseAureal(1);
        }
    }
}
