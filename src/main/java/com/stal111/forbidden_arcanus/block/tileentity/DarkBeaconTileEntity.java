package com.stal111.forbidden_arcanus.block.tileentity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.block.tileentity.container.DarkBeaconContainer;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.util.GuiTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DarkBeaconTileEntity extends BlockEntity implements MenuProvider, GuiTile {
	/** List of effects that Beacons can apply */
	public static final MobEffect[][] EFFECTS_LIST = new MobEffect[][]{{MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED}, {MobEffects.DAMAGE_RESISTANCE, MobEffects.JUMP}, {MobEffects.DAMAGE_BOOST}, {MobEffects.REGENERATION}};
	private static final Set<MobEffect> VALID_EFFECTS = Arrays.stream(EFFECTS_LIST).<MobEffect>flatMap(Arrays::stream).collect(Collectors.toSet());
	private List<DarkBeaconTileEntity.BeamSegment> beamSegments = Lists.newArrayList();
	private List<DarkBeaconTileEntity.BeamSegment> checkingBeamSections = Lists.newArrayList();
	private int levels = 0;
	private int lastCheckY = -1;
	/** Primary potion effect given by this beacon */
	@Nullable
	private MobEffect primaryEffect;
	/** Secondary potion effect given by this beacon. */
	@Nullable
	private MobEffect secondaryEffect;
	/** The custom name for this beacon. This was unused until 1.14; see https://bugs.mojang.com/browse/MC-124395 */
	@Nullable
	private Component customName;
	private LockCode lockKey = LockCode.NO_LOCK;
	private final ContainerData dataAccess = new ContainerData() {
		public int get(int index) {
			switch(index) {
				case 0:
					return DarkBeaconTileEntity.this.levels;
				case 1:
					return MobEffect.getId(DarkBeaconTileEntity.this.primaryEffect);
				case 2:
					return MobEffect.getId(DarkBeaconTileEntity.this.secondaryEffect);
				default:
					return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
				case 0:
					DarkBeaconTileEntity.this.levels = value;
					break;
				case 1:
					if (!DarkBeaconTileEntity.this.level.isClientSide && !DarkBeaconTileEntity.this.beamSegments.isEmpty()) {
						DarkBeaconTileEntity.this.playSound(SoundEvents.BEACON_POWER_SELECT);
					}

					DarkBeaconTileEntity.this.primaryEffect = DarkBeaconTileEntity.isBeaconEffect(value);
					break;
				case 2:
					DarkBeaconTileEntity.this.secondaryEffect = DarkBeaconTileEntity.isBeaconEffect(value);
			}

		}

		public int getCount() {
			return 3;
		}
	};

	public DarkBeaconTileEntity(BlockPos pos, BlockState state) {
		super(ModTileEntities.SIGN.get(), pos, state);
	}

//	public void tick() {
//		int i = this.pos.getX();
//		int j = this.pos.getY();
//		int k = this.pos.getZ();
//		BlockPos blockpos;
//		if (this.lastCheckY < j) {
//			blockpos = this.pos;
//			this.checkingBeamSections = Lists.newArrayList();
//			this.lastCheckY = blockpos.getY() - 1;
//		} else {
//			blockpos = new BlockPos(i, this.lastCheckY + 1, k);
//		}
//
//		DarkBeaconTileEntity.BeamSegment beacontileentity$beamsegment = this.checkingBeamSections.isEmpty() ? null : this.checkingBeamSections.get(this.checkingBeamSections.size() - 1);
//		int l = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, i, k);
//
//		for(int i1 = 0; i1 < 10 && blockpos.getY() <= l; ++i1) {
//			BlockState blockstate = this.world.getBlockState(blockpos);
//			Block block = blockstate.getBlock();
//			float[] afloat = blockstate.getBeaconColorMultiplier(this.world, blockpos, getPos());
//			if (afloat != null) {
//				if (this.checkingBeamSections.size() <= 1) {
//					beacontileentity$beamsegment = new DarkBeaconTileEntity.BeamSegment(afloat);
//					this.checkingBeamSections.add(beacontileentity$beamsegment);
//				} else if (beacontileentity$beamsegment != null) {
//					if (Arrays.equals(afloat, beacontileentity$beamsegment.colors)) {
//						beacontileentity$beamsegment.incrementHeight();
//					} else {
//						beacontileentity$beamsegment = new DarkBeaconTileEntity.BeamSegment(new float[]{(beacontileentity$beamsegment.colors[0] + afloat[0]) / 2.0F, (beacontileentity$beamsegment.colors[1] + afloat[1]) / 2.0F, (beacontileentity$beamsegment.colors[2] + afloat[2]) / 2.0F});
//						this.checkingBeamSections.add(beacontileentity$beamsegment);
//					}
//				}
//			} else {
//				if (beacontileentity$beamsegment == null || blockstate.getOpacity(this.world, blockpos) >= 15 && block != Blocks.BEDROCK) {
//					this.checkingBeamSections.clear();
//					this.lastCheckY = l;
//					break;
//				}
//
//				beacontileentity$beamsegment.incrementHeight();
//			}
//
//			blockpos = blockpos.up();
//			++this.lastCheckY;
//		}
//
//		int j1 = this.levels;
//		if (this.world.getGameTime() % 80L == 0L) {
//			if (!this.beamSegments.isEmpty()) {
//				this.updateBase(i, j, k);
//			}
//
//			if (this.levels > 0 && !this.beamSegments.isEmpty()) {
//				this.addEffectsToPlayers();
//				this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT);
//			}
//		}
//
//		if (this.lastCheckY >= l) {
//			this.lastCheckY = -1;
//			boolean flag = j1 > 0;
//			this.beamSegments = this.checkingBeamSections;
//			if (!this.world.isRemote) {
//				boolean flag1 = this.levels > 0;
//				if (!flag && flag1) {
//					this.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE);
//
//					for(ServerPlayerEntity serverplayerentity : this.world.getEntitiesWithinAABB(ServerPlayerEntity.class, (new AxisAlignedBB((double)i, (double)j, (double)k, (double)i, (double)(j - 4), (double)k)).grow(10.0D, 5.0D, 10.0D))) {
//					}
//				} else if (flag && !flag1) {
//					this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
//				}
//			}
//		}
//
//	}
//
//	private void updateBase(int p_213927_1_, int p_213927_2_, int p_213927_3_) {
//		this.levels = 0;
//
//		for(int i = 1; i <= 4; this.levels = i++) {
//			int j = p_213927_2_ - i;
//			if (j < 0) {
//				break;
//			}
//
//			boolean flag = true;
//
//			for(int k = p_213927_1_ - i; k <= p_213927_1_ + i && flag; ++k) {
//				for(int l = p_213927_3_ - i; l <= p_213927_3_ + i; ++l) {
//					if (!this.world.getBlockState(new BlockPos(k, j, l)).isBeaconBase(this.world, new BlockPos(k, j, l), getPos())) {
//						flag = false;
//						break;
//					}
//				}
//			}
//
//			if (!flag) {
//				break;
//			}
//		}
//
//	}

	/**
	 * invalidates a tile entity
	 */
	public void setRemoved() {
		this.playSound(SoundEvents.BEACON_DEACTIVATE);
		super.setRemoved();
	}

	private void addEffectsToPlayers() {
		if (!this.level.isClientSide && this.primaryEffect != null) {
			double d0 = (double)(this.levels * 10 + 10);
			int i = 0;
			if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
				i = 1;
			}

			int j = (9 + this.levels * 2) * 20;
			AABB axisalignedbb = (new AABB(this.worldPosition)).inflate(d0).expandTowards(0.0D, (double)this.level.getMaxBuildHeight(), 0.0D);
			List<Player> list = this.level.getEntitiesOfClass(Player.class, axisalignedbb);

			for(Player playerentity : list) {
				playerentity.addEffect(new MobEffectInstance(this.primaryEffect, j, i, true, true));
			}

			if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null) {
				for(Player playerentity1 : list) {
					playerentity1.addEffect(new MobEffectInstance(this.secondaryEffect, j, 0, true, true));
				}
			}

		}
	}

	public void playSound(SoundEvent p_205736_1_) {
		this.level.playSound(null, this.worldPosition, p_205736_1_, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	@OnlyIn(Dist.CLIENT)
	public List<DarkBeaconTileEntity.BeamSegment> getBeamSegments() {
		return (List<DarkBeaconTileEntity.BeamSegment>)(this.levels == 0 ? ImmutableList.of() : this.beamSegments);
	}

	public int getLevels() {
		return this.levels;
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
	 * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
	 */
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(this.worldPosition, 3, this.getUpdateTag());
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link }
	 */
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	@OnlyIn(Dist.CLIENT)
	public double getViewDistance() {
		return 65536.0D;
	}

	@Nullable
	private static MobEffect isBeaconEffect(int p_184279_0_) {
		MobEffect effect = MobEffect.byId(p_184279_0_);
		return VALID_EFFECTS.contains(effect) ? effect : null;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.primaryEffect = isBeaconEffect(compound.getInt("Primary"));
		this.secondaryEffect = isBeaconEffect(compound.getInt("Secondary"));
//		if (compound.contains("CustomName", 8)) {
//			this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
//		}

		this.lockKey = LockCode.fromTag(compound);
	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);
		compound.putInt("Primary", MobEffect.getId(this.primaryEffect));
		compound.putInt("Secondary", MobEffect.getId(this.secondaryEffect));
		compound.putInt("Levels", this.levels);
		if (this.customName != null) {
			compound.putString("CustomName", Component.Serializer.toJson(this.customName));
		}

		this.lockKey.addToTag(compound);
		return compound;
	}

	/**
	 * Sets the custom name for this beacon.
	 */
	public void setCustomName(@Nullable Component aname) {
		this.customName = aname;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new DarkBeaconContainer(i, level, worldPosition, inventory, player);
	}

	public Component getDisplayName() {
		return (this.customName != null ? this.customName : new TranslatableComponent("container.dark_beacon"));
	}

	public static class BeamSegment {
		private final float[] colors;
		private int height;

		public BeamSegment(float[] colorsIn) {
			this.colors = colorsIn;
			this.height = 1;
		}

		protected void incrementHeight() {
			++this.height;
		}

		/**
		 * Returns RGB (0 to 1.0) colors of this beam segment
		 */
		@OnlyIn(Dist.CLIENT)
		public float[] getColors() {
			return this.colors;
		}

		@OnlyIn(Dist.CLIENT)
		public int getHeight() {
			return this.height;
		}
	}
}
