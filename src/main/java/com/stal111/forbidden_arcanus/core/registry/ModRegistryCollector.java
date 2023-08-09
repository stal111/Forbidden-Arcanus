package com.stal111.forbidden_arcanus.core.registry;

import com.stal111.forbidden_arcanus.core.init.*;
import com.stal111.forbidden_arcanus.core.init.other.*;
import com.stal111.forbidden_arcanus.core.init.world.*;
import com.stal111.forbidden_arcanus.data.ModDamageTypes;
import com.stal111.forbidden_arcanus.data.enhancer.ModEnhancerDefinitions;
import com.stal111.forbidden_arcanus.data.hephaestus_forge.input.ModForgeInputs;
import com.stal111.forbidden_arcanus.data.hephaestus_forge.rituals.ModRituals;
import com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryCollector;
import net.valhelsia.valhelsia_core.api.common.registry.helper.EntityRegistryHelper;

/**
 * @author stal111
 * @since 2022-12-29
 */
public class ModRegistryCollector extends RegistryCollector {

    public ModRegistryCollector(String modId) {
        super(modId);
    }

    @Override
    protected void collectHelpers() {
        this.addBlockHelper(ModBlocks::new);
        this.addItemHelper(ModItems::new);
        this.addMappedHelper(Registries.ENTITY_TYPE, EntityRegistryHelper::new, ModEntities::new);
        this.addMappedHelper(Registries.STRUCTURE_TYPE, ModStructureTypes::new);
        this.addMappedHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModLootModifiers::new);
        this.addMappedHelper(Registries.STRUCTURE_PIECE, ModStructurePieces::new);
        this.addMappedHelper(Registries.SOUND_EVENT, ModSounds::new);
        this.addMappedHelper(Registries.PARTICLE_TYPE, ModParticles::new);
        this.addMappedHelper(Registries.MEMORY_MODULE_TYPE, ModMemoryModules::new);
        this.addMappedHelper(Registries.ACTIVITY, ModActivities::new);
        this.addMappedHelper(Registries.TRUNK_PLACER_TYPE, ModTrunkPlacers::new);
        this.addMappedHelper(FARegistries.RITUAL_RESULT_TYPE, ModRitualResultTypes::new);
        this.addMappedHelper(FARegistries.ITEM_MODIFIER, ModItemModifiers::new);
        this.addMappedHelper(FARegistries.ENHANCER_EFFECT, ModEnhancerEffects::new);
        this.addMappedHelper(FARegistries.ENHANCER_EFFECT_CONDITION, ModEnhancerEffectConditions::new);
        this.addMappedHelper(FARegistries.FORGE_INPUT_TYPE, ModForgeInputTypes::new);
        this.addMappedHelper(Registries.CREATIVE_MODE_TAB, ModCreativeModeTabs::new);

        this.addDatapackHelper(Registries.STRUCTURE, ModStructures::new);
        this.addDatapackHelper(Registries.STRUCTURE_SET, ModStructureSets::new);
        this.addDatapackHelper(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::new, ModVegetationFeatures::new);
        this.addDatapackHelper(Registries.PLACED_FEATURE, ModCavePlacements::new, ModOrePlacements::new, ModTreePlacements::new, ModVegetationPlacements::new);
        //this.addDatapackHelper(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::new);
        this.addDatapackHelper(FARegistries.RITUAL, ModRituals::new);
        this.addDatapackHelper(FARegistries.ENHANCER_DEFINITION, ModEnhancerDefinitions::new);
        this.addDatapackHelper(Registries.DAMAGE_TYPE, ModDamageTypes::new);
        this.addDatapackHelper(FARegistries.FORGE_INPUT, ModForgeInputs::new);
    }
}
