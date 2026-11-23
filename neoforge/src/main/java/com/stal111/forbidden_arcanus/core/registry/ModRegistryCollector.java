package com.stal111.forbidden_arcanus.core.registry;

import com.stal111.forbidden_arcanus.common.advancements.critereon.FACriteriaTriggers;
import com.stal111.forbidden_arcanus.common.advancements.critereon.FAEntitySubPredicates;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffects;
import com.stal111.forbidden_arcanus.common.entity.attribute.FAAttributes;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTraderVariant;
import com.stal111.forbidden_arcanus.common.essence.source.EssenceSources;
import com.stal111.forbidden_arcanus.common.item.crafting.display.FARecipeDisplays;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturInteractions;
import com.stal111.forbidden_arcanus.core.init.*;
import com.stal111.forbidden_arcanus.core.init.other.*;
import com.stal111.forbidden_arcanus.core.init.world.ModFeatures;
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
        this.addHelper(Registries.ENTITY_TYPE, EntityRegistryHelper::new, ModEntities.class);
        this.addHelper(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModLootModifiers.class);
        this.addHelper(Registries.SOUND_EVENT, ModSounds.class);
        this.addHelper(Registries.PARTICLE_TYPE, ModParticles.class);
        this.addHelper(Registries.MEMORY_MODULE_TYPE, ModMemoryModules.class);
        this.addHelper(Registries.CREATIVE_MODE_TAB, ModCreativeModeTabs.class);
        this.addHelper(Registries.MENU, ModMenuTypes.class);
        this.addHelper(Registries.POINT_OF_INTEREST_TYPE, ModPOITypes.class);
        this.addHelper(Registries.FEATURE, ModFeatures.class);
        this.addHelper(Registries.BLOCK_ENTITY_TYPE, ModBlockEntities.class);
        this.addHelper(Registries.MOB_EFFECT, ModMobEffects.class);
        this.addHelper(Registries.RECIPE_TYPE, ModRecipeTypes.class);
        this.addHelper(Registries.RECIPE_SERIALIZER, ModRecipeSerializers.class);
        this.addHelper(Registries.CUSTOM_STAT, ModStats.class);
        this.addHelper(Registries.DATA_COMPONENT_TYPE, ModDataComponents.class);
        this.addHelper(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, ModEnchantmentDataComponents.class);
        this.addHelper(Registries.ENTITY_SUB_PREDICATE_TYPE, FAEntitySubPredicates.class);
        this.addHelper(Registries.TRIGGER_TYPE, FACriteriaTriggers.class);
        this.addHelper(Registries.ATTRIBUTE, FAAttributes.class);
        this.addHelper(Registries.CONSUME_EFFECT_TYPE, ModConsumeEffects.class);
        this.addHelper(Registries.RECIPE_DISPLAY, FARecipeDisplays.class);
        this.addHelper(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, ModEntityDataSerializers.class);
        this.addHelper(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ModAttachmentTypes.class);
        this.addHelper(FARegistries.RITUAL_RESULT_TYPE, ModRitualResultTypes.class);
        this.addHelper(FARegistries.ENHANCER_EFFECT, ModEnhancerEffects.class);
        this.addHelper(FARegistries.ENHANCER_EFFECT_CONDITION, ModEnhancerEffectConditions.class);
        this.addHelper(FARegistries.DARK_TRADER_VARIANT, DarkTraderVariant.class);
        this.addHelper(FARegistries.MUNDABITUR_INTERACTION, MundabiturInteractions.class);
        this.addHelper(FARegistries.PEDESTAL_EFFECT, PedestalEffects.class);
        this.addHelper(FARegistries.ESSENCE_SOURCE, EssenceSources.class);
    }
}
