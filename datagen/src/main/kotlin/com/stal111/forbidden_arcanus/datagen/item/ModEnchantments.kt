package com.stal111.forbidden_arcanus.datagen.item

import com.stal111.forbidden_arcanus.common.item.enchantment.BuiltInEnchantments
import com.stal111.forbidden_arcanus.core.init.ModEnchantmentDataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.tags.ItemTags
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.LevelBasedValue
import net.minecraft.world.item.enchantment.effects.AddValue
import net.valhelsia.dataforge.RegistryDataProvider

object ModEnchantments : RegistryDataProvider<Enchantment> {
    override fun bootstrap(context: BootstrapContext<Enchantment>) {
        val itemHolderGetter = context.lookup(Registries.ITEM)

        context.register(
            BuiltInEnchantments.SOUL_LOOTING, Enchantment.enchantment(
                Enchantment.definition(
                    itemHolderGetter.getOrThrow(ItemTags.MELEE_WEAPON_ENCHANTABLE),
                    2,
                    3,
                    Enchantment.dynamicCost(15, 9),
                    Enchantment.dynamicCost(65, 9),
                    4,
                    EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                ModEnchantmentDataComponents.LOST_SOUL_SPAWN_CHANCE.get(), AddValue(
                    LevelBasedValue.perLevel(0.05f)
                )
            ).build(BuiltInEnchantments.SOUL_LOOTING.identifier())
        )

        context.register(
            BuiltInEnchantments.ANCHORED, Enchantment.enchantment(
                Enchantment.definition(
                    itemHolderGetter.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                    5,
                    3,
                    Enchantment.dynamicCost(14, 8),
                    Enchantment.dynamicCost(26, 9),
                    3,
                    EquipmentSlotGroup.FEET
                )
            ).withEffect(
                ModEnchantmentDataComponents.PULL_RESISTANCE.get(), AddValue(
                    LevelBasedValue.lookup(listOf(0.25F, 0.55F, 0.9F), LevelBasedValue.constant(1.0F))
                )
            ).build(BuiltInEnchantments.ANCHORED.identifier())
        )
    }
}
