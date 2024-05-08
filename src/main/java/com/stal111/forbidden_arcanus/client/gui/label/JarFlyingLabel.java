package com.stal111.forbidden_arcanus.client.gui.label;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class JarFlyingLabel extends BlockFlyingLabel {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png");

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

            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            float f = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);

            Minecraft.getInstance().font.drawInBatch(Component.translatable(ESSENCE_FORMAT, type.getComponent(), blockEntity.getAmount(), blockEntity.getLimit()), 0, 0, -1, true, poseStack.last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, (int)(f * 255.0F) << 24, 1);

          //  guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(ESSENCE_FORMAT, type.getComponent(), blockEntity.getAmount(), blockEntity.getLimit()), centerX, centerY - 20, -1);

            poseStack.popPose();
        }
    }
}
