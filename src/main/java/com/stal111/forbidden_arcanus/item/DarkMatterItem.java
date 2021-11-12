package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

/**
 * Dark Matter Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.DarkMatterItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-03
 */
public class DarkMatterItem extends Item {

    public DarkMatterItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.getCommandSenderWorld();
        BlockPos pos = entity.blockPosition();

        List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(0.5));

        for (ItemEntity itemEntity : itemEntities) {
            if (itemEntity.getItem().getItem() == ModItems.CORRUPTI_DUST.get() && world.getBlockState(pos).isAir()) {
                entity.getItem().shrink(1);
                itemEntity.getItem().shrink(1);

                world.setBlockAndUpdate(pos, NewModBlocks.BLACK_HOLE.get().defaultBlockState());
            }
        }
        return false;
    }
}
