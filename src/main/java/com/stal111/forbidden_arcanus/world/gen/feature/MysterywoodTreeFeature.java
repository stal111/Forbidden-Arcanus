package com.stal111.forbidden_arcanus.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class MysterywoodTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {

	private static final BlockState LOG = ModBlocks.MYSTERYWOOD_LOG.getState();
	private static final BlockState LEAVES = ModBlocks.MYSTERYWOOD_LEAVES.getState();

	public MysterywoodTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49918_1_, boolean p_i49918_2_) {
		super(p_i49918_1_, p_i49918_2_);
	}

	private void crossSection(IWorldGenerationReader p_208529_1_, BlockPos p_208529_2_, float p_208529_3_,
			MutableBoundingBox p_208529_4_, Set<BlockPos> p_208529_5_) {
		int i = (int) ((double) p_208529_3_ + 0.618D);

		for (int j = -i; j <= i; ++j) {
			for (int k = -i; k <= i; ++k) {
				if (Math.pow((double) Math.abs(j) + 0.5D, 2.0D)
						+ Math.pow((double) Math.abs(k) + 0.5D, 2.0D) <= (double) (p_208529_3_ * p_208529_3_)) {
					BlockPos blockpos = p_208529_2_.add(j, 0, k);
					if (isAirOrLeaves(p_208529_1_, blockpos)) {
						this.setLogState(p_208529_5_, p_208529_1_, blockpos, LEAVES, p_208529_4_);
					}
				}
			}
		}

	}

	private float treeShape(int p_208527_1_, int p_208527_2_) {
		if ((float) p_208527_2_ < (float) p_208527_1_ * 0.3F) {
			return -1.0F;
		} else {
			float f = (float) p_208527_1_ / 2.0F;
			float f1 = f - (float) p_208527_2_;
			float f2 = MathHelper.sqrt(f * f - f1 * f1);
			if (f1 == 0.0F) {
				f2 = f;
			} else if (Math.abs(f1) >= f) {
				return 0.0F;
			}

			return f2 * 0.5F;
		}
	}

	private float foliageShape(int y) {
		if (y >= 0 && y < 5) {
			return y != 0 && y != 4 ? 3.0F : 2.0F;
		} else {
			return -1.0F;
		}
	}

	private void foliageCluster(IWorldGenerationReader p_202393_1_, BlockPos p_202393_2_,
			MutableBoundingBox p_202393_3_, Set<BlockPos> p_202393_4_) {
		for (int i = 0; i < 5; ++i) {
			this.crossSection(p_202393_1_, p_202393_2_.up(i), this.foliageShape(i), p_202393_3_, p_202393_4_);
		}

	}

	private int makeLimb(Set<BlockPos> p_208523_1_, IWorldGenerationReader p_208523_2_, BlockPos p_208523_3_,
			BlockPos p_208523_4_, boolean p_208523_5_, MutableBoundingBox p_208523_6_) {
		if (!p_208523_5_ && Objects.equals(p_208523_3_, p_208523_4_)) {
			return -1;
		} else {
			BlockPos blockpos = p_208523_4_.add(-p_208523_3_.getX(), -p_208523_3_.getY(), -p_208523_3_.getZ());
			int i = this.getGreatestDistance(blockpos);
			float f = (float) blockpos.getX() / (float) i;
			float f1 = (float) blockpos.getY() / (float) i;
			float f2 = (float) blockpos.getZ() / (float) i;

			for (int j = 0; j <= i; ++j) {
				BlockPos blockpos1 = p_208523_3_.add((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1),
						(double) (0.5F + (float) j * f2));
				if (p_208523_5_) {
					this.setLogState(p_208523_1_, p_208523_2_, blockpos1,
							LOG.with(LogBlock.AXIS, this.getLoxAxis(p_208523_3_, blockpos1)), p_208523_6_);
				} else if (!func_214587_a(p_208523_2_, blockpos1)) {
					return j;
				}
			}

			return -1;
		}
	}

	/**
	 * Returns the absolute greatest distance in the BlockPos object.
	 */
	private int getGreatestDistance(BlockPos posIn) {
		int i = MathHelper.abs(posIn.getX());
		int j = MathHelper.abs(posIn.getY());
		int k = MathHelper.abs(posIn.getZ());
		if (k > i && k > j) {
			return k;
		} else {
			return j > i ? j : i;
		}
	}

	private Direction.Axis getLoxAxis(BlockPos p_197170_1_, BlockPos p_197170_2_) {
		Direction.Axis direction$axis = Direction.Axis.Y;
		int i = Math.abs(p_197170_2_.getX() - p_197170_1_.getX());
		int j = Math.abs(p_197170_2_.getZ() - p_197170_1_.getZ());
		int k = Math.max(i, j);
		if (k > 0) {
			if (i == k) {
				direction$axis = Direction.Axis.X;
			} else if (j == k) {
				direction$axis = Direction.Axis.Z;
			}
		}

		return direction$axis;
	}

	private void makeFoliage(IWorldGenerationReader p_208525_1_, int p_208525_2_, BlockPos p_208525_3_,
			List<MysterywoodTreeFeature.FoliageCoordinates> p_208525_4_, MutableBoundingBox p_208525_5_,
			Set<BlockPos> p_208525_6_) {
		for (MysterywoodTreeFeature.FoliageCoordinates bigtreefeature$foliagecoordinates : p_208525_4_) {
			if (this.trimBranches(p_208525_2_,
					bigtreefeature$foliagecoordinates.getBranchBase() - p_208525_3_.getY())) {
				this.foliageCluster(p_208525_1_, bigtreefeature$foliagecoordinates, p_208525_5_, p_208525_6_);
			}
		}

	}

	private boolean trimBranches(int p_208522_1_, int p_208522_2_) {
		return (double) p_208522_2_ >= (double) p_208522_1_ * 0.2D;
	}

	private void makeTrunk(Set<BlockPos> p_208526_1_, IWorldGenerationReader p_208526_2_, BlockPos p_208526_3_,
			int p_208526_4_, MutableBoundingBox p_208526_5_) {
		this.makeLimb(p_208526_1_, p_208526_2_, p_208526_3_, p_208526_3_.up(p_208526_4_), true, p_208526_5_);
	}

	private void makeBranches(Set<BlockPos> p_208524_1_, IWorldGenerationReader p_208524_2_, int p_208524_3_,
			BlockPos p_208524_4_, List<MysterywoodTreeFeature.FoliageCoordinates> p_208524_5_,
			MutableBoundingBox p_208524_6_) {
		for (MysterywoodTreeFeature.FoliageCoordinates bigtreefeature$foliagecoordinates : p_208524_5_) {
			int i = bigtreefeature$foliagecoordinates.getBranchBase();
			BlockPos blockpos = new BlockPos(p_208524_4_.getX(), i, p_208524_4_.getZ());
			if (!blockpos.equals(bigtreefeature$foliagecoordinates)
					&& this.trimBranches(p_208524_3_, i - p_208524_4_.getY())) {
				this.makeLimb(p_208524_1_, p_208524_2_, blockpos, bigtreefeature$foliagecoordinates, true, p_208524_6_);
			}
		}

	}

	public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position,
			MutableBoundingBox p_208519_5_) {
		Random random = new Random(rand.nextLong());
		int i = this.checkLocation(changedBlocks, worldIn, position, 5 + random.nextInt(12), p_208519_5_);
		if (i == -1) {
			return false;
		} else {
			this.setDirtAt(worldIn, position.down(), position);
			int j = (int) ((double) i * 0.618D);
			if (j >= i) {
				j = i - 1;
			}

			int k = (int) (1.382D + Math.pow(1.0D * (double) i / 13.0D, 2.0D));
			if (k < 1) {
				k = 1;
			}

			int l = position.getY() + j;
			int i1 = i - 5;
			List<MysterywoodTreeFeature.FoliageCoordinates> list = Lists.newArrayList();
			list.add(new MysterywoodTreeFeature.FoliageCoordinates(position.up(i1), l));

			for (; i1 >= 0; --i1) {
				float f = this.treeShape(i, i1);
				if (!(f < 0.0F)) {
					for (int j1 = 0; j1 < k; ++j1) {
						double d2 = 1.0D * (double) f * ((double) random.nextFloat() + 0.328D);
						double d3 = (double) (random.nextFloat() * 2.0F) * Math.PI;
						double d4 = d2 * Math.sin(d3) + 0.5D;
						double d5 = d2 * Math.cos(d3) + 0.5D;
						BlockPos blockpos = position.add(d4, (double) (i1 - 1), d5);
						BlockPos blockpos1 = blockpos.up(5);
						if (this.makeLimb(changedBlocks, worldIn, blockpos, blockpos1, false, p_208519_5_) == -1) {
							int k1 = position.getX() - blockpos.getX();
							int l1 = position.getZ() - blockpos.getZ();
							double d6 = (double) blockpos.getY() - Math.sqrt((double) (k1 * k1 + l1 * l1)) * 0.381D;
							int i2 = d6 > (double) l ? l : (int) d6;
							BlockPos blockpos2 = new BlockPos(position.getX(), i2, position.getZ());
							if (this.makeLimb(changedBlocks, worldIn, blockpos2, blockpos, false, p_208519_5_) == -1) {
								list.add(new MysterywoodTreeFeature.FoliageCoordinates(blockpos, blockpos2.getY()));
							}
						}
					}
				}
			}

			this.makeFoliage(worldIn, i, position, list, p_208519_5_, changedBlocks);
			this.makeTrunk(changedBlocks, worldIn, position, j, p_208519_5_);
			this.makeBranches(changedBlocks, worldIn, i, position, list, p_208519_5_);
			return true;
		}
	}

	private int checkLocation(Set<BlockPos> p_208528_1_, IWorldGenerationReader p_208528_2_, BlockPos p_208528_3_,
			int p_208528_4_, MutableBoundingBox p_208528_5_) {
		if (!isSoilOrFarm(p_208528_2_, p_208528_3_.down(), getSapling())) {
			return -1;
		} else {
			int i = this.makeLimb(p_208528_1_, p_208528_2_, p_208528_3_, p_208528_3_.up(p_208528_4_ - 1), false,
					p_208528_5_);
			if (i == -1) {
				return p_208528_4_;
			} else {
				return i < 6 ? -1 : i;
			}
		}
	}

	static class FoliageCoordinates extends BlockPos {
		private final int branchBase;

		public FoliageCoordinates(BlockPos pos, int p_i45635_2_) {
			super(pos.getX(), pos.getY(), pos.getZ());
			this.branchBase = p_i45635_2_;
		}

		public int getBranchBase() {
			return this.branchBase;
		}
	}
}
