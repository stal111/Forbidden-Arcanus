package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DarkBeaconBlock extends Block implements IBeaconBeamColorProvider, ITileEntityProvider {

	public DarkBeaconBlock(Properties properties) {
		super(properties);
	}

	@Override
	public DyeColor getColor() {
		return DyeColor.WHITE;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new DarkBeaconTileEntity();
	}
	
	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return (INamedContainerProvider) worldIn.getTileEntity(pos);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof DarkBeaconTileEntity) {
				((DarkBeaconTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}
}
