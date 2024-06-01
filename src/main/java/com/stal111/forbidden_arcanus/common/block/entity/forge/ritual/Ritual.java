package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

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
public record Ritual(List<RitualInput> inputs,
                     Ingredient mainIngredient,
                     RitualResult result,
                     RitualRequirements requirements,
                     Holder<MagicCircleType> magicCircleType,
                     int duration) {

    public static final int DEFAULT_DURATION = 500;

    public static final Codec<Ritual> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RitualInput.CODEC.listOf().fieldOf("inputs").forGetter(ritual -> {
                return ritual.inputs;
            }),
            Ingredient.CODEC_NONEMPTY.fieldOf("main_ingredient").forGetter(ritual -> {
                return ritual.mainIngredient;
            }),
            RitualResult.DIRECT_CODEC.fieldOf("result").forGetter(ritual -> {
                return ritual.result;
            }),
            RitualRequirements.CODEC.forGetter(ritual -> {
                return ritual.requirements;
            }),
            MagicCircleType.CODEC.fieldOf("magic_circle").forGetter(ritual -> {
                return ritual.magicCircleType;
            }),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("duration", DEFAULT_DURATION).forGetter(ritual -> {
                return ritual.duration;
            })
    ).apply(instance, Ritual::new));

    public static final Codec<Holder<Ritual>> CODEC = RegistryFileCodec.create(FARegistries.RITUAL, DIRECT_CODEC);

    public static final Codec<Ritual> NETWORK_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RitualInput.CODEC.listOf().fieldOf("inputs").forGetter(ritual -> {
                return ritual.inputs;
            }),
            Ingredient.CODEC_NONEMPTY.fieldOf("main_ingredient").forGetter(ritual -> {
                return ritual.mainIngredient;
            }),
            RitualResult.DIRECT_CODEC.fieldOf("result").forGetter(ritual -> {
                return ritual.result;
            }),
            RitualRequirements.CODEC.forGetter(ritual -> {
                return ritual.requirements;
            }),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("duration", DEFAULT_DURATION).forGetter(ritual -> {
                return ritual.duration;
            })
    ).apply(instance, (inputs, mainIngredient, result, requirements, duration) -> {
        return new Ritual(inputs, mainIngredient, result, requirements, null, duration);
    }));

    public boolean canStart(RitualStartContext context) {
        if (!this.requirements.checkRequirements(context.forgeTier(), context.enhancers())) {
            return false;
        }

        ItemStack mainIngredient = context.mainIngredient();

        return this.checkIngredients(context.inputs(), mainIngredient);
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

        return ingredients.stream().filter(stack -> !stack.isEmpty()).toList().isEmpty();
    }

    protected record RitualStartContext(Level level, BlockPos pos, int forgeTier, Collection<ItemStack> inputs, ItemStack mainIngredient, HolderSet<EnhancerDefinition> enhancers) {
        public static RitualStartContext of(Level level, BlockPos pos, int forgeTier, Collection<ItemStack> inputs, ItemStack mainIngredient, HolderSet<EnhancerDefinition> enhancers) {
            return new RitualStartContext(level, pos, forgeTier, inputs, mainIngredient, enhancers);
        }
    }
}
