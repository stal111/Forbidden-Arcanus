package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullShieldItem;
import com.stal111.forbidden_arcanus.core.config.RenderingConfig;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Set;

/**
 * @author stal111
 * @since 2022-02-14
 */
public class ObsidianSkullOverlay extends GuiComponent implements IGuiOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/obsidian_skull_overlay.png");

    private static final Set<Item> ITEMS = ImmutableSet.of(ModItems.OBSIDIAN_SKULL.get(), ModItems.OBSIDIAN_SKULL_SHIELD.get());

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null || !player.getInventory().hasAnyOf(ITEMS)) {
            return;
        }

        ItemStack stack = ObsidianSkullItem.getSkullWithLowestCounter(player.getInventory());

        if (stack.isEmpty()) {
            stack = ObsidianSkullShieldItem.getSkullWithLowestCounter(player.getInventory());
        }

        int value = ObsidianSkullItem.getCounterValue(stack);

        ChatFormatting color = value / 20 >= 25 ? ChatFormatting.RED : ChatFormatting.WHITE;
        int posX = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_X_POSITION.get();
        int posY = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_Y_POSITION.get();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);

        GuiComponent.blit(poseStack, posX, posY, 0, 0, 0, 57, 25, 64, 32);

        minecraft.getItemRenderer().renderAndDecorateItem(poseStack, stack, posX + 5, (int) (posY + 5.5F));

        minecraft.font.draw(poseStack, StringUtil.formatTickDuration(ObsidianSkullItem.OBSIDIAN_SKULL_PROTECTION_TIME - value), posX + 27.0F, posY + 9, color.getColor());

    }
}
