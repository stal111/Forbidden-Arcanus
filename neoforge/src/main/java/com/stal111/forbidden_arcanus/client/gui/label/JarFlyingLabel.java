package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class JarFlyingLabel implements BlockFlyingLabel {

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, ItemStack stack, DeltaTracker deltaTracker, int centerX, int centerY, BlockHitResult result) {
        BlockPos pos = result.getBlockPos();
        Level level = Minecraft.getInstance().level;

        if (level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
            EssenceStorage storage = blockEntity.getEssenceStorage();
            Component component = storage.asComponent(ChatFormatting.WHITE);
            int width = Minecraft.getInstance().font.width(component.getVisualOrderText());

            guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 1, 0x44000000);
            guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 3, 0x44000000);

            guiGraphics.text(Minecraft.getInstance().font, component, centerX - width / 2, centerY - 20, -1);
        }
    }
}
