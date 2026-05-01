package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class EncapsulatedBlackHoleItem extends BlockItem {

    public EncapsulatedBlackHoleItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        Player player = context.getPlayer();

        if (result.consumesAction() && player != null && !player.hasInfiniteMaterials()) {
            player.setItemInHand(context.getHand(), ModItems.CONTAINMENT_CAPSULE.get().getDefaultInstance());
        }

        return result;
    }
}
