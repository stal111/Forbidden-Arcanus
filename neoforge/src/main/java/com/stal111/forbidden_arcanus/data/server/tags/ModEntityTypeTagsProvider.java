package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;

import javax.annotation.Nonnull;

/**
 * Mod Enchantment Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModEnchantmentTagsProvider
 *
 * @author stal111
 * @since 2021-02-27
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(DataProviderContext context, ExistingFileHelper fileHelper) {
        super(context.output(), context.lookupProvider(), ForbiddenArcanus.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(ModTags.EntityTypes.BLACK_HOLE_AFFECTED).add(EntityType.ITEM, EntityType.EXPERIENCE_ORB, EntityType.ARROW, EntityType.SPECTRAL_ARROW, ModEntities.BOOM_ARROW.get(), ModEntities.DRACO_ARCANUS_ARROW.get());
        this.tag(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED).addTag(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED).addTag(Tags.EntityTypes.BOSSES);
        this.tag(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED).add(EntityType.PLAYER);
        this.tag(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WANDERING_TRADER);
        this.tag(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE).addTag(EntityTypeTags.SKELETONS).add(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,  EntityType.WITCH, EntityType.DROWNED, EntityType.PILLAGER, EntityType.ILLUSIONER, EntityType.VINDICATOR, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.EVOKER);
        this.tag(ModTags.EntityTypes.TEST_TUBE_BLACKLISTED);
    }

    @Nonnull
    @Override
    public String getName() {
        return ForbiddenArcanus.MOD_ID + ": Entity Type Tags";
    }
}
