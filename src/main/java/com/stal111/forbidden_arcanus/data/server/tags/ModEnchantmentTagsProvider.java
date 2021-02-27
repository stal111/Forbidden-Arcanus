package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

/**
 * Mod Enchantment Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModEnchantmentTagsProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-27
 */
public class ModEnchantmentTagsProvider extends ForgeRegistryTagsProvider<Enchantment> {

    public ModEnchantmentTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForgeRegistries.ENCHANTMENTS, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ModTags.Enchantments.INDESTRUCTIBLE_BLACKLISTED).add(Enchantments.UNBREAKING, Enchantments.MENDING);
    }

    @Override
    public String getName() {
        return ForbiddenArcanus.MOD_ID + ": Enchantment Tags";
    }
}
