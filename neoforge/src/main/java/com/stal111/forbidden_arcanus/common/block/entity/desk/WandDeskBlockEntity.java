package com.stal111.forbidden_arcanus.common.block.entity.desk;

import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WandDeskBlockEntity extends BaseContainerBlockEntity implements BlockEntityAgeAccess, ItemOwner {

    private int tickCount;

    public WandDeskBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.WAND_DESK.get(), worldPosition, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, WandDeskBlockEntity blockEntity) {
        blockEntity.tickCount++;
    }

    @Override
    public int getAgeInTicks() {
        return this.tickCount;
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return NonNullList.withSize(0, ItemStack.EMPTY);
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public Level level() {
        return this.level;
    }

    @Override
    public Vec3 position() {
        return this.getBlockPos().getCenter();
    }

    @Override
    public float getVisualRotationYInDegrees() {
        return 0;
    }
}
