package com.stal111.forbidden_arcanus.item.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraft.world.item.Item.Properties;

public class WallFloorOrCeilingItem extends BlockItem {

    protected final Block wallBlock;
    protected final Block ceilingBlock;

    public WallFloorOrCeilingItem(Block floorBlock, Block wallBlock, Block ceilingBlock, Properties properties) {
        super(floorBlock, properties);
        this.wallBlock = wallBlock;
        this.ceilingBlock = ceilingBlock;
    }


    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate1 = null;
        LevelReader iworldreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        Direction.Axis axis = direction.getAxis();

        if (axis == Direction.Axis.Y) {
            BlockState state2 = direction == Direction.DOWN ? this.ceilingBlock.getStateForPlacement(context) : this.getBlock().getStateForPlacement(context);
            if (state2.canSurvive(iworldreader, blockpos)) {
                blockstate1 = state2;
            }
        } else {
            BlockState state2 = this.wallBlock.getStateForPlacement(context);
            if (state2.canSurvive(iworldreader, blockpos)) {
                blockstate1 = state2;
            }
        }

        return blockstate1 != null && iworldreader.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
    }

    @Override
    public void registerBlocks(Map<Block, Item> map, Item item) {
        super.registerBlocks(map, item);
        map.put(this.wallBlock, item);
        map.put(this.ceilingBlock, item);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> map, Item item) {
        super.removeFromBlockToItemMap(map, item);
        map.remove(this.wallBlock);
        map.remove(this.ceilingBlock);
    }
}
