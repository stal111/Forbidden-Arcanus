package com.stal111.forbidden_arcanus.data.hephaestus_forge.rituals;

import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesStorage;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.data.hephaestus_forge.ModMagicCircles;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stal111
 * @since 2023-02-03
 */
public class RitualBuilder {

    private final Ingredient mainIngredient;
    private final RitualResult result;
    private final HolderGetter<MagicCircleType> magicCircleLookup;

    private final List<RitualInput> inputs = new ArrayList<>();
    private final EssencesStorage essences = new EssencesStorage();
    private RitualRequirements requirements = RitualRequirements.NONE;
    private Holder<MagicCircleType> magicCircleType;

    public RitualBuilder(ItemStack mainIngredient, RitualResult result, HolderGetter<MagicCircleType> magicCircleLookup) {
        this.mainIngredient = Ingredient.of(mainIngredient);
        this.result = result;
        this.magicCircleLookup = magicCircleLookup;

        this.magicCircleType = this.magicCircleLookup.getOrThrow(ModMagicCircles.CREATE_ITEM);
    }

    public RitualBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public RitualBuilder input(Ingredient ingredient, int amount) {
        this.inputs.add(new RitualInput(ingredient, amount));

        return this;
    }

    public RitualBuilder aureal(int amount) {
        this.essences.put(EssenceType.AUREAL, amount);

        return this;
    }

    public RitualBuilder souls(int amount) {
        this.essences.put(EssenceType.SOULS, amount);

        return this;
    }

    public RitualBuilder blood(int amount) {
        this.essences.put(EssenceType.BLOOD, amount);

        return this;
    }

    public RitualBuilder experience(int amount) {
        this.essences.put(EssenceType.EXPERIENCE, amount);

        return this;
    }

    public RitualBuilder magicCircle(ResourceKey<MagicCircleType> type) {
        this.magicCircleType = this.magicCircleLookup.getOrThrow(type);

        return this;
    }

    public RitualBuilder requirements(RitualRequirements requirements) {
        this.requirements = requirements;

        return this;
    }

    public Ritual build() {
        return new Ritual(this.inputs, this.mainIngredient, this.result, this.essences.immutable(), this.requirements, this.magicCircleType, Ritual.DEFAULT_DURATION);
    }
}
