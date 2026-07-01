package com.stal111.forbidden_arcanus.client.gui.components.clibano;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedSlotState;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class AlloySlot extends AbstractClibanoSlot {

    private static final Identifier ALLOY_SLOT_SPRITE = ForbiddenArcanus.identifier("container/clibano/alloy_slot");
    private static final Identifier ALLOY_SLOT_SELECTED_SPRITE = ForbiddenArcanus.identifier("container/clibano/alloy_slot_selected");
    private static final Identifier ALLOY_SLOT_HIGHLIGHTED_SPRITE = ForbiddenArcanus.identifier("container/clibano/alloy_slot_highlighted");

    private final RecipeHolder<ClibanoAlloyingRecipe> recipeHolder;

    public AlloySlot(RecipeHolder<ClibanoAlloyingRecipe> recipeHolder, int x, int y, Component message, SelectedSlotState selectedSlotState) {
        super(x, y, message, selectedSlotState, new WidgetSprites(ALLOY_SLOT_SELECTED_SPRITE, ALLOY_SLOT_SPRITE, ALLOY_SLOT_HIGHLIGHTED_SPRITE, ALLOY_SLOT_HIGHLIGHTED_SPRITE));
        this.recipeHolder = recipeHolder;
    }

    @Override
    protected Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> getSlotContent() {
        return Either.right(this.recipeHolder.id());
    }

    @Override
    protected boolean isSelected(Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> either) {
        return either.right()
                .map(resourceKey -> resourceKey == this.recipeHolder.id())
                .orElse(false);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        ItemStack result = this.recipeHolder.value().result().create();

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getSprite(), this.getX(), this.getY(), this.width, this.height);
        graphics.fakeItem(result, this.getX() + 4, this.getY() + 4);
    }
}
