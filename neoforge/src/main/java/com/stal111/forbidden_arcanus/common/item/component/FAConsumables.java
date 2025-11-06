package com.stal111.forbidden_arcanus.common.item.component;

import com.stal111.forbidden_arcanus.common.essence.source.ValueComponentEssenceSource;
import com.stal111.forbidden_arcanus.common.item.consumeeffect.AddEssenceConsumeEffect;
import com.stal111.forbidden_arcanus.common.item.consumeeffect.GiveExperienceConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class FAConsumables {

    public static final Consumable BAT_SOUP = Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0), 1.0F)).build();
    public static final Consumable BAT_WING = Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 160, 0), 0.9F)).build();
    public static final Consumable AUREAL_BOTTLE = Consumables.defaultDrink().onConsume(new AddEssenceConsumeEffect(ValueComponentEssenceSource.INSTANCE)).build();
    public static final Consumable XPETRIFIED_ORB = Consumable.builder().consumeSeconds(0.0F).onConsume(new GiveExperienceConsumeEffect()).build();
}
