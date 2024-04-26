package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author stal111
 * @since 2023-01-02
 */
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @Inject(at = @At(value = "RETURN"), method = "getModel", cancellable = true)
    public void forbiddenArcanus_getModel(ItemStack stack, Level level, LivingEntity livingEntity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.getItem() instanceof ArmorItem item && item.getType() == ArmorItem.Type.BOOTS && ModifierHelper.hasModifier(stack, ModItemModifiers.MAGNETIZED.get())) {
            ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);

            BakedModel model = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(new ResourceLocation(resourceLocation.getNamespace(), "magnetized_" + resourceLocation.getPath()), "inventory"));

            cir.setReturnValue(model);
        }
    }
}
