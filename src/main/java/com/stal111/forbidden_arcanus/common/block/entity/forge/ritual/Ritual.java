package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
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
                     int requiredTier,
                     MagicCircle.Config magicCircleConfig) implements MagicCircle.TextureProvider {

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
            }),
            Codec.INT.optionalFieldOf("required_tier").forGetter(ritual -> {
                return Optional.ofNullable(ritual.requiredTier == 1 ? null : ritual.requiredTier);
            }),
            MagicCircle.Config.CODEC.optionalFieldOf("magic_circle").forGetter(ritual -> {
                return Optional.ofNullable(ritual.magicCircleConfig.equals(MagicCircle.Config.DEFAULT) ? null : ritual.magicCircleConfig);
            })
    ).apply(instance, (inputs, mainIngredient, result, essences, requiredTier, magicCircleConfig) -> {
        return new Ritual(inputs, mainIngredient, result, essences, requiredTier.orElse(1), magicCircleConfig.orElse(MagicCircle.Config.DEFAULT));
    }));

    public boolean canStart(int forgeTier, Collection<ItemStack> list, RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos) {
        return forgeTier >= this.requiredTier && this.checkIngredients(list, accessor) && this.getResult().checkConditions(accessor, level, pos);
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
        return this.magicCircleConfig.innerTexture();
    }

    @Override
    public ResourceLocation getOuterTexture() {
        return this.magicCircleConfig.outerTexture();
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
