package com.stal111.forbidden_arcanus.block.gui;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.block.container.DarkBeaconContainer;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketUpdateBeacon;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkBeaconGui extends GuiContainer {

	private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation(
			"textures/gui/container/beacon.png");
	private IInventory tileBeacon;
	private DarkBeaconGui.ConfirmButton beaconConfirmButton;
	private boolean buttonsNotDrawn;

	private final java.util.List<String> keys = Lists.newArrayList();

	public DarkBeaconGui(IInventory tileEntitySqueezer, DarkBeaconContainer containerSqueezer) {
		super(containerSqueezer);
		this.xSize = 230;
		this.ySize = 219;
		this.tileBeacon = tileEntitySqueezer;
	}

	@Override
	protected void initGui() {
		super.initGui();
		this.keys.clear();
		this.beaconConfirmButton = new DarkBeaconGui.ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107);
		this.addButton(this.beaconConfirmButton);
		this.addButton(new DarkBeaconGui.CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
		this.buttonsNotDrawn = true;
		this.beaconConfirmButton.enabled = false;
		int j = 100;
		for (int i = 0; i < j; i++) {
			this.keys.add("" + i);
		}
	}

	@Override
	public void tick() {
		super.tick();
		int i = this.tileBeacon.getField(0);
		Potion potion = Potion.getPotionById(this.tileBeacon.getField(1));
		Potion potion1 = Potion.getPotionById(this.tileBeacon.getField(2));
		if (this.buttonsNotDrawn && i >= 0) {
			this.buttonsNotDrawn = false;
			int j = 100;

			for (int k = 0; k <= 2; ++k) {
				int l = TileEntityBeacon.EFFECTS_LIST[k].length;
				int i1 = l * 22 + (l - 1) * 2;

				for (int j1 = 0; j1 < l; ++j1) {
					Potion potion2 = TileEntityBeacon.EFFECTS_LIST[k][j1];
					DarkBeaconGui.PowerButton guibeacon$powerbutton = new DarkBeaconGui.PowerButton(j++,
							this.guiLeft + 76 + j1 * 24 - i1 / 2, this.guiTop + 22 + k * 25, potion2, k);
					this.addButton(guibeacon$powerbutton);
					if (k >= i) {
						guibeacon$powerbutton.enabled = false;
					} else if (potion2 == potion) {
						guibeacon$powerbutton.setSelected(true);
					}
				}
			}
			int l1 = TileEntityBeacon.EFFECTS_LIST[3].length + 1;
			int i2 = l1 * 22 + (l1 - 1) * 2;

			for (int j2 = 0; j2 < l1 - 1; ++j2) {
				Potion potion3 = TileEntityBeacon.EFFECTS_LIST[3][j2];
				DarkBeaconGui.PowerButton guibeacon$powerbutton2 = new DarkBeaconGui.PowerButton(j++,
						this.guiLeft + 167 + j2 * 24 - i2 / 2, this.guiTop + 47, potion3, 3);
				this.addButton(guibeacon$powerbutton2);
				if (3 >= i) {
					guibeacon$powerbutton2.enabled = false;
				} else if (potion3 == potion1) {
					guibeacon$powerbutton2.setSelected(true);
				}
			}

			if (potion != null) {
				DarkBeaconGui.PowerButton guibeacon$powerbutton1 = new DarkBeaconGui.PowerButton(j++,
						this.guiLeft + 167 + (l1 - 1) * 24 - i2 / 2, this.guiTop + 47, potion, 3);
				this.addButton(guibeacon$powerbutton1);
				if (3 >= i) {
					guibeacon$powerbutton1.enabled = false;
				} else if (potion == potion1) {
					guibeacon$powerbutton1.setSelected(true);
				}
			}
		}

		// this.beaconConfirmButton.enabled =
		// !this.tileBeacon.getStackInSlot(0).isEmpty() && potion != null;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		this.itemRender.zLevel = 100.0F;
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(ModItems.dragon_scale), i + 42, j + 109);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(ModItems.obsidian_ingot), i + 42 + 22, j + 109);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(ModItems.arcane_gold_ingot), i + 42 + 44, j + 109);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(ModItems.arcane_crystal), i + 42 + 66, j + 109);
		this.itemRender.zLevel = 0.0F;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();
		String s = this.tileBeacon.getName().getString();
		this.fontRenderer.drawString(s, (float) (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6.0F,
				4210752);
		for (GuiButton guibutton : this.buttons) {
			if (guibutton.isMouseOver()) {
				guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
				break;
			}
		}

		RenderHelper.enableGUIStandardItemLighting();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@OnlyIn(Dist.CLIENT)
	abstract static class Button extends GuiButton {
		private final ResourceLocation iconTexture;
		private final int iconX;
		private final int iconY;
		private boolean selected;

		protected Button(int buttonId, int x, int y, ResourceLocation iconTextureIn, int iconXIn, int iconYIn) {
			super(buttonId, x, y, 22, 22, "");
			this.iconTexture = iconTextureIn;
			this.iconX = iconXIn;
			this.iconY = iconYIn;
		}

		public void render(int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				Minecraft.getInstance().getTextureManager().bindTexture(DarkBeaconGui.BEACON_GUI_TEXTURES);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
						&& mouseY < this.y + this.height;
				int j = 0;
				if (!this.enabled) {
					j += this.width * 2;
				} else if (this.selected) {
					j += this.width * 1;
				} else if (this.hovered) {
					j += this.width * 3;
				}

				this.drawTexturedModalRect(this.x, this.y, j, 219, this.width, this.height);
				if (!DarkBeaconGui.BEACON_GUI_TEXTURES.equals(this.iconTexture)) {
					Minecraft.getInstance().getTextureManager().bindTexture(this.iconTexture);
				}

				this.drawTexturedModalRect(this.x + 2, this.y + 2, this.iconX, this.iconY, 18, 18);
			}
		}

		public boolean isSelected() {
			return this.selected;
		}

		public void setSelected(boolean selectedIn) {
			this.selected = selectedIn;
		}
	}

	@OnlyIn(Dist.CLIENT)
	class CancelButton extends DarkBeaconGui.Button {
		public CancelButton(int buttonId, int x, int y) {
			super(buttonId, x, y, DarkBeaconGui.BEACON_GUI_TEXTURES, 112, 220);
		}

		/**
		 * Called when the left mouse button is pressed over this button. This
		 * method is specific to GuiButton.
		 */
		public void onClick(double mouseX, double mouseY) {
			DarkBeaconGui.this.mc.player.connection
					.sendPacket(new CPacketCloseWindow(DarkBeaconGui.this.mc.player.openContainer.windowId));
			DarkBeaconGui.this.mc.displayGuiScreen((GuiScreen) null);
		}

		public void drawButtonForegroundLayer(int mouseX, int mouseY) {
			DarkBeaconGui.this.drawHoveringText(I18n.format("gui.cancel"), mouseX, mouseY);
		}
	}

	@OnlyIn(Dist.CLIENT)
	class ConfirmButton extends DarkBeaconGui.Button {
		public ConfirmButton(int buttonId, int x, int y) {
			super(buttonId, x, y, DarkBeaconGui.BEACON_GUI_TEXTURES, 90, 220);
		}

		/**
		 * Called when the left mouse button is pressed over this button. This
		 * method is specific to GuiButton.
		 */
		public void onClick(double mouseX, double mouseY) {
			DarkBeaconGui.this.mc.getConnection().sendPacket(new CPacketUpdateBeacon(
					DarkBeaconGui.this.tileBeacon.getField(1), DarkBeaconGui.this.tileBeacon.getField(2)));
			DarkBeaconGui.this.mc.player.connection
					.sendPacket(new CPacketCloseWindow(DarkBeaconGui.this.mc.player.openContainer.windowId));
			DarkBeaconGui.this.mc.displayGuiScreen((GuiScreen) null);
		}

		public void drawButtonForegroundLayer(int mouseX, int mouseY) {
			DarkBeaconGui.this.drawHoveringText(I18n.format("gui.done"), mouseX, mouseY);
		}
	}

	@OnlyIn(Dist.CLIENT)
	class PowerButton extends DarkBeaconGui.Button {
		private final Potion effect;
		private final int tier;

		public PowerButton(int buttonId, int x, int y, Potion effectIn, int tierIn) {
			super(buttonId, x, y, GuiContainer.INVENTORY_BACKGROUND, effectIn.getStatusIconIndex() % 12 * 18,
					198 + effectIn.getStatusIconIndex() / 12 * 18);
			this.effect = effectIn;
			this.tier = tierIn;
		}

		/**
		 * Called when the left mouse button is pressed over this button. This
		 * method is specific to GuiButton.
		 */
		public void onClick(double mouseX, double mouseY) {
			if (!this.isSelected()) {
				int i = Potion.getIdFromPotion(this.effect);
				if (this.tier < 3) {
					DarkBeaconGui.this.tileBeacon.setField(1, i);
				} else {
					DarkBeaconGui.this.tileBeacon.setField(2, i);
				}

				DarkBeaconGui.this.buttons.clear();
				DarkBeaconGui.this.children.clear();
				DarkBeaconGui.this.initGui();
				DarkBeaconGui.this.tick();
			}
		}

		public void drawButtonForegroundLayer(int mouseX, int mouseY) {
			String s = I18n.format(this.effect.getName());
			if (this.tier >= 3 && this.effect != MobEffects.REGENERATION) {
				s = s + " II";
			}

			DarkBeaconGui.this.drawHoveringText(s, mouseX, mouseY);
		}
	}
}
