package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.common.block.dispenser.SoulDispenseBehavior;
import com.stal111.forbidden_arcanus.common.entity.projectile.BoomArrow;
import com.stal111.forbidden_arcanus.common.entity.projectile.DracoArcanusArrow;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import javax.annotation.Nonnull;

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
        DispenserBlock.registerBehavior(ModItems.OBSIDIAN_SKULL_SHIELD.get(), ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModItems.OBSIDIAN_SKULL.get(), ObsidianSkullItem.getDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.ETERNAL_OBSIDIAN_SKULL.get(), ObsidianSkullItem.getDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.BOOM_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @Nonnull
            protected Projectile getProjectile(@Nonnull Level level, @Nonnull Position position, @Nonnull ItemStack stack) {
                AbstractArrow arrow = new BoomArrow(level, position.x(), position.y(), position.z());
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;

                return arrow;
            }
        });
        DispenserBlock.registerBehavior(ModItems.DRACO_ARCANUS_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @Nonnull
            protected Projectile getProjectile(@Nonnull Level level, @Nonnull Position position, @Nonnull ItemStack stack) {
                AbstractArrow arrow = new DracoArcanusArrow(level, position.x(), position.y(), position.z());
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;

                return arrow;
            }
        });
    }
}
