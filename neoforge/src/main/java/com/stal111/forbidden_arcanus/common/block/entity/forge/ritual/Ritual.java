package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collection;
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
            RitualInput.CODEC.listOf(1, 8).fieldOf("inputs").forGetter(Ritual::inputs),
            Ingredient.CODEC.fieldOf("main_ingredient").forGetter(Ritual::mainIngredient),
            RitualResult.DIRECT_CODEC.fieldOf("result").forGetter(Ritual::result),
            RitualRequirements.CODEC.forGetter(Ritual::requirements),
            MagicCircleType.CODEC.fieldOf("magic_circle").forGetter(Ritual::magicCircleType),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("duration", DEFAULT_DURATION).forGetter(Ritual::duration)
    ).apply(instance, Ritual::new));

    public static final Codec<Holder<Ritual>> CODEC = RegistryFileCodec.create(FARegistries.RITUAL, DIRECT_CODEC);

    public boolean canStart(HephaestusForgeState state) {
        if (!this.checkIngredients(state.pedestalItems(), state.mainItem())) {
            return false;
        }

        return this.requirements.checkRequirements(state.tier(), state.enhancers(), state.essenceSet());
    }

    public boolean checkIngredients(Collection<ItemStack> pedestalItems, ItemStack mainItem) {
        if (!this.mainIngredient().test(mainItem)) {
            return false;
        }

        List<ItemStack> remainingItems = new ArrayList<>(pedestalItems);

        for (RitualInput input : this.inputs) {
            if (!input.consumeFrom(remainingItems)) {
                return false;
            }
        }

        return remainingItems.isEmpty();
    }
}
