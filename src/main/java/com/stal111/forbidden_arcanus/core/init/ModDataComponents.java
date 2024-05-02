package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.ItemEssenceData;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 26.04.2024
 */
public class ModDataComponents {

    public static MappedRegistryHelper<DataComponentType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.DATA_COMPONENT_TYPE);

    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<ItemModifier>>> ITEM_MODIFIER = HELPER.register("modifier", () -> DataComponentType.<Holder<ItemModifier>>builder().persistent(ItemModifier.CODEC).networkSynchronized(ItemModifier.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<ItemEssenceData>> ESSENCE_DATA = HELPER.register("essence_data", () -> DataComponentType.<ItemEssenceData>builder().persistent(ItemEssenceData.CODEC).networkSynchronized(ItemEssenceData.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<Item>>> EMPTY_ITEM = HELPER.register("empty_item", () -> DataComponentType.<Holder<Item>>builder().persistent(ItemStack.ITEM_NON_AIR_CODEC).networkSynchronized(ByteBufCodecs.holderRegistry(Registries.ITEM)).build());

}
