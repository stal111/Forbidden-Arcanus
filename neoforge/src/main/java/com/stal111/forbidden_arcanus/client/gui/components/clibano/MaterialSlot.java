package com.stal111.forbidden_arcanus.client.gui.components.clibano;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedSlotState;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Optional;

public class MaterialSlot extends AbstractClibanoSlot {

    private static final Identifier MATERIAL_SLOT_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_slot");
    private static final Identifier MATERIAL_SLOT_SELECTED_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_slot_selected");
    private static final Identifier MATERIAL_SLOT_HIGHLIGHTED_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_slot_highlighted");
    private static final Identifier MATERIAL_FULLNESS_SPRITE = ForbiddenArcanus.identifier("container/clibano/material_fullness");

    private final MoltenMaterial material;

    public MaterialSlot(MoltenMaterial moltenMaterial, int x, int y, Component message, SelectedSlotState selectedSlotState) {
        super(x, y, message, selectedSlotState, new WidgetSprites(MATERIAL_SLOT_SELECTED_SPRITE, MATERIAL_SLOT_SPRITE, MATERIAL_SLOT_HIGHLIGHTED_SPRITE, MATERIAL_SLOT_HIGHLIGHTED_SPRITE));
        this.material = moltenMaterial;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTick) {
        ItemStack result = this.material.type().value().result().create();

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getSprite(), this.getX(), this.getY(), this.width, this.height);
        guiGraphics.fakeItem(result, this.getX() + 4, this.getY() + 4);

        int width = Mth.ceil(this.material.getFullnessPercentage() * 20.0F);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, MATERIAL_FULLNESS_SPRITE, 20, 5, 0, 0, this.getX() + 2, this.getY() + 27, width, 5);

        if (this.isHovered()) {
            Component amount = getAmountComponent(this.material);
            MutableComponent capacity = Component.literal("Capacity: " + this.material.type().value().maxAmount() / 9).withStyle(ChatFormatting.GRAY);

            guiGraphics.setTooltipForNextFrame(Minecraft.getInstance().font, List.of(amount, capacity), Optional.empty(), x, y);
        }
    }

    public static Component getAmountComponent(MoltenMaterial material) {
        int amount = material.amount();
        int ingots = amount / 9;
        int residue = amount % 9;

        MutableComponent component = Component.literal(ingots + " ").append(material.type().value().result().create().getItemName());

        if (residue > 0) {
            component.append(", " + residue + " Residue");
        }

        return component;
    }

    @Override
    protected Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> getSlotContent() {
        return Either.left(this.material.type());
    }

    @Override
    protected boolean isSelected(Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> either) {
        return either.left()
                .map(moltenMaterialType -> moltenMaterialType == this.material.type())
                .orElse(false);
    }
}
