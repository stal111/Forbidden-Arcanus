package com.stal111.forbidden_arcanus.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.stal111.forbidden_arcanus.block.ModBlocks;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CherrywoodTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {

	private static final BlockState TRUNK = ModBlocks.cherrywood_log.getDefaultState();
	private static final BlockState LEAF = ModBlocks.cherrywood_leaves.getDefaultState();

	public CherrywoodTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51443_1_, boolean p_i51443_2_) {
		super(p_i51443_1_, p_i51443_2_);
	}

	public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position,
			MutableBoundingBox p_208519_5_) {
		int i = rand.nextInt(3) + rand.nextInt(3) + 5;
		boolean flag = true;
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;
				if (j == position.getY()) {
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getMaxHeight()) {
							if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else if (isSoil(worldIn, position.down(), getSapling())
					&& position.getY() < worldIn.getMaxHeight() - i - 1) {
				this.setDirtAt(worldIn, position.down(), position);
				Direction direction = Direction.Plane.HORIZONTAL.random(rand);
				int k2 = i - rand.nextInt(4) - 1;
				int l2 = 3 - rand.nextInt(3);
				int i3 = position.getX();
				int j3 = position.getZ();
				int j1 = 0;

				for (int k1 = 0; k1 < i; ++k1) {
					int l1 = position.getY() + k1;
					if (k1 >= k2 && l2 > 0) {
						i3 += direction.getXOffset();
						j3 += direction.getZOffset();
						--l2;
					}

					BlockPos blockpos = new BlockPos(i3, l1, j3);
					if (isAirOrLeaves(worldIn, blockpos)) {
						this.func_208532_a(changedBlocks, worldIn, blockpos, p_208519_5_);
						j1 = l1;
					}
				}

				BlockPos blockpos2 = new BlockPos(i3, j1, j3);

				for (int k3 = -3; k3 <= 3; ++k3) {
					for (int j4 = -3; j4 <= 3; ++j4) {
						if (Math.abs(k3) != 3 || Math.abs(j4) != 3) {
							this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(k3, 0, j4), p_208519_5_);
						}
					}
				}

				blockpos2 = blockpos2.up();

				for (int l3 = -1; l3 <= 1; ++l3) {
					for (int k4 = -1; k4 <= 1; ++k4) {
						this.placeLeafAt(changedBlocks, worldIn, blockpos2.add(l3, 0, k4), p_208519_5_);
					}
				}

				this.placeLeafAt(changedBlocks, worldIn, blockpos2.east(2), p_208519_5_);
				this.placeLeafAt(changedBlocks, worldIn, blockpos2.west(2), p_208519_5_);
				this.placeLeafAt(changedBlocks, worldIn, blockpos2.south(2), p_208519_5_);
				this.placeLeafAt(changedBlocks, worldIn, blockpos2.north(2), p_208519_5_);
				i3 = position.getX();
				j3 = position.getZ();
				Direction direction1 = Direction.Plane.HORIZONTAL.random(rand);
				if (direction1 != direction) {
					int i4 = k2 - rand.nextInt(2) - 1;
					int l4 = 1 + rand.nextInt(3);
					j1 = 0;

					for (int i2 = i4; i2 < i && l4 > 0; --l4) {
						if (i2 >= 1) {
							int j2 = position.getY() + i2;
							i3 += direction1.getXOffset();
							j3 += direction1.getZOffset();
							BlockPos blockpos1 = new BlockPos(i3, j2, j3);
							if (isAirOrLeaves(worldIn, blockpos1)) {
								this.func_208532_a(changedBlocks, worldIn, blockpos1, p_208519_5_);
								j1 = j2;
							}
						}

						++i2;
					}

					if (j1 > 0) {
						BlockPos blockpos3 = new BlockPos(i3, j1, j3);

						for (int i5 = -2; i5 <= 2; ++i5) {
							for (int k5 = -2; k5 <= 2; ++k5) {
								if (Math.abs(i5) != 2 || Math.abs(k5) != 2) {
									this.placeLeafAt(changedBlocks, worldIn, blockpos3.add(i5, 0, k5), p_208519_5_);
								}
							}
						}

						blockpos3 = blockpos3.up();

						for (int j5 = -1; j5 <= 1; ++j5) {
							for (int l5 = -1; l5 <= 1; ++l5) {
								this.placeLeafAt(changedBlocks, worldIn, blockpos3.add(j5, 0, l5), p_208519_5_);
							}
						}
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void func_208532_a(Set<BlockPos> p_208532_1_, IWorldWriter p_208532_2_, BlockPos p_208532_3_,
			MutableBoundingBox p_208532_4_) {
		this.setLogState(p_208532_1_, p_208532_2_, p_208532_3_, TRUNK, p_208532_4_);
	}

	private void placeLeafAt(Set<BlockPos> worldIn, IWorldGenerationReader pos, BlockPos p_175924_3_,
			MutableBoundingBox p_175924_4_) {
		if (isAirOrLeaves(pos, p_175924_3_)) {
			this.setLogState(worldIn, pos, p_175924_3_, LEAF, p_175924_4_);
		}

	}
}
