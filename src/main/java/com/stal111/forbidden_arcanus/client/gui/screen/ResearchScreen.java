package com.stal111.forbidden_arcanus.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.research.ResearchDeskMenu;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 09.11.2023
 */
public class ResearchScreen extends AbstractContainerScreen<ResearchDeskMenu> {

    private static final ResourceLocation FRAME_TOP_LEFT_CORNER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/top_left_corner.png");
    private static final ResourceLocation FRAME_TOP_RIGHT_CORNER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/top_right_corner.png");
    private static final ResourceLocation FRAME_BOTTOM_LEFT_CORNER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/bottom_left_corner.png");
    private static final ResourceLocation FRAME_BOTTOM_RIGHT_CORNER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/bottom_right_corner.png");
    private static final ResourceLocation FRAME_TOP_CENTER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/top_center.png");
    private static final ResourceLocation FRAME_TOP = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/top.png");
    private static final ResourceLocation FRAME_BOTTOM = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/bottom.png");
    private static final ResourceLocation FRAME_LEFT = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/left.png");
    private static final ResourceLocation FRAME_RIGHT = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/frame/right.png");

    private static final ResourceLocation BACKGROUND = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/background.png");
    private static final ResourceLocation BACKGROUND_STARS = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/background_stars.png");
    private static final ResourceLocation BACKGROUND_STELLAR_DUST_0 = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/background_stellar_dust_0.png");
    private static final ResourceLocation BACKGROUND_STELLAR_DUST_1 = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/background_stellar_dust_1.png");

    private double scrollX;
    private double scrollY;

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public ResearchScreen(ResearchDeskMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        guiGraphics.blit(BACKGROUND, 0, 0, -i * 0.9F, -j * 0.9F, this.width, this.height, 512, 512);

        guiGraphics.blit(BACKGROUND_STELLAR_DUST_0, (int) (i * 1.15F), (int) (j * 1.15F), 0, 0, 512, 512, 512, 512);
        guiGraphics.blit(BACKGROUND_STELLAR_DUST_1, (int) (this.width / 2 + i * 1.15F), (int) (this.height / 2 + j * 1.15F), 0, 0, 512, 512, 512, 512);

        guiGraphics.blit(BACKGROUND_STARS, 0, 0, -i * 1.35F, -j * 1.35F, this.width, this.height, 512, 512);

        int x = 0;

        while (x < this.width) {
            int width = Math.min(42, this.width - x);

            guiGraphics.blit(FRAME_TOP, x, 0, 0, 0, width, 22, 42, 22);
            guiGraphics.blit(FRAME_BOTTOM, x, this.height - 22, 0, 0, width, 22, 42, 22);

            x += width;
        }

        int y = 0;

        while (y < this.height) {
            int height = Math.min(28, this.height - y);

            guiGraphics.blit(FRAME_LEFT, 0, y, 0, 0, 22, height, 22, 28);
            guiGraphics.blit(FRAME_RIGHT, this.width - 22, y, 0, 0, 22, height, 22, 28);

            y += height;
        }

        guiGraphics.blit(FRAME_TOP_LEFT_CORNER, 0, 0, 0, 0, 22, 22, 22, 22);
        guiGraphics.blit(FRAME_TOP_RIGHT_CORNER, this.width - 22, 0, 0, 0, 22, 22, 22, 22);
        guiGraphics.blit(FRAME_BOTTOM_LEFT_CORNER, 0, this.height - 22, 0, 0, 22, 22, 22, 22);
        guiGraphics.blit(FRAME_BOTTOM_RIGHT_CORNER, this.width - 22, this.height - 22, 0, 0, 22, 22, 22, 22);

        guiGraphics.blit(FRAME_TOP_CENTER, this.width / 2 - 25, 0, 0, 0, 50, 22, 50, 22);

        RenderSystem.disableBlend();

        guiGraphics.renderItem(new ItemStack(ModItems.SANITY_METER.get()), this.width / 2 - 8 + i, this.height / 2 - 8 + j);
        guiGraphics.renderItem(new ItemStack(ModItems.QUANTUM_CATCHER.get()), this.width / 2 - 40 + i, this.height / 2 + 80 + j);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        this.scroll(dragX, dragY);

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll(scrollX * 16.0D, scrollY * 16.0D);

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    public void scroll(double dragX, double dragY) {
//        this.scrollX = Mth.clamp(this.scrollX + dragX, -(this.maxX - 234), 0.0D);
//
//        this.scrollY = Mth.clamp(this.scrollY + dragY, -(this.maxY - 113), 0.0D);

        this.scrollX = this.scrollX + dragX;

        this.scrollY = this.scrollY + dragY;
    }
}
