package com.stal111.forbidden_arcanus.common.block.entity.clibano.residue;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInResidueTypes {

    public static final ResourceKeyHelper<ResidueType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.RESIDUE_TYPE);

    public static final ResourceKey<ResidueType> ARCANE_CRYSTAL = HELPER.createKey("arcane_crystal");
    public static final ResourceKey<ResidueType> RUNE = HELPER.createKey("rune");
    public static final ResourceKey<ResidueType> COAL = HELPER.createKey("coal");
    public static final ResourceKey<ResidueType> IRON = HELPER.createKey("iron");
    public static final ResourceKey<ResidueType> GOLD = HELPER.createKey("gold");
    public static final ResourceKey<ResidueType> COPPER = HELPER.createKey("copper");
    public static final ResourceKey<ResidueType> LAPIS_LAZULI = HELPER.createKey("lapis_lazuli");
    public static final ResourceKey<ResidueType> DIAMOND = HELPER.createKey("diamond");
    public static final ResourceKey<ResidueType> EMERALD = HELPER.createKey("emerald");
    public static final ResourceKey<ResidueType> NETHERITE = HELPER.createKey("netherite");
    public static final ResourceKey<ResidueType> DEORUM = HELPER.createKey("deorum");
}
