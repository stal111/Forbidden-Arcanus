package com.stal111.forbidden_arcanus.gui.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.gui.GuiManager;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;

import java.util.List;

public abstract class GuiElement {

    private int posX;
    private int posY;
    private final int sizeX;
    private final int sizeY;

    private GuiManager manager;

    private final Minecraft minecraft = Minecraft.getInstance();

    public GuiElement(int posX, int posY, int sizeX, int sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void init() {
    }

    public abstract String getName();

    public List<GuiElement> getChildElements() {
        return manager.getElements(getName());
    }

    public void addChildElement(GuiElement element) {
        manager.addGuiObject(getName(), element);
    }

    public abstract void render(MatrixStack matrixStack, int x, int y);

    public void renderHoverEffect(MatrixStack matrixStack, int x, int y) {
    }

    public boolean isMouseOver(int x, int y) {
        return x >= getPosX() && y >= getPosY() && x < getEndX() && y < getEndY();
    }

    public boolean onClicked(double x, double y) {
        return false;
    }

    public void setGuiManager(GuiManager manager) {
        this.manager = manager;
    }

    public void setPos(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getEndX() {
        return posX + sizeX;
    }

    public int getEndY() {
        return posY + sizeY;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public Screen getCurrentScreen() {
        return getMinecraft().currentScreen;
    }

    public void bindTexture(ResourceLocation resourceLocation) {
        getMinecraft().getTextureManager().bindTexture(resourceLocation);
    }

    public void blit(MatrixStack matrixStack, int blitOffset, int startX, int startY, int sizeX, int sizeY, int textureSizeX, int textureSizeY) {
        blit(matrixStack, getPosX(), getPosY(), blitOffset, startX, startY, sizeX, sizeY, textureSizeX, textureSizeY);
    }

    public void blit(MatrixStack matrixStack, int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, int textureSizeX, int textureSizeY) {
        AbstractGui.blit(matrixStack, posX, posY, blitOffset, startX, startY, sizeX, sizeY, textureSizeX, textureSizeY);
    }

    public void fill(int p_fill_1_, int p_fill_2_, int p_fill_3_, int p_fill_4_, int red, int green, int blue, int alpha) {
        int lvt_6_2_;
        if (p_fill_1_ < p_fill_3_) {
            lvt_6_2_ = p_fill_1_;
            p_fill_1_ = p_fill_3_;
            p_fill_3_ = lvt_6_2_;
        }

        if (p_fill_2_ < p_fill_4_) {
            lvt_6_2_ = p_fill_2_;
            p_fill_2_ = p_fill_4_;
            p_fill_4_ = lvt_6_2_;
        }

        BufferBuilder lvt_10_1_ = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION_COLOR);
        lvt_10_1_.pos(TransformationMatrix.identity().getMatrix(), (float)p_fill_1_, (float)p_fill_4_, 300.0F).color(red, green, blue, alpha).endVertex();
        lvt_10_1_.pos(TransformationMatrix.identity().getMatrix(), (float)p_fill_3_, (float)p_fill_4_, 300.0F).color(red, green, blue, alpha).endVertex();
        lvt_10_1_.pos(TransformationMatrix.identity().getMatrix(), (float)p_fill_3_, (float)p_fill_2_, 300.0F).color(red, green, blue, alpha).endVertex();
        lvt_10_1_.pos(TransformationMatrix.identity().getMatrix(), (float)p_fill_1_, (float)p_fill_2_, 300.0F).color(red, green, blue, alpha).endVertex();
        lvt_10_1_.finishDrawing();
        WorldVertexBufferUploader.draw(lvt_10_1_);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public void renderFancyTooltip(MatrixStack matrixStack, List<? extends IReorderingProcessor> tooltip, int x, int y) {
        int i = 0;

        for(IReorderingProcessor iReorderingProcessor : tooltip) {
            int j = getMinecraft().fontRenderer.func_243245_a(iReorderingProcessor);
            if (j > i) {
                i = j;
            }
        }

        int l1 = x + 12;
        int i2 = y - 12;
        int k = 8;
        if (tooltip.size() > 1) {
            k += 2 + (tooltip.size() - 1) * 10;
        }

        if (l1 + i > ForbiddenmiconScreen.INSTANCE.width) {
            l1 -= 28 + i;
        }

        if (i2 + k + 6 > ForbiddenmiconScreen.INSTANCE.height) {
            i2 = ForbiddenmiconScreen.INSTANCE.height - k - 6;
        }

        getMinecraft().getItemRenderer().zLevel = 300.0F;

        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        blit(matrixStack, l1 - 4, i2 - 4, 300, 153, 236, 10, 7, 256, 512);
        blit(matrixStack, l1 - 4, i2 + k - 2, 300, 153, 243, 10, 8, 256, 512);

        blit(matrixStack, l1 + i - 6, i2 - 4, 300, 207, 236, 10, 7, 256, 512);
        blit(matrixStack, l1 + i - 6, i2 + k - 2, 300, 207, 243, 10, 8, 256, 512);

        fill(l1 + 6, i2 - 4, l1 + i - 6, i2 - 3, 104, 103, 101, 255);
        fill(l1 + 6, i2 + k + 4, l1 + i - 6, i2 + k + 5, 67, 64, 59, 255);

        fill(l1 + 6, i2 - 3, l1 + i - 6, i2 - 2, 210, 203, 185, 255);
        fill(l1 + 6, i2 + k + 3, l1 + i - 6, i2 + k + 4, 210, 203, 185, 255);

        fill(l1 - 3, i2 + 3, l1 - 4, i2 + k - 1, 104, 103, 101, 255);
        fill(l1 + i + 3, i2 + 3, l1 + i + 4, i2 + k - 2, 104, 103, 101, 255);

        fill(l1 - 2, i2 + 3, l1 - 3, i2 + k - 1, 210, 203, 185, 255);
        fill(l1 + i + 2, i2 + 3, l1 + i + 3, i2 + k - 2, 210, 203, 185, 255);

        fill(l1 - 2, i2 - 2, l1 + i + 2, i2 + k + 3, 33, 33, 33, 255);

        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        matrixstack.translate(0.0D, 0.0D, (double)getMinecraft().getItemRenderer().zLevel);
        Matrix4f matrix4f = matrixstack.getLast().getMatrix();

        for(int k1 = 0; k1 < tooltip.size(); ++k1) {
            IReorderingProcessor iReorderingProcessor = tooltip.get(k1);
            if (iReorderingProcessor != null) {
                getMinecraft().fontRenderer.func_238416_a_(iReorderingProcessor, (float) l1, (float) i2, -1, true, matrix4f, irendertypebuffer$impl, false, 0, 15728880);
            }

            if (k1 == 0) {
                i2 += 2;
            }

            i2 += 10;
        }

        irendertypebuffer$impl.finish();
        getMinecraft().getItemRenderer().zLevel = 0.0F;
        RenderSystem.enableDepthTest();
        RenderSystem.enableRescaleNormal();
    }
}