package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        Player player = event.getEntity();

        if (player == null) {
            return;
        }

        EnhancerCache.get(stack.getItem()).ifPresent(definition -> {
            List<Component> tooltip = event.getToolTip();
            boolean advanced = event.getFlags().isAdvanced();

            this.expandTooltip(advanced, tooltip, ENHANCER_COMPONENT);
            this.expandTooltip(advanced, tooltip, CommonComponents.EMPTY);

            for (Map.Entry<EnhancerTarget, Component> test : definition.description().entrySet()) {
                this.expandTooltip(advanced, tooltip, test.getKey().getTitle());
                this.expandTooltip(advanced, tooltip, Component.literal(" ").append(test.getValue()).withStyle(DESCRIPTION_FORMAT));
            }
        });
    }

    private void expandTooltip(boolean advanced, List<Component> tooltip, Component addition) {
        if (advanced) {
            tooltip.add(tooltip.size() - 1, addition);
        } else {
            tooltip.add(addition);
        }
    }
}
