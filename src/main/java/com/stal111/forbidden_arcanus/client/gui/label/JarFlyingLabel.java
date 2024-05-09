package com.stal111.forbidden_arcanus.client.gui.label;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class JarFlyingLabel extends BlockFlyingLabel {
    
    private static final String ESSENCE_FORMAT = "tooltip.forbidden_arcanus.essence.storage_format";

    public JarFlyingLabel(Predicate<BlockState> predicate) {
        super(predicate);
    }

    @Override
    public void render(GuiGraphics guiGraphics, float partialTick, int centerX, int centerY) {
        PoseStack poseStack = guiGraphics.pose();
        EssenceType type = this.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE);
        BlockPos pos = this.getHitResult().getBlockPos();

        if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
            poseStack.pushPose();

            Component component = Component.translatable(ESSENCE_FORMAT, type.getComponent(), blockEntity.getAmount(), blockEntity.getLimit());
            int width = Minecraft.getInstance().font.width(component.getVisualOrderText());

            guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 2, centerX + width / 2 + 2, centerY - 10 + 2, 0x44000000);
            guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 4, centerX + width / 2 + 4, centerY - 10 + 4, 0x44000000);

            guiGraphics.drawString(Minecraft.getInstance().font, component.getVisualOrderText(), centerX - width / 2, centerY - 20, -1);

            poseStack.popPose();
        }
    }
}
