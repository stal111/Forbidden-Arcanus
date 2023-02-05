package com.stal111.forbidden_arcanus.core.init;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResultType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class ModRitualResults implements RegistryClass {

    public static final MappedRegistryHelper<RitualResultType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(ForbiddenArcanus.RITUAL_RESULT_TYPE);

    public static final RegistryObject<RitualResultType<CreateItemResult>> CREATE_ITEM = register("create_item", CreateItemResult.SERIALIZER, CreateItemResult.DESERIALIZER, CreateItemResult.CODEC);

    public static <T extends RitualResult> RegistryObject<RitualResultType<T>> register(String name, RitualResultType.NetworkSerializer<T> toNetwork, RitualResultType.NetworkDeserializer<T> fromNetwork, Codec<T> codec) {
        return HELPER.register(name, () -> new RitualResultType<>(toNetwork, fromNetwork, codec));
    }
}
