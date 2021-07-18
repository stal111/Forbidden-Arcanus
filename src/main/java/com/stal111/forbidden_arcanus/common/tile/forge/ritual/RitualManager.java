package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdatePedestalPacket;
import com.stal111.forbidden_arcanus.util.ISavedData;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Ritual Manager
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class RitualManager implements ISavedData {

    private final List<BlockPos> pedestals = new ArrayList<>();
    private boolean ritualActive = false;

    public List<BlockPos> getPedestals() {
        return pedestals;
    }

    public void addPedestal(BlockPos pos) {
        this.pedestals.add(pos);
    }

    public void removePedestal(BlockPos pos) {
        this.pedestals.remove(pos);
    }

    public boolean isRitualActive() {
        return ritualActive;
    }

    public void setRitualActive(boolean ritualActive) {
        this.ritualActive = ritualActive;
    }

    public void tryStartRitual(World world, HephaestusForgeTileEntity forgeTileEntity, PlayerEntity player) {
        List<ItemStack> list = new ArrayList<>();

        for (BlockPos pos : this.getPedestals()) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (!(tileEntity instanceof PedestalTileEntity)) {
                continue;
            }

            if (!((PedestalTileEntity) tileEntity).getStack().isEmpty()) {
                list.add(((PedestalTileEntity) tileEntity).getStack());
            }
        }

        for (Ritual ritual : RitualLoader.getRituals()) {
            if (ritual.canStart(list, forgeTileEntity)) {
                this.startRitual(world, ritual, forgeTileEntity, player);
                return;
            }
        }
    }

    public void startRitual(World world, Ritual ritual, HephaestusForgeTileEntity tileEntity, PlayerEntity player) {
        tileEntity.getMagicCircle().setTextures(player, tileEntity.getPos(), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/absolute.png"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/inner_protection.png"));

        ritual.getEssences().reduceEssences(tileEntity);
        tileEntity.setInventorySlotContents(4, ritual.getResult());

        for (BlockPos pos : this.getPedestals()) {
            if (!(world.getTileEntity(pos) instanceof PedestalTileEntity)) {
                continue;
            }

            PedestalTileEntity pedestalTileEntity = (PedestalTileEntity) world.getTileEntity(pos);

            if (!pedestalTileEntity.getStack().isEmpty()) {
                RenderUtils.addItemParticles(world, pedestalTileEntity.getStack(), pos, 1600);
               // NetworkHandler.sendTo(player, new ItemParticlePacket(pos, pedestalTileEntity.getStack()));

                pedestalTileEntity.clearStack();
                NetworkHandler.sendTo(player, new UpdatePedestalPacket(pos));
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (!this.getPedestals().isEmpty()) {
            compound.putLongArray("pedestals", Lists.transform(this.getPedestals(), BlockPos::toLong));
        }
        if (this.isRitualActive()) {
            compound.putBoolean("RitualActive", true);
        }

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("pedestals")) {
            this.pedestals.clear();
            long[] pedestals = compound.getLongArray("pedestals");

            for (long pedestal : pedestals) {
                this.addPedestal(BlockPos.fromLong(pedestal));
            }
        }
        if (compound.contains("RitualActive")) {
            this.setRitualActive(compound.getBoolean("RitualActive"));
        }
    }
}
