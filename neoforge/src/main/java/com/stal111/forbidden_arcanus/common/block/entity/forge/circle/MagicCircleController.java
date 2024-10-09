package com.stal111.forbidden_arcanus.common.block.entity.forge.circle;

import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 14.04.2024
 */
public class MagicCircleController {

    private final int eventId;

    private MagicCircle magicCircle;

    public MagicCircleController(int eventId) {
        this.eventId = eventId;
    }

    public void tick() {
        if (this.magicCircle != null) {
            this.magicCircle.tick();
        }
    }

    @Nullable
    public MagicCircle getMagicCircle() {
        return this.magicCircle;
    }

    void setMagicCircle(@Nullable MagicCircle magicCircle) {
        this.magicCircle = magicCircle;
    }

    public void handleEvent(@Nullable Level level, BlockPos pos, int type) {
        MagicCircle magicCircle = level != null ? this.getMagicCircleFromRegistry(level, pos, type) : null;

        this.setMagicCircle(magicCircle);
    }

    public void createMagicCircle(ServerLevel level, BlockPos pos, Holder<MagicCircleType> type) {
        int id = level.registryAccess().registryOrThrow(FARegistries.MAGIC_CIRCLE).getId(type.value());

        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, id);
    }

    public void removeMagicCircle(ServerLevel level, BlockPos pos) {
        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, -1);
    }

    private MagicCircle getMagicCircleFromRegistry(Level level, BlockPos pos, int id) {
        return level.registryAccess().registryOrThrow(FARegistries.MAGIC_CIRCLE).getHolder(id)
                .map(holder -> holder.value().create(level, pos))
                .orElse(null);
    }
}
