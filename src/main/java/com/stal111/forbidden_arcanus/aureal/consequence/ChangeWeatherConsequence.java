package com.stal111.forbidden_arcanus.aureal.consequence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

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
    public void tick(Player player) {
        if (player.getCommandSenderWorld() instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) player.getCommandSenderWorld();
            world.setWeatherParameters(0, 4800, true, world.getRandom().nextBoolean());
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
