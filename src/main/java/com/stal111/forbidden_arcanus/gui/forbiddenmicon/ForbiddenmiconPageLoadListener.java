package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.stal111.forbidden_arcanus.gui.ForbiddenmiconEntry;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ForbiddenmiconPageLoadListener extends JsonReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<ResourceLocation, ForbiddenmiconEntry> entries = new HashMap<>();
    private boolean someRecipesErrored;

    public ForbiddenmiconPageLoadListener() {
        super(GSON, "forbiddenmicon");
    }

    public ForbiddenmiconEntry deserializeJson(ResourceLocation recipeId, JsonObject json) {
        ItemStack stack = ShapedRecipe.deserializeItem(json.get("topic").getAsJsonObject());
        String description = json.has("description") ? json.get("description").getAsString() : "";
        return new ForbiddenmiconEntry(stack, description);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonObject> splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        this.someRecipesErrored = false;

        for(Map.Entry<ResourceLocation, JsonObject> entry : splashList.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.

            try {
                ForbiddenmiconEntry entry1 = deserializeJson(resourcelocation, entry.getValue());
                if (entry1 == null) {
                    LOGGER.info("Skipping loading recipe {} as it's serializer returned null", resourcelocation);
                    continue;
                }
                entries.put(resourcelocation, entry1);
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                LOGGER.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
            }
        }

        LOGGER.info("Loaded {} recipes", entries.size());
    }

    public Collection<ForbiddenmiconEntry> getEntries() {
        return this.entries.values();
    }
}
