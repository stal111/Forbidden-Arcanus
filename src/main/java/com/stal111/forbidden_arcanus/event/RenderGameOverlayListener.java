package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderGameOverlayListener {

    public static int flightTimeLeft = 0;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        if (flightTimeLeft != 0) {
            FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Main.MOD_ID, "textures/gui/orb_of_temporary_flight_time.png"));
            AbstractGui.func_238464_a_(event.getMatrixStack(), 1, 1, 0, 0, 0, 57, 25, 25, 57);

            Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(ModItems.ORB_OF_TEMPORARY_FLIGHT.get()), 6, (int) 6.5F);

            TextFormatting color = flightTimeLeft / 20 <= 20 ? TextFormatting.RED : TextFormatting.WHITE;
            int i = flightTimeLeft < 12000 ? 28 : (int) 26.5F;

            fontRenderer.func_238421_b_(event.getMatrixStack(), StringUtils.ticksToElapsedTime(flightTimeLeft), i, 10, color.getColor());
        }
    }
}
