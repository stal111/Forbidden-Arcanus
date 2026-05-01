package com.stal111.forbidden_arcanus.datagen.tags

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.tags.EntityTypeTagsProvider
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.entity.EntityType
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
        this.tag(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED).add(EntityType.PLAYER)
        this.tag(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE)
            .add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WANDERING_TRADER)
        this.tag(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE).addTag(EntityTypeTags.SKELETONS).add(
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.WITCH,
            EntityType.DROWNED,
            EntityType.PILLAGER,
            EntityType.ILLUSIONER,
            EntityType.VINDICATOR,
            EntityType.HUSK,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.EVOKER
        )
        this.tag(ModTags.EntityTypes.TEST_TUBE_BLACKLISTED)
        this.tag(ModTags.EntityTypes.SPECTRAL_VISION_UNAFFECTED)
    }

    override fun getName(): String {
        return ForbiddenArcanus.MOD_ID + ": Entity Type Tags"
    }
}
