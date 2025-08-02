package com.stal111.forbidden_arcanus.common.entity.attribute;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

public class FAAttributes {

    public static final MappedRegistryHelper<Attribute> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ATTRIBUTE);

    public static final Holder<Attribute> AUREAL_REGENERATION = HELPER.register("generic.aureal_regeneration", () -> new RangedAttribute("attribute.name.generic.aureal_regeneration", 0.0D, 0.0D, Double.MAX_VALUE).setSyncable(true));

}
