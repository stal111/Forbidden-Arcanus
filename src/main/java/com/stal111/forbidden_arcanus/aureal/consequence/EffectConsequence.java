package com.stal111.forbidden_arcanus.aureal.consequence;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;

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

    public List<Pair<Effect, Integer>> effects = new ArrayList<>();

    @Override
    public ResourceLocation getName() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "effect");
    }

    @Override
    public void tick(PlayerEntity player) {
        effects.clear();
        effects.add(Pair.of(Effects.NAUSEA, 100));
        effects.add(Pair.of(Effects.WEAKNESS, 1800));
        effects.add(Pair.of(Effects.UNLUCK, 3600));
        effects.add(Pair.of(Effects.BAD_OMEN, 3600));
        effects.add(Pair.of(ModEffects.DARKENED.get(), 2400));

        Pair<Effect, Integer> pair = effects.get(player.getRNG().nextInt(effects.size()));
        player.addPotionEffect(new EffectInstance(pair.getFirst(), pair.getSecond()));
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
