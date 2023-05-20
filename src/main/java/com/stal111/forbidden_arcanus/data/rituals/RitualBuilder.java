package com.stal111.forbidden_arcanus.data.rituals;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesStorage;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
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

    private final List<RitualInput> inputs = new ArrayList<>();
    private final EssencesStorage essences = new EssencesStorage();
    private RitualRequirements additionalRequirements;
    private MagicCircle.Config magicCircleConfig = MagicCircle.Config.DEFAULT;

    public RitualBuilder(ItemStack mainIngredient, RitualResult result) {
        this.mainIngredient = Ingredient.of(mainIngredient);
        this.result = result;
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

    public RitualBuilder magicCircle(String innerTexture, String outerTexture) {
        this.magicCircleConfig = new MagicCircle.Config(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/inner/" + innerTexture + ".png"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/outer/" + outerTexture + ".png"));

        return this;
    }

    public RitualBuilder requirements(int tier) {
        this.additionalRequirements = new RitualRequirements(tier, List.of());

        return this;
    }

    @SafeVarargs
    public final RitualBuilder requirements(int tier, Holder<EnhancerDefinition>... enhancers) {
        this.additionalRequirements = new RitualRequirements(tier, List.of(enhancers));

        return this;
    }

    public Ritual build() {
        return new Ritual(this.inputs, this.mainIngredient, this.result, this.essences.immutable(), this.additionalRequirements, this.magicCircleConfig);
    }
}
