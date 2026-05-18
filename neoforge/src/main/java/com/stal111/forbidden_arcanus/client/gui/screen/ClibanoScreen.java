package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.EssenceBar;
import com.stal111.forbidden_arcanus.client.gui.components.EssenceBarType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.inventory.ClibanoMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Inventory;

import java.util.EnumMap;
import java.util.Map;

public class ClibanoScreen extends AbstractContainerScreen<ClibanoMenu> {

    private static final Identifier CONTAINER_TEXTURE = ForbiddenArcanus.identifier("textures/gui/container/clibano.png");
    private static final Map<ClibanoFireType, Identifier> LIT_PROGRESS_SPRITES = Util.make(new EnumMap<>(ClibanoFireType.class), map -> {
        map.put(ClibanoFireType.FIRE, ForbiddenArcanus.identifier("container/clibano/lit_progress"));
        map.put(ClibanoFireType.SOUL_FIRE, ForbiddenArcanus.identifier("container/clibano/soul_lit_progress"));
        map.put(ClibanoFireType.ENCHANTED_FIRE, ForbiddenArcanus.identifier("container/clibano/enchanted_lit_progress"));
    });
    private static final Identifier ECTOPLASM_PROGRESS_SPRITE = ForbiddenArcanus.identifier("container/clibano/ectoplasm_progress");
    private static final Identifier SMELT_PROGRESS_SPRITE = ForbiddenArcanus.identifier("container/clibano/smelt_progress");
    private static final Identifier SMELT_PROGRESS_OVERLAY_SPRITE = ForbiddenArcanus.identifier("container/clibano/smelt_progress_overlay");
    private static final Identifier RESULT_PROGRESS_SPRITE = ForbiddenArcanus.identifier("container/clibano/result_progress");

    private final MaterialListComponent materialList;

    public ClibanoScreen(ClibanoMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 221);
        this.titleLabelY -= 2;
        this.inventoryLabelY += 2;

        this.materialList = new MaterialListComponent(menu.getStoredMaterials(), menu.getSelectedMaterialState());
    }

    @Override
    protected void init() {
        super.init();

        int totalWidth = this.imageWidth + 2 + this.materialList.getWidth();
        int totalLeft = (this.width - totalWidth) / 2;

        this.leftPos = totalLeft + 2 + this.materialList.getWidth();

        this.materialList.init(this.height, this.minecraft, totalLeft);

        this.addRenderableWidget(this.materialList);
        this.addRenderableWidget(new EssenceBar(this.getLeftPos() + 74, this.getTopPos() + 83, EssenceBarType.CLIBANO_ECTOPLASM, this.menu::getEssenceStorage));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTicks) {
        super.extractRenderState(guiGraphics, x, y, partialTicks);

        this.extractTooltip(guiGraphics, x, y);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);

        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        // Lit Progress
        float litProgress = this.menu.getLitProgress();
        if (litProgress > 0.0F) {
            int height = Mth.ceil(litProgress * 17.0F);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITES.get(this.menu.getFireType()), 18, 18, 0, 17 - height, this.leftPos + 79, this.topPos + 58 + 17 - height, 18, height);
        }

        float ectoplasmProgress = this.menu.getEctoplasmProgress();
        if (ectoplasmProgress > 0.0F) {
            int height = Mth.ceil(ectoplasmProgress * 15.0F);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ECTOPLASM_PROGRESS_SPRITE, 18, 18, 0, 16 - height, this.leftPos + 79, this.topPos + 58 + 16 - height, 18, height);
        }

        int[] totalSmeltTimes = this.menu.getCookingTotalTimes();
        int[] smeltTimes = this.menu.getCookingTimes();
        int[] smeltProgress = new int[totalSmeltTimes.length];

        for (int i = 0; i < smeltProgress.length; i++) {
            smeltProgress[i] = Math.round((float) smeltTimes[i] / (float) totalSmeltTimes[i] * 34.0F);
        }

        // Smelt Progress 1
        if (smeltProgress[0] > 0) {
            int height = smeltProgress[0];
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SMELT_PROGRESS_SPRITE, 8, 34, 0, 34 - (height + 1), this.leftPos + 49, this.topPos + 19, 8, height + 1);
        }

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SMELT_PROGRESS_OVERLAY_SPRITE, 8, 3, 0, 0, this.leftPos + 49, this.topPos + 19, 8, 3);

        // Smelt Progress 2
        if (smeltProgress[1] > 0) {
            int height = smeltProgress[1];
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SMELT_PROGRESS_SPRITE, 8, 34, 0, 34 - (height + 1), this.leftPos + 119, this.topPos + 19, 8, height + 1);
        }

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SMELT_PROGRESS_OVERLAY_SPRITE, 8, 3, 0, 0, this.leftPos + 119, this.topPos + 19, 8, 3);


        // Result Progress
        float resultProgress = this.menu.getResultProgress();

        int width = Math.round(resultProgress * 22.0F);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, RESULT_PROGRESS_SPRITE, 22, 16, 0, 0, this.leftPos + 95, this.topPos + 39, width, 16);

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean isDoubleClick) {
        if (this.materialList.mouseClicked(mouseButtonEvent, isDoubleClick)) {
            this.setFocused(this.materialList);
            return true;
        }

        return super.mouseClicked(mouseButtonEvent, isDoubleClick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.materialList.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
            this.setFocused(this.materialList);
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
