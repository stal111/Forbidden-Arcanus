package com.stal111.forbidden_arcanus.core.registry;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResultType;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffect;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTraderVariant;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectConditionType;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.EnhancerEffectType;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturInteraction;
import com.stal111.forbidden_arcanus.common.research.Constellation;
import com.stal111.forbidden_arcanus.common.research.Knowledge;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * @author stal111
 * @since 2023-02-18
 */
public class FARegistries {

    public static final ResourceKey<Registry<Ritual>> RITUAL = FARegistries.createRegistryKey("hephaestus_forge/ritual");
    public static final ResourceKey<Registry<RitualResultType<?>>> RITUAL_RESULT_TYPE = FARegistries.createRegistryKey("ritual_result");
    public static final ResourceKey<Registry<HephaestusForgeInput>> FORGE_INPUT = FARegistries.createRegistryKey("hephaestus_forge/input");
    public static final ResourceKey<Registry<ItemModifier>> ITEM_MODIFIER = FARegistries.createRegistryKey("item_modifier");
    public static final ResourceKey<Registry<EnhancerEffectType<?>>> ENHANCER_EFFECT = FARegistries.createRegistryKey("enhancer/effect");
    public static final ResourceKey<Registry<EffectConditionType<?>>> ENHANCER_EFFECT_CONDITION = FARegistries.createRegistryKey("enhancer/effect_condition");
    public static final ResourceKey<Registry<EnhancerDefinition>> ENHANCER_DEFINITION = FARegistries.createRegistryKey("enhancer/definition");
    public static final ResourceKey<Registry<DarkTraderVariant>> DARK_TRADER_VARIANT = FARegistries.createRegistryKey("dark_trader_variant");
    public static final ResourceKey<Registry<MundabiturInteraction<?>>> MUNDABITUR_INTERACTION = FARegistries.createRegistryKey("mundabitur_interaction");
    public static final ResourceKey<Registry<Knowledge>> KNOWLEDGE = FARegistries.createRegistryKey("research/knowledge");
    public static final ResourceKey<Registry<Constellation>> CONSTELLATION = FARegistries.createRegistryKey("research/constellation");
    public static final ResourceKey<Registry<ResidueType>> RESIDUE_TYPE = FARegistries.createRegistryKey("residue_type");
    public static final ResourceKey<Registry<PedestalEffect>> PEDESTAL_EFFECT = FARegistries.createRegistryKey("pedestal_effect");
    public static final ResourceKey<Registry<MagicCircleType>> MAGIC_CIRCLE = FARegistries.createRegistryKey("magic_circle");

    public static final Registry<RitualResultType<?>> RITUAL_RESULT_TYPE_REGISTRY = FARegistries.makeSyncedRegistry(RITUAL_RESULT_TYPE);
    public static final Registry<HephaestusForgeInput> FORGE_INPUT_REGISTRY = FARegistries.makeSyncedRegistry(FORGE_INPUT);
    public static final Registry<ItemModifier> ITEM_MODIFIER_REGISTRY = FARegistries.makeSyncedRegistry(ITEM_MODIFIER);
    public static final Registry<EnhancerEffectType<?>> ENHANCER_EFFECT_REGISTRY = FARegistries.makeRegistry(ENHANCER_EFFECT);
    public static final Registry<EffectConditionType<?>> ENHANCER_EFFECT_CONDITION_REGISTRY = FARegistries.makeRegistry(ENHANCER_EFFECT_CONDITION);
    public static final Registry<DarkTraderVariant> DARK_TRADER_VARIANT_REGISTRY = FARegistries.makeSyncedRegistry(DARK_TRADER_VARIANT);
    public static final Registry<MundabiturInteraction<?>> MUNDABITUR_INTERACTION_REGISTRY = FARegistries.makeSyncedRegistry(MUNDABITUR_INTERACTION);
    public static final Registry<PedestalEffect> PEDESTAL_EFFECT_REGISTRY = FARegistries.makeRegistry(PEDESTAL_EFFECT);

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(ForbiddenArcanus.location(name));
    }

    /**
     * Creates a {@link Registry} that get synchronised to clients.
     *
     * @param <T> the entry of the registry.
     */
    private static <T> Registry<T> makeSyncedRegistry(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey).sync(true).create();
    }

    /**
     * Creates a simple {@link Registry} that <B>won't</B> be synced to clients.
     *
     * @param <T> the entry of the registry.
     */
    private static <T> Registry<T> makeRegistry(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey).create();
    }
}
