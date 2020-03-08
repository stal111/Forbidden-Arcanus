package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.BoomArrowEntity;
import com.stal111.forbidden_arcanus.entity.projectile.DracoArcanusArrowEntity;
import com.stal111.forbidden_arcanus.entity.projectile.FrozenArrowEntity;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModArrowItem extends ArrowItem {

    public ModArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity entity) {
        if (this == ModItems.BOOM_ARROW.get()) {
            return  new BoomArrowEntity(entity, world);
        } else if (this == ModItems.DRACO_ARCANUS_ARROW.get()) {
            return new DracoArcanusArrowEntity(entity, world);
        } else {
            return new FrozenArrowEntity(entity, world);
        }
    }
}
