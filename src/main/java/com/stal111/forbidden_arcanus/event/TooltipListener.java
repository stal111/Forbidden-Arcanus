package com.stal111.forbidden_arcanus.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.item.EdelwoodFishBucketItem;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import com.stal111.forbidden_arcanus.util.TooltipUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TooltipListener {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderTooltipPostText(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();
        if (stack.getItem() instanceof ICapacityBucket && !(stack.getItem() instanceof EdelwoodFishBucketItem)) {
            int capacity = ((ICapacityBucket) stack.getItem()).getCapacity();
            int fullness = ICapacityBucket.getFullness(stack);

            if (capacity > 0) {
                RenderSystem.pushMatrix();
                RenderSystem.color3f(1F, 1F, 1F);
                int y = TooltipUtils.shiftTextByLines(event.getLines(), event.getY() + 11);

                for (int i = 0; i < fullness; i++) {
                    int x = event.getX() + i * 14 - 2;

                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(stack, x, y);
                }
                for (int i = 0; i < (capacity - fullness); i++) {
                    int x = event.getX() + i * 14 + (fullness * 14 - 2);

                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(ModItems.EDELWOOD_BUCKET.get()), x, y);
                }
                RenderSystem.popMatrix();
            }
        }
    }
}
