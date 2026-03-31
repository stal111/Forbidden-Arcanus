package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.UnbakedElementsHelper;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record WandItemModel(Identifier base, BakingContext bakingContext, Matrix4fc transformation) implements ItemModel {

    private static final ModelDebugName DEBUG_NAME = () -> "WandModel";

    private static final Map<CacheKey, ItemModel> CACHE = new HashMap<>();

    @Override
    public void update(ItemStackRenderState output, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        var pommelMaterial = stack.get(ModDataComponents.POMMEL_MATERIAL);
        var transitionMaterial = stack.get(ModDataComponents.TRANSITION_MATERIAL);

        var key = new CacheKey(
                this.base,
                pommelMaterial != null ? pommelMaterial.value() : null,
                transitionMaterial != null ? transitionMaterial.value() : null
        );

        ItemModel model = CACHE.computeIfAbsent(key, cacheKey -> {
            var models = new ArrayList<ItemModel>();

            ModelBaker baker = this.bakingContext.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.base);
            TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            QuadCollection quads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY);

            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);

            models.add(new CuboidItemModelWrapper(List.of(), quads, properties, this.transformation));

            if (cacheKey.pommel() != null) {
                models.add(this.createModel(baker, cacheKey.pommel().texture(), properties));
            }
            if (cacheKey.transition() != null) {
                models.add(this.createModel(baker, cacheKey.transition().texture(), properties));
            }
            return new CompositeModel(models);
        });

        model.update(output, stack, resolver, displayContext, level, owner, seed);
    }

    private ItemModel createModel(ModelBaker baker, Identifier texture, ModelRenderProperties properties) {
        Material.Baked templateSprite = baker.materials().get(new Material(texture), DEBUG_NAME);
        var quads = UnbakedElementsHelper.bakeItemMaskQuads(baker, 0, templateSprite, templateSprite, BlockModelRotation.IDENTITY);

        return new CuboidItemModelWrapper(List.of(), quads, properties, this.transformation);
    }

    public record Unbaked(Identifier base) implements ItemModel.Unbaked {

        public static final MapCodec<WandItemModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(Unbaked::base)
        ).apply(instance, WandItemModel.Unbaked::new));

        @Override
        public MapCodec<? extends ItemModel.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            return new WandItemModel(this.base, context, transformation);
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.base);
        }
    }

    private record CacheKey(Identifier base, WandMaterial pommel, WandMaterial transition) {}
}
