package com.stal111.forbidden_arcanus.init.other;

import com.stal111.forbidden_arcanus.common.block.dispenser.SoulDispenseBehavior;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * Mod Dispense Behaviours <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModDispenseBehaviors
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-21
 */
public class ModDispenseBehaviors {

    public static void registerDispenseBehaviours() {
        DispenserBlock.registerBehavior(ModItems.SOUL.get(), new SoulDispenseBehavior());
    }
}
