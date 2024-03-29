package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.AdditionalCodecs;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

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
                     @Nullable RitualRequirements requirements,
                     MagicCircle.Config magicCircleConfig) implements MagicCircle.TextureProvider {

    public static final Codec<Ritual> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RitualInput.CODEC.listOf().fieldOf("inputs").forGetter(ritual -> {
                return ritual.inputs;
            }),
            AdditionalCodecs.INGREDIENT.fieldOf("main_ingredient").forGetter(ritual -> {
                return ritual.mainIngredient;
            }),
            RitualResult.DIRECT_CODEC.fieldOf("result").forGetter(ritual -> {
                return ritual.result;
            }),
            EssencesDefinition.CODEC.fieldOf("essences").forGetter(ritual -> {
                return ritual.essences;
            }),
            RitualRequirements.CODEC.optionalFieldOf("additional_requirements").forGetter(ritual -> {
                return Optional.ofNullable(ritual.requirements);
            }),
            MagicCircle.Config.CODEC.optionalFieldOf("magic_circle").forGetter(ritual -> {
                return Optional.ofNullable(ritual.magicCircleConfig.equals(MagicCircle.Config.DEFAULT) ? null : ritual.magicCircleConfig);
            })
    ).apply(instance, (inputs, mainIngredient, result, essences, requirements, magicCircleConfig) -> {
        return new Ritual(inputs, mainIngredient, result, essences, requirements.orElse(null), magicCircleConfig.orElse(MagicCircle.Config.DEFAULT));
    }));

    public static final Codec<Ritual> NETWORK_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RitualInput.CODEC.listOf().fieldOf("inputs").forGetter(ritual -> {
                return ritual.inputs;
            }),
            AdditionalCodecs.INGREDIENT.fieldOf("main_ingredient").forGetter(ritual -> {
                return ritual.mainIngredient;
            }),
            RitualResult.DIRECT_CODEC.fieldOf("result").forGetter(ritual -> {
                return ritual.result;
            }),
            EssencesDefinition.CODEC.fieldOf("essences").forGetter(ritual -> {
                return ritual.essences;
            }),
            RitualRequirements.CODEC.optionalFieldOf("additional_requirements").forGetter(ritual -> {
                return Optional.ofNullable(ritual.requirements);
            })
    ).apply(instance, (inputs, mainIngredient, result, essences, requirements) -> {
        return new Ritual(inputs, mainIngredient, result, essences, requirements.orElse(null), null);
    }));

    public boolean canStart(RitualStartContext context) {
        if (this.requirements != null && !this.requirements.checkRequirements(context.forgeTier(), context.enhancers())) {
            return false;
        }

        ItemStack mainIngredient = context.mainIngredient();

        return this.checkIngredients(context.inputs(), mainIngredient) && this.result().checkConditions(mainIngredient, context.level(), context.pos());
    }

    public boolean checkIngredients(Collection<ItemStack> list, ItemStack mainIngredient) {
        if (!this.mainIngredient().test(mainIngredient)) {
            return false;
        }

        List<ItemStack> ingredients = new ArrayList<>(list);

        outer:
        for (RitualInput input : this.inputs()) {
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

    protected record RitualStartContext(Level level, BlockPos pos, int forgeTier, Collection<ItemStack> inputs, ItemStack mainIngredient, List<EnhancerDefinition> enhancers) {
        public static RitualStartContext of(Level level, BlockPos pos, int forgeTier, Collection<ItemStack> inputs, ItemStack mainIngredient, List<EnhancerDefinition> enhancers) {
            return new RitualStartContext(level, pos, forgeTier, inputs, mainIngredient, enhancers);
        }
    }
}
