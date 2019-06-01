package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ModOreBlock extends BlockOre {

	public ModOreBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
		if (this == ModBlocks.arcane_crystal_ore) {
			return ModItems.arcane_crystal;
		} else if (this == ModBlocks.runestone) {
			return ModItems.rune;
		} else if (this == ModBlocks.dark_runestone) {
			return ModItems.dark_rune;
		} else {
			return this;
		}
	}

	@Override
	public int getExpDrop(IBlockState state, IWorldReader reader, BlockPos pos, int fortune) {
		World world = reader instanceof World ? (World) reader : null;
		if (world == null || this.getItemDropped(state, world, pos, fortune) != this) {
			int i = 0;
			if (this == ModBlocks.arcane_crystal_ore) {
				i = MathHelper.nextInt(RANDOM, 3, 5);
			} else if (this == ModBlocks.runestone) {
				i = MathHelper.nextInt(RANDOM, 5, 8);
			} else if (this == ModBlocks.dark_runestone) {
				i = MathHelper.nextInt(RANDOM, 5, 8);
			}
			return i;
		}
		return 0;
	}

}
