package com.stal111.forbidden_arcanus.common.block.entity.forge.tick;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.TickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeServerData;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeSharedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.state.BlockState;

public record ProgressRitualTickEffect(
        HephaestusForgeServerData serverData,
        HephaestusForgeSharedData sharedData
) implements TickEffect {

    public static final int PEDESTAL_ITEM_HEIGHT = 140;

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        this.sharedData.getActiveRitualData().ifPresent(activeRitualData -> {
            activeRitualData.incrementCounter();

            float progress = activeRitualData.calculateRitualProgress();

            this.serverData.getPedestalItems().forEach((pedestalPos, stack) -> {
                this.addItemParticles(level, pos, pedestalPos, Math.min(PedestalBlockEntity.DEFAULT_ITEM_HEIGHT + activeRitualData.getCounter(), PEDESTAL_ITEM_HEIGHT), ItemStackTemplate.fromNonEmptyStack(stack));
            });

            if (progress == 1.0F) {
                this.serverData.finishRitual(level, pos, activeRitualData, this.sharedData);
            }
        });
    }

    @Override
    public int getTickInterval() {
        return 1;
    }

    private void addItemParticles(ServerLevel level, BlockPos pos, BlockPos pedestalPos, int itemHeight, ItemStackTemplate stack) {
        double posX = pedestalPos.getX() + 0.5D;
        double posY = pedestalPos.getY() + 0.1D + itemHeight / 100.0F;
        double posZ = pedestalPos.getZ() + 0.5D;
        double xSpeed = 0.1D * (pos.getX() - pedestalPos.getX());
        double ySpeed = 0.22D;
        double zSpeed = 0.1D * (pos.getZ() - pedestalPos.getZ());

        if (level.getRandom().nextDouble() < 0.6D) {
            level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), posX, posY, posZ, 0, xSpeed, ySpeed, zSpeed, 0.9D);
        }
    }
}
