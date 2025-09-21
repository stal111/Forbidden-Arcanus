package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInRituals {

    public static final ResourceKeyHelper<Ritual> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.RITUAL);

    public static final ResourceKey<Ritual> ETERNAL_STELLA = HELPER.createKey("eternal_stella");
    public static final ResourceKey<Ritual> TERRASTOMP_PRISM = HELPER.createKey("terrastomp_prism");
    public static final ResourceKey<Ritual> SEA_PRISM = HELPER.createKey("sea_prism");
    public static final ResourceKey<Ritual> WHIRLWIND_PRISM = HELPER.createKey("whirlwind_prism");
    public static final ResourceKey<Ritual> SMELTER_PRISM = HELPER.createKey("smelter_prism");
    public static final ResourceKey<Ritual> FERROGNETIC_MIXTURE = HELPER.createKey("ferrognetic_mixture");
    public static final ResourceKey<Ritual> QUANTUM_CATCHER = HELPER.createKey("quantum_catcher");
    public static final ResourceKey<Ritual> BOSS_CATCHER = HELPER.createKey("boss_catcher");
    public static final ResourceKey<Ritual> QUANTUM_INJECTOR = HELPER.createKey("quantum_injector");
    public static final ResourceKey<Ritual> SOUL_BINDING_CRYSTAL = HELPER.createKey("soul_binding_crystal");

    public static final ResourceKey<Ritual> DRACO_ARCANUS_HELMET = HELPER.createKey("draco_arcanus_helmet");
    public static final ResourceKey<Ritual> DRACO_ARCANUS_CHESTPLATE = HELPER.createKey("draco_arcanus_chestplate");
    public static final ResourceKey<Ritual> DRACO_ARCANUS_LEGGINGS = HELPER.createKey("draco_arcanus_leggings");
    public static final ResourceKey<Ritual> DRACO_ARCANUS_BOOTS = HELPER.createKey("draco_arcanus_boots");

    public static final ResourceKey<Ritual> TYR_HELMET = HELPER.createKey("tyr_helmet");
    public static final ResourceKey<Ritual> TYR_CHESTPLATE = HELPER.createKey("tyr_chestplate");
    public static final ResourceKey<Ritual> TYR_LEGGINGS = HELPER.createKey("tyr_leggings");
    public static final ResourceKey<Ritual> TYR_BOOTS = HELPER.createKey("tyr_boots");

    public static final ResourceKey<Ritual> UPGRADE_TIER_2 = HELPER.createKey("upgrade_tier_2");
    public static final ResourceKey<Ritual> UPGRADE_TIER_3 = HELPER.createKey("upgrade_tier_3");
    public static final ResourceKey<Ritual> UPGRADE_TIER_4 = HELPER.createKey("upgrade_tier_4");
    public static final ResourceKey<Ritual> UPGRADE_TIER_5 = HELPER.createKey("upgrade_tier_5");
}
