package com.stal111.forbidden_arcanus.common.loader;

import com.google.common.collect.Maps;
import com.google.gson.*;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.container.InputType;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Hephaestus Forge Input Loader
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loader.HephaestusForgeInputLoader
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-05
 */
public class HephaestusForgeInputLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final Map<InputType, Map<Item, InputData>> INPUTS = Maps.newHashMap();

    public HephaestusForgeInputLoader() {
        super(GSON, "hephaestus_forge/inputs");
    }

    @Override
    protected void apply(@Nonnull Map<ResourceLocation, JsonElement> object, @Nonnull ResourceManager resourceManager, @Nonnull ProfilerFiller profiler) {
        INPUTS.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();

            if (resourceLocation.getPath().startsWith("_")) {
                continue;
            }

            try {
                InputType type = InputType.valueOf(GsonHelper.getAsString(entry.getValue().getAsJsonObject(), "type").toUpperCase(Locale.ROOT));
                InputData data = deserializeInput(resourceLocation, entry.getValue().getAsJsonObject());

                INPUTS.computeIfAbsent(type, (inputType) -> new HashMap<>());
                INPUTS.get(type).put(data.getStack().getItem(), data);
            } catch (IllegalArgumentException | JsonParseException jsonParseException) {
                ForbiddenArcanus.LOGGER.error("Parsing error loading hephaestus forge input {}", resourceLocation, jsonParseException);
            }
        }
    }

    private static Map<InputType, Map<Item, InputData>> getInputs() {
        return INPUTS;
    }

    private static InputData deserializeInput(ResourceLocation input, JsonObject jsonObject) {
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "item"));
        Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

        if (item == null) {
            ForbiddenArcanus.LOGGER.error("Item " + resourceLocation + " does not exist.");
            throw new IllegalArgumentException();
        }

        int value = GsonHelper.getAsInt(jsonObject, "value");

        return new InputData(new ItemStack(item), value);
    }

    public static boolean isValidInput(InputType inputType, ItemStack stack) {
        return getInputs().containsKey(inputType) && HephaestusForgeInputLoader.getInputs().get(inputType).containsKey(stack.getItem());
    }

    public static InputData getInputData(InputType inputType, ItemStack stack) {
        return getInputs().get(inputType).get(stack.getItem());
    }

    public static class InputData {

        private final ItemStack stack;
        private final int value;

        public InputData(ItemStack stack, int value) {
            this.stack = stack;
            this.value = value;
        }

        public ItemStack getStack() {
            return stack;
        }

        public int getValue() {
            return value;
        }
    }
}
