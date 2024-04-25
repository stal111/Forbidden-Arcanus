package com.stal111.forbidden_arcanus.data.residue;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

    public ModResidueTypes(BootstrapContext<ResidueType> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<ResidueType> context) {
        context.register(ARCANE_CRYSTAL, ResidueType.withDefaultKey("arcane_crystal", 9, new ItemStack(ModBlocks.ARCANE_CRYSTAL_BLOCK.get())));
        context.register(RUNE, ResidueType.withDefaultKey("rune", 9, new ItemStack(ModBlocks.RUNE_BLOCK.get())));
        context.register(COAL, ResidueType.withDefaultKey("coal", 9, Items.COAL_BLOCK.getDefaultInstance()));
        context.register(IRON, ResidueType.withDefaultKey("iron", 9, Items.IRON_BLOCK.getDefaultInstance()));
        context.register(GOLD, ResidueType.withDefaultKey("gold", 9, Items.GOLD_BLOCK.getDefaultInstance()));
        context.register(COPPER, ResidueType.withDefaultKey("copper", 9, Items.COPPER_BLOCK.getDefaultInstance()));
        context.register(LAPIS_LAZULI, ResidueType.withDefaultKey("lapis_lazuli", 9, Items.LAPIS_BLOCK.getDefaultInstance()));
        context.register(DIAMOND, ResidueType.withDefaultKey("diamond", 9, Items.DIAMOND_BLOCK.getDefaultInstance()));
        context.register(EMERALD, ResidueType.withDefaultKey("emerald", 9, Items.EMERALD_BLOCK.getDefaultInstance()));
        context.register(NETHERITE, ResidueType.withDefaultKey("netherite", 9, Items.NETHERITE_BLOCK.getDefaultInstance()));
        context.register(DEORUM, ResidueType.withDefaultKey("deorum", 1, new ItemStack(ModItems.DEORUM_NUGGET.get(), 2)));
    }
}
