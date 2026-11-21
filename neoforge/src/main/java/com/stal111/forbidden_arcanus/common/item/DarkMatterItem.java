package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public class DarkMatterItem extends Item {

    public DarkMatterItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();

        if (level.isClientSide() || entity.tickCount % 5 != 0) {
            return false;
        }

        BlockPos pos = entity.blockPosition();

        if (!level.getBlockState(pos).isAir()) {
            return false;
        }

        List<ItemEntity> itemEntities = level.getEntitiesOfClass(
                ItemEntity.class,
                new AABB(pos).inflate(0.5),
                e -> e.getItem().is(ModItems.CORRUPTI_DUST.get())
        );

        if (!itemEntities.isEmpty()) {
            stack.shrink(1);
            itemEntities.getFirst().getItem().shrink(1);

            level.setBlockAndUpdate(pos, ModBlocks.BLACK_HOLE.get().defaultBlockState());
        }

        return false;
    }
}
