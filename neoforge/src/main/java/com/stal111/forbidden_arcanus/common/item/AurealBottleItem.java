package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class AurealBottleItem extends Item {

    private static final int USE_DURATION = 32;

    public AurealBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide()) {
            int aurealAmount = EssenceHelper.getEssenceAmount(stack, EssenceType.AUREAL);

            EssenceHelper.getEssenceProvider(livingEntity).ifPresent(provider -> {
                provider.updateAmount(EssenceType.AUREAL, amount -> amount + aurealAmount);
            });
        }

        stack.consume(1, livingEntity);

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        EssenceProvider essenceProvider = EssenceHelper.getEssenceProvider(player).orElse(null);

        if (essenceProvider != null && !essenceProvider.isFull(EssenceType.AUREAL)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return super.use(level, player, hand);
    }
}
