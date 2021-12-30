package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Nipa Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.NipaBlockEntity
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-21
 */
public class NipaBlockEntity extends BlockEntity {

    private int lastSpeck = 0;
    private int speckHeight = 10;

    private Map<UUID, Integer> players = new HashMap<>();

    private int cachedPower = 0;

    public NipaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NIPA.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, NipaBlockEntity blockEntity) {
        if (blockEntity.speckHeight < 75) {
            blockEntity.speckHeight++;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, NipaBlockEntity blockEntity) {
        if (!state.getValue(ModBlockStateProperties.SPECK)) {
            blockEntity.lastSpeck++;

            if (blockEntity.lastSpeck >= 3600) {
                level.setBlockAndUpdate(pos, state.setValue(ModBlockStateProperties.SPECK, true));
                blockEntity.lastSpeck = 0;
            }
        }

        if (level.getGameTime() % 20 == 0) {
            AABB axisAlignedBB = new AABB(pos).inflate(3);
            List<Player> playersInRange = level.getEntitiesOfClass(Player.class, axisAlignedBB);

            Map<UUID, Integer> players = new HashMap<>();

            for (Player player : playersInRange) {
                UUID uuid = player.getUUID();

                players.put(uuid, blockEntity.players.getOrDefault(uuid, 0) + 1);

                if (players.get(uuid) == 30) {
                    AurealHelper.increaseAureal(player, 1);
                    players.remove(uuid);
                }
            }

            blockEntity.players = players;
        }
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        this.lastSpeck = tag.getInt("LastSpeck");

        this.players.clear();

        ListTag players = tag.getList("Players", 10);

        players.forEach(nbt -> {
            if (nbt instanceof CompoundTag player) {
                this.players.put(player.getUUID("Player"), player.getInt("Time"));
            }
        });
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("LastSpeck", this.lastSpeck);

        ListTag players = new ListTag();

        this.players.forEach((uuid, integer) -> {
            CompoundTag player = new CompoundTag();
            player.putUUID("Player", uuid);
            player.putInt("Time", integer);

            players.add(player);
        });
        tag.put("Players", players);
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