package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.AurealMissile;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class MagicWandItem extends Item {

    public static final float CHARGE_DURATION = 30.0F;

    public MagicWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        stack.addToTooltip(ModDataComponents.WAND_PARTS, context, tooltipDisplay, tooltipAdder, flag);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (EssenceHelper.hasEnough(player, stack)) {
            player.startUsingItem(usedHand);

            return InteractionResult.CONSUME;
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if ((stack.getUseDuration(livingEntity) - timeLeft) >= CHARGE_DURATION && EssenceHelper.hasEnough(livingEntity, stack)) {
            if (!level.isClientSide()) {
                this.shootProjectile(level, livingEntity);

                EssenceHelper.consume(livingEntity, stack);
            }

            livingEntity.swing(livingEntity.getUsedItemHand());

            return true;
        }
        return false;
    }

    private void shootProjectile(Level level, LivingEntity livingEntity) {
        AurealMissile aurealMissile = new AurealMissile(livingEntity, level, livingEntity.position().x(), livingEntity.getEyePosition().y(), livingEntity.position().z());
        aurealMissile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0F, 1.1F, 0.5F);

        level.addFreshEntity(aurealMissile);

        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSounds.MAGIC_WAND_CAST.get(), livingEntity.getSoundSource(), 1.0F, 1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F);
    }

    public static float getUseProgress(LivingEntity entity, float partialTick) {
        if (!entity.isUsingItem() || !(entity.getUseItem().getItem() instanceof MagicWandItem)) {
            return 0.0F;
        }
        return Mth.clamp(entity.getTicksUsingItem(partialTick) / CHARGE_DURATION, 0.0F, 1.0F);
    }
}
