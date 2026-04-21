package com.stal111.forbidden_arcanus.client.event;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.client.tooltip.CapacityBucketTooltip;
import com.stal111.forbidden_arcanus.common.inventory.wand.WandDeskMenu;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityBucket;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

/**
 * Tooltip Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.event.TooltipEvents
 *
 * @author stal111
 * @since 2021-11-25
 */
@EventBusSubscriber(value = Dist.CLIENT)
public class TooltipEvents {

    @SubscribeEvent
    public static void onRenderTooltipColor(RenderTooltipEvent.Texture event) {
        ItemStack stack = event.getItemStack();

        ModifierHelper.getModifier(stack).ifPresent(modifier -> {
            event.setTexture(modifier.displaySettings().texture());
        });
    }

    @SubscribeEvent
    public static void onGatherComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        var elements = event.getTooltipElements();

        Holder<WandMaterial> materialHolder = stack.get(ModDataComponents.PROVIDES_WAND_MATERIAL);

        if (stack.getItem() instanceof CapacityBucket capacityBucket && capacityBucket.getCapacity(stack) != 0) {
            elements.add(1, Either.right(new CapacityBucketTooltip(stack, capacityBucket.getFullness(stack), capacityBucket.getCapacity(stack))));
        } else if (Minecraft.getInstance().player != null && materialHolder != null && Minecraft.getInstance().player.containerMenu instanceof WandDeskMenu) {
            elements.add(Either.right(materialHolder.value().stats()));
        }
    }
}
