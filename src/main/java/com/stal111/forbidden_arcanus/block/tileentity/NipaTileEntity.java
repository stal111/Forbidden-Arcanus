package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Nipa Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.NipaTileEntity
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-21
 */
public class NipaTileEntity extends TileEntity implements ITickableTileEntity {

    private int lastSpeck = 0;
    private int speckHeight = 10;

    private Map<UUID, Integer> players = new HashMap<>();

    private int cachedPower = 0;

    public NipaTileEntity() {
        super(ModTileEntities.NIPA.get());
    }

    @Override
    public void tick() {
        if (!this.getBlockState().get(ModBlockStateProperties.SPECK)) {
            this.lastSpeck++;

            if (this.lastSpeck >= 3600) {
                this.getWorld().setBlockState(this.getPos(), this.getBlockState().with(ModBlockStateProperties.SPECK, true), 3);
                this.lastSpeck = 0;
                this.speckHeight = 10;
            }
        } else {
            if (this.speckHeight < 75) {
                this.speckHeight++;
            }
            this.lastSpeck = 0;
        }

        if (this.world != null && this.world.getGameTime() % 20 == 0) {
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.pos).grow(3);
            List<PlayerEntity> playersInRange = this.world.getEntitiesWithinAABB(PlayerEntity.class, axisAlignedBB);

            Map<UUID, Integer> players = new HashMap<>();

            for (PlayerEntity player : playersInRange) {
                players.put(player.getUniqueID(), this.players.getOrDefault(player.getUniqueID(), 0) + 1);

                if (players.get(player.getUniqueID()) == 30) {
                    AurealHelper.increaseAureal(player, 1);
                    players.remove(player.getUniqueID());
                }
            }

            this.players = players;
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        this.lastSpeck = compound.getInt("LastSpeck");
        this.speckHeight = compound.getInt("SpeckHeight");

        ListNBT players = compound.getList("Players", 10);

        Map<UUID, Integer> playersInRange = new HashMap<>();

        players.forEach(nbt -> {
            if (nbt instanceof CompoundNBT) {
                CompoundNBT player = (CompoundNBT) nbt;

                playersInRange.put(player.getUniqueId("Player"), player.getInt("Time"));
            }
            this.players = playersInRange;
        });
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        compound.putInt("LastSpeck", this.lastSpeck);
        compound.putInt("SpeckHeight", this.speckHeight);

        ListNBT players = new ListNBT();

        this.players.forEach((uuid, integer) -> {
            CompoundNBT player = new CompoundNBT();
            player.putUniqueId("Player", uuid);
            player.putInt("Time", integer);

            players.add(player);
        });
        compound.put("Players", players);

        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public double getSpeckHeight() {
        return speckHeight / 100.0F;
    }

    public void setSpeckHeight(int speckHeight) {
        this.speckHeight = speckHeight;
    }

    public int getCachedPower() {
        return cachedPower;
    }

    public void setCachedPower(int cachedPower) {
        this.cachedPower = cachedPower;
    }
}