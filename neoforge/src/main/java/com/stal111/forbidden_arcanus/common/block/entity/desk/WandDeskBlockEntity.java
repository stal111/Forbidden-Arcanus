package com.stal111.forbidden_arcanus.common.block.entity.desk;

import com.stal111.forbidden_arcanus.common.block.WandDeskBlock;
import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.ResultSlotResourceHandler;
import com.stal111.forbidden_arcanus.common.inventory.wand.WandDeskMenu;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class WandDeskBlockEntity extends BaseContainerBlockEntity implements BlockEntityAgeAccess, ItemOwner {

    private static final Component NAME = Component.translatable("container.forbidden_arcanus.wand_desk");

    private final ResultSlotResourceHandler wandResourceHandler = new ResultSlotResourceHandler(true, _ -> {
        this.level.setBlockAndUpdate(this.getBlockPos(), ModBlocks.DESK.get().withPropertiesOf(this.getBlockState()));
    });

    private ItemStack stack = ItemStack.EMPTY;
    private int tickCount;

    public WandDeskBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.WAND_DESK.get(), worldPosition, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, WandDeskBlockEntity blockEntity) {
        blockEntity.tickCount++;
    }

    public void setItem(ItemStack stack) {
        this.stack = stack;
        this.wandResourceHandler.setStack(stack);

        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public ItemStack getItem() {
        return this.stack;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.store("item", ItemStack.CODEC, this.stack);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.stack = input.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        this.wandResourceHandler.setStack(this.stack);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    public int getAgeInTicks() {
        return this.tickCount;
    }

    @Override
    protected Component getDefaultName() {
        return NAME;
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
        return new WandDeskMenu(containerId, inventory, ContainerLevelAccess.create(this.level, this.getBlockPos()), this.wandResourceHandler);
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
        return this.getBlockState().getValue(WandDeskBlock.FACING).getOpposite().toYRot();
    }
}
