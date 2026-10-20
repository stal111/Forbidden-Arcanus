package com.stal111.forbidden_arcanus.common.essence;

import net.minecraft.network.chat.Component;

public class EssenceCosts {

    // Text Components
    public static final Component CAST_SPELL_COMPONENT = Component.translatable("tooltip.forbidden_arcanus.action.cast_spell");
    public static final Component CATCH_COMPONENT = Component.translatable("tooltip.forbidden_arcanus.action.catch");


    // Predefined Essence Costs
    public static final EssenceCost WAND = EssenceCost.of(EssenceType.AUREAL, 5, CAST_SPELL_COMPONENT);
}
