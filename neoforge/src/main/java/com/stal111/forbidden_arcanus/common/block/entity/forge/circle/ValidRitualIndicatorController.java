package com.stal111.forbidden_arcanus.common.block.entity.forge.circle;

import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.apache.commons.lang3.BooleanUtils;

public class ValidRitualIndicatorController implements BlockEntityAgeAccess {

    private final int eventId;

    private boolean hasIndicator;
    private int ageInTicks;

    public ValidRitualIndicatorController(int eventId) {
        this.eventId = eventId;
    }

    public void tick() {
        if (this.hasIndicator()) {
            this.ageInTicks++;
        }
    }

    public void changeVisibility(ServerLevel level, BlockPos pos, boolean showIndicator) {
        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, BooleanUtils.toInteger(showIndicator));
    }

    public void updateIndicator(boolean showIndicator) {
        this.hasIndicator = showIndicator;
        this.ageInTicks = 0;
    }

    public boolean hasIndicator() {
        return this.hasIndicator;
    }

    @Override
    public int getAgeInTicks() {
        return this.ageInTicks;
    }
}
