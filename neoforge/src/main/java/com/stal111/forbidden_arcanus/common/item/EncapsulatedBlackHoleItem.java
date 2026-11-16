package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class EncapsulatedBlackHoleItem extends BlockItem {

    public EncapsulatedBlackHoleItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        UseRemainder remainder = stack.get(DataComponents.USE_REMAINDER);
        int countBefore = stack.getCount();

        InteractionResult result = super.useOn(context);

        if (result.consumesAction() && remainder != null && player != null) {
            ItemStack remaining = remainder.convertIntoRemainder(stack, countBefore, player.hasInfiniteMaterials(), player::handleExtraItemsCreatedOnUse);
            player.setItemInHand(context.getHand(), remaining);
        }

        return result;
    }
}
