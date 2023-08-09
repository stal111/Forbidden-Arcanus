package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Mod Enchantment Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModEnchantmentTagsProvider
 *
 * @author stal111
 * @since 2021-02-27
 */
public class ModEnchantmentTagsProvider extends IntrinsicHolderTagsProvider<Enchantment> {

    public ModEnchantmentTagsProvider(DataProviderContext context, ExistingFileHelper fileHelper) {
        super(context.output(), Registries.ENCHANTMENT, context.lookupProvider(), enchantment -> ForgeRegistries.ENCHANTMENTS.getResourceKey(enchantment).orElseThrow(), ForbiddenArcanus.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(ModTags.Enchantments.INDESTRUCTIBLE_BLACKLISTED).add(Enchantments.UNBREAKING, Enchantments.MENDING);
        this.tag(ModTags.Enchantments.ETERNAL_INCOMPATIBLE).add(Enchantments.UNBREAKING, Enchantments.MENDING);
        this.tag(ModTags.Enchantments.FIERY_INCOMPATIBLE).add(Enchantments.SILK_TOUCH);
        this.tag(ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE);
        this.tag(ModTags.Enchantments.DEMOLISHING_INCOMPATIBLE);
        this.tag(ModTags.Enchantments.AQUATIC_INCOMPATIBLE);
    }

    @Nonnull
    @Override
    public String getName() {
        return ForbiddenArcanus.MOD_ID + ": Enchantment Tags";
    }
}
