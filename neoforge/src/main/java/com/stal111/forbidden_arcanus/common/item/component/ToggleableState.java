package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record ToggleableState(boolean active) implements TooltipProvider {

    public static final Codec<ToggleableState> CODEC = Codec.BOOL.xmap(ToggleableState::new, ToggleableState::active);
    public static final StreamCodec<ByteBuf, ToggleableState> STREAM_CODEC = ByteBufCodecs.BOOL.map(ToggleableState::new, ToggleableState::active);

    public static final ToggleableState ACTIVE = new ToggleableState(true);

    private static final Component TOGGLE = Component.translatable(Util.makeDescriptionId("item", ForbiddenArcanus.identifier("toggle_state"))).withStyle(ChatFormatting.GRAY);
    private static final Component TOGGLE_ACTIVATED = Component.translatable(Util.makeDescriptionId("item", ForbiddenArcanus.identifier("toggle_state.activated"))).withStyle(ChatFormatting.GREEN).append(" ").append(TOGGLE);
    private static final Component TOGGLE_DEACTIVATED = Component.translatable(Util.makeDescriptionId("item", ForbiddenArcanus.identifier("toggle_state.deactivated"))).withStyle(ChatFormatting.RED).append(" ").append(TOGGLE);

    public ToggleableState toggle() {
        return new ToggleableState(!this.active);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        tooltipAdder.accept(this.active ? TOGGLE_ACTIVATED : TOGGLE_DEACTIVATED);
    }
}
