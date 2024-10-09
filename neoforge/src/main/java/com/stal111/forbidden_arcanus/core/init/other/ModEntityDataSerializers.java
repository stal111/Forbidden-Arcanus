package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTraderVariant;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-12
 */
public class ModEntityDataSerializers implements RegistryClass {

    public static final MappedRegistryHelper<EntityDataSerializer<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS);

    public static final RegistryEntry<EntityDataSerializer<?>, EntityDataSerializer<Holder<DarkTraderVariant>>> DARK_TRADER_VARIANT = HELPER.register("dark_trader_variant", () -> EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FARegistries.DARK_TRADER_VARIANT)));
}
