package com.stal111.forbidden_arcanus.item.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class StrangeRootItem extends BlockItem {

    public StrangeRootItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        super.onItemUseFinish(stack, world, entity);
        if (!world.isRemote) {
            for (int i = 0; i < entity.getActivePotionEffects().size(); i++) {
                EffectInstance effectInstance = (EffectInstance) entity.getActivePotionEffects().toArray()[i];
                if (effectInstance.getPotion().getEffect().getEffectType() == EffectType.HARMFUL) {
                    entity.removePotionEffect(effectInstance.getPotion());
                }
            }
        }

        return stack;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getDefaultTranslationKey();
    }
}
