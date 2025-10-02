package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.common.block.dispenser.ArcaneBoneMealDispenseBehavior;
import com.stal111.forbidden_arcanus.common.block.dispenser.SoulDispenseBehavior;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.level.block.DispenserBlock;

public class ModDispenseBehaviors {

    public static void registerDispenseBehaviors() {
        DispenserBlock.registerBehavior(ModItems.SOUL.get(), new SoulDispenseBehavior());
        DispenserBlock.registerProjectileBehavior(ModItems.BOOM_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.DRACO_ARCANUS_ARROW.get());
        DispenserBlock.registerBehavior(ModItems.ARCANE_BONE_MEAL.get(), new ArcaneBoneMealDispenseBehavior());
    }
}
