package com.stal111.forbidden_arcanus.common.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInBiomeModifiers {

    public static final ResourceKeyHelper<BiomeModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(NeoForgeRegistries.Keys.BIOME_MODIFIERS);

    public static final ResourceKey<BiomeModifier> ADD_ARCANE_CRYSTAL_ORE = HELPER.createKey("add_arcane_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_RUNIC_STONE = HELPER.createKey("add_runic_stone");
    public static final ResourceKey<BiomeModifier> ADD_DARKSTONE = HELPER.createKey("add_darkstone");
    public static final ResourceKey<BiomeModifier> ADD_STELLA_ARCANUM = HELPER.createKey("add_stella_arcanum");

    public static final ResourceKey<BiomeModifier> ADD_AURUM_TREES = HELPER.createKey("add_aurum_trees");
    public static final ResourceKey<BiomeModifier> ADD_YELLOW_ORCHIDS = HELPER.createKey("add_yellow_orchids");

    public static final ResourceKey<BiomeModifier> ADD_LOST_SOUL_OVERWORLD = HELPER.createKey("add_lost_soul_overworld");
}
