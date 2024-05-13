package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.component.StoredEntity;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
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
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<EssenceData>> ESSENCE_DATA = HELPER.register("essence_data", () -> DataComponentType.<EssenceData>builder().persistent(EssenceData.CODEC).networkSynchronized(EssenceData.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<EssenceStorage>> ESSENCE_STORAGE = HELPER.register("essence_storage", () -> DataComponentType.<EssenceStorage>builder().persistent(EssenceStorage.CODEC).networkSynchronized(EssenceStorage.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<Item>>> EMPTY_ITEM = HELPER.register("empty_item", () -> DataComponentType.<Holder<Item>>builder().persistent(ItemStack.ITEM_NON_AIR_CODEC).networkSynchronized(ByteBufCodecs.holderRegistry(Registries.ITEM)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<StoredEntity>> STORED_ENTITY = HELPER.register("stored_entity", () -> DataComponentType.<StoredEntity>builder().persistent(StoredEntity.CODEC).networkSynchronized(StoredEntity.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> STORED_FLUID_AMOUNT = HELPER.register("stored_fluid_amount", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> TICKS_TILL_NEXT_STAGE = HELPER.register("ticks_till_next_stage", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> RITUAL_USES = HELPER.register("ritual_uses", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> REMAINING_RITUAL_USES = HELPER.register("remaining_ritual_uses", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

}
