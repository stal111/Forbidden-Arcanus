package com.stal111.forbidden_arcanus.datagen.item

import com.mojang.datafixers.util.Pair
import com.stal111.forbidden_arcanus.common.item.modifier.BuiltInItemModifiers
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier.DisplaySettings
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.Util
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderSet
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.util.ARGB
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantment
import net.valhelsia.dataforge.RegistryDataProvider

object ModItemModifiers : RegistryDataProvider<ItemModifier> {
    private var context: BootstrapContext<ItemModifier>? = null

    private var enchantmentGetter: HolderGetter<Enchantment>? = null
    private var itemsGetter: HolderGetter<Item>? = null

    override fun bootstrap(context: BootstrapContext<ItemModifier>) {
        this.context = context
        this.itemsGetter = context.lookup<Item>(Registries.ITEM)
        this.enchantmentGetter = context.lookup<Enchantment>(Registries.ENCHANTMENT)

        //TODO
        val eternalPredicate = ItemPredicate.Builder.item().build()
//        val eternalPredicate = ItemPredicate.Builder.item().withSubPredicate(
//            ValhelsiaItemSubPredicates.ALL_OF.get(), ItemAllOfPredicate(
//                mapOf<ItemSubPredicate.Type<*>, List<ItemSubPredicate>>(
//                    ValhelsiaItemSubPredicates.HAS_COMPONENT.get() to listOf(
//                        ItemHasComponentPredicate(DataComponents.MAX_DAMAGE),
//                        ItemHasComponentPredicate(DataComponents.DAMAGE)
//                    )
//                )
//            )
//        ).build()

        val isToolPredicate = ItemPredicate.Builder.item().build()
//        val isToolPredicate = ItemPredicate.Builder.item().withSubPredicate<ItemAnyOfPredicate>(
//            ValhelsiaItemSubPredicates.ANY_OF.get(), ItemAnyOfPredicate(
//                mapOf<ItemSubPredicate.Type<*>, List<ItemSubPredicate>>(
//                    ItemAbilityPredicate.TYPE to listOf(
//                        ItemAbilityPredicate(ItemAbilities.PICKAXE_DIG),
//                        ItemAbilityPredicate(ItemAbilities.AXE_DIG),
//                        ItemAbilityPredicate(ItemAbilities.SHOVEL_DIG),
//                        ItemAbilityPredicate(ItemAbilities.HOE_DIG)
//                    )
//                )
//            )
//        ).build()
        val magnetizedPredicate = ItemPredicate.Builder.item().of(itemsGetter!!, ItemTags.FOOT_ARMOR).build()
        val aquaticPredicate = ItemPredicate.Builder.item().of(itemsGetter!!, ItemTags.HEAD_ARMOR).build()
        val soulBoundPredicate =
            ItemPredicate.Builder.item().of(itemsGetter!!, ModTags.Items.SOULBOUND_APPLICABLE).build()

        register(
            BuiltInItemModifiers.ETERNAL,
            eternalPredicate,
            ModTags.Items.ETERNAL_INCOMPATIBLE,
            ModTags.Enchantments.ETERNAL_INCOMPATIBLE,
            HolderSet.direct<DataComponentType<*>>(
                BuiltInRegistries.DATA_COMPONENT_TYPE.wrapAsHolder(DataComponents.DAMAGE),
                BuiltInRegistries.DATA_COMPONENT_TYPE.wrapAsHolder(
                    DataComponents.MAX_DAMAGE
                )
            ),
            createDisplay(BuiltInItemModifiers.ETERNAL, ARGB.color(255, 170, 181, 159), ARGB.color(255, 49, 57, 56))
        )
        register(
            BuiltInItemModifiers.FIERY,
            isToolPredicate,
            ModTags.Items.FIERY_INCOMPATIBLE,
            ModTags.Enchantments.FIERY_INCOMPATIBLE,
            createDisplay(BuiltInItemModifiers.FIERY, ARGB.color(255, 255, 143, 0), ARGB.color(255, 88, 6, 6))
        )
        register(
            BuiltInItemModifiers.MAGNETIZED,
            magnetizedPredicate,
            ModTags.Items.MAGNETIZED_INCOMPATIBLE,
            ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE,
            createDisplay(BuiltInItemModifiers.MAGNETIZED, ARGB.color(255, 200, 201, 215), ARGB.color(255, 87, 105, 99))
        )
        register(
            BuiltInItemModifiers.DEMOLISHING,
            isToolPredicate,
            ModTags.Items.DEMOLISHING_INCOMPATIBLE,
            ModTags.Enchantments.DEMOLISHING_INCOMPATIBLE,
            createDisplay(BuiltInItemModifiers.DEMOLISHING, ARGB.color(255, 111, 84, 80), ARGB.color(255, 78, 58, 39))
        )
        register(
            BuiltInItemModifiers.AQUATIC,
            aquaticPredicate,
            ModTags.Items.AQUATIC_INCOMPATIBLE,
            ModTags.Enchantments.AQUATIC_INCOMPATIBLE,
            createDisplay(BuiltInItemModifiers.AQUATIC, ARGB.color(255, 90, 130, 243), ARGB.color(255, 35, 79, 204))
        )
        register(
            BuiltInItemModifiers.SOULBOUND,
            soulBoundPredicate,
            ModTags.Items.SOULBOUND_INCOMPATIBLE,
            ModTags.Enchantments.SOULBOUND_INCOMPATIBLE,
            createDisplay(
                BuiltInItemModifiers.SOULBOUND,
                ARGB.color(255, 166, 185, 246),
                ARGB.color(255, 247, 184, 217)
            )
        )
    }

    private fun register(
        key: ResourceKey<ItemModifier>,
        predicate: ItemPredicate,
        incompatibleItems: TagKey<Item>,
        incompatibleEnchantments: TagKey<Enchantment>,
        displaySettings: DisplaySettings
    ) {
        this.context!!.register(
            key,
            ItemModifier(
                predicate,
                this.itemsGetter!!.getOrThrow(incompatibleItems),
                this.enchantmentGetter!!.getOrThrow(incompatibleEnchantments),
                HolderSet.empty(),
                displaySettings
            )
        )
    }

    private fun register(
        key: ResourceKey<ItemModifier>,
        predicate: ItemPredicate,
        incompatibleItems: TagKey<Item>,
        incompatibleEnchantments: TagKey<Enchantment>,
        componentsToRemove: HolderSet<DataComponentType<*>>,
        displaySettings: DisplaySettings
    ) {
        this.context!!.register(
            key,
            ItemModifier(
                predicate,
                this.itemsGetter!!.getOrThrow(incompatibleItems),
                this.enchantmentGetter!!.getOrThrow(incompatibleEnchantments),
                componentsToRemove,
                displaySettings
            )
        )
    }

    private fun createDisplay(key: ResourceKey<ItemModifier?>, startColor: Int, endColor: Int): DisplaySettings {
        return DisplaySettings(
            Component.translatable(Util.makeDescriptionId("modifier", key.location())),
            key.location().withPrefix("textures/gui/tooltip/").withSuffix(".png"),
            Pair.of<Int?, Int?>(startColor, endColor)
        )
    }
}
