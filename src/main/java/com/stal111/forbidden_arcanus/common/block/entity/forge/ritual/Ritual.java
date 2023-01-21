package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Ritual <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual
 *
 * @author stal111
 * @since 2021-07-09
 */
public class Ritual implements MagicCircle.TextureProvider {

    private final ResourceLocation name;

    private final List<RitualInput> inputs;
    private final ItemStack hephaestusForgeItem;
    private final ItemStack result;

    private final EssencesDefinition essences;

    private final ResourceLocation outerTexture;
    private final ResourceLocation innerTexture;

    private final PedestalType pedestalType;

    public Ritual(ResourceLocation name, List<RitualInput> inputs, ItemStack hephaestusForgeItem, ItemStack result, EssencesDefinition essences, ResourceLocation outerTexture, ResourceLocation innerTexture) {
        this.name = name;
        this.inputs = inputs;
        this.hephaestusForgeItem = hephaestusForgeItem;
        this.result = result;
        this.essences = essences;
        this.outerTexture = outerTexture;
        this.innerTexture = innerTexture;
        this.pedestalType = PedestalType.DARKSTONE_PEDESTAL;
     }

    public static Ritual fromNetwork(FriendlyByteBuf buffer) {
        ResourceLocation name = buffer.readResourceLocation();
        List<RitualInput> inputs = buffer.readList(RitualInput::fromNetwork);
        ItemStack hephaestusForgeItem = buffer.readItem();
        ItemStack result = buffer.readItem();
        EssencesDefinition essences = EssencesDefinition.fromNetwork(buffer);
        ResourceLocation outerTexture = buffer.readResourceLocation();
        ResourceLocation innerTexture = buffer.readResourceLocation();

        return new Ritual(name, inputs, hephaestusForgeItem, result, essences, outerTexture, innerTexture);
    }

    public boolean checkIngredients(Collection<ItemStack> list, RitualManager.MainIngredientAccessor accessor) {
        if (!this.getHephaestusForgeItem().equals(accessor.get(), false)) {
            return false;
        }

        List<ItemStack> ingredients = new ArrayList<>(list);

        outer: for (RitualInput input : this.getInputs()) {
            int amount = 0;

            Iterator<ItemStack> iterator = ingredients.iterator();

            while (iterator.hasNext()) {
                if (input.ingredient().test(iterator.next())) {
                    iterator.remove();

                    amount++;

                    if (amount == input.amount()) {
                        continue outer;
                    }
                }
            }

            return false;
        }

        return ingredients.isEmpty();
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public List<RitualInput> getInputs() {
        return this.inputs;
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

    @Override
    public ResourceLocation getInnerTexture() {
        return this.innerTexture;
    }

    @Override
    public ResourceLocation getOuterTexture() {
        return this.outerTexture;
    }

    public PedestalType getPedestalType() {
        return this.pedestalType;
    }

    public void serializeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.name);
        buffer.writeCollection(this.inputs, (buf, input) -> input.toNetwork(buf));
        buffer.writeItem(this.hephaestusForgeItem);
        buffer.writeItem(this.result);
        this.essences.serializeToNetwork(buffer);
        buffer.writeResourceLocation(this.outerTexture);
        buffer.writeResourceLocation(this.innerTexture);
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
