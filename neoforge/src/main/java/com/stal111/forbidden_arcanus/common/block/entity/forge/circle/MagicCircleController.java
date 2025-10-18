package com.stal111.forbidden_arcanus.common.block.entity.forge.circle;

import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
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
public class MagicCircleController implements BlockEntityAgeAccess {

    private final int eventId;

    private @Nullable MagicCircleType magicCircleType;
    private int ageInTicks;

    public MagicCircleController(int eventId) {
        this.eventId = eventId;
    }

    public void tick() {
        if (this.magicCircleType != null) {
            this.ageInTicks++;
        }
    }

    public void handleEvent(@Nullable Level level, BlockPos pos, int type) {
        this.magicCircleType = level != null ? this.getMagicCircleFromRegistry(level, pos, type) : null;
        this.ageInTicks = 0;
    }

    public void createMagicCircle(ServerLevel level, BlockPos pos, Holder<MagicCircleType> type) {
        int id = level.registryAccess().lookupOrThrow(FARegistries.MAGIC_CIRCLE).getId(type.value());

        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, id);
    }

    public void removeMagicCircle(ServerLevel level, BlockPos pos) {
        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, -1);
    }

    @Nullable
    private MagicCircleType getMagicCircleFromRegistry(Level level, BlockPos pos, int id) {
        return level.registryAccess().lookupOrThrow(FARegistries.MAGIC_CIRCLE).get(id)
                .map(Holder.Reference::value)
                .orElse(null);
    }

    @Nullable
    public MagicCircleType getMagicCircleType() {
        return this.magicCircleType;
    }

    @Override
    public int getAgeInTicks() {
        return this.ageInTicks;
    }
}
