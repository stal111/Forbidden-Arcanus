package com.stal111.forbidden_arcanus.common.tile;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.init.other.ModPOITypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

/**
 * Arcane Crystal Obelisk Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.ArcaneCrystalObeliskTileEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-03
 */
public class ArcaneCrystalObeliskTileEntity extends TileEntity implements ITickableTileEntity {

    public ArcaneCrystalObeliskTileEntity() {
        super(ModTileEntities.ARCANE_CRYSTAL_OBELISK.get());
    }

    @Override
    public void tick() {
        if (this.world == null || this.world.isRemote() || this.world.getGameTime() % 100 != 0) {
            return;
        }

        PointOfInterestManager manager = ((ServerWorld) this.world).getPointOfInterestManager();

        BlockPos pos = manager.func_219146_b(poiType -> poiType == ModPOITypes.HEPHAESTUS_FORGE.get(), this.pos, 4, PointOfInterestManager.Status.ANY).map(PointOfInterest::getPos).findFirst().orElse(null);

        if (pos != null) {
            HephaestusForgeTileEntity tileEntity = (HephaestusForgeTileEntity) world.getTileEntity(pos);

            Objects.requireNonNull(tileEntity).getEssenceManager().increaseAureal(1);
        }
    }
}
