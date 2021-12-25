package com.stal111.forbidden_arcanus.common.aureal.consequence;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * Effect Consequence <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.common.consequence.EffectConsequence
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-04
 */
public class EffectConsequence extends Consequence {

    private static final List<Pair<MobEffect, Integer>> EFFECTS = List.of(
            Pair.of(MobEffects.CONFUSION, 100),
            Pair.of(MobEffects.WEAKNESS, 1800),
            Pair.of(MobEffects.UNLUCK, 3600),
            Pair.of(MobEffects.BAD_OMEN, 3600),
            Pair.of(ModEffects.DARKENED.get(), 2400)
    );

    public EffectConsequence(ConsequenceType<?> type) {
        super(type);
    }

    @Override
    public void tick(Player player) {
        Pair<MobEffect, Integer> pair = EFFECTS.get(player.getRandom().nextInt(EFFECTS.size()));
        player.addEffect(new MobEffectInstance(pair.getFirst(), pair.getSecond()));
    }
}
