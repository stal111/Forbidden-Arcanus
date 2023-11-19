package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 18.11.2023
 */
public class ResearchTab extends AbstractTab {

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

    public ResearchTab(int width, int height) {
        super(width, height);
    }

    @Override
    public void init() {

    }

    @Override
    public void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        guiGraphics.blit(BACKGROUND, 0, 0, -i * 0.9F, -j * 0.9F, this.getWidth(), this.getHeight(), 512, 512);

        guiGraphics.blit(BACKGROUND_STELLAR_DUST_0, (int) (i * 1.15F), (int) (j * 1.15F), 0, 0, 512, 512, 512, 512);
        guiGraphics.blit(BACKGROUND_STELLAR_DUST_1, (int) (this.getWidth() / 2 + i * 1.15F), (int) (this.getHeight() / 2 + j * 1.15F), 0, 0, 512, 512, 512, 512);

        guiGraphics.blit(BACKGROUND_STARS, 0, 0, -i * 1.35F, -j * 1.35F, this.getWidth(), this.getHeight(), 512, 512);

        RenderSystem.disableBlend();

        guiGraphics.renderItem(new ItemStack(ModItems.SANITY_METER.get()), this.getWidth() / 2 - 8 + i, this.getHeight() / 2 - 8 + j);
        guiGraphics.renderItem(new ItemStack(ModItems.QUANTUM_CATCHER.get()), this.getWidth() / 2 - 40 + i, this.getHeight() / 2 + 80 + j);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        this.scroll(dragX, dragY);

        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll(scrollX * 16.0D, scrollY * 16.0D);

        return false;
    }

    public void scroll(double dragX, double dragY) {
//        this.scrollX = Mth.clamp(this.scrollX + dragX, -(this.maxX - 234), 0.0D);
//
//        this.scrollY = Mth.clamp(this.scrollY + dragY, -(this.maxY - 113), 0.0D);

        this.scrollX = this.scrollX + dragX;

        this.scrollY = this.scrollY + dragY;
    }
}
