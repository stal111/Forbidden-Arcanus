package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Item Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
 *
 * @author stal111
 * @since 2021-11-24
 */
public record ItemModifier(
        ItemPredicate predicate,
        HolderSet<Item> incompatibleItems,
        HolderSet<Enchantment> incompatibleEnchantments,
        HolderSet<DataComponentType<?>> componentsToRemove,
        DisplaySettings displaySettings) {

    public static final Codec<ItemModifier> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemPredicate.CODEC.fieldOf("predicate").forGetter(ItemModifier::predicate),
            RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("incompatible_items", HolderSet.empty()).forGetter(ItemModifier::incompatibleItems),
            RegistryCodecs.homogeneousList(Registries.ENCHANTMENT).optionalFieldOf("incompatible_enchantments", HolderSet.empty()).forGetter(ItemModifier::incompatibleEnchantments),
            RegistryCodecs.homogeneousList(Registries.DATA_COMPONENT_TYPE).optionalFieldOf("components_to_remove", HolderSet.empty()).forGetter(ItemModifier::componentsToRemove),
            DisplaySettings.CODEC.fieldOf("display").forGetter(modifier -> modifier.displaySettings)
    ).apply(instance, ItemModifier::new));

    public static final Codec<Holder<ItemModifier>> CODEC = RegistryFileCodec.create(FARegistries.ITEM_MODIFIER, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ItemModifier>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FARegistries.ITEM_MODIFIER);

    public void onApplied(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_NAME)) {
            stack.update(DataComponents.ITEM_NAME, stack.getItem().getName(stack), component -> this.displaySettings.name.copy().append(" ").append(component));
        }

        for (Holder<DataComponentType<?>> component : this.componentsToRemove) {
            stack.remove(component.value());
        }
    }

    public boolean canItemContainModifier(ItemStack stack, HolderLookup.Provider lookupProvider) {
        if (!this.isValidItem(stack)) {
            return false;
        }

        var enchantments = stack.getTagEnchantments().keySet();

        return this.incompatibleEnchantments.stream().noneMatch(enchantments::contains);
    }

    public boolean isValidItem(ItemStack stack) {
        return !stack.is(this.incompatibleItems) && this.predicate.test(stack);
    }

    public record DisplaySettings(Component name, ResourceLocation texture, Pair<Integer, Integer> tooltipColor) {

        public static final Codec<DisplaySettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.fieldOf("name").forGetter(DisplaySettings::name),
                ResourceLocation.CODEC.fieldOf("texture").forGetter(DisplaySettings::texture),
                Codec.pair(Codec.INT.fieldOf("start").codec(), Codec.INT.fieldOf("end").codec()).fieldOf("tooltip_color").forGetter(DisplaySettings::tooltipColor)
        ).apply(instance, DisplaySettings::new));
    }
}
