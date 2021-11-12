package com.stal111.forbidden_arcanus.event;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DrawBlockHighlightListener {

//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void onBlockHighlight(DrawHighlightEvent.HighlightBlock event) {
//        if (event.getTarget().getType() == HitResult.Type.BLOCK) {
//            BlockHitResult blockRayTraceResult = event.getTarget();
//            Level world = Minecraft.getInstance().level;
//            BlockState state = world.getBlockState(blockRayTraceResult.getBlockPos());
//            if (state.getBlock() == NewModBlocks.ARCANE_CRYSTAL_OBELISK.get()) {
//                ArcaneCrystalObeliskPart part = state.getValue(ArcaneCrystalObeliskBlock.PART);
//                if (part == ArcaneCrystalObeliskPart.LOWER) {
//                    //RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().up());
//                    //RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().up(2));
//                } else if (part == ArcaneCrystalObeliskPart.MIDDLE) {
//                  //  RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().up());
//                    //RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().down());
//                } else {
//                  //  RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().down());
//                    //RenderUtils.drawSelectionBox(event.getInfo(), blockRayTraceResult.getPos().down(2));
//                }
//            }
//        }
//    }
}
