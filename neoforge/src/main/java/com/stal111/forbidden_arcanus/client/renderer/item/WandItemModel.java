package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.model.UnbakedElementsHelper;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record WandItemModel(Identifier base, BakingContext bakingContext) implements ItemModel {

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
            List<BakedQuad> quads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY).getAll();
            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);

            models.add(new BlockModelWrapper(List.of(), quads, properties, _ -> Sheets.translucentItemSheet()));

            if (cacheKey.pommel() != null) {
                models.add(this.createModel(cacheKey.pommel().texture(), properties));
            }
            if (cacheKey.transition() != null) {
                models.add(this.createModel(cacheKey.transition().texture(), properties));
            }
            return new CompositeModel(models);
        });

        model.update(output, stack, resolver, displayContext, level, owner, seed);
    }

    private ItemModel createModel(Identifier texture, ModelRenderProperties properties) {
        Material material = ClientHooks.getItemMaterial(texture);
        TextureAtlasSprite sprite = this.bakingContext.blockModelBaker().sprites().get(material, DEBUG_NAME);

        var unbaked = UnbakedElementsHelper.createUnbakedItemElements(0, sprite);
        var quads = UnbakedElementsHelper.bakeElements(unbaked, _ -> sprite, BlockModelRotation.IDENTITY);

        return new BlockModelWrapper(List.of(), quads, properties, _ -> Sheets.translucentItemSheet());
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
        public ItemModel bake(BakingContext context) {
            return new WandItemModel(this.base, context);
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.base);
        }
    }

    private record CacheKey(Identifier base, WandMaterial pommel, WandMaterial transition) {}
}
