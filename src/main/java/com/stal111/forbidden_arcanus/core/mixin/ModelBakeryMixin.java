package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 2023-01-02
 */
@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow abstract UnbakedModel getModel(ResourceLocation p_119342_);

    @Shadow protected abstract void registerModelAndLoadDependencies(ModelResourceLocation p_352435_, UnbakedModel p_352250_);

    @Shadow @Final private Map<ModelResourceLocation, UnbakedModel> topLevelModels;

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> unbakedCache;

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void forbiddenArcanus_init(BlockColors colors, ProfilerFiller filler, Map<ResourceLocation, BlockModel> map, Map<ResourceLocation, List<BlockStateModelLoader.LoadedJson>> map2, CallbackInfo ci) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof ArmorItem armorItem && armorItem.getType() == ArmorItem.Type.BOOTS) {
                ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);

                UnbakedModel model = this.getModel(resourceLocation);

                if (model instanceof BlockModel blockModel) {
                    BlockModel magnetizedModel = BlockModel.fromString("{\"parent\": \"minecraft:item/generated\"}");

                    magnetizedModel.textureMap.put("layer0", Either.left(new Material(InventoryMenu.BLOCK_ATLAS, ForbiddenArcanus.location("item/armor/magnetized_boots_layer"))));

                    for (int i = 0; i < blockModel.textureMap.size(); i++) {
                        magnetizedModel.textureMap.put("layer" + (i + 1), blockModel.textureMap.get("layer" + i));
                    }

                    resourceLocation = ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "magnetized_" + resourceLocation.getPath());
                    magnetizedModel.name = resourceLocation.toString();

                    ModelResourceLocation modelResourceLocation = ModelResourceLocation.inventory(resourceLocation);

                    this.unbakedCache.put(resourceLocation, magnetizedModel);
                    this.topLevelModels.put(modelResourceLocation, magnetizedModel);

                    magnetizedModel.resolveParents(this::getModel);
                }
            }
        }
    }
}
