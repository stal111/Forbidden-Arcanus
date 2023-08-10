package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

/**
 * Mod Foods <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ModFoods
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-17
 */
public class ModFoods {

    public static final FoodProperties BAT_SOUP = new FoodProperties.Builder().nutrition(7).saturationMod (0.7F).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0), 1.0F).build();
    public static final FoodProperties BAT_WING = new FoodProperties.Builder().nutrition(3).saturationMod (0.2F).effect(() -> new MobEffectInstance(MobEffects.POISON, 160, 0), 0.9F).build();
    public static final FoodProperties TENTACLE = new FoodProperties.Builder().nutrition(2).saturationMod (0.1F).meat().build();
    public static final FoodProperties COOKED_TENTACLE = new FoodProperties.Builder().nutrition(5).saturationMod (0.6F).meat().build();
    public static final FoodProperties STRANGE_ROOT = new FoodProperties.Builder().nutrition(3).saturationMod (0.6F).build();

}
