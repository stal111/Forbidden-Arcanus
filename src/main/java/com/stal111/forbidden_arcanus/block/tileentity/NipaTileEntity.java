package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

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
public class NipaTileEntity extends BlockEntity {

    private int lastSpeck = 0;
    private int speckHeight = 10;

    private Map<UUID, Integer> players = new HashMap<>();

    private int cachedPower = 0;

    public NipaTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.NIPA.get(), pos, state);
    }

    //TODO
    //@Override
    public void tick() {
        if (!this.getBlockState().getValue(ModBlockStateProperties.SPECK)) {
            this.lastSpeck++;

            if (this.lastSpeck >= 3600) {
                this.getLevel().setBlock(this.getBlockPos(), this.getBlockState().setValue(ModBlockStateProperties.SPECK, true), 3);
                this.lastSpeck = 0;
                this.speckHeight = 10;
            }
        } else {
            if (this.speckHeight < 75) {
                this.speckHeight++;
            }
            this.lastSpeck = 0;
        }

        if (this.level != null && this.level.getGameTime() % 20 == 0) {
            AABB axisAlignedBB = new AABB(this.worldPosition).inflate(3);
            List<Player> playersInRange = this.level.getEntitiesOfClass(Player.class, axisAlignedBB);

            Map<UUID, Integer> players = new HashMap<>();

            for (Player player : playersInRange) {
                players.put(player.getUUID(), this.players.getOrDefault(player.getUUID(), 0) + 1);

                if (players.get(player.getUUID()) == 30) {
                    AurealHelper.increaseAureal(player, 1);
                    players.remove(player.getUUID());
                }
            }

            this.players = players;
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.lastSpeck = compound.getInt("LastSpeck");
        this.speckHeight = compound.getInt("SpeckHeight");

        ListTag players = compound.getList("Players", 10);

        Map<UUID, Integer> playersInRange = new HashMap<>();

        players.forEach(nbt -> {
            if (nbt instanceof CompoundTag) {
                CompoundTag player = (CompoundTag) nbt;

                playersInRange.put(player.getUUID("Player"), player.getInt("Time"));
            }
            this.players = playersInRange;
        });
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound = super.save(compound);
        compound.putInt("LastSpeck", this.lastSpeck);
        compound.putInt("SpeckHeight", this.speckHeight);

        ListTag players = new ListTag();

        this.players.forEach((uuid, integer) -> {
            CompoundTag player = new CompoundTag();
            player.putUUID("Player", uuid);
            player.putInt("Time", integer);

            players.add(player);
        });
        compound.put("Players", players);

        return compound;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
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