package com.stal111.forbidden_arcanus.core.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mod Enchantment Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModEnchantmentTagsProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-27
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModTags.EntityTypes.BLACK_HOLE_AFFECTED).add(EntityType.ITEM, EntityType.EXPERIENCE_ORB, EntityType.ARROW, EntityType.SPECTRAL_ARROW, ModEntities.BOOM_ARROW.get(), ModEntities.DRACO_ARCANUS_ARROW.get());
        this.tag(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED).add(EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.ELDER_GUARDIAN);
    }

    @Nonnull
    @Override
    public String getName() {
        return ForbiddenArcanus.MOD_ID + ": Entity Type Tags";
    }
}
