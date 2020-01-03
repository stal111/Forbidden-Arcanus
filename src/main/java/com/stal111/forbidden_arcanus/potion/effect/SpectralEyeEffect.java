package com.stal111.forbidden_arcanus.potion.effect;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.potion.*;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class SpectralEyeEffect extends Effect {

    protected SpectralEyeEffect(EffectType effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        int i = entity.getActivePotionEffect(this).getDuration();
        if (!entity.getEntityWorld().isRemote()) {
            Scoreboard scoreboard = entity.getEntityWorld().getServer().getScoreboard();
            ScorePlayerTeam teamPassiveOrNeutral = ModUtils.createTeam(scoreboard, "PassiveOrNeutral", TextFormatting.GREEN);
            ScorePlayerTeam teamHostile = ModUtils.createTeam(scoreboard, "Hostile", TextFormatting.RED);
            ScorePlayerTeam teamWater = ModUtils.createTeam(scoreboard, "Water", TextFormatting.BLUE);

            double k = entity.func_226277_ct_();
            double l = entity.func_226278_cu_();
            double i1 = entity.func_226281_cx_();

            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(k, l, i1, (k + 1), (l + 1), (i1 + 1))).grow(70).expand(0.0D, entity.world.getHeight(), 0.0D);
            List<LivingEntity> list = entity.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);

            for(LivingEntity livingEntity : list) {
                if (livingEntity instanceof AnimalEntity || livingEntity instanceof AmbientEntity) {
                    scoreboard.addPlayerToTeam(livingEntity.getScoreboardName(), teamPassiveOrNeutral);
                } else if (livingEntity instanceof MonsterEntity) {
                    scoreboard.addPlayerToTeam(livingEntity.getScoreboardName(), teamHostile);
                } else {
                    scoreboard.addPlayerToTeam(livingEntity.getScoreboardName(), teamWater);
                }
                livingEntity.addPotionEffect(new EffectInstance(Effects.GLOWING, 10, 0, true, true, false));
            }

            if (i <= 20) {
                ModUtils.removeTeam(scoreboard, teamPassiveOrNeutral);
                ModUtils.removeTeam(scoreboard, teamHostile);
                ModUtils.removeTeam(scoreboard, teamWater);
            }
        }
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/spectral_vision.png"));
    }
}
