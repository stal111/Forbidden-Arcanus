package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.valhelsia.valhelsia_core.api.common.util.DeferredCodec;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

/**
 * Item Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-24
 */
public class ItemModifier {

    public static final Codec<ItemModifier> NAME_CODEC = new DeferredCodec<>(FARegistries.ITEM_MODIFIER_REGISTRY::byNameCodec);
    public static final Codec<Holder<ItemModifier>> CODEC = RegistryFileCodec.create(FARegistries.ITEM_MODIFIER, NAME_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ItemModifier>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FARegistries.ITEM_MODIFIER);

    private final Predicate<ItemStack> predicate;

    private final TagKey<Item> incompatibleItems;
    private final TagKey<Enchantment> incompatibleEnchantments;

    private final int startTooltipColor;
    private final int endTooltipColor;

    private String translationKey;

    private List<ItemStack> cachedValidItems;

    public ItemModifier(Predicate<ItemStack> predicate, TagKey<Item> incompatibleItems, TagKey<Enchantment> incompatibleEnchantments, int startTooltipColor, int endTooltipColor) {
        this.predicate = predicate;
        this.incompatibleItems = incompatibleItems;
        this.incompatibleEnchantments = incompatibleEnchantments;
        this.startTooltipColor = startTooltipColor;
        this.endTooltipColor = endTooltipColor;
    }

    public void onApplied(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_NAME)) {
            stack.update(DataComponents.ITEM_NAME, stack.getItem().getName(stack), component -> this.getComponent().append(" ").append(component));
        }
    }

    public MutableComponent getComponent() {
        return Component.translatable(this.getTranslationKey());
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("modifier", this.getRegistryName());
        }
        return this.translationKey;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return FARegistries.ITEM_MODIFIER_REGISTRY.getKey(this);
    }

    public ResourceLocation getTooltipTexture() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/tooltip/" + this.getRegistryName().getPath() + ".png");
    }

    public TagKey<Item> getIncompatibleItems() {
        return this.incompatibleItems;
    }

    public TagKey<Enchantment> getIncompatibleEnchantments() {
        return this.incompatibleEnchantments;
    }

    public boolean canItemContainModifier(ItemStack stack) {
        if (stack.is(this.getIncompatibleItems()) || !this.predicate.test(stack)) {
            return false;
        }

        var tagHolder = BuiltInRegistries.ENCHANTMENT.getOrCreateTag(this.getIncompatibleEnchantments());

        return stack.getEnchantments().keySet().stream()
                .noneMatch(enchantment -> tagHolder.contains(enchantment.value().builtInRegistryHolder()));
    }

    public List<ItemStack> getValidItems() {
        if (this.cachedValidItems == null) {
            this.cachedValidItems = BuiltInRegistries.ITEM.stream().map(ItemStack::new).filter(this::canItemContainModifier).toList();
        }
        return this.cachedValidItems;
    }

    public void clearCachedValidItems() {
        this.cachedValidItems = null;
    }

    public int getStartTooltipColor() {
        return this.startTooltipColor;
    }

    public int getEndTooltipColor() {
        return this.endTooltipColor;
    }
}
