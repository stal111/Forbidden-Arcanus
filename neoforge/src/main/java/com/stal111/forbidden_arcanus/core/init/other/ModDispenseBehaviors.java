package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.common.block.dispenser.ArcaneBoneMealDispenseBehavior;
import com.stal111.forbidden_arcanus.common.block.dispenser.SoulDispenseBehavior;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * Mod Dispense Behaviours <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModDispenseBehaviors
 *
 * @author stal111
 * @version 1.18.1 - 2.0.1
 * @since 2021-11-21
 */
public class ModDispenseBehaviors {

    public static void registerDispenseBehaviors() {
        DispenserBlock.registerBehavior(ModItems.SOUL.get(), new SoulDispenseBehavior());
        DispenserBlock.registerBehavior(ModBlocks.OBSIDIAN_SKULL.getSkull(), ObsidianSkullItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(), ObsidianSkullItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(), ObsidianSkullItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(), ObsidianSkullItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull(), ObsidianSkullItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull(), ObsidianSkullItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerProjectileBehavior(ModItems.BOOM_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.DRACO_ARCANUS_ARROW.get());
        DispenserBlock.registerBehavior(ModItems.ARCANE_BONE_MEAL.get(), new ArcaneBoneMealDispenseBehavior());
    }
}
