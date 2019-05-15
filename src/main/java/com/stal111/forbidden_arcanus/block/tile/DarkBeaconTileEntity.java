package com.stal111.forbidden_arcanus.block.tile;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.block.container.DarkBeaconContainer;
import com.stal111.forbidden_arcanus.block.gui.DarkBeaconGui;
import com.stal111.forbidden_arcanus.util.IGuiTile;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkBeaconTileEntity extends TileEntity implements ITickable, IInteractionObject, IGuiTile, ISidedInventory {

	public static final Potion[][] EFFECTS_LIST = new Potion[][] { { MobEffects.SLOW_FALLING, MobEffects.LUCK },
			{ MobEffects.NIGHT_VISION, MobEffects.INVISIBILITY }, { MobEffects.FIRE_RESISTANCE },
			{ MobEffects.SATURATION } };
	private static final Set<Potion> VALID_EFFECTS = Arrays.stream(EFFECTS_LIST).<Potion>flatMap(Arrays::stream)
			.collect(Collectors.toSet());

	private final List<TileEntityBeacon.BeamSegment> beamSegments = Lists.newArrayList();

	@OnlyIn(Dist.CLIENT)
	private long beamRenderCounter;

	@OnlyIn(Dist.CLIENT)
	private float beamRenderScale;

	private boolean isComplete;
	private boolean lastTickComplete;
	private int levels = -1;

	@Nullable
	private Potion primaryEffect;

	@Nullable
	private Potion secondaryEffect;

	private ItemStack payment = ItemStack.EMPTY;

	private ITextComponent customName;

	public DarkBeaconTileEntity() {
		super(ModBlocks.tile_dark_beacon);
	}

	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return !isRemoved() && entityPlayer.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public void tick() {
		if (this.world.getGameTime() % 80L == 0L) {
			if (this.isComplete) {
				this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT);
			}
		}
	}

	public void playSound(SoundEvent p_205736_1_) {
		this.world.playSound((EntityPlayer) null, this.pos, p_205736_1_, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	private void addEffectsToPlayers() {
		// if (this.isComplete && this.levels > 0 && !this.world.isRemote &&
		// this.primaryEffect != null) {
		// double d0 = (double)(this.levels * 10 + 10);
		// int i = 0;
		// if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
		// i = 1;
		// }
		//
		// int j = (9 + this.levels * 2) * 20;
		// int k = this.pos.getX();
		// int l = this.pos.getY();
		// int i1 = this.pos.getZ();
		// AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)k,
		// (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1
		// + 1))).grow(d0).expand(0.0D, (double)this.world.getHeight(), 0.0D);
		// List<EntityPlayer> list =
		// this.world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
		//
		// for(EntityPlayer entityplayer : list) {
		// entityplayer.addPotionEffect(new PotionEffect(this.primaryEffect, j,
		// i, true, true));
		// }
		//
		// if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect &&
		// this.secondaryEffect != null) {
		// for(EntityPlayer entityplayer1 : list) {
		// entityplayer1.addPotionEffect(new PotionEffect(this.secondaryEffect,
		// j, 0, true, true));
		// }
		// }
		// }

	}

	@OnlyIn(Dist.CLIENT)
	public float shouldBeamRender() {
		if (!this.isComplete) {
			return 0.0F;
		} else {
			int i = (int) (this.world.getGameTime() - this.beamRenderCounter);
			this.beamRenderCounter = this.world.getGameTime();
			if (i > 1) {
				this.beamRenderScale -= (float) i / 40.0F;
				if (this.beamRenderScale < 0.0F) {
					this.beamRenderScale = 0.0F;
				}
			}

			this.beamRenderScale += 0.025F;
			if (this.beamRenderScale > 1.0F) {
				this.beamRenderScale = 1.0F;
			}

			return this.beamRenderScale;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public ITextComponent getCustomName() {
		return null;
	}

	@Override
	public ITextComponent getName() {
		return new TextComponentTranslation("tile." + Main.MODID + ".arcane_beacon");
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer arg1) {
		return new DarkBeaconContainer(playerInventory, this);
	}

	public void wasPlaced(EntityLivingBase entityliving, ItemStack stack) {
	}

	@Override
	public String getGuiID() {
		return "forbidden_arcanus:dark_beacon";
	}

	@Override
	public GuiContainer createGui(EntityPlayer player) {
		return new DarkBeaconGui(this, new DarkBeaconContainer(player.inventory, this));
	}

	@Override
	public void clear() {
		this.payment = ItemStack.EMPTY;
	}

	@Override
	public void closeInventory(EntityPlayer arg0) {
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index == 0 && !this.payment.isEmpty()) {
			if (count >= this.payment.getCount()) {
				ItemStack itemstack = this.payment;
				this.payment = ItemStack.EMPTY;
				return itemstack;
			} else {
				return this.payment.split(count);
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.levels;
		case 1:
			return this.levels;
		case 2:
			return this.levels;
		default:
			return 0;
		}
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? this.payment : ItemStack.EMPTY;
	}

	@Override
	public boolean isEmpty() {
		return this.payment.isEmpty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.isBeaconPayment();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
					(double) this.pos.getZ() + 0.5D) > 64.0D);
		}
	}

	@Override
	public void openInventory(EntityPlayer arg0) {
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index == 0) {
			ItemStack itemstack = this.payment;
			this.payment = ItemStack.EMPTY;
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.levels = value;
			break;
		case 1:
			// this.primaryEffect = isBeaconEffect(value);
			break;
		case 2:
			// this.secondaryEffect = isBeaconEffect(value);
		}

		if (!this.world.isRemote && id == 1 && this.isComplete) {
			this.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT);
		}

	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			this.payment = stack;
		}

	}

	@Override
	public boolean canExtractItem(int arg0, ItemStack arg1, EnumFacing arg2) {
		return false;
	}

	@Override
	public boolean canInsertItem(int arg0, ItemStack arg1, EnumFacing arg2) {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing arg0) {
		return new int[0];
	}

}
