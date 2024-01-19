package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author stal111
 * @since 2023-04-03
 */
public class DemolishingModifierBlockBreaker {

    private static final Map<UUID, DemolishingModifierBlockBreaker> ACTIVE_BREAKERS = new HashMap<>();
    private static final Map<UUID, DemolishingModifierBlockBreaker> ACTIVE_CLIENT_BREAKERS = new HashMap<>();

    private final Level level;
    private final BlockPos pos;
    private final BlockState state;
    private final Player player;

    private final Map<BlockPos, Integer> idFromPos;

    private DemolishingModifierBlockBreaker(Level level, BlockPos pos, BlockState state, Player player, List<BlockPos> positions) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.player = player;

        this.idFromPos = positions.stream().collect(Collectors.toMap(Function.identity(), offsetPos -> player.getRandom().nextInt(5000)));
    }

    public static DemolishingModifierBlockBreaker getOrCreate(Level level, BlockPos pos, BlockState state, Player player) {
        UUID uuid = player.getUUID();

        boolean createBreaker = !DemolishingModifierBlockBreaker.containsBreaker(level, uuid);

        if (!createBreaker && !DemolishingModifierBlockBreaker.getBreaker(level, uuid).getPos().equals(pos)) {
            createBreaker = true;

            DemolishingModifierBlockBreaker.getBreaker(level, uuid).stop();
        }

        if (createBreaker) {
            DemolishingModifierBlockBreaker.create(level, pos, state, player);
        }

        return DemolishingModifierBlockBreaker.getBreaker(level, uuid);
    }

    private static void create(Level level, BlockPos pos, BlockState state, Player player) {
        List<BlockPos> positions = new BlockScanner(pos).getValidPositions(level, DemolishingModifierBlockBreaker.calculateBlockSide(level, player), offsetState -> offsetState.is(state.getBlock()));

        DemolishingModifierBlockBreaker.putBreaker(level, player.getUUID(), new DemolishingModifierBlockBreaker(level, pos, state, player, positions));
    }

    private static Direction calculateBlockSide(Level level, Player player) {
        Vec3 eyePosition = player.getEyePosition();
        Vec3 viewVector = player.getViewVector(1);
        double reach = player.getAttributeValue(NeoForgeMod.BLOCK_REACH.value());

        Vec3 combined = eyePosition.add(viewVector.x * reach, viewVector.y * reach, viewVector.z * reach);

        BlockHitResult hitResult = level.clip(new ClipContext(eyePosition, combined, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return Direction.NORTH;
        }

        return hitResult.getDirection();
    }

    private static DemolishingModifierBlockBreaker getBreaker(Level level, UUID uuid) {
        if (level.isClientSide()) {
            return ACTIVE_CLIENT_BREAKERS.getOrDefault(uuid, null);
        }

        return ACTIVE_BREAKERS.getOrDefault(uuid, null);
    }

    private static boolean containsBreaker(Level level, UUID uuid) {
        if (level.isClientSide()) {
            return ACTIVE_CLIENT_BREAKERS.containsKey(uuid);
        }

        return ACTIVE_BREAKERS.containsKey(uuid);
    }

    private static void putBreaker(Level level, UUID uuid, DemolishingModifierBlockBreaker breaker) {
        if (level.isClientSide()) {
            ACTIVE_CLIENT_BREAKERS.put(uuid, breaker);

            return;
        }

        ACTIVE_BREAKERS.put(uuid, breaker);
    }

    private static void removeBreaker(Level level, UUID uuid) {
        if (level.isClientSide()) {
            ACTIVE_CLIENT_BREAKERS.remove(uuid);

            return;
        }

        ACTIVE_BREAKERS.remove(uuid);
    }

    public static Optional<DemolishingModifierBlockBreaker> get(Level level, Player player) {
        return Optional.ofNullable(DemolishingModifierBlockBreaker.getBreaker(level, player.getUUID()));
    }

    public void update(int destroyProgress) {
        if (!this.checkPositions()) {
            return;
        }

        for (BlockPos offsetPos : this.idFromPos.keySet()) {
            if (destroyProgress >= 10) {
                return;
            }

            this.updateBreakingProgress(offsetPos, destroyProgress - 1);

        }
    }

    private boolean checkPositions() {
        Iterator<Map.Entry<BlockPos, Integer>> iterator = this.idFromPos.entrySet().iterator();

        while (iterator.hasNext()) {
            BlockPos pos = iterator.next().getKey();

            if (!this.level.getBlockState(pos).is(this.state.getBlock())) {
                iterator.remove();
            }
        }

        if (this.idFromPos.isEmpty()) {
            this.stop();

            return false;
        }

        return true;
    }

    public void stop() {
        this.idFromPos.forEach((offsetPos, integer) -> {
            this.updateBreakingProgress(offsetPos, -1);
        });

        DemolishingModifierBlockBreaker.removeBreaker(this.level, this.player.getUUID());
    }

    public void breakBlocks(ServerPlayer player) {
        this.stop();

        this.idFromPos.keySet().forEach(player.gameMode::destroyBlock);
    }

    private void updateBreakingProgress(BlockPos pos, int progress) {
        int id = this.idFromPos.get(pos);

        if (this.level instanceof ServerLevel serverLevel) {
            for(ServerPlayer serverPlayer : serverLevel.getServer().getPlayerList().getPlayers()) {
                if (serverPlayer != null && serverPlayer.level() == serverLevel && serverPlayer.getId() != this.player.getId()) {
                    double d0 = (double)pos.getX() - serverPlayer.getX();
                    double d1 = (double)pos.getY() - serverPlayer.getY();
                    double d2 = (double)pos.getZ() - serverPlayer.getZ();

                    if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D) {
                        serverPlayer.connection.send(new ClientboundBlockDestructionPacket(id, pos, progress));
                    }
                }
            }
            return;
        }

        this.level.destroyBlockProgress(id, pos, progress);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    private record BlockScanner(BlockPos centerPos) {

        private List<BlockPos> getValidPositions(Level level, Direction facing, Predicate<BlockState> predicate) {
            int data3d = facing.get3DDataValue();

            var cornerPositions = this.getCornerPositions(Direction.from3DDataValue(data3d + 2), Direction.from3DDataValue(data3d + 4));

            return BlockPos.betweenClosedStream(cornerPositions.getFirst(), cornerPositions.getSecond())
                    .filter(pos -> predicate.test(level.getBlockState(pos)))
                    .filter(pos -> !pos.equals(this.centerPos))
                    .map(BlockPos::immutable)
                    .toList();
        }

        private Pair<BlockPos, BlockPos> getCornerPositions(Direction first, Direction second) {
            return Pair.of(this.centerPos.relative(first, 1).relative(second, 1), this.centerPos.relative(first, -1).relative(second, -1));
        }
    }
}
