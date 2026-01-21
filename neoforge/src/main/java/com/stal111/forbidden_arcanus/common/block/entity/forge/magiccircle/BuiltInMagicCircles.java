package com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInMagicCircles {

    public static final ResourceKeyHelper<MagicCircleType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.MAGIC_CIRCLE);

    public static final ResourceKey<MagicCircleType> CREATE_ITEM = HELPER.createKey("create_item");
    public static final ResourceKey<MagicCircleType> UPGRADE_TIER = HELPER.createKey("upgrade_tier");
    public static final ResourceKey<MagicCircleType> UPGRADE_FINAL_TIER = HELPER.createKey("upgrade_final_tier");
    public static final ResourceKey<MagicCircleType> WAND_DESK = HELPER.createKey("wand_desk");

}
