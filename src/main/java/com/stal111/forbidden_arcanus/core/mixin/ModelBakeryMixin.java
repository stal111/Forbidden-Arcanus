package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
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

    @Shadow protected abstract void cacheAndQueueDependencies(ResourceLocation pLocation, UnbakedModel pModel);

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> unbakedCache;

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;

    @Shadow public abstract UnbakedModel getModel(ResourceLocation p_119342_);

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void forbiddenArcanus_init(BlockColors colors, ProfilerFiller filler, Map<ResourceLocation, BlockModel> map, Map<ResourceLocation, List<ModelBakery.LoadedJson>> map2, CallbackInfo ci) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.FEET) {
                ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);

                if (resourceLocation == null) {
                    continue;
                }

                UnbakedModel model = this.getModel(new ModelResourceLocation(resourceLocation, "inventory"));

                if (model instanceof BlockModel blockModel) {
                    BlockModel magnetizedModel = BlockModel.fromString("{\"parent\": \"minecraft:item/generated\"}");

                    magnetizedModel.textureMap.put("layer0", Either.left(new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(ForbiddenArcanus.MOD_ID, "item/armor/magnetized_boots_layer"))));

                    for (int i = 0; i < blockModel.textureMap.size(); i++) {
                        magnetizedModel.textureMap.put("layer" + (i + 1), blockModel.textureMap.get("layer" + i));
                    }

                    resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), "magnetized_" + resourceLocation.getPath());
                    magnetizedModel.name = resourceLocation.toString();

                    ModelResourceLocation modelLoc = new ModelResourceLocation(resourceLocation, "inventory");

                    this.cacheAndQueueDependencies(modelLoc, magnetizedModel);
                    this.unbakedCache.put(modelLoc, magnetizedModel);
                    this.topLevelModels.put(modelLoc, magnetizedModel);

                    magnetizedModel.resolveParents(this::getModel);
                }
            }
        }
    }
}
