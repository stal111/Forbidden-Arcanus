package com.stal111.forbidden_arcanus.client.gui.components.clibano;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedMaterialState;
import com.stal111.forbidden_arcanus.common.network.serverbound.ToggleMaterialPayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class MaterialSlot extends AbstractButton {

    private static final Identifier MATERIAL_SLOT_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_slot");
    private static final Identifier MATERIAL_SLOT_ENABLED_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_slot_enabled");
    private static final Identifier MATERIAL_SLOT_HIGHLIGHTED_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_slot_highlighted");
    private static final Identifier MATERIAL_FULLNESS_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_fullness");

    private final MoltenMaterial material;
    private final SelectedMaterialState selectedMaterialState;

    public MaterialSlot(MoltenMaterial moltenMaterial, int x, int y, Component message, SelectedMaterialState selectedMaterialState) {
        super(x, y, 25, 32, message);
        this.material = moltenMaterial;
        this.selectedMaterialState = selectedMaterialState;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        this.selectedMaterialState.toggleType(this.material.type());

        ClientPacketDistributor.sendToServer(new ToggleMaterialPayload(this.material.type()));
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTick) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getSprite(), this.getX(), this.getY(), this.width, this.height);

        guiGraphics.fakeItem(this.material.type().value().display().create(), this.getX() + 4, this.getY() + 4);

        int width = Mth.ceil((this.material.amount() / 64.0F) * 22.0F);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, MATERIAL_FULLNESS_SPRITE, 22, 6, 0, 0, this.getX() + 1, this.getY() + 25, width, 6);

        if (this.isHovered()) {
            guiGraphics.setTooltipForNextFrame(Component.literal("Amount: " + this.material.amount() + " / 64"), x, y);
        }
    }

    private Identifier getSprite() {
        if (this.selectedMaterialState.getSelected() == this.material.type()) {
            return MATERIAL_SLOT_ENABLED_SPRITE;
        }
        return this.isHovered() ? MATERIAL_SLOT_HIGHLIGHTED_SPRITE : MATERIAL_SLOT_SPRITE;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
