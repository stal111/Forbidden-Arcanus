package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Custom Head Layer Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.mixin.CustomHeadLayerMixin
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-02-11
 */
@Mixin(CustomHeadLayer.class)
public class CustomHeadLayerMixin<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> {

    private EntityModelSet modelSet;
    private Pair<SkullModel, SkullModel> models;

    @Inject(at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/client/renderer/entity/RenderLayerParent;Lnet/minecraft/client/model/geom/EntityModelSet;FFF)V")
    private void forbiddenArcanus_init(RenderLayerParent<T, M> renderLayerParent, EntityModelSet modelSet, float scaleX, float scaleY, float scaleZ, CallbackInfo ci) {
        this.modelSet = modelSet;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/CustomHeadLayer;translateToHead(Lcom/mojang/blaze3d/vertex/PoseStack;Z)V", shift = At.Shift.BEFORE), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", cancellable = true)
    private void forbiddenArcanus_renderHeads(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);

        if (this.models == null) {
            this.models = ObsidianSkullRenderer.createModels(modelSet);
        }

        if (stack.getItem() instanceof BlockItem blockItem && stack.is(ModTags.Items.OBSIDIAN_SKULLS)) {
            poseStack.scale(1.1875F, -1.1875F, -1.1875F);

            if (entity instanceof Villager || entity instanceof ZombieVillager) {
                poseStack.translate(0.0D, 0.0625D, 0.0D);
            }

            poseStack.translate(-0.5D, 0.0D, -0.5D);
            ObsidianSkullRenderer.render(null, 180.0F, poseStack, bufferSource, packedLight, this.models, blockItem.getBlock());

            poseStack.popPose();
            ci.cancel();
        }
    }
}
