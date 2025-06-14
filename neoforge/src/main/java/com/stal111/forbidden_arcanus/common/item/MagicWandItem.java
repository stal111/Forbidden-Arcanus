package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.AurealMissile;
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

    private static final float CHARGE_DURATION = 30.0F;

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
        if (!level.isClientSide() && (stack.getUseDuration(livingEntity) - timeLeft) >= CHARGE_DURATION) {
            AurealMissile aurealMissile = new AurealMissile(livingEntity, level, livingEntity.position().x(), livingEntity.getEyePosition().y(), livingEntity.position().z());
            aurealMissile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0F, 1.1F, 0.5F);

            level.addFreshEntity(aurealMissile);
        }
    }

    public static float getUseProgress(ItemStack stack, LivingEntity entity) {
        return entity.isUsingItem() ? Math.min(CHARGE_DURATION, stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / CHARGE_DURATION : 0.0F;
    }
}
