package com.stal111.forbidden_arcanus.item.block;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class StrangeRootItem extends BlockItem {

    public StrangeRootItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        super.finishUsingItem(stack, world, entity);
        if (!world.isClientSide) {
            for (int i = 0; i < entity.getActiveEffects().size(); i++) {
                MobEffectInstance effectInstance = (MobEffectInstance) entity.getActiveEffects().toArray()[i];
                if (effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                    entity.removeEffect(effectInstance.getEffect());
                }
            }
        }

        return stack;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return this.getOrCreateDescriptionId();
    }
}
