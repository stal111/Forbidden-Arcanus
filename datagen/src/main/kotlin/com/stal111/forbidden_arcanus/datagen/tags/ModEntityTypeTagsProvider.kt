package com.stal111.forbidden_arcanus.datagen.tags

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.tags.EntityTypeTagsProvider
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.entity.EntityTypeIds
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.DataProviderContext
import javax.annotation.Nonnull

class ModEntityTypeTagsProvider(context: DataProviderContext.Server) :
    EntityTypeTagsProvider(context.packOutput, context.lookupProvider, ForbiddenArcanus.MOD_ID) {
    override fun addTags(@Nonnull provider: HolderLookup.Provider) {
        this.tag(ModTags.EntityTypes.BLACK_HOLE_UNAFFECTED)
        this.tag(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED)
            .addTag(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED)
            .addTag(Tags.EntityTypes.BOSSES)
        this.tag(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED).add(EntityTypeIds.PLAYER)
        this.tag(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE)
            .add(EntityTypeIds.PLAYER, EntityTypeIds.VILLAGER, EntityTypeIds.WANDERING_TRADER)
        this.tag(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE).addTag(EntityTypeTags.SKELETONS).add(
            EntityTypeIds.ZOMBIE,
            EntityTypeIds.ZOMBIE_VILLAGER,
            EntityTypeIds.WITCH,
            EntityTypeIds.DROWNED,
            EntityTypeIds.PILLAGER,
            EntityTypeIds.ILLUSIONER,
            EntityTypeIds.VINDICATOR,
            EntityTypeIds.HUSK,
            EntityTypeIds.PIGLIN,
            EntityTypeIds.PIGLIN_BRUTE,
            EntityTypeIds.EVOKER
        )
        this.tag(ModTags.EntityTypes.TEST_TUBE_BLACKLISTED)
        this.tag(ModTags.EntityTypes.SPECTRAL_VISION_UNAFFECTED)
    }

    override fun getName(): String {
        return ForbiddenArcanus.MOD_ID + ": Entity Type Tags"
    }
}
