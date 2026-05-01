package com.stal111.forbidden_arcanus.datagen.tags

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.item.enchantment.BuiltInEnchantments
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.tags.EnchantmentTagsProvider
import net.minecraft.tags.EnchantmentTags
import net.minecraft.world.item.enchantment.Enchantments
import net.valhelsia.dataforge.DataProviderContext
import org.jetbrains.annotations.NotNull

class ModEnchantmentTagsProvider(
    context: DataProviderContext.Server,
) : EnchantmentTagsProvider(context.packOutput, context.lookupProvider, ForbiddenArcanus.MOD_ID) {
    override fun addTags(provider: HolderLookup.Provider) {
        this.tag(ModTags.Enchantments.ETERNAL_INCOMPATIBLE).add(Enchantments.UNBREAKING, Enchantments.MENDING)
        this.tag(ModTags.Enchantments.FIERY_INCOMPATIBLE).add(Enchantments.SILK_TOUCH)
        this.tag(ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE)
        this.tag(ModTags.Enchantments.DEMOLISHING_INCOMPATIBLE)
        this.tag(ModTags.Enchantments.AQUATIC_INCOMPATIBLE)
        this.tag(ModTags.Enchantments.SOULBOUND_INCOMPATIBLE)
        this.tag(EnchantmentTags.NON_TREASURE).add(BuiltInEnchantments.SOUL_LOOTING, BuiltInEnchantments.ANCHORED);
    }

    @NotNull
    override fun getName(): String {
        return ForbiddenArcanus.MOD_ID + ": Enchantment Tags"
    }
}
