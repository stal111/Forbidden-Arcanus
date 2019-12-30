package com.stal111.forbidden_arcanus.block.tileentity.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.container.DarkBeaconContainer;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CUpdateBeaconPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DarkBeaconScreen extends ContainerScreen<DarkBeaconContainer> {

	private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation(Main.MOD_ID, "textures/gui/container/dark_beacon.png");
	private DarkBeaconScreen.ConfirmButton beaconConfirmButton;
	private boolean buttonsNotDrawn;
	private Effect field_214105_n;
	private Effect field_214106_o;

	private int field_214139_n;

	public DarkBeaconScreen(DarkBeaconContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
		super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
		this.xSize = 230;
		this.ySize = 219;
		p_i51105_1_.addListener(new IContainerListener() {
			/**
			 * update the crafting window inventory with the items in the list
			 */
			public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
			}

			/**
			 * Sends the contents of an inventory slot to the client-side Container. This
			 * doesn't have to match the actual contents of that slot.
			 */
			public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
			}

			/**
			 * Sends two ints to the client-side Container. Used for furnace burning time,
			 * smelting progress, brewing progress, and enchanting level. Normally the first
			 * int identifies which variable to update, and the second contains the new
			 * value. Both are truncated to shorts in non-local SMP.
			 */
			public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
				DarkBeaconScreen.this.field_214105_n = p_i51105_1_.func_216967_f();
				DarkBeaconScreen.this.field_214106_o = p_i51105_1_.func_216968_g();
				DarkBeaconScreen.this.buttonsNotDrawn = true;
			}
		});
	}

	@Override
	protected void init() {
		super.init();
		this.beaconConfirmButton = this.addButton(new DarkBeaconScreen.ConfirmButton(this.guiLeft + 164, this.guiTop + 107));
		this.addButton(new DarkBeaconScreen.CancelButton(this.guiLeft + 190, this.guiTop + 107));
		this.buttonsNotDrawn = true;
		this.beaconConfirmButton.active = false;
	}

	@Override
	public void tick() {
		super.tick();
		int i = this.container.func_216969_e();
		if (this.buttonsNotDrawn && i >= 0) {
			this.buttonsNotDrawn = false;

			for (int j = 0; j <= 2; ++j) {
				int k = DarkBeaconTileEntity.EFFECTS_LIST[j].length;
				int l = k * 22 + (k - 1) * 2;

				for (int i1 = 0; i1 < k; ++i1) {
					Effect effect = DarkBeaconTileEntity.EFFECTS_LIST[j][i1];
					DarkBeaconScreen.PowerButton beaconscreen$powerbutton = new DarkBeaconScreen.PowerButton(
							this.guiLeft + 76 + i1 * 24 - l / 2, this.guiTop + 22 + j * 25, effect, true);
					this.addButton(beaconscreen$powerbutton);
					if (j >= i) {
						beaconscreen$powerbutton.active = false;
					} else if (effect == this.field_214105_n) {
						beaconscreen$powerbutton.setSelected(true);
					}
				}
			}

			int k1 = DarkBeaconTileEntity.EFFECTS_LIST[2].length + 1;
			int l1 = k1 * 22 + (k1 - 1) * 2;

			for (int i2 = 0; i2 < k1 - 1; ++i2) {
				Effect effect1 = DarkBeaconTileEntity.EFFECTS_LIST[2][i2];
				DarkBeaconScreen.PowerButton beaconscreen$powerbutton2 = new DarkBeaconScreen.PowerButton(this.guiLeft + 167 + i2 * 24 - l1 / 2, this.guiTop + 47, effect1, false);
				this.addButton(beaconscreen$powerbutton2);
				if (3 >= i) {
					beaconscreen$powerbutton2.active = false;
				} else if (effect1 == this.field_214106_o) {
					beaconscreen$powerbutton2.setSelected(true);
				}
			}

			if (this.field_214105_n != null) {
				DarkBeaconScreen.PowerButton beaconscreen$powerbutton1 = new DarkBeaconScreen.PowerButton(this.guiLeft + 167 + (k1 - 1) * 24 - l1 / 2, this.guiTop + 47, this.field_214105_n, false);
				this.addButton(beaconscreen$powerbutton1);
				if (3 >= i) {
					beaconscreen$powerbutton1.active = false;
				} else if (this.field_214105_n == this.field_214106_o) {
					beaconscreen$powerbutton1.setSelected(true);
				}
			}
		}

		this.beaconConfirmButton.active = this.container.func_216970_h() && this.field_214105_n != null;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();

		for (Widget widget : this.buttons) {
			if (widget.isHovered()) {
				widget.renderToolTip(mouseX - this.guiLeft, mouseY - this.guiTop);
				break;
			}
		}

		RenderHelper.enableGUIStandardItemLighting();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(i, j, 0, 0, this.xSize, this.ySize);
		this.itemRenderer.zLevel = 100.0F;
		this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(ModItems.ARCANE_CRYSTAL.getItem()), i + 38 + 44, j + 110);
		this.itemRenderer.zLevel = 0.0F;
	}

	@Override
	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground();
		super.render(p_render_1_, p_render_2_, p_render_3_);
		this.renderHoveredToolTip(p_render_1_, p_render_2_);
	}

	private boolean func_214135_a(int i) {
		return i > 7;
	}

	@Override
	public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
		int i = 10;
		if (func_214135_a(i)) {
			int j = i - 7;
			this.field_214139_n = (int) ((double) this.field_214139_n - p_mouseScrolled_5_);
			this.field_214139_n = MathHelper.clamp(this.field_214139_n, 0, j);
		}
		return super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, p_mouseScrolled_5_);
	}

	@OnlyIn(Dist.CLIENT)
	abstract static class Button extends AbstractButton {
		private boolean selected;

		protected Button(int p_i50826_1_, int p_i50826_2_) {
			super(p_i50826_1_, p_i50826_2_, 22, 22, "");
		}

		public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
			Minecraft.getInstance().getTextureManager().bindTexture(DarkBeaconScreen.BEACON_GUI_TEXTURES);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			int j = 0;
			if (!this.active) {
				j += this.width * 2;
			} else if (this.selected) {
				j += this.width * 1;
			} else if (this.isHovered()) {
				j += this.width * 3;
			}

			this.blit(this.x, this.y, j, 219, this.width, this.height);
			this.func_212945_a();
		}

		protected abstract void func_212945_a();

		public boolean isSelected() {
			return this.selected;
		}

		public void setSelected(boolean selectedIn) {
			this.selected = selectedIn;
		}
	}

	@OnlyIn(Dist.CLIENT)
	class CancelButton extends DarkBeaconScreen.SpriteButton {
		public CancelButton(int p_i50829_2_, int p_i50829_3_) {
			super(p_i50829_2_, p_i50829_3_, 112, 220);
		}

		public void onPress() {
			DarkBeaconScreen.this.minecraft.player.connection
					.sendPacket(new CCloseWindowPacket(DarkBeaconScreen.this.minecraft.player.openContainer.windowId));
			DarkBeaconScreen.this.minecraft.displayGuiScreen((Screen) null);
		}

		public void renderToolTip(int p_renderToolTip_1_, int p_renderToolTip_2_) {
			DarkBeaconScreen.this.renderTooltip(I18n.format("gui.cancel"), p_renderToolTip_1_, p_renderToolTip_2_);
		}
	}

	@OnlyIn(Dist.CLIENT)
	class ConfirmButton extends DarkBeaconScreen.SpriteButton {
		public ConfirmButton(int p_i50828_2_, int p_i50828_3_) {
			super(p_i50828_2_, p_i50828_3_, 90, 220);
		}

		public void onPress() {
			DarkBeaconScreen.this.minecraft.getConnection()
					.sendPacket(new CUpdateBeaconPacket(Effect.getId(DarkBeaconScreen.this.field_214105_n),
							Effect.getId(DarkBeaconScreen.this.field_214106_o)));
			DarkBeaconScreen.this.minecraft.player.connection
					.sendPacket(new CCloseWindowPacket(DarkBeaconScreen.this.minecraft.player.openContainer.windowId));
			DarkBeaconScreen.this.minecraft.displayGuiScreen((Screen) null);
		}

		public void renderToolTip(int p_renderToolTip_1_, int p_renderToolTip_2_) {
			DarkBeaconScreen.this.renderTooltip(I18n.format("gui.done"), p_renderToolTip_1_, p_renderToolTip_2_);
		}
	}

	@OnlyIn(Dist.CLIENT)
	class PowerButton extends DarkBeaconScreen.Button {
		private final Effect effect;
		private final TextureAtlasSprite field_212946_c;
		private final boolean field_212947_d;

		public PowerButton(int p_i50827_2_, int p_i50827_3_, Effect p_i50827_4_, boolean p_i50827_5_) {
			super(p_i50827_2_, p_i50827_3_);
			this.effect = p_i50827_4_;
			this.field_212946_c = Minecraft.getInstance().getPotionSpriteUploader().getSprite(p_i50827_4_);
			this.field_212947_d = p_i50827_5_;
		}

		public void onPress() {
			if (!this.isSelected()) {
				if (this.field_212947_d) {
					DarkBeaconScreen.this.field_214105_n = this.effect;
				} else {
					DarkBeaconScreen.this.field_214106_o = this.effect;
				}

				DarkBeaconScreen.this.buttons.clear();
				DarkBeaconScreen.this.children.clear();
				DarkBeaconScreen.this.init();
				DarkBeaconScreen.this.tick();
			}
		}

		public void renderToolTip(int p_renderToolTip_1_, int p_renderToolTip_2_) {
			String s = I18n.format(this.effect.getName());
			if (!this.field_212947_d && this.effect != Effects.REGENERATION) {
				s = s + " II";
			}

			DarkBeaconScreen.this.renderTooltip(s, p_renderToolTip_1_, p_renderToolTip_2_);
		}

		protected void func_212945_a() {
			Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_EFFECTS_TEXTURE);
			blit(this.x + 2, this.y + 2, this.blitOffset, 18, 18, this.field_212946_c);
		}
	}

	@OnlyIn(Dist.CLIENT)
	abstract static class SpriteButton extends DarkBeaconScreen.Button {
		private final int field_212948_a;
		private final int field_212949_b;

		protected SpriteButton(int p_i50825_1_, int p_i50825_2_, int p_i50825_3_, int p_i50825_4_) {
			super(p_i50825_1_, p_i50825_2_);
			this.field_212948_a = p_i50825_3_;
			this.field_212949_b = p_i50825_4_;
		}

		protected void func_212945_a() {
			this.blit(this.x + 2, this.y + 2, this.field_212948_a, this.field_212949_b, 18, 18);
		}
	}

}
