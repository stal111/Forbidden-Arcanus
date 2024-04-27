package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.common.advancements.critereon.ToolActionItemPredicate;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Predicate;

/**
 * Mod Item Modifiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModItemModifiers
 *
 * @author stal111
 * @since 2021-11-24
 */
public class ModItemModifiers implements RegistryClass {

    public static final MappedRegistryHelper<ItemModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ITEM_MODIFIER);

    private static final Predicate<ItemStack> TOOL_PREDICATE =
            ItemPredicate.Builder.item().withSubPredicate(ToolActionItemPredicate.TYPE, new ToolActionItemPredicate(ToolActions.PICKAXE_DIG)).build().or(
            ItemPredicate.Builder.item().withSubPredicate(ToolActionItemPredicate.TYPE, new ToolActionItemPredicate(ToolActions.AXE_DIG)).build().or(
            ItemPredicate.Builder.item().withSubPredicate(ToolActionItemPredicate.TYPE, new ToolActionItemPredicate(ToolActions.SHOVEL_DIG)).build().or(
            ItemPredicate.Builder.item().withSubPredicate(ToolActionItemPredicate.TYPE, new ToolActionItemPredicate(ToolActions.HOE_DIG)).build())));

    public static final RegistryEntry<ItemModifier, EternalModifier> ETERNAL = HELPER.register("eternal", () -> new EternalModifier(ItemStack::isDamageableItem, ModTags.Items.ETERNAL_INCOMPATIBLE, ModTags.Enchantments.ETERNAL_INCOMPATIBLE, FastColor.ARGB32.color(255, 170, 181, 159), FastColor.ARGB32.color(255, 49, 57, 56)));
    public static final RegistryEntry<ItemModifier, ItemModifier> FIERY = HELPER.register("fiery", () -> new ItemModifier(TOOL_PREDICATE, ModTags.Items.FIERY_INCOMPATIBLE, ModTags.Enchantments.FIERY_INCOMPATIBLE, FastColor.ARGB32.color(255, 255, 143, 0), FastColor.ARGB32.color(255, 88, 6, 6)));
    public static final RegistryEntry<ItemModifier, ItemModifier> MAGNETIZED = HELPER.register("magnetized", () -> new ItemModifier(ItemPredicate.Builder.item().of(ItemTags.FOOT_ARMOR).build(), ModTags.Items.MAGNETIZED_INCOMPATIBLE, ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE, FastColor.ARGB32.color(255, 200, 201, 215), FastColor.ARGB32.color(255, 87, 105, 99)));
    public static final RegistryEntry<ItemModifier, ItemModifier> DEMOLISHING = HELPER.register("demolishing", () -> new ItemModifier(TOOL_PREDICATE, ModTags.Items.DEMOLISHING_INCOMPATIBLE, ModTags.Enchantments.DEMOLISHING_INCOMPATIBLE, FastColor.ARGB32.color(255, 111, 84, 80), FastColor.ARGB32.color(255, 78, 58, 39)));
    public static final RegistryEntry<ItemModifier, ItemModifier> AQUATIC = HELPER.register("aquatic", () -> new ItemModifier(ItemPredicate.Builder.item().of(ItemTags.HEAD_ARMOR).build(), ModTags.Items.AQUATIC_INCOMPATIBLE, ModTags.Enchantments.AQUATIC_INCOMPATIBLE, FastColor.ARGB32.color(255, 90, 130, 243), FastColor.ARGB32.color(255, 35, 79, 204)));

}
