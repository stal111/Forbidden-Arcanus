package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-03-25
 */
public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {

    public ModDamageTypeTagsProvider(DataProviderInfo info) {
        super(info.output(), Registries.DAMAGE_TYPE, info.lookupProvider(), ForbiddenArcanus.MOD_ID, info.fileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypes.EXTRACT_SOUL);
    }
}
