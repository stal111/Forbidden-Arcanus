package com.stal111.forbidden_arcanus.common.loader;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualEssences;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Ritual Loader
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loader.RitualLoader
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class RitualLoader extends JsonReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final Map<ResourceLocation, Ritual> RITUALS = new HashMap<>();


    public RitualLoader() {
        super(GSON, "hephaestus_forge/rituals");
    }

    @Override
    protected void apply(@Nonnull Map<ResourceLocation, JsonElement> object, @Nonnull IResourceManager resourceManager, @Nonnull IProfiler profiler) {
        RITUALS.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();

            if (resourceLocation.getPath().startsWith("_")) {
                continue;
            }

            try {
                Ritual ritual = deserializeRitual(resourceLocation, entry.getValue().getAsJsonObject());
                if (ritual != null) {
                    RITUALS.put(resourceLocation, ritual);
                }
            } catch (IllegalArgumentException | JsonParseException jsonParseException) {
                ForbiddenArcanus.LOGGER.error("Parsing error loading hephaestus forge input {}", resourceLocation, jsonParseException);
            }
        }
    }

    public static Ritual getRitual(ResourceLocation resourceLocation) {
        return RITUALS.get(resourceLocation);
    }

    public static List<Ritual> getRituals() {
        List<Ritual> rituals = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Ritual> entry : RITUALS.entrySet()) {
            rituals.add(entry.getValue());
        }
        return rituals;
    }

    private static Ritual deserializeRitual(ResourceLocation name, JsonObject jsonObject) {
        ItemStack hephaestusForgeInput = ItemStack.EMPTY;

        if (jsonObject.has("hephaestus_forge_item")) {
            hephaestusForgeInput = new ItemStack(deserializeItem(new ResourceLocation(JSONUtils.getString(jsonObject, "hephaestus_forge_item"))));
        }

        try {
            System.out.println(JsonToNBT.getTagFromJson(GSON.toJson(jsonObject.get("result"))));
            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(jsonObject, "result"), true);

            return new Ritual(name, deserializeInputs(jsonObject), hephaestusForgeInput, result, deserializeEssences(jsonObject), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/absolute.png"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/inner_protection.png"), 1200);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Map<Integer, Ingredient> deserializeInputs(JsonObject jsonObject) {
        Map<Integer, Ingredient> inputs = new HashMap<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("inputs");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject input = jsonArray.get(i).getAsJsonObject();
            ItemStack stack = CraftingHelper.getItemStack(input, true);
            int slot = input.get("slot").getAsInt();

            if (inputs.containsKey(slot)) {
                throw new IllegalStateException("Slot " + slot + " was already assigned.");
            } else {
                inputs.put(slot, Ingredient.fromStacks(stack));
            }
        }

        return inputs;
    }

    private static Item deserializeItem(ResourceLocation name) {
        Item item = ForgeRegistries.ITEMS.getValue(name);

        if (item == null) {
            ForbiddenArcanus.LOGGER.error("Item " + name + " does not exist.");
            throw new IllegalArgumentException();
        }
        return item;
    }

    private static RitualEssences deserializeEssences(JsonObject jsonObject) {
        JsonObject essences = jsonObject.get("essences").getAsJsonObject();

        int aureal = JSONUtils.getInt(essences, "aureal", 0);
        int corruption = JSONUtils.getInt(essences, "corruption", 0);
        int souls = JSONUtils.getInt(essences, "souls", 0);
        int blood = JSONUtils.getInt(essences, "blood", 0);
        int experience = JSONUtils.getInt(essences, "experience", 0);

        return new RitualEssences(aureal, corruption, souls, blood, experience);
    }
}
