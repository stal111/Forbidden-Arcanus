package com.stal111.forbidden_arcanus.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class FullbrightBakedModel extends DelegateBakedModel {

    private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<>() {
        @Nonnull
        @Override
        public List<BakedQuad> load(@Nonnull CacheKey key) {
            return transformQuads(key.base().getQuads(key.state(), key.side(), key.random()), key.textures());
        }
    });

    private final Set<ResourceLocation> textures;
    private final boolean doCaching;

    public FullbrightBakedModel(BakedModel base, ResourceLocation... textures) {
        this(base, true, textures);
    }

    public FullbrightBakedModel(BakedModel base, boolean doCaching, ResourceLocation... textures) {
        super(base);
        this.textures = new HashSet<>(Arrays.asList(textures));
        this.doCaching = doCaching;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData data, @Nullable RenderType renderType) {
        BakedModel base = this.getBase();

        if (state == null) {
            return base.getQuads(null, side, rand, data, renderType);
        }

        if (!this.doCaching) {
            return transformQuads(base.getQuads(state, side, rand, data, renderType), this.textures);
        }

        return CACHE.getUnchecked(new CacheKey(base, this.textures, rand, state, side));
    }

    private static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads, Set<ResourceLocation> textures) {
        List<BakedQuad> quads = new ArrayList<>(oldQuads);

        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);

            if (textures.contains(quad.getSprite().getName())) {
                quads.set(i, transformQuad(quad));
            }
        }

        return quads;
    }

    private static BakedQuad transformQuad(BakedQuad quad) {
        int[] vertexData = quad.getVertices().clone();
        int step = vertexData.length / 4;

        // Set lighting to fullbright on all vertices
        vertexData[6] = 0x00F000F0;
        vertexData[6 + step] = 0x00F000F0;
        vertexData[6 + 2 * step] = 0x00F000F0;
        vertexData[6 + 3 * step] = 0x00F000F0;

        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
    }

    public static void invalidateCache() {
        CACHE.invalidateAll();
    }

    private record CacheKey(BakedModel base, Set<ResourceLocation> textures, RandomSource random, BlockState state, Direction side) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            CacheKey cacheKey = (CacheKey) o;

            if (cacheKey.side != side) {
                return false;
            }

            return state.equals(cacheKey.state);
        }

        @Override
        public int hashCode() {
            return state.hashCode() + (31 * (side != null ? side.hashCode() : 0));
        }
    }
}