package com.stal111.forbidden_arcanus.common.effect;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.phys.AABB;
import net.minecraft.ChatFormatting;

import java.util.List;

/**
 * Spectral Eye Effect <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.effect.SpectralEyeEffect
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class SpectralEyeEffect extends MobEffect {

    private static final int EFFECT_RADIUS = 60;

    public SpectralEyeEffect(MobEffectCategory effectCategory, int color) {
        super(effectCategory, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int i = entity.getEffect(this).getDuration();
        if (!entity.getCommandSenderWorld().isClientSide()) {
            Scoreboard scoreboard = entity.getCommandSenderWorld().getServer().getScoreboard();
            PlayerTeam teamPassiveOrNeutral = ModUtils.createTeam(scoreboard, "PassiveOrNeutral", ChatFormatting.GREEN);
            PlayerTeam teamHostile = ModUtils.createTeam(scoreboard, "Hostile", ChatFormatting.RED);
            PlayerTeam teamWater = ModUtils.createTeam(scoreboard, "Water", ChatFormatting.BLUE);

            double k = entity.getX();
            double l = entity.getY();
            double i1 = entity.getZ();

            AABB aabb = new AABB(k, l, i1, (k + 1), (l + 1), (i1 + 1)).inflate(EFFECT_RADIUS).expandTowards(0.0D, entity.level().getMaxBuildHeight(), 0.0D);
            List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, aabb);

            for(LivingEntity livingEntity : list) {
                if (livingEntity instanceof Animal || livingEntity instanceof AmbientCreature) {
                    scoreboard.addPlayerToTeam(livingEntity.getScoreboardName(), teamPassiveOrNeutral);
                } else if (livingEntity instanceof Monster) {
                    scoreboard.addPlayerToTeam(livingEntity.getScoreboardName(), teamHostile);
                } else if (!(livingEntity instanceof Player)) {
                    scoreboard.addPlayerToTeam(livingEntity.getScoreboardName(), teamWater);
                }
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 5, 0, true, true, false));
            }

            if (i <= 5) {
                ModUtils.removeTeam(scoreboard, teamPassiveOrNeutral);
                ModUtils.removeTeam(scoreboard, teamHostile);
                ModUtils.removeTeam(scoreboard, teamWater);
            }
        }
    }
}
