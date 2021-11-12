package com.stal111.forbidden_arcanus.aureal.consequence;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Effect Consequence
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.consequence.EffectConsequence
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-04
 */
public class EffectConsequence implements IConsequence {

    public List<Pair<MobEffect, Integer>> effects = new ArrayList<>();

    @Override
    public ResourceLocation getName() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "effect");
    }

    @Override
    public void tick(Player player) {
        effects.clear();
        effects.add(Pair.of(MobEffects.CONFUSION, 100));
        effects.add(Pair.of(MobEffects.WEAKNESS, 1800));
        effects.add(Pair.of(MobEffects.UNLUCK, 3600));
        effects.add(Pair.of(MobEffects.BAD_OMEN, 3600));
        effects.add(Pair.of(ModEffects.DARKENED.get(), 2400));

        Pair<MobEffect, Integer> pair = effects.get(player.getRandom().nextInt(effects.size()));
        player.addEffect(new MobEffectInstance(pair.getFirst(), pair.getSecond()));
    }

    @Override
    public IConsequenceType getType() {
        return Consequences.EFFECT;
    }

    public static class Type implements IConsequenceType {

        @Override
        public ResourceLocation getName() {
            return new ResourceLocation(ForbiddenArcanus.MOD_ID, "effect");
        }

        @Override
        public IConsequence createConsequence() {
            return new EffectConsequence();
        }
    }
}
