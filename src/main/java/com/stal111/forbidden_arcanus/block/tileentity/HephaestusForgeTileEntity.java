package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Hephaestus Forge Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.HephaestusForgeTileEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-18
 */
public class HephaestusForgeTileEntity extends TileEntity implements ITickableTileEntity {

    public HephaestusForgeTileEntity() {
        super(ModTileEntities.HEPHAESTUS_FORGE.get());
    }

    @Override
    public void tick() {
        if (this.world == null) {
            return;
        }
        if (this.world.getGameTime() % 80L == 0L) {
            ((HephaestusForgeBlock) this.getBlockState().getBlock()).updateState(this.getBlockState(), this.world, this.pos);
        }
    }
}
