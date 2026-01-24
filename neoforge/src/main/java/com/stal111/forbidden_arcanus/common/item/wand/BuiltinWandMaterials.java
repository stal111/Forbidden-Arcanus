package com.stal111.forbidden_arcanus.common.item.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltinWandMaterials {

    public static final ResourceKeyHelper<WandMaterial> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.WAND_MATERIAL);

    //Pommel
    public static final ResourceKey<WandMaterial> AMETHYST = HELPER.createKey("amethyst");
    public static final ResourceKey<WandMaterial> ARCANE_CRYSTAL = HELPER.createKey("arcane_crystal");
    public static final ResourceKey<WandMaterial> CORRUPTED_ARCANE_CRYSTAL = HELPER.createKey("corrupted_arcane_crystal");
    public static final ResourceKey<WandMaterial> DIAMOND = HELPER.createKey("diamond");
    public static final ResourceKey<WandMaterial> ECHO_SHARD = HELPER.createKey("echo_shard");
    public static final ResourceKey<WandMaterial> EMERALD = HELPER.createKey("emerald");
    public static final ResourceKey<WandMaterial> ENDER_PEARL = HELPER.createKey("ender_pearl");
    public static final ResourceKey<WandMaterial> LAPIS_LAZULI = HELPER.createKey("lapis_lazuli");
    public static final ResourceKey<WandMaterial> NETHER_STAR = HELPER.createKey("nether_star");
    public static final ResourceKey<WandMaterial> PRISMARINE = HELPER.createKey("prismarine");
    public static final ResourceKey<WandMaterial> QUARTZ = HELPER.createKey("quartz");
    public static final ResourceKey<WandMaterial> RESIN = HELPER.createKey("resin");
    public static final ResourceKey<WandMaterial> RUNE = HELPER.createKey("rune");

    //Transition
    public static final ResourceKey<WandMaterial> ALCHEMICAL_SILVER = HELPER.createKey("alchemical_silver");
    public static final ResourceKey<WandMaterial> COPPER = HELPER.createKey("copper");
    public static final ResourceKey<WandMaterial> DEORUM = HELPER.createKey("deorum");
    public static final ResourceKey<WandMaterial> GOLD = HELPER.createKey("gold");
    public static final ResourceKey<WandMaterial> IRON = HELPER.createKey("iron");
    public static final ResourceKey<WandMaterial> NETHERITE = HELPER.createKey("netherite");
    public static final ResourceKey<WandMaterial> OBSIDIANSTEEL = HELPER.createKey("obsidiansteel");
    public static final ResourceKey<WandMaterial> STELLARITE = HELPER.createKey("stellarite");
}
