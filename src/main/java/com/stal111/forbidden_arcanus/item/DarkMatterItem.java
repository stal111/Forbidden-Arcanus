package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

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
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();

        List<ItemEntity> itemEntities = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(0.5));

        for (ItemEntity itemEntity : itemEntities) {
            if (itemEntity.getItem().getItem() == ModItems.CORRUPTI_DUST.get() && world.getBlockState(pos).isAir()) {
                entity.getItem().shrink(1);
                itemEntity.getItem().shrink(1);

                world.setBlockState(pos, NewModBlocks.BLACK_HOLE.get().getDefaultState());
            }
        }
        return false;
    }
}
