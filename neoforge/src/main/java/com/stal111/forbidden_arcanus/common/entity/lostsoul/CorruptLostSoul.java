package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

public class CorruptLostSoul extends AbstractLostSoul {

    public CorruptLostSoul(EntityType<? extends AbstractLostSoul> entityType, Level level) {
        super(entityType, level, 68 << 16 | 83 << 8 | 149);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getMainHandItem();

        if (stack.is(ModItems.AUREAL_BOTTLE)) {
            this.convertTo(ModEntities.LOST_SOUL.get(), ConversionParams.single(this, true, true), entity -> {
                EventHooks.onLivingConvert(this, entity);
            });

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }
}
