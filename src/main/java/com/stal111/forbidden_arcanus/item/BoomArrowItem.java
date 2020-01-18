package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.BoomArrowEntity;
import com.stal111.forbidden_arcanus.entity.projectile.DracoArcanusArrowEntity;
import com.stal111.forbidden_arcanus.entity.projectile.FrozenArrowEntity;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BoomArrowItem extends ArrowItem {

    public BoomArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity entity) {
        if (this == ModItems.BOOM_ARROW.getItem()) {
            return  new BoomArrowEntity(entity, world);
        } else if (this == ModItems.DRACO_ARCANUS_ARROW.getItem()) {
            return new DracoArcanusArrowEntity(entity, world);
        } else {
            return new FrozenArrowEntity(entity, world);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote) {
            BoomArrowEntity entity = new BoomArrowEntity(player, world);
            entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(entity);
        }
        return super.onItemRightClick(world, player, hand);
    }
}
