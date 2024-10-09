package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.BoomArrow;
import com.stal111.forbidden_arcanus.common.entity.projectile.DracoArcanusArrow;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Mod Arrow Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ModArrowItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-16
 */
public class ModArrowItem extends ArrowItem {

    public ModArrowItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public AbstractArrow createArrow(@Nonnull Level level, @Nonnull ItemStack pickupStack, @Nonnull LivingEntity shooter, @Nullable ItemStack stack) {
        if (this == ModItems.BOOM_ARROW.get()) {
            return new BoomArrow(level, shooter, pickupStack.copyWithCount(1), stack);
        } else if (this == ModItems.DRACO_ARCANUS_ARROW.get()) {
            return new DracoArcanusArrow(level, shooter, pickupStack.copyWithCount(1), stack);
        }
        return super.createArrow(level, pickupStack, shooter, stack);
    }
}
