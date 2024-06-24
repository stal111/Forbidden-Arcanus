package com.stal111.forbidden_arcanus.client.gui.label;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class JarFlyingLabel extends BlockFlyingLabel {

    private static final String ESSENCE_FORMAT = "tooltip.forbidden_arcanus.essence.storage_format";
    private static final int ICON_SIZE = 12;

    @Override
    public void render(GuiGraphics guiGraphics, ItemStack stack, DeltaTracker deltaTracker, int centerX, int centerY, BlockHitResult result) {
        PoseStack poseStack = guiGraphics.pose();
        BlockPos pos = result.getBlockPos();
        Level level = Minecraft.getInstance().level;

        if (level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
            EssenceType type = level.getBlockState(pos).getValue(ModBlockStateProperties.ESSENCE_TYPE);

            poseStack.pushPose();

            Component component = Component.translatable(ESSENCE_FORMAT, blockEntity.getAmount(), blockEntity.getLimit());
            int width = Minecraft.getInstance().font.width(component.getVisualOrderText()) + ICON_SIZE + 3;

            guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 2, 0x44000000);
            guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 4, 0x44000000);

            guiGraphics.blitSprite(type.getSpriteLocation(), centerX - width / 2, centerY - 20 - 2, ICON_SIZE, ICON_SIZE);
            guiGraphics.drawString(Minecraft.getInstance().font, component.getVisualOrderText(), centerX - width / 2 + ICON_SIZE + 2, centerY - 20, -1);

            poseStack.popPose();
        }
    }
}
