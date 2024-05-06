package com.stal111.forbidden_arcanus.data.hephaestus_forge.input;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * @author stal111
 * @since 2023-05-25
 */
public class ModForgeInputs extends DatapackRegistryClass<HephaestusForgeInput> {

    public static final DatapackRegistryHelper<HephaestusForgeInput> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.FORGE_INPUT);

    public static final ResourceKey<HephaestusForgeInput> SOUL = HELPER.createKey("soul");
    public static final ResourceKey<HephaestusForgeInput> CORRUPT_SOUL = HELPER.createKey("corrupt_soul");
    public static final ResourceKey<HephaestusForgeInput> ENCHANTED_SOUL = HELPER.createKey("enchanted_soul");
    public static final ResourceKey<HephaestusForgeInput> AUREAL_BOTTLE = HELPER.createKey("aureal_bottle");
    public static final ResourceKey<HephaestusForgeInput> SPLASH_AUREAL_BOTTLE = HELPER.createKey("splash_aureal_bottle");
    public static final ResourceKey<HephaestusForgeInput> EXPERIENCE_BOTTLE = HELPER.createKey("experience_bottle");
    public static final ResourceKey<HephaestusForgeInput> XPETRIFIED_ORB = HELPER.createKey("xpetrified_orb");
    public static final ResourceKey<HephaestusForgeInput> AUREAL_ESSENCE_CONTAINER = HELPER.createKey("aureal_essence_container");
    public static final ResourceKey<HephaestusForgeInput> SOULS_ESSENCE_CONTAINER = HELPER.createKey("souls_essence_container");
    public static final ResourceKey<HephaestusForgeInput> BLOOD_ESSENCE_CONTAINER = HELPER.createKey("blood_essence_container");
    public static final ResourceKey<HephaestusForgeInput> EXPERIENCE_ESSENCE_CONTAINER = HELPER.createKey("experience_essence_container");
    public static final ResourceKey<HephaestusForgeInput> EXTRACT_ENCHANTMENTS = HELPER.createKey("extract_enchantments");

    public ModForgeInputs(BootstrapContext<HephaestusForgeInput> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<HephaestusForgeInput> context) {
        //TODO
        //context.register(EXPERIENCE_BOTTLE, new ItemInput(EssenceType.EXPERIENCE, Ingredient.of(Items.EXPERIENCE_BOTTLE), 15));

    }
}
