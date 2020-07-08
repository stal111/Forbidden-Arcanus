package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeCategoryButton;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ForbiddenmiconPageLoadListener implements IResourceManagerReloadListener {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final int PATH_PREFIX_LENGTH = "forbiddenmicon/".length();
    public static final int PATH_SUFFIX_LENGTH = ".json".length();

    public static Map<ResourceLocation, ForbiddenmiconEntry> entries = new HashMap<>();

    public ForbiddenmiconEntry deserializeJson(ResourceLocation recipeId, JsonObject json) {
        ItemStack stack = ShapedRecipe.deserializeItem(json.get("topic").getAsJsonObject());
        String description = json.has("description") ? json.get("description").getAsString() : "";
        return new ForbiddenmiconEntry(stack, description, ForbiddenmiconCategory.valueOf(json.get("category").getAsString()));
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        System.out.println("TEST");
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

        for (ResourceLocation resourcelocation : resourceManager.getAllResourceLocations("forbiddenmicon", (p_199516_0_) -> {
            return p_199516_0_.endsWith(".json") && !p_199516_0_.startsWith("_"); //Forge filter anything beginning with "_" as it's used for metadata.
        })) {
            String s = resourcelocation.getPath();
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s.substring(PATH_PREFIX_LENGTH, s.length() - PATH_SUFFIX_LENGTH));

            try (IResource iresource = resourceManager.getResource(resourcelocation)) {
                JsonObject jsonobject = JSONUtils.fromJson(gson, IOUtils.toString(iresource.getInputStream(), StandardCharsets.UTF_8), JsonObject.class);
                if (jsonobject == null) {
                    LOGGER.error("Couldn't load entry {} as it's null or empty", (Object) resourcelocation1);
                } else {
                    ForbiddenmiconEntry entry1 = deserializeJson(resourcelocation, jsonobject);
                    entries.put(resourcelocation, entry1);
                }
            } catch (IllegalArgumentException | JsonParseException | IOException e) {
                LOGGER.error("Error loading entry {}", resourcelocation1, e);
            }
        }
        LOGGER.info("Loaded {} recipes", entries.size());
    }

    public Collection<ForbiddenmiconEntry> getEntries(ForbiddenmiconCategory category) {
        List<ForbiddenmiconEntry> list = new ArrayList<>();
        entries.forEach((location, forbiddenmiconEntry) -> {
            if (forbiddenmiconEntry.getCategory() == category) {
                list.add(forbiddenmiconEntry);
            }
        });
        return list;
    }
}
