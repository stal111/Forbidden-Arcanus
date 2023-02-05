package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.*;

/**
 * Ritual <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual
 *
 * @author stal111
 * @since 2021-07-09
 */
public record Ritual(List<RitualInput> inputs,
                     Ingredient mainIngredient,
                     RitualResult result,
                     EssencesDefinition essences,
                     ResourceLocation outerTexture,
                     ResourceLocation innerTexture) implements MagicCircle.TextureProvider {

    public static final Codec<Ritual> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RitualInput.CODEC.listOf().fieldOf("inputs").forGetter(ritual -> {
                return ritual.inputs;
            }),
            RitualInput.INGREDIENT.fieldOf("main_ingredient").forGetter(ritual -> {
                return ritual.mainIngredient;
            }),
            RitualResult.DIRECT_CODEC.fieldOf("result").forGetter(ritual -> {
                return ritual.result;
            }),
            EssencesDefinition.CODEC.fieldOf("essences").forGetter(ritual -> {
                return ritual.essences;
            })
    ).apply(instance, (inputs, mainIngredient, result, essences) -> {
        return new Ritual(inputs, mainIngredient, result, essences, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/absolute.png"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/inner_protection.png"));
    }));

    public static Ritual fromNetwork(FriendlyByteBuf buffer) {
        List<RitualInput> inputs = buffer.readList(RitualInput::fromNetwork);
        Ingredient mainIngredient = Ingredient.fromNetwork(buffer);
        ResourceLocation resultType = buffer.readResourceLocation();
        RitualResult result = Objects.requireNonNull(ForbiddenArcanus.RITUAL_RESULT_TYPE_REGISTRY.get().getValue(resultType)).fromNetwork(buffer);
        EssencesDefinition essences = EssencesDefinition.fromNetwork(buffer);
        ResourceLocation outerTexture = buffer.readResourceLocation();
        ResourceLocation innerTexture = buffer.readResourceLocation();

        return new Ritual(inputs, mainIngredient, result, essences, outerTexture, innerTexture);
    }

    public boolean checkIngredients(Collection<ItemStack> list, RitualManager.MainIngredientAccessor accessor) {
        if (!this.getMainIngredient().test(accessor.get())) {
            return false;
        }

        List<ItemStack> ingredients = new ArrayList<>(list);

        outer:
        for (RitualInput input : this.getInputs()) {
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

    public List<RitualInput> getInputs() {
        return this.inputs;
    }

    public Ingredient getMainIngredient() {
        return this.mainIngredient;
    }

    public RitualResult getResult() {
        return this.result;
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

    public void serializeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeCollection(this.inputs, (buf, input) -> input.toNetwork(buf));
        this.mainIngredient.toNetwork(buffer);
        buffer.writeResourceLocation(Objects.requireNonNull(ForbiddenArcanus.RITUAL_RESULT_TYPE_REGISTRY.get().getKey(this.result.getType())));
        this.result.toNetwork(buffer);
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
