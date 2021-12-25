package com.stal111.forbidden_arcanus.common.aureal.consequence;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

/**
 * Change Weather Consequence <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.consequence.ChangeWeatherConsequence
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-04
 */
public class ChangeWeatherConsequence extends Consequence {

    private static final int WEATHER_TIME = 4800;

    public ChangeWeatherConsequence(ConsequenceType<?> type) {
        super(type);
    }

    @Override
    public void tick(Player player) {
        if (player.getCommandSenderWorld() instanceof ServerLevel level) {
            level.setWeatherParameters(0, WEATHER_TIME, true, level.getRandom().nextBoolean());
        }
    }
}
