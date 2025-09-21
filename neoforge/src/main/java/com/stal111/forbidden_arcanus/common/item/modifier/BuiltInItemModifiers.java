package com.stal111.forbidden_arcanus.common.item.modifier;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInItemModifiers {

    public static final ResourceKeyHelper<ItemModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.ITEM_MODIFIER);

    public static final ResourceKey<ItemModifier> ETERNAL = HELPER.createKey("eternal");
    public static final ResourceKey<ItemModifier> FIERY = HELPER.createKey("fiery");
    public static final ResourceKey<ItemModifier> MAGNETIZED = HELPER.createKey("magnetized");
    public static final ResourceKey<ItemModifier> DEMOLISHING = HELPER.createKey("demolishing");
    public static final ResourceKey<ItemModifier> AQUATIC = HELPER.createKey("aquatic");
    public static final ResourceKey<ItemModifier> SOULBOUND = HELPER.createKey("soulbound");
}
