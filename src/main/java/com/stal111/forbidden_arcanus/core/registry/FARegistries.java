package com.stal111.forbidden_arcanus.core.registry;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInputType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResultType;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTraderVariant;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerEffectType;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectConditionType;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturInteraction;
import com.stal111.forbidden_arcanus.common.research.Constellation;
import com.stal111.forbidden_arcanus.common.research.Knowledge;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2023-02-18
 */
public class FARegistries {

    public static final ResourceKey<Registry<Ritual>> RITUAL = FARegistries.createRegistryKey("hephaestus_forge/ritual");
    public static final ResourceKey<Registry<RitualResultType<?>>> RITUAL_RESULT_TYPE = FARegistries.createRegistryKey("ritual_result");
    public static final ResourceKey<Registry<HephaestusForgeInputType<?>>> FORGE_INPUT_TYPE = FARegistries.createRegistryKey("hephaestus_forge/input_type");
    public static final ResourceKey<Registry<HephaestusForgeInput>> FORGE_INPUT = FARegistries.createRegistryKey("hephaestus_forge/input");
    public static final ResourceKey<Registry<ItemModifier>> ITEM_MODIFIER = FARegistries.createRegistryKey("item_modifier");
    public static final ResourceKey<Registry<EnhancerEffectType<?>>> ENHANCER_EFFECT = FARegistries.createRegistryKey("enhancer/effect");
    public static final ResourceKey<Registry<EffectConditionType<?>>> ENHANCER_EFFECT_CONDITION = FARegistries.createRegistryKey("enhancer/effect_condition");
    public static final ResourceKey<Registry<EnhancerDefinition>> ENHANCER_DEFINITION = FARegistries.createRegistryKey("enhancer/definition");
    public static final ResourceKey<Registry<DarkTraderVariant>> DARK_TRADER_VARIANT = FARegistries.createRegistryKey("dark_trader_variant");
    public static final ResourceKey<Registry<MundabiturInteraction<?>>> MUNDABITUR_INTERACTION = FARegistries.createRegistryKey("mundabitur_interaction");
    public static final ResourceKey<Registry<Knowledge>> KNOWLEDGE = FARegistries.createRegistryKey("research/knowledge");
    public static final ResourceKey<Registry<Constellation>> CONSTELLATION = FARegistries.createRegistryKey("research/constellation");

    private static final DeferredRegister<RitualResultType<?>> RITUAL_RESULT_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.RITUAL_RESULT_TYPE, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<HephaestusForgeInputType<?>> FORGE_INPUT_TYPE_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.FORGE_INPUT_TYPE, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<ItemModifier> ITEM_MODIFIER_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.ITEM_MODIFIER, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<EnhancerEffectType<?>> ENHANCER_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.ENHANCER_EFFECT, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<EffectConditionType<?>> ENHANCER_EFFECT_CONDITION_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.ENHANCER_EFFECT_CONDITION, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<DarkTraderVariant> DARK_TRADER_VARIANT_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.DARK_TRADER_VARIANT, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<MundabiturInteraction<?>> MUNDABITUR_INTERACTION_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.MUNDABITUR_INTERACTION, ForbiddenArcanus.MOD_ID);

    public static final Supplier<IForgeRegistry<RitualResultType<?>>> RITUAL_RESULT_TYPE_REGISTRY = FARegistries.makeSyncedRegistry(RITUAL_RESULT_DEFERRED_REGISTER);
    public static final Supplier<IForgeRegistry<HephaestusForgeInputType<?>>> FORGE_INPUT_TYPE_REGISTRY = FARegistries.makeSyncedRegistry(FORGE_INPUT_TYPE_DEFERRED_REGISTER);
    public static final Supplier<IForgeRegistry<ItemModifier>> ITEM_MODIFIER_REGISTRY = FARegistries.makeSyncedRegistry(ITEM_MODIFIER_DEFERRED_REGISTER);
    public static final Supplier<IForgeRegistry<EnhancerEffectType<?>>> ENHANCER_EFFECT_REGISTRY = FARegistries.makeRegistry(ENHANCER_EFFECT_DEFERRED_REGISTER);
    public static final Supplier<IForgeRegistry<EffectConditionType<?>>> ENHANCER_EFFECT_CONDITION_REGISTRY = FARegistries.makeRegistry(ENHANCER_EFFECT_CONDITION_DEFERRED_REGISTER);
    public static final Supplier<IForgeRegistry<DarkTraderVariant>> DARK_TRADER_VARIANT_REGISTRY = FARegistries.makeSyncedRegistry(DARK_TRADER_VARIANT_DEFERRED_REGISTER);
    public static final Supplier<IForgeRegistry<MundabiturInteraction<?>>> MUNDABITUR_INTERACTION_REGISTRY = FARegistries.makeSyncedRegistry(MUNDABITUR_INTERACTION_DEFERRED_REGISTER);

    public static void register(IEventBus modEventBus) {
        FARegistries.RITUAL_RESULT_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.ITEM_MODIFIER_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.ENHANCER_EFFECT_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.ENHANCER_EFFECT_CONDITION_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.FORGE_INPUT_TYPE_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.DARK_TRADER_VARIANT_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.MUNDABITUR_INTERACTION_DEFERRED_REGISTER.register(modEventBus);
    }

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, name));
    }

    /**
     * Creates a {@link IForgeRegistry} that get synchronised to clients.
     *
     * @param <T> the entry of the registry.
     */
    private static <T> Supplier<IForgeRegistry<T>> makeSyncedRegistry(DeferredRegister<T> deferredRegister) {
        return deferredRegister.makeRegistry(() -> new RegistryBuilder<T>().disableSaving());
    }

    /**
     * Creates a simple {@link IForgeRegistry} that <B>won't</B> be synced to clients.
     *
     * @param <T> the entry of the registry.
     */
    private static <T> Supplier<IForgeRegistry<T>> makeRegistry(DeferredRegister<T> deferredRegister) {
        return deferredRegister.makeRegistry(() -> new RegistryBuilder<T>().disableSaving().disableSync());
    }
}
