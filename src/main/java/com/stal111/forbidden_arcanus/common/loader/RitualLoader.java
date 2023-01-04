package com.stal111.forbidden_arcanus.common.loader;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualEssences;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Ritual Loader <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loader.RitualLoader
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class RitualLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static Map<ResourceLocation, Ritual> rituals = new HashMap<>();

    public RitualLoader() {
        super(GSON, "hephaestus_forge/rituals");
    }

    @Override
    protected void apply(@Nonnull Map<ResourceLocation, JsonElement> object, @Nonnull ResourceManager resourceManager, @Nonnull ProfilerFiller profiler) {
        rituals.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();

            if (resourceLocation.getPath().startsWith("_")) {
                continue;
            }

            try {
                Ritual ritual = this.deserializeRitual(resourceLocation, entry.getValue().getAsJsonObject());
                if (ritual != null) {
                    rituals.put(resourceLocation, ritual);
                }
            } catch (IllegalArgumentException | JsonParseException jsonParseException) {
                ForbiddenArcanus.LOGGER.error("Parsing error loading hephaestus forge input {}", resourceLocation, jsonParseException);
            }
        }
    }

    public static Ritual getRitual(ResourceLocation resourceLocation) {
        return rituals.get(resourceLocation);
    }

    public static List<Ritual> getRituals() {
        List<Ritual> rituals = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Ritual> entry : RitualLoader.rituals.entrySet()) {
            rituals.add(entry.getValue());
        }
        return rituals;
    }

    public static void setRituals(Map<ResourceLocation, Ritual> rituals) {
        RitualLoader.rituals = rituals;
    }

    private Ritual deserializeRitual(ResourceLocation name, JsonObject jsonObject) {
        ItemStack hephaestusForgeInput = ItemStack.EMPTY;

        if (jsonObject.has("hephaestus_forge_item")) {
            hephaestusForgeInput = new ItemStack(this.deserializeItem(new ResourceLocation(GsonHelper.getAsString(jsonObject, "hephaestus_forge_item"))));
        }

        try {
            System.out.println(TagParser.parseTag(GSON.toJson(jsonObject.get("result"))));
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(jsonObject, "result"), true);

            return new Ritual(name, this.deserializeInputs(jsonObject), hephaestusForgeInput, result, this.deserializeEssences(jsonObject), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/absolute.png"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/inner_protection.png"), 1200);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Map<Integer, Ingredient> deserializeInputs(JsonObject jsonObject) {
        Map<Integer, Ingredient> inputs = new HashMap<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("inputs");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject input = jsonArray.get(i).getAsJsonObject();
            ItemStack stack = CraftingHelper.getItemStack(input, true);
            int slot = input.get("slot").getAsInt();

            if (inputs.containsKey(slot)) {
                throw new IllegalStateException("Slot " + slot + " was already assigned.");
            } else {
                inputs.put(slot, Ingredient.of(stack));
            }
        }

        return inputs;
    }

    private Item deserializeItem(ResourceLocation name) {
        Item item = ForgeRegistries.ITEMS.getValue(name);

        if (item == null) {
            ForbiddenArcanus.LOGGER.error("Item " + name + " does not exist.");
            throw new IllegalArgumentException();
        }
        return item;
    }

    private RitualEssences deserializeEssences(JsonObject jsonObject) {
        JsonObject essences = jsonObject.get("essences").getAsJsonObject();

        int aureal = GsonHelper.getAsInt(essences, "aureal", 0);
        int souls = GsonHelper.getAsInt(essences, "souls", 0);
        int blood = GsonHelper.getAsInt(essences, "blood", 0);
        int experience = GsonHelper.getAsInt(essences, "experience", 0);

        return new RitualEssences(aureal, souls, blood, experience);
    }
}
