package com.stal111.forbidden_arcanus.common.item.crafting.display;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

public class FARecipeDisplays {

    public static final MappedRegistryHelper<RecipeDisplay.Type<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.RECIPE_DISPLAY);

    public static final Supplier<RecipeDisplay.Type<ClibanoRecipeDisplay>> CLIBANO = HELPER.register("clibano", () -> new RecipeDisplay.Type<>(ClibanoRecipeDisplay.CODEC, ClibanoRecipeDisplay.STREAM_CODEC));
}
