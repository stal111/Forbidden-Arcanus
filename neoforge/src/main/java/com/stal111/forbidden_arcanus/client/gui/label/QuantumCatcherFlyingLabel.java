package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;

/**
 * @author stal111
 * @since 12.05.2024
 */
public class QuantumCatcherFlyingLabel implements EntityFlyingLabel {

    @Override
    public void render(GuiGraphics guiGraphics, ItemStack stack, DeltaTracker deltaTracker, int centerX, int centerY, EntityHitResult result) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = result.getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!stack.has(ModDataComponents.STORED_ENTITY) && stack.getItem() instanceof QuantumCatcherItem item && item.isValidEntity(livingEntity)) {
            EssenceHelper.getEssenceProvider(minecraft.player).ifPresent(provider -> {
                EssenceValue cost = QuantumCatcherItem.calculateAurealCost(livingEntity);

                Component component = cost.asComponent(provider.getAmount(EssenceType.AUREAL) < cost.amount() ? ChatFormatting.RED : ChatFormatting.WHITE);
                int width = minecraft.font.width(component.getVisualOrderText()) + 3;

                guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 1, 0x44000000);
                guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 3, 0x44000000);

                guiGraphics.drawString(minecraft.font, component, centerX - width / 2, centerY - 20, -1);
            });
        }
    }
}
