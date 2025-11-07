package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class TooltipEvents {

    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component ENHANCER_COMPONENT = Component.translatable("item.forbidden_arcanus.enhancer").withStyle(ChatFormatting.GOLD);

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        boolean advanced = event.getFlags().isAdvanced();
        TooltipDisplay tooltipDisplay = stack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);

        stack.addToTooltip(ModDataComponents.ESSENCE_VALUE.get(), event.getContext(), tooltipDisplay, component -> this.expandTooltip(advanced, tooltip, component), event.getFlags());
        stack.addToTooltip(ModDataComponents.ESSENCE_STORAGE.get(), event.getContext(), tooltipDisplay, component -> this.expandTooltip(advanced, tooltip, component), event.getFlags());

        EnhancerHelper.getEnhancer(stack).ifPresent(definition -> {
            this.expandTooltip(advanced, tooltip, ENHANCER_COMPONENT);
            this.expandTooltip(advanced, tooltip, CommonComponents.EMPTY);

            for (Map.Entry<EnhancerTarget, Component> test : definition.description().entrySet()) {
                this.expandTooltip(advanced, tooltip, test.getKey().getTitle());
                this.expandTooltip(advanced, tooltip, Component.literal(" ").append(test.getValue()).withStyle(DESCRIPTION_FORMAT));
            }
        });

        HephaestusForgeLevel level = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).get(ModBlockStateProperties.FORGE_TIER);

        if (level != null) {
            this.expandTooltip(advanced, tooltip, Component.translatable(HephaestusForgeBlock.TIER_ID, level.getAsInt()).withStyle(ChatFormatting.GRAY));
        }
    }

    private void expandTooltip(boolean advanced, List<Component> tooltip, Component addition) {
        if (advanced) {
            tooltip.add(tooltip.size() - 2, addition);
        } else {
            tooltip.add(addition);
        }
    }
}
