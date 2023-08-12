package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTraderVariant;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-12
 */
public class ModEntityDataSerializers implements RegistryClass {

    public static final MappedRegistryHelper<EntityDataSerializer<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS);

    public static final RegistryEntry<EntityDataSerializer<DarkTraderVariant>> DARK_TRADER_VARIANT = HELPER.register("dark_trader_variant", () -> EntityDataSerializer.simple((friendlyByteBuf, variant) -> {
        friendlyByteBuf.writeNullable(FARegistries.DARK_TRADER_VARIANT_REGISTRY.get().getKey(variant), FriendlyByteBuf::writeResourceLocation);
    }, friendlyByteBuf -> {
        DarkTraderVariant variant = FARegistries.DARK_TRADER_VARIANT_REGISTRY.get().getValue(friendlyByteBuf.readNullable(FriendlyByteBuf::readResourceLocation));

        if (variant == null) {
            variant = DarkTraderVariant.BROOK.get();
        }
        return variant;
    }));
}
