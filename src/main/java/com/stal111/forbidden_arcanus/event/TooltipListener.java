package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.EdelwoodFishBucketItem;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import com.stal111.forbidden_arcanus.util.TooltipUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TooltipListener {

    @SubscribeEvent
    public static void onRenderTooltipPostText(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        //TODO

        if (stack.getItem() instanceof ICapacityBucket && !(stack.getItem() instanceof EdelwoodFishBucketItem)) {
            int capacity = ((ICapacityBucket) stack.getItem()).getCapacity();
            int fullness = ICapacityBucket.getFullness(stack);

            if (capacity > 0) {
              //  RenderSystem.pushMatrix();
              //  RenderSystem.color3f(1F, 1F, 1F);
                int y = TooltipUtils.shiftTextByLines(event.getLines(), event.getY() + 12);

                for (int i = 0; i < fullness; i++) {
                    int x = event.getX() + i * 14 - 2;

                    itemRenderer.blitOffset += 500;
                    itemRenderer.renderAndDecorateItem(stack, x, y);
                    itemRenderer.blitOffset -= 500;
                }
                for (int i = 0; i < (capacity - fullness); i++) {
                    int x = event.getX() + i * 14 + (fullness * 14 - 2);

                    itemRenderer.blitOffset += 500;
                    itemRenderer.renderAndDecorateItem(new ItemStack(NewModItems.EDELWOOD_BUCKET.get()), x, y);
                    itemRenderer.blitOffset -= 500;

                }
              //  RenderSystem.popMatrix();
            }
        }
    }
}
