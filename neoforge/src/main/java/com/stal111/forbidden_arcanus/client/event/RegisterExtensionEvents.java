package com.stal111.forbidden_arcanus.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.renderer.block.EssenceUtremJarRenderer;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 20.07.2024
 */
@EventBusSubscriber(value = Dist.CLIENT)
public class RegisterExtensionEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(() -> new EssenceUtremJarRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer.get();
            }
        }, ModItems.ESSENCE_UTREM_JAR.get());

        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
                    float useDuration = (float)itemInHand.getUseDuration(player) - ((float)player.getUseItemRemainingTicks() - partialTick + 1.0F);
                    float progress = useDuration / (float)itemInHand.getUseDuration(player);

                    poseStack.mulPose(Axis.XP.rotationDegrees(-50.0F));
                    poseStack.translate(-0.26F, 0.7F, 0.2F);

                    float f = (float)(player.getUseItemRemainingTicks() % 10);
                    float f1 = f - partialTick + 1.0F;
                    float f2 = 1.0F - f1 / 10.0F;

                    float xOffset = 0.03F * Mth.sin(f2 * 2.0F * (float)Math.PI);
                    float zOffset = 0.03F * Mth.cos(f2 * 2.0F * (float)Math.PI);

                    poseStack.translate(xOffset, 0.0F, zOffset);
                }

                return false;
            }
        }, ModItems.MAGIC_WAND.get());
    }
}
