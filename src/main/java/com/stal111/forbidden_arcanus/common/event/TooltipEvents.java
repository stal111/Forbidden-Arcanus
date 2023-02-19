package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class TooltipEvents {

    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component HEPHAESTUS_FORGE_EFFECT_TITLE = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(ForbiddenArcanus.MOD_ID, "enhancer.hephaestus_forge_effect"))).withStyle(TITLE_FORMAT);
    private static final Component CLIBANO_EFFECT = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(ForbiddenArcanus.MOD_ID, "enhancer.clibano_effect"))).withStyle(TITLE_FORMAT);


    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (player == null) {
            return;
        }

        Level level = event.getEntity().getLevel();

        Registry<EnhancerDefinition> registry = level.registryAccess().registryOrThrow(FARegistries.ENHANCER_DEFINITION);

        registry.entrySet().forEach(entry -> {
            if (stack.is(entry.getValue().item())) {
                event.getToolTip().add(event.getToolTip().size() - 1, CommonComponents.EMPTY);
                event.getToolTip().add(event.getToolTip().size() - 1, HEPHAESTUS_FORGE_EFFECT_TITLE);
                event.getToolTip().add(event.getToolTip().size() - 1, Component.literal(" ").append(entry.getValue().description()).withStyle(DESCRIPTION_FORMAT));
                event.getToolTip().add(event.getToolTip().size() - 1, CLIBANO_EFFECT);
                event.getToolTip().add(event.getToolTip().size() - 1, Component.literal(" ").append(Component.literal("Allows you to create new alloys.")).withStyle(DESCRIPTION_FORMAT));
            }
        });
    }
}
