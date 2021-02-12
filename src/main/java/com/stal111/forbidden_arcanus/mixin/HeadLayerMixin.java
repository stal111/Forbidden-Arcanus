package com.stal111.forbidden_arcanus.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.block.tileentity.render.ObsidianSkullTileEntityRenderer;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Head Layer Mixin
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.HeadLayerMixin
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
@Mixin(HeadLayer.class)
public class HeadLayerMixin<T extends LivingEntity, M extends EntityModel<T> & IHasHead> {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/model/ModelRenderer;translateRotate(Lcom/mojang/blaze3d/matrix/MatrixStack;)V", shift = At.Shift.AFTER), method = "render", cancellable = true)
    private void forbiddenArcanus_renderHeads(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        ItemStack stack = entity.getItemStackFromSlot(EquipmentSlotType.HEAD);

        if (stack.getItem() instanceof BlockItem && ModTags.Items.OBSIDIAN_SKULLS.contains(stack.getItem())) {
            matrixStack.scale(1.1875F, -1.1875F, -1.1875F);
            if (entity instanceof VillagerEntity || entity instanceof ZombieVillagerEntity) {
                matrixStack.translate(0.0D, 0.0625D, 0.0D);
            }

            matrixStack.translate(-0.5D, 0.0D, -0.5D);
            ObsidianSkullTileEntityRenderer.render(null, 180.0F, limbSwing, matrixStack, buffer, packedLight);

            matrixStack.pop();
            ci.cancel();
        }
    }
}
