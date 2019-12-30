package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DrawBlockHighlightListener {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.getTarget().getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) event.getTarget();
            World world = Minecraft.getInstance().world;
            BlockState state = world.getBlockState(blockRayTraceResult.getPos());
            if (state.getBlock() == ModBlocks.ARCANE_CRYSTAL_OBELISK.getBlock()) {
                ArcaneCrystalObeliskPart part = state.get(ArcaneCrystalObeliskBlock.PART);
                if (part == ArcaneCrystalObeliskPart.LOWER) {
                    RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().up());
                    RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().up(2));
                } else if (part == ArcaneCrystalObeliskPart.MIDDLE) {
                    RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().up());
                    RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().down());
                } else {
                    RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().down());
                    RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().down(2));
                }
            }
        }
    }
}
