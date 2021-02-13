package com.stal111.forbidden_arcanus.aureal.consequence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

/**
 * Change Weather Consequence
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.consequence.ChangeWeatherConsequence
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-04
 */
public class ChangeWeatherConsequence implements IConsequence {

    @Override
    public ResourceLocation getName() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "change_weather");
    }

    @Override
    public void tick(PlayerEntity player) {
        if (player.getEntityWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) player.getEntityWorld();
            world.func_241113_a_(0, 4800, true, world.getRandom().nextBoolean());
        }
    }

    @Override
    public IConsequenceType getType() {
        return Consequences.CHANGE_WEATHER;
    }

    public static class Type implements IConsequenceType {

        @Override
        public ResourceLocation getName() {
            return new ResourceLocation(ForbiddenArcanus.MOD_ID, "change_weather");
        }

        @Override
        public IConsequence createConsequence() {
            return new ChangeWeatherConsequence();
        }
    }
}
