package com.stal111.forbidden_arcanus.core.registry;

import com.stal111.forbidden_arcanus.common.advancements.critereon.FACriteriaTriggers;
import com.stal111.forbidden_arcanus.common.advancements.critereon.FAEntitySubPredicates;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffects;
import com.stal111.forbidden_arcanus.common.entity.attribute.FAAttributes;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTraderVariant;
import com.stal111.forbidden_arcanus.common.essence.source.EssenceSources;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturInteractions;
import com.stal111.forbidden_arcanus.core.init.*;
import com.stal111.forbidden_arcanus.core.init.other.*;
import com.stal111.forbidden_arcanus.core.init.world.ModFeatures;
import com.stal111.forbidden_arcanus.data.research.ModConstellations;
import com.stal111.forbidden_arcanus.data.research.ModKnowledge;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
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
        this.addBlockHelper(ModBlocks.class);
        this.addItemHelper(ModItems.class);
        this.addMappedHelper(Registries.ENTITY_TYPE, EntityRegistryHelper::new, ModEntities.class);
        this.addMappedHelper(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModLootModifiers.class);
        this.addMappedHelper(Registries.SOUND_EVENT, ModSounds.class);
        this.addMappedHelper(Registries.PARTICLE_TYPE, ModParticles.class);
        this.addMappedHelper(Registries.MEMORY_MODULE_TYPE, ModMemoryModules.class);
        this.addMappedHelper(Registries.CREATIVE_MODE_TAB, ModCreativeModeTabs.class);
        this.addMappedHelper(Registries.MENU, ModMenuTypes.class);
        this.addMappedHelper(Registries.POINT_OF_INTEREST_TYPE, ModPOITypes.class);
        this.addMappedHelper(Registries.FEATURE, ModFeatures.class);
        this.addMappedHelper(Registries.BLOCK_ENTITY_TYPE, ModBlockEntities.class);
        this.addMappedHelper(Registries.MOB_EFFECT, ModMobEffects.class);
        this.addMappedHelper(Registries.RECIPE_TYPE, ModRecipeTypes.class);
        this.addMappedHelper(Registries.RECIPE_SERIALIZER, ModRecipeSerializers.class);
        this.addMappedHelper(Registries.CUSTOM_STAT, ModStats.class);
        this.addMappedHelper(Registries.DATA_COMPONENT_TYPE, ModDataComponents.class);
        this.addMappedHelper(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, ModEnchantmentDataComponents.class);
        this.addMappedHelper(Registries.ENTITY_SUB_PREDICATE_TYPE, FAEntitySubPredicates.class);
        this.addMappedHelper(Registries.TRIGGER_TYPE, FACriteriaTriggers.class);
        this.addMappedHelper(Registries.ATTRIBUTE, FAAttributes.class);
        this.addMappedHelper(Registries.CONSUME_EFFECT_TYPE, ModConsumeEffects.class);
        this.addMappedHelper(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, ModEntityDataSerializers.class);
        this.addMappedHelper(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ModAttachmentTypes.class);
        this.addMappedHelper(FARegistries.RITUAL_RESULT_TYPE, ModRitualResultTypes.class);
        this.addMappedHelper(FARegistries.ENHANCER_EFFECT, ModEnhancerEffects.class);
        this.addMappedHelper(FARegistries.ENHANCER_EFFECT_CONDITION, ModEnhancerEffectConditions.class);
        this.addMappedHelper(FARegistries.ESSENCE_INPUT, ModForgeInputTypes.class);
        this.addMappedHelper(FARegistries.DARK_TRADER_VARIANT, DarkTraderVariant.class);
        this.addMappedHelper(FARegistries.MUNDABITUR_INTERACTION, MundabiturInteractions.class);
        this.addMappedHelper(FARegistries.PEDESTAL_EFFECT, PedestalEffects.class);
        this.addMappedHelper(FARegistries.ESSENCE_SOURCE_TYPE, EssenceSources.class);

        this.addDatapackHelper(FARegistries.KNOWLEDGE, ModKnowledge::new);
        this.addDatapackHelper(FARegistries.CONSTELLATION, ModConstellations::new);
    }
}
