package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MagicWandItem extends Item {

    public MagicWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        stack.addToTooltip(ModDataComponents.WAND_PARTS, context, components::add, flag);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);

        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if ((stack.getUseDuration(livingEntity) - timeLeft) >= 60) {
            EnergyBall energyBall = new EnergyBall(level, livingEntity, livingEntity.getLookAngle().x * 1, livingEntity.getLookAngle().y * 1, livingEntity.getLookAngle().z * 1);
            energyBall.setPos(energyBall.getX(), livingEntity.getY() + livingEntity.getEyeHeight(), energyBall.getZ());

            level.addFreshEntity(energyBall);
        }
    }

    public static float getUseProgress(ItemStack stack, LivingEntity entity) {
        return entity.isUsingItem() ? Math.min(60, stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 60.0F : 0.0F;
    }
}
