package com.stal111.forbidden_arcanus.data.particle;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaDataProvider;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author stal111
 * @since 2023-01-03
 */
public class ParticleDataProvider implements DataProvider, ValhelsiaDataProvider {

    private final PackOutput.PathProvider particlePathProvider;
    private final RegistryManager registryManager;

    private final Map<ResourceLocation, ParticleDefinition> builders = new HashMap<>();

    public ParticleDataProvider(DataProviderInfo info) {
        this.particlePathProvider = info.output().createPathProvider(PackOutput.Target.RESOURCE_PACK, "particles");
        this.registryManager = info.registryManager();
    }

    protected void registerParticles() {
        this.register(ModParticles.SOUL.get(), this.modLoc("soul_0"), this.modLoc("soul_1"), this.modLoc("soul_2"), this.modLoc("soul_3"), this.modLoc("soul_4"), this.modLoc("soul_5"), this.modLoc("soul_6"), this.modLoc("soul_7"), this.modLoc("soul_8"), this.modLoc("soul_9"), this.modLoc("soul_10"), this.modLoc("soul_11"), this.modLoc("soul_12"), this.modLoc("soul_13"), this.modLoc("soul_14"), this.modLoc("soul_15"));
        this.register(ModParticles.AUREAL_MOTE.get(), this.modLoc("aureal_mote"));
        this.register(ModParticles.MAGIC_EXPLOSION.get(), this.modLoc("magic_explosion_0"), this.modLoc("magic_explosion_1"), this.modLoc("magic_explosion_2"), this.modLoc("magic_explosion_3"), this.modLoc("magic_explosion_4"));
        this.register(ModParticles.HUGE_MAGIC_EXPLOSION.get());
        this.register(ModParticles.MAGNETIC_GLOW.get(), this.modLoc("magnetic_glow"));
    }

    private void register(ParticleType<?> particleType, ResourceLocation... textures) {
        this.builders.put(ForgeRegistries.PARTICLE_TYPES.getKey(particleType), new ParticleDefinition(List.of(textures)));
    }

    private void register(ParticleType<?> particleType) {
        this.builders.put(ForgeRegistries.PARTICLE_TYPES.getKey(particleType), new ParticleDefinition(null));
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        this.registerParticles();

        return CompletableFuture.allOf(this.builders.entrySet().stream().map(entry -> {
            JsonElement element = ParticleDefinition.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue()).getOrThrow(false, LOGGER::error);

            return DataProvider.saveStable(output, element, this.particlePathProvider.json(entry.getKey()));
        }).toArray(CompletableFuture[]::new));
    }

    private ResourceLocation modLoc(String path) {
        return new ResourceLocation(this.getModId(), path);
    }

    @Override
    public @NotNull String getName() {
        return ForbiddenArcanus.MOD_ID + " - Particles";
    }

    @Override
    public String getModId() {
        return this.registryManager.modId();
    }

    public record ParticleDefinition(@Nullable List<ResourceLocation> textures) {
        public static final Codec<ParticleDefinition> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(ResourceLocation.CODEC.listOf().optionalFieldOf("textures").forGetter(particleDefinition -> {
                return Optional.ofNullable(particleDefinition.textures);
            })).apply(instance, resourceLocations -> {
                return new ParticleDefinition(resourceLocations.orElse(null));
            });
        });
    }
}
