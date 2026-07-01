package com.stal111.forbidden_arcanus.client.gui.components.clibano;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedSlotState;
import com.stal111.forbidden_arcanus.common.network.serverbound.ToggleSlotPayload;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public abstract class AbstractClibanoSlot extends AbstractButton {

    private final SelectedSlotState selectedSlotState;
    private final WidgetSprites sprites;

    public AbstractClibanoSlot(int x, int y, Component message, SelectedSlotState selectedSlotState, WidgetSprites sprites) {
        super(x, y, 24, 36, message);
        this.selectedSlotState = selectedSlotState;
        this.sprites = sprites;
    }

    protected abstract Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> getSlotContent();

    protected abstract boolean isSelected(Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> either);

    public Identifier getSprite() {
        boolean isSelected = this.selectedSlotState.getSelected().map(this::isSelected).orElse(false);

        return this.sprites.get(isSelected, this.isFocused());
    }

    @Override
    public void onPress(InputWithModifiers input) {
        this.selectedSlotState.toggleSlot(getSlotContent());

        ClientPacketDistributor.sendToServer(new ToggleSlotPayload(this.selectedSlotState));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }
}
