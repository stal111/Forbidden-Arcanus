package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltinMoltenMaterialTypes {

    public static final ResourceKeyHelper<MoltenMaterialType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.MOLTEN_MATERIAL_TYPE);

    public static final ResourceKey<MoltenMaterialType> ARCANE_CRYSTAL = HELPER.createKey("arcane_crystal");
    public static final ResourceKey<MoltenMaterialType> RUNE = HELPER.createKey("rune");
    public static final ResourceKey<MoltenMaterialType> COAL = HELPER.createKey("coal");
    public static final ResourceKey<MoltenMaterialType> IRON = HELPER.createKey("iron");
    public static final ResourceKey<MoltenMaterialType> GOLD = HELPER.createKey("gold");
    public static final ResourceKey<MoltenMaterialType> COPPER = HELPER.createKey("copper");
    public static final ResourceKey<MoltenMaterialType> LAPIS_LAZULI = HELPER.createKey("lapis_lazuli");
    public static final ResourceKey<MoltenMaterialType> DIAMOND = HELPER.createKey("diamond");
    public static final ResourceKey<MoltenMaterialType> EMERALD = HELPER.createKey("emerald");
    public static final ResourceKey<MoltenMaterialType> NETHERITE = HELPER.createKey("netherite");
    public static final ResourceKey<MoltenMaterialType> ASTERITE = HELPER.createKey("asterite");
}
