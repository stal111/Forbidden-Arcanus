//package com.stal111.forbidden_arcanus.world.gen.feature;
//
//import java.util.Random;
//import java.util.Set;
//
//import com.stal111.forbidden_arcanus.block.ModBlocks;
//
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.init.Blocks;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IWorld;
//import net.minecraft.world.gen.feature.AbstractTreeFeature;
//import net.minecraft.world.gen.feature.NoFeatureConfig;
//
//public class CherrywoodTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
//
//	private static final IBlockState TRUNK = ModBlocks.cherrywood_log.getDefaultState();
//	private static final IBlockState LEAF = ModBlocks.cherrywood_leaves.getDefaultState();
//
//	public CherrywoodTreeFeature(boolean notify) {
//		super(notify);
//	}
//
//	@Override
//	protected boolean place(Set<BlockPos> changedBlocks, IWorld worldIn, Random rand, BlockPos position) {
//		int i = rand.nextInt(3) + rand.nextInt(3) + 5;
//		boolean flag = true;
//		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getWorld().getHeight()) {
//			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
//				int k = 1;
//				if (j == position.getY()) {
//					k = 0;
//				}
//
//				if (j >= position.getY() + 1 + i - 2) {
//					k = 2;
//				}
//
//				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
//
//				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
//					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
//						if (j >= 0 && j < worldIn.getWorld().getHeight()) {
//							if (!this.canGrowInto(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
//								flag = false;
//							}
//						} else {
//							flag = false;
//						}
//					}
//				}
//			}
//
//			if (!flag) {
//				return false;
//			} else {
//				boolean isSoil = worldIn.getBlockState(position.down()).canSustainPlant(worldIn, position.down(),
//						net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling) Blocks.ACACIA_SAPLING);
//				if (isSoil && position.getY() < worldIn.getWorld().getHeight() - i - 1) {
//					this.setDirtAt(worldIn, position.down(), position);
//					EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
//					int k2 = i - rand.nextInt(4) - 1;
//					int l2 = 3 - rand.nextInt(3);
//					int i3 = position.getX();
//					int j1 = position.getZ();
//					int k1 = 0;
//
//					for (int l1 = 0; l1 < i; ++l1) {
//						int i2 = position.getY() + l1;
//						if (l1 >= k2 && l2 > 0) {
//							i3 += enumfacing.getXOffset();
//							j1 += enumfacing.getZOffset();
//							--l2;
//						}
//
//						BlockPos blockpos = new BlockPos(i3, i2, j1);
//						IBlockState iblockstate = worldIn.getBlockState(blockpos);
//						if (iblockstate.isAir(worldIn, blockpos) || iblockstate.isIn(BlockTags.LEAVES)) {
//							this.func_208532_a(changedBlocks, worldIn, blockpos);
//							k1 = i2;
//						}
//					}
//
//					BlockPos blockpos2 = new BlockPos(i3, k1, j1);
//
//					for (int j3 = -3; j3 <= 3; ++j3) {
//						for (int i4 = -3; i4 <= 3; ++i4) {
//							if (Math.abs(j3) != 3 || Math.abs(i4) != 3) {
//								this.placeLeafAt(worldIn, blockpos2.add(j3, 0, i4));
//							}
//						}
//					}
//
//					blockpos2 = blockpos2.up();
//
//					for (int k3 = -1; k3 <= 1; ++k3) {
//						for (int j4 = -1; j4 <= 1; ++j4) {
//							this.placeLeafAt(worldIn, blockpos2.add(k3, 0, j4));
//						}
//					}
//
//					this.placeLeafAt(worldIn, blockpos2.east(2));
//					this.placeLeafAt(worldIn, blockpos2.west(2));
//					this.placeLeafAt(worldIn, blockpos2.south(2));
//					this.placeLeafAt(worldIn, blockpos2.north(2));
//					i3 = position.getX();
//					j1 = position.getZ();
//					EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);
//					if (enumfacing1 != enumfacing) {
//						int l3 = k2 - rand.nextInt(2) - 1;
//						int k4 = 1 + rand.nextInt(3);
//						k1 = 0;
//
//						for (int l4 = l3; l4 < i && k4 > 0; --k4) {
//							if (l4 >= 1) {
//								int j2 = position.getY() + l4;
//								i3 += enumfacing1.getXOffset();
//								j1 += enumfacing1.getZOffset();
//								BlockPos blockpos1 = new BlockPos(i3, j2, j1);
//								IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
//								if (iblockstate1.isAir(worldIn, blockpos1) || iblockstate1.isIn(BlockTags.LEAVES)) {
//									this.func_208532_a(changedBlocks, worldIn, blockpos1);
//									k1 = j2;
//								}
//							}
//
//							++l4;
//						}
//
//						if (k1 > 0) {
//							BlockPos blockpos3 = new BlockPos(i3, k1, j1);
//
//							for (int i5 = -2; i5 <= 2; ++i5) {
//								for (int k5 = -2; k5 <= 2; ++k5) {
//									if (Math.abs(i5) != 2 || Math.abs(k5) != 2) {
//										this.placeLeafAt(worldIn, blockpos3.add(i5, 0, k5));
//									}
//								}
//							}
//
//							blockpos3 = blockpos3.up();
//
//							for (int j5 = -1; j5 <= 1; ++j5) {
//								for (int l5 = -1; l5 <= 1; ++l5) {
//									this.placeLeafAt(worldIn, blockpos3.add(j5, 0, l5));
//								}
//							}
//						}
//					}
//
//					return true;
//				} else {
//					return false;
//				}
//			}
//		} else {
//			return false;
//		}
//	}
//
//	private void func_208532_a(Set<BlockPos> p_208532_1_, IWorld p_208532_2_, BlockPos p_208532_3_) {
//		this.func_208520_a(p_208532_1_, p_208532_2_, p_208532_3_, TRUNK);
//	}
//
//	private void placeLeafAt(IWorld worldIn, BlockPos pos) {
//		IBlockState iblockstate = worldIn.getBlockState(pos);
//		if (iblockstate.canBeReplacedByLeaves(worldIn, pos)) {
//			this.setBlockState(worldIn, pos, LEAF);
//		}
//
//	}
//
//}
