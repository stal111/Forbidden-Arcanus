package com.stal111.forbidden_arcanus.client.gui.label;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
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

    @Override
    public void render(GuiGraphics guiGraphics, ItemStack stack, float partialTick, int centerX, int centerY, BlockHitResult result) {
        PoseStack poseStack = guiGraphics.pose();
        BlockPos pos = result.getBlockPos();
        Level level = Minecraft.getInstance().level;

        if (level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
            EssenceType type = level.getBlockState(pos).getValue(ModBlockStateProperties.ESSENCE_TYPE);

            poseStack.pushPose();

            Component component = Component.translatable(ESSENCE_FORMAT, type.getComponent(), blockEntity.getAmount(), blockEntity.getLimit());
            int width = Minecraft.getInstance().font.width(component.getVisualOrderText());

            guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 2, 0x44000000);
            guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 4, 0x44000000);

            guiGraphics.drawString(Minecraft.getInstance().font, component.getVisualOrderText(), centerX - width / 2, centerY - 20, -1);

            poseStack.popPose();
        }
    }
}
