package com.stal111.forbidden_arcanus.common.item.enchantment;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInEnchantments {

    public static final ResourceKeyHelper<Enchantment> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(Registries.ENCHANTMENT);

    public static final ResourceKey<Enchantment> SOUL_LOOTING = HELPER.createKey("soul_looting");
    public static final ResourceKey<Enchantment> ANCHORED = HELPER.createKey("anchored");
}
