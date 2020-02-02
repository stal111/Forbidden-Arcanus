package com.stal111.forbidden_arcanus.item.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;
import java.util.Map;

public class WallFloorOrCeilingItem extends BlockItem {

    protected final Block wallBlock;
    protected final Block ceilingBlock;

    public WallFloorOrCeilingItem(Block floorBlock, Block wallBlock, Block ceilingBlock, Properties properties) {
        super(floorBlock, properties);
        this.wallBlock = wallBlock;
        this.ceilingBlock = ceilingBlock;
    }


    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate1 = null;
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        Direction direction = context.getFace();
        Direction.Axis axis = direction.getAxis();

        if (axis == Direction.Axis.Y) {
            BlockState state2 = direction == Direction.DOWN ? this.ceilingBlock.getStateForPlacement(context) : this.getBlock().getStateForPlacement(context);
            if (state2.isValidPosition(iworldreader, blockpos)) {
                blockstate1 = state2;
            }
        } else {
            BlockState state2 = this.wallBlock.getStateForPlacement(context);
            if (state2.isValidPosition(iworldreader, blockpos)) {
                blockstate1 = state2;
            }
        }

        return blockstate1 != null && iworldreader.func_226663_a_(blockstate1, blockpos, ISelectionContext.dummy()) ? blockstate1 : null;
    }

    @Override
    public void addToBlockToItemMap(Map<Block, Item> map, Item item) {
        super.addToBlockToItemMap(map, item);
        map.put(this.wallBlock, item);
        map.put(this.ceilingBlock, item);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> map, Item item) {
        super.removeFromBlockToItemMap(map, item);
        map.remove(this.wallBlock);
        map.remove(this.ceilingBlock);
    }
}
