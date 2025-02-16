package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.component.RitualStarter;
import com.stal111.forbidden_arcanus.common.item.component.StoredEntity;
import com.stal111.forbidden_arcanus.common.item.component.ToggleableState;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 26.04.2024
 */
public class ModDataComponents {

    public static final MappedRegistryHelper<DataComponentType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.DATA_COMPONENT_TYPE);

    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<ItemModifier>>> ITEM_MODIFIER = HELPER.register("modifier", () -> DataComponentType.<Holder<ItemModifier>>builder().persistent(ItemModifier.CODEC).networkSynchronized(ItemModifier.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<EssenceData>> ESSENCE_DATA = HELPER.register("essence_data", () -> DataComponentType.<EssenceData>builder().persistent(EssenceData.CODEC).networkSynchronized(EssenceData.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<EssenceStorage>> ESSENCE_STORAGE = HELPER.register("essence_storage", () -> DataComponentType.<EssenceStorage>builder().persistent(EssenceStorage.CODEC).networkSynchronized(EssenceStorage.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<Item>>> EMPTY_ITEM = HELPER.register("empty_item", () -> DataComponentType.<Holder<Item>>builder().persistent(ItemStack.ITEM_NON_AIR_CODEC).networkSynchronized(ByteBufCodecs.holderRegistry(Registries.ITEM)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<StoredEntity>> STORED_ENTITY = HELPER.register("stored_entity", () -> DataComponentType.<StoredEntity>builder().persistent(StoredEntity.CODEC).networkSynchronized(StoredEntity.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> STORED_FLUID_AMOUNT = HELPER.register("stored_fluid_amount", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> TICKS_TILL_NEXT_STAGE = HELPER.register("ticks_till_next_stage", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<ResourceKey<EnhancerDefinition>>> ENHANCER = HELPER.register("enhancer", () -> DataComponentType.<ResourceKey<EnhancerDefinition>>builder().persistent(ResourceKey.codec(FARegistries.ENHANCER_DEFINITION)).networkSynchronized(ResourceKey.streamCodec(FARegistries.ENHANCER_DEFINITION)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<ToggleableState>> TOGGLEABLE_STATE = HELPER.register("toggleable_state", () -> DataComponentType.<ToggleableState>builder().persistent(ToggleableState.CODEC).networkSynchronized(ToggleableState.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Unit>> SHOWS_AUREAL_METER = HELPER.register("shows_aureal_meter", () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<RitualStarter>> RITUAL_STARTER = HELPER.register("ritual_starter", () -> DataComponentType.<RitualStarter>builder().persistent(RitualStarter.CODEC).build());

}
