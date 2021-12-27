package com.stal111.forbidden_arcanus.common.loader;

import com.google.gson.*;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.InputType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Hephaestus Forge Input Loader <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loader.HephaestusForgeInputLoader
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-05
 */
public class HephaestusForgeInputLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static EnumMap<InputType, Map<Item, InputData>> inputs = new EnumMap<>(InputType.class);

    public HephaestusForgeInputLoader() {
        super(GSON, "hephaestus_forge/inputs");
    }

    @Override
    protected void apply(@Nonnull Map<ResourceLocation, JsonElement> object, @Nonnull ResourceManager resourceManager, @Nonnull ProfilerFiller profiler) {
        inputs.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();

            if (resourceLocation.getPath().startsWith("_")) {
                continue;
            }

            try {
                InputType type = InputType.valueOf(GsonHelper.getAsString(entry.getValue().getAsJsonObject(), "type").toUpperCase(Locale.ROOT));
                InputData data = this.deserializeInput(resourceLocation, entry.getValue().getAsJsonObject());

                inputs.computeIfAbsent(type, (inputType) -> new HashMap<>());
                inputs.get(type).put(data.stack().getItem(), data);
            } catch (IllegalArgumentException | JsonParseException jsonParseException) {
                ForbiddenArcanus.LOGGER.error("Parsing error loading hephaestus forge input {}", resourceLocation, jsonParseException);
            }
        }
    }

    private static Map<InputType, Map<Item, InputData>> getInputs() {
        return inputs;
    }

    private InputData deserializeInput(ResourceLocation input, JsonObject jsonObject) {
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

    public static void setInputs(EnumMap<InputType, Map<Item, InputData>> inputTypeMapMap) {
        HephaestusForgeInputLoader.inputs = inputTypeMapMap;
    }

    public record InputData(ItemStack stack, int value) {

        public void serializeToNetwork(FriendlyByteBuf buffer) {
            buffer.writeItem(this.stack);
            buffer.writeVarInt(this.value);
        }

        public static InputData fromNetwork(FriendlyByteBuf buffer) {
            return new InputData(buffer.readItem(), buffer.readVarInt());
        }
    }
}
