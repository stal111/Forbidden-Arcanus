package com.stal111.forbidden_arcanus.common.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.NamedRitual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Ritual Loader <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loader.RitualLoader
 *
 * @author stal111
 * @since 2021-07-09
 */
public class RitualLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public Map<ResourceLocation, NamedRitual> rituals = new HashMap<>();

    public RitualLoader() {
        super(GSON, "hephaestus_forge/rituals");
    }

    @Override
    protected void apply(@Nonnull Map<ResourceLocation, JsonElement> object, @Nonnull ResourceManager resourceManager, @Nonnull ProfilerFiller profiler) {
        this.rituals.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();

            if (resourceLocation.getPath().startsWith("_")) {
                continue;
            }

            Ritual ritual = Util.getOrThrow(Ritual.CODEC.decode(JsonOps.INSTANCE, entry.getValue()), JsonParseException::new).getFirst();

            this.rituals.put(resourceLocation, new NamedRitual(resourceLocation, ritual));
        }
    }

    public void setRituals(Collection<NamedRitual> rituals) {
        this.rituals = rituals.stream().collect(Collectors.toMap(NamedRitual::name, namedRitual -> namedRitual));
    }
}
