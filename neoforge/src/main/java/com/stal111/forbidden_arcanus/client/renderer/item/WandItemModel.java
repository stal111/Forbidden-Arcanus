package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
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

import java.util.List;

public record WandItemModel(List<BakedQuad> base,
                            ModelRenderProperties properties,
                            BakingContext bakingContext,
                            Identifier texture) implements ItemModel {

    private static final ModelDebugName DEBUG_NAME = () -> "WandModel";

    @Override
    public void update(ItemStackRenderState output, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        ItemModel base = new BlockModelWrapper(List.of(), this.base, this.properties, _ -> Sheets.translucentItemSheet());
        ItemModel pommel = this.createModel(ForbiddenArcanus.identifier("item/wand/pommel/arcane_crystal"));
        ItemModel transition = this.createModel(ForbiddenArcanus.identifier("item/wand/transition/deorum"));

        new CompositeModel(List.of(base, pommel, transition)).update(output, stack, resolver, displayContext, level, owner, seed);
    }

    private ItemModel createModel(Identifier texture) {
        Material material = ClientHooks.getItemMaterial(texture);
        TextureAtlasSprite sprite = this.bakingContext.blockModelBaker().sprites().get(material, DEBUG_NAME);

        var unbaked = UnbakedElementsHelper.createUnbakedItemElements(0, sprite);
        var quads = UnbakedElementsHelper.bakeElements(unbaked, _ -> sprite, BlockModelRotation.IDENTITY);

        return new BlockModelWrapper(List.of(), quads, this.properties, _ -> Sheets.translucentItemSheet());
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
            ModelBaker baker = context.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.base);
            TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            List<BakedQuad> quads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY).getAll();
            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);

            return new WandItemModel(quads, properties, context, null);
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.base);
        }
    }
}
