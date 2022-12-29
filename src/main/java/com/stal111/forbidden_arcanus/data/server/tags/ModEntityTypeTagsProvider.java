package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;

import javax.annotation.Nonnull;

/**
 * Mod Enchantment Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModEnchantmentTagsProvider
 *
 * @author stal111
 * @since 2021-02-27
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(DataProviderInfo info) {
        super(info.output(), info.lookupProvider(), ForbiddenArcanus.MOD_ID, info.fileHelper());
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(ModTags.EntityTypes.BLACK_HOLE_AFFECTED).add(EntityType.ITEM, EntityType.EXPERIENCE_ORB, EntityType.ARROW, EntityType.SPECTRAL_ARROW, ModEntities.BOOM_ARROW.get(), ModEntities.DRACO_ARCANUS_ARROW.get());
        this.tag(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED).add(EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.ELDER_GUARDIAN);
        this.tag(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WANDERING_TRADER);
        this.tag(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE).addTag(EntityTypeTags.SKELETONS).add(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,  EntityType.WITCH, EntityType.DROWNED, EntityType.PILLAGER, EntityType.ILLUSIONER, EntityType.VINDICATOR, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.EVOKER);

    }

    @Nonnull
    @Override
    public String getName() {
        return ForbiddenArcanus.MOD_ID + ": Entity Type Tags";
    }
}
