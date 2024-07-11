package com.stal111.forbidden_arcanus.data;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.advancements.critereon.ItemAbilityPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemAllOfPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemAnyOfPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemHasComponentPredicate;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.ValhelsiaItemSubPredicates;

import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 10.07.2024
 */
public class ModItemModifiers extends DatapackRegistryClass<ItemModifier> {

    public static final DatapackRegistryHelper<ItemModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ITEM_MODIFIER);

    public static final ResourceKey<ItemModifier> ETERNAL = HELPER.createKey("eternal");
    public static final ResourceKey<ItemModifier> FIERY = HELPER.createKey("fiery");
    public static final ResourceKey<ItemModifier> MAGNETIZED = HELPER.createKey("magnetized");
    public static final ResourceKey<ItemModifier> DEMOLISHING = HELPER.createKey("demolishing");
    public static final ResourceKey<ItemModifier> AQUATIC = HELPER.createKey("aquatic");
    public static final ResourceKey<ItemModifier> SOULBOUND = HELPER.createKey("soulbound");

    private HolderGetter<Enchantment> enchantmentGetter;

    public ModItemModifiers(BootstrapContext<ItemModifier> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<ItemModifier> context) {
        this.enchantmentGetter = context.lookup(Registries.ENCHANTMENT);

        var eternalPredicate = ItemPredicate.Builder.item().withSubPredicate(ValhelsiaItemSubPredicates.ALL_OF.get(), new ItemAllOfPredicate(Map.of(ValhelsiaItemSubPredicates.HAS_COMPONENT.get(), List.of(new ItemHasComponentPredicate(DataComponents.MAX_DAMAGE), new ItemHasComponentPredicate(DataComponents.DAMAGE))))).build();
        var isToolPredicate = ItemPredicate.Builder.item().withSubPredicate(ValhelsiaItemSubPredicates.ANY_OF.get(), new ItemAnyOfPredicate(Map.of(ItemAbilityPredicate.TYPE, List.of(new ItemAbilityPredicate(ItemAbilities.PICKAXE_DIG), new ItemAbilityPredicate(ItemAbilities.AXE_DIG), new ItemAbilityPredicate(ItemAbilities.SHOVEL_DIG), new ItemAbilityPredicate(ItemAbilities.HOE_DIG))))).build();
        var magnetizedPredicate = ItemPredicate.Builder.item().of(ItemTags.FOOT_ARMOR).build();
        var aquaticPredicate = ItemPredicate.Builder.item().of(ItemTags.HEAD_ARMOR).build();
        var soulBoundPredicate = ItemPredicate.Builder.item().build();

        register(ETERNAL, eternalPredicate, ModTags.Items.ETERNAL_INCOMPATIBLE, ModTags.Enchantments.ETERNAL_INCOMPATIBLE, createDisplay(ETERNAL, FastColor.ARGB32.color(255, 170, 181, 159), FastColor.ARGB32.color(255, 49, 57, 56)));
        register(FIERY, isToolPredicate, ModTags.Items.FIERY_INCOMPATIBLE, ModTags.Enchantments.FIERY_INCOMPATIBLE, createDisplay(FIERY, FastColor.ARGB32.color(255, 255, 143, 0), FastColor.ARGB32.color(255, 88, 6, 6)));
        register(MAGNETIZED, magnetizedPredicate, ModTags.Items.MAGNETIZED_INCOMPATIBLE, ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE, createDisplay(MAGNETIZED, FastColor.ARGB32.color(255, 200, 201, 215), FastColor.ARGB32.color(255, 87, 105, 99)));
        register(DEMOLISHING, isToolPredicate, ModTags.Items.DEMOLISHING_INCOMPATIBLE, ModTags.Enchantments.DEMOLISHING_INCOMPATIBLE, createDisplay(DEMOLISHING, FastColor.ARGB32.color(255, 111, 84, 80), FastColor.ARGB32.color(255, 78, 58, 39)));
        register(AQUATIC, aquaticPredicate, ModTags.Items.AQUATIC_INCOMPATIBLE, ModTags.Enchantments.AQUATIC_INCOMPATIBLE, createDisplay(AQUATIC, FastColor.ARGB32.color(255, 90, 130, 243), FastColor.ARGB32.color(255, 35, 79, 204)));
        register(SOULBOUND, soulBoundPredicate, ModTags.Items.SOULBOUND_INCOMPATIBLE, ModTags.Enchantments.SOULBOUND_INCOMPATIBLE, createDisplay(SOULBOUND, FastColor.ARGB32.color(255, 166, 185, 246), FastColor.ARGB32.color(255, 247, 184, 217)));
    }

    private void register(ResourceKey<ItemModifier> key, ItemPredicate predicate, TagKey<Item> incompatibleItems, TagKey<Enchantment> incompatibleEnchantments, ItemModifier.DisplaySettings displaySettings) {
        this.getContext().register(key, new ItemModifier(predicate, BuiltInRegistries.ITEM.getOrCreateTag(incompatibleItems), this.enchantmentGetter.getOrThrow(incompatibleEnchantments), displaySettings));
    }

    private static ItemModifier.DisplaySettings createDisplay(ResourceKey<ItemModifier> key, int startColor, int endColor) {
        return new ItemModifier.DisplaySettings(Component.translatable(Util.makeDescriptionId("modifier", key.location())), key.location().withPrefix("textures/gui/tooltip/").withSuffix(".png"), Pair.of(startColor, endColor));
    }
}
