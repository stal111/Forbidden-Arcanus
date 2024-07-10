package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.advancements.critereon.ItemAbilityPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemAllOfPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemAnyOfPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemHasComponentPredicate;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.ValhelsiaItemSubPredicates;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Mod Item Modifiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModItemModifiers
 *
 * @author stal111
 * @since 2021-11-24
 */
public class ModItemModifiers implements RegistryClass {

    public static final MappedRegistryHelper<ItemModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ITEM_MODIFIER);

    private static final Supplier<ItemPredicate> TOOL_PREDICATE = () -> ItemPredicate.Builder.item().withSubPredicate(ValhelsiaItemSubPredicates.ANY_OF.get(), new ItemAnyOfPredicate(
            Map.entry(ItemAbilityPredicate.TYPE, new ItemAbilityPredicate(ItemAbilities.PICKAXE_DIG)),
            Map.entry(ItemAbilityPredicate.TYPE, new ItemAbilityPredicate(ItemAbilities.AXE_DIG)),
            Map.entry(ItemAbilityPredicate.TYPE, new ItemAbilityPredicate(ItemAbilities.SHOVEL_DIG)),
            Map.entry(ItemAbilityPredicate.TYPE, new ItemAbilityPredicate(ItemAbilities.HOE_DIG))
    )).build();

    public static final RegistryEntry<ItemModifier, EternalModifier> ETERNAL = HELPER.register("eternal", () -> new EternalModifier(ItemPredicate.Builder.item().withSubPredicate(ValhelsiaItemSubPredicates.ALL_OF.get(), new ItemAllOfPredicate(Map.entry(ValhelsiaItemSubPredicates.HAS_COMPONENT.get(), new ItemHasComponentPredicate(DataComponents.MAX_DAMAGE)), Map.entry(ValhelsiaItemSubPredicates.HAS_COMPONENT.get(), new ItemHasComponentPredicate(DataComponents.DAMAGE)))).build(), ModTags.Items.ETERNAL_INCOMPATIBLE, ModTags.Enchantments.ETERNAL_INCOMPATIBLE, FastColor.ARGB32.color(255, 170, 181, 159), FastColor.ARGB32.color(255, 49, 57, 56)));
    public static final RegistryEntry<ItemModifier, ItemModifier> FIERY = HELPER.register("fiery", () -> new ItemModifier(TOOL_PREDICATE.get(), BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.FIERY_INCOMPATIBLE), HolderSet.empty(), FastColor.ARGB32.color(255, 255, 143, 0), FastColor.ARGB32.color(255, 88, 6, 6)));
    public static final RegistryEntry<ItemModifier, ItemModifier> MAGNETIZED = HELPER.register("magnetized", () -> new ItemModifier(ItemPredicate.Builder.item().of(ItemTags.FOOT_ARMOR).build(), BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.MAGNETIZED_INCOMPATIBLE), HolderSet.empty(), FastColor.ARGB32.color(255, 200, 201, 215), FastColor.ARGB32.color(255, 87, 105, 99)));
    public static final RegistryEntry<ItemModifier, ItemModifier> DEMOLISHING = HELPER.register("demolishing", () -> new ItemModifier(TOOL_PREDICATE.get(), BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.DEMOLISHING_INCOMPATIBLE), HolderSet.empty(), FastColor.ARGB32.color(255, 111, 84, 80), FastColor.ARGB32.color(255, 78, 58, 39)));
    public static final RegistryEntry<ItemModifier, ItemModifier> AQUATIC = HELPER.register("aquatic", () -> new ItemModifier(ItemPredicate.Builder.item().of(ItemTags.HEAD_ARMOR).build(), BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.AQUATIC_INCOMPATIBLE), HolderSet.empty(), FastColor.ARGB32.color(255, 90, 130, 243), FastColor.ARGB32.color(255, 35, 79, 204)));
    public static final RegistryEntry<ItemModifier, ItemModifier> SOULBOUND = HELPER.register("soulbound", () -> new ItemModifier(ItemPredicate.Builder.item().build(), BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.SOULBOUND_INCOMPATIBLE), HolderSet.empty(), FastColor.ARGB32.color(255, 166, 185, 246), FastColor.ARGB32.color(255, 247, 184, 217)));

}
