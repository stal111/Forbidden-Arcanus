package com.stal111.forbidden_arcanus.common.item;

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

    public static final FoodProperties BAT_SOUP = new FoodProperties.Builder().nutrition(7).saturationModifier(0.7F).build();
    public static final FoodProperties BAT_WING = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2F).build();
    public static final FoodProperties TENTACLE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build();
    public static final FoodProperties COOKED_TENTACLE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6F).build();

}
