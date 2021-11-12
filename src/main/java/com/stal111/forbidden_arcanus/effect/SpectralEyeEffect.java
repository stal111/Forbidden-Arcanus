package com.stal111.forbidden_arcanus.effect;

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

public class SpectralEyeEffect extends MobEffect {

    public SpectralEyeEffect(MobEffectCategory effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
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

            AABB axisalignedbb = (new AABB(k, l, i1, (k + 1), (l + 1), (i1 + 1))).inflate(70).expandTowards(0.0D, entity.level.getMaxBuildHeight(), 0.0D);
            List<LivingEntity> list = entity.level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);

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

//    @Override
//    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
//        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/spectral_vision.png"));
//    }
}
