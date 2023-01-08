package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ritual <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class Ritual {

    private final ResourceLocation name;

    private final Map<Integer, Ingredient> inputs;
    private final ItemStack hephaestusForgeItem;
    private final ItemStack result;

    private final EssencesDefinition essences;

    private final ResourceLocation outerTexture;
    private final ResourceLocation innerTexture;

    private final PedestalType pedestalType;

    private final int time;

    public Ritual(ResourceLocation name, Map<Integer, Ingredient> inputs, ItemStack hephaestusForgeItem, ItemStack result, EssencesDefinition essences, ResourceLocation outerTexture, ResourceLocation innerTexture, int time) {
        this.name = name;
        this.inputs = inputs;
        this.hephaestusForgeItem = hephaestusForgeItem;
        this.result = result;
        this.essences = essences;
        this.outerTexture = outerTexture;
        this.innerTexture = innerTexture;
        this.pedestalType = PedestalType.DARKSTONE_PEDESTAL;
        this.time = time;
    }

    public boolean checkIngredients(List<ItemStack> list, ItemStack mainSlotStack) {
        List<ItemStack> ingredients = new ArrayList<>(list);

        for (Ingredient ingredient : this.getInputs()) {
            boolean foundStack = false;

            for (ItemStack input : ingredients) {
                if (ingredient.test(input)) {
                    ingredients.remove(input);

                    foundStack = true;
                    break;
                }
            }

            if (!foundStack) {
                return false;
            }
        }

        if (!ingredients.isEmpty()) {
            return false;
        }

        return this.getHephaestusForgeItem().isEmpty() ? mainSlotStack.isEmpty() : this.getHephaestusForgeItem().equals(mainSlotStack, false);
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public Ingredient getInput(int slot) {
        return this.inputs.get(slot);
    }

    public List<Ingredient> getInputs() {
        return new ArrayList<>(this.inputs.values());
    }

    public ItemStack getHephaestusForgeItem() {
        return this.hephaestusForgeItem;
    }

    public ItemStack getResult() {
        return this.result.copy();
    }

    public EssencesDefinition getEssences() {
        return this.essences;
    }

    public ResourceLocation getOuterTexture() {
        return this.outerTexture;
    }

    public ResourceLocation getInnerTexture() {
        return this.innerTexture;
    }

    public PedestalType getPedestalType() {
        return this.pedestalType;
    }

    public int getTime() {
        return this.time;
    }

    public void serializeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.name);
        buffer.writeMap(this.inputs, FriendlyByteBuf::writeVarInt, (friendlyByteBuf, ingredient) -> ingredient.toNetwork(friendlyByteBuf));
        buffer.writeItem(this.hephaestusForgeItem);
        buffer.writeItem(this.result);
        this.essences.serializeToNetwork(buffer);
        buffer.writeResourceLocation(this.outerTexture);
        buffer.writeResourceLocation(this.innerTexture);
        buffer.writeVarInt(this.time);
    }

    public static Ritual fromNetwork(FriendlyByteBuf buffer) {
        ResourceLocation name = buffer.readResourceLocation();
        Map<Integer, Ingredient> inputs = buffer.readMap(FriendlyByteBuf::readVarInt, Ingredient::fromNetwork);
        ItemStack hephaestusForgeItem = buffer.readItem();
        ItemStack result = buffer.readItem();
        EssencesDefinition essences = EssencesDefinition.fromNetwork(buffer);
        ResourceLocation outerTexture = buffer.readResourceLocation();
        ResourceLocation innerTexture = buffer.readResourceLocation();
        int time = buffer.readVarInt();

        return new Ritual(name, inputs, hephaestusForgeItem, result, essences, outerTexture, innerTexture, time);
    }

    public enum PedestalType {
        DARKSTONE_PEDESTAL(ModBlocks.DARKSTONE_PEDESTAL.get()),
        MAGNETIZED_DARKSTONE_PEDESTAL(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get());

        private final Block block;

        PedestalType(Block block) {
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }
    }
}
