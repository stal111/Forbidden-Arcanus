package com.stal111.forbidden_arcanus.data.residue;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * @author stal111
 * @since 05.02.2024
 */
public class ModResidueTypes extends DatapackRegistryClass<ResidueType> {

    public static final DatapackRegistryHelper<ResidueType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.RESIDUE_TYPE);

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

    public ModResidueTypes(BootstapContext<ResidueType> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<ResidueType> context) {
        context.register(ARCANE_CRYSTAL, ResidueType.withDefaultKey("arcane_crystal"));
        context.register(RUNE, ResidueType.withDefaultKey("rune"));
        context.register(COAL, ResidueType.withDefaultKey("coal"));
        context.register(IRON, ResidueType.withDefaultKey("iron"));
        context.register(GOLD, ResidueType.withDefaultKey("gold"));
        context.register(COPPER, ResidueType.withDefaultKey("copper"));
        context.register(LAPIS_LAZULI, ResidueType.withDefaultKey("lapis_lazuli"));
        context.register(DIAMOND, ResidueType.withDefaultKey("diamond"));
        context.register(EMERALD, ResidueType.withDefaultKey("emerald"));
        context.register(NETHERITE, ResidueType.withDefaultKey("netherite"));
        context.register(DEORUM, ResidueType.withDefaultKey("deorum"));
    }
}
