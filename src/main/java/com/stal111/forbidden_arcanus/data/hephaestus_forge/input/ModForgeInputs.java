package com.stal111.forbidden_arcanus.data.hephaestus_forge.input;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.inventory.input.BloodInput;
import com.stal111.forbidden_arcanus.common.inventory.input.ExtractEnchantmentsInput;
import com.stal111.forbidden_arcanus.common.inventory.input.ItemInput;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

/**
 * @author stal111
 * @since 2023-05-25
 */
public class ModForgeInputs extends DatapackRegistryClass<HephaestusForgeInput> {

    public static final DatapackRegistryHelper<HephaestusForgeInput> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(FARegistries.FORGE_INPUT);

    public static final ResourceKey<HephaestusForgeInput> SOUL = HELPER.createKey("soul");
    public static final ResourceKey<HephaestusForgeInput> CORRUPT_SOUL = HELPER.createKey("corrupt_soul");
    public static final ResourceKey<HephaestusForgeInput> ENCHANTED_SOUL = HELPER.createKey("enchanted_soul");
    public static final ResourceKey<HephaestusForgeInput> AUREAL_BOTTLE = HELPER.createKey("aureal_bottle");
    public static final ResourceKey<HephaestusForgeInput> SPLASH_AUREAL_BOTTLE = HELPER.createKey("splash_aureal_bottle");
    public static final ResourceKey<HephaestusForgeInput> EXPERIENCE_BOTTLE = HELPER.createKey("experience_bottle");
    public static final ResourceKey<HephaestusForgeInput> XPETRIFIED_ORB = HELPER.createKey("xpetrified_orb");
    public static final ResourceKey<HephaestusForgeInput> BLOOD_TEST_TUBE = HELPER.createKey("blood_test_tube");
    public static final ResourceKey<HephaestusForgeInput> EXTRACT_ENCHANTMENTS = HELPER.createKey("extract_enchantments");

    public ModForgeInputs(DataProviderInfo info, BootstapContext<HephaestusForgeInput> context) {
        super(info, context);
    }

    @Override
    public void bootstrap(BootstapContext<HephaestusForgeInput> context) {
        context.register(SOUL, new ItemInput(EssenceType.SOULS, Ingredient.of(ModItems.SOUL.get()), 1));
        context.register(CORRUPT_SOUL, new ItemInput(EssenceType.SOULS, Ingredient.of(ModItems.CORRUPT_SOUL.get()), 1));
        context.register(ENCHANTED_SOUL, new ItemInput(EssenceType.SOULS, Ingredient.of(ModItems.ENCHANTED_SOUL.get()), 10));
        context.register(AUREAL_BOTTLE, new ItemInput(EssenceType.AUREAL, Ingredient.of(ModItems.AUREAL_BOTTLE.get()), 35));
        context.register(SPLASH_AUREAL_BOTTLE, new ItemInput(EssenceType.AUREAL, Ingredient.of(ModItems.SPLASH_AUREAL_BOTTLE.get()), 30));
        context.register(EXPERIENCE_BOTTLE, new ItemInput(EssenceType.EXPERIENCE, Ingredient.of(Items.EXPERIENCE_BOTTLE), 15));
        context.register(XPETRIFIED_ORB, new ItemInput(EssenceType.EXPERIENCE, Ingredient.of(ModItems.XPETRIFIED_ORB.get()), 91));
        context.register(BLOOD_TEST_TUBE, new BloodInput());
        context.register(EXTRACT_ENCHANTMENTS, new ExtractEnchantmentsInput());
    }
}
