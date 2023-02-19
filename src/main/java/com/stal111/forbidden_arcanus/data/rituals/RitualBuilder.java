package com.stal111.forbidden_arcanus.data.rituals;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 2023-02-03
 */
public class RitualBuilder {

    private final Ingredient mainIngredient;
    private final RitualResult result;

    private final List<RitualInput> inputs = new ArrayList<>();
    private final Map<EssenceType, Integer> essences = new HashMap<>();
    private int requiredTier = 1;
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

    public RitualBuilder requiredTier(int tier) {
        this.requiredTier = tier;

        return this;
    }

    public Ritual build() {
        return new Ritual(this.inputs, this.mainIngredient, this.result, EssencesDefinition.of(this.essences), this.requiredTier, this.magicCircleConfig);
    }
}
