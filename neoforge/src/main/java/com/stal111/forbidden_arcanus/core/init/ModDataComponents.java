package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.component.*;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial;
import com.stal111.forbidden_arcanus.common.item.wand.WandPart;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.List;

/**
 * @author stal111
 * @since 26.04.2024
 */
public class ModDataComponents {

    public static final MappedRegistryHelper<DataComponentType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.DATA_COMPONENT_TYPE);

    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<ItemModifier>>> ITEM_MODIFIER = HELPER.register("modifier", () -> DataComponentType.<Holder<ItemModifier>>builder().persistent(ItemModifier.CODEC).networkSynchronized(ItemModifier.STREAM_CODEC).build());
    //TODO: rename
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<EssenceValue>> ESSENCE_VALUE = HELPER.register("essence_data", () -> DataComponentType.<EssenceValue>builder().persistent(EssenceValue.CODEC).networkSynchronized(EssenceValue.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<EssenceStorage>> ESSENCE_STORAGE = HELPER.register("essence_storage", () -> DataComponentType.<EssenceStorage>builder().persistent(EssenceStorage.CODEC).networkSynchronized(EssenceStorage.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<Item>>> EMPTY_ITEM = HELPER.register("empty_item", () -> DataComponentType.<Holder<Item>>builder().persistent(Item.CODEC).networkSynchronized(ByteBufCodecs.holderRegistry(Registries.ITEM)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<StoredEntity>> STORED_ENTITY = HELPER.register("stored_entity", () -> DataComponentType.<StoredEntity>builder().persistent(StoredEntity.CODEC).networkSynchronized(StoredEntity.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> BUCKET_CAPACITY = HELPER.register("bucket_capacity", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> STORED_FLUID_AMOUNT = HELPER.register("stored_fluid_amount", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Integer>> TICKS_TILL_NEXT_STAGE = HELPER.register("ticks_till_next_stage", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<EnhancerDefinition>>> ENHANCER = HELPER.register("enhancer", () -> DataComponentType.<Holder<EnhancerDefinition>>builder().persistent(EnhancerDefinition.CODEC).networkSynchronized(EnhancerDefinition.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<ToggleableState>> TOGGLEABLE_STATE = HELPER.register("toggleable_state", () -> DataComponentType.<ToggleableState>builder().persistent(ToggleableState.CODEC).networkSynchronized(ToggleableState.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Unit>> SHOWS_AUREAL_METER = HELPER.register("shows_aureal_meter", () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<RitualStarter>> RITUAL_STARTER = HELPER.register("ritual_starter", () -> DataComponentType.<RitualStarter>builder().persistent(RitualStarter.CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<ObsidianSkullType>> OBSIDIAN_SKULL_TYPE = HELPER.register("obsidian_skull_type", () -> DataComponentType.<ObsidianSkullType>builder().persistent(ObsidianSkullType.CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<List<EffectGrantingRule>>> GRANTS_EFFECTS = HELPER.register("grants_effects", () -> DataComponentType.<List<EffectGrantingRule>>builder().persistent(EffectGrantingRule.CODEC.listOf()).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<WandParts>> WAND_PARTS = HELPER.register("wand_parts", () -> DataComponentType.<WandParts>builder().persistent(WandParts.CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<AurealCost>> AUREAL_COST = HELPER.register("aureal_cost", () -> DataComponentType.<AurealCost>builder().persistent(AurealCost.CODEC).networkSynchronized(AurealCost.STREAM_CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<WandMaterial>>> PROVIDES_WAND_MATERIAL = HELPER.register("provides_wand_material", () -> DataComponentType.<Holder<WandMaterial>>builder().persistent(WandMaterial.CODEC).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<WandMaterial>>> POMMEL_MATERIAL = HELPER.register("wand/pommel_material", () -> DataComponentType.<Holder<WandMaterial>>builder().persistent(WandMaterial.validatedCodec(WandPart.POMMEL)).build());
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Holder<WandMaterial>>> TRANSITION_MATERIAL = HELPER.register("wand/transition_material", () -> DataComponentType.<Holder<WandMaterial>>builder().persistent(WandMaterial.validatedCodec(WandPart.TRANSITION)).build());
}
