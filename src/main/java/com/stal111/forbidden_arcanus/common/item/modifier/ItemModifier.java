package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.valhelsia.valhelsia_core.api.common.util.DeferredCodec;

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

    private final Predicate<ItemStack> predicate;

    private final TagKey<Item> incompatibleItems;
    private final TagKey<Enchantment> incompatibleEnchantments;

    private final int startTooltipColor;
    private final int endTooltipColor;

    private List<ItemStack> cachedValidItems;
    public ItemModifier(Predicate<ItemStack> predicate, TagKey<Item> incompatibleItems, TagKey<Enchantment> incompatibleEnchantments, int startTooltipColor, int endTooltipColor) {
        this.predicate = predicate;
        this.incompatibleItems = incompatibleItems;
        this.incompatibleEnchantments = incompatibleEnchantments;
        this.startTooltipColor = startTooltipColor;
        this.endTooltipColor = endTooltipColor;
    }

    public void onApplied(ItemStack stack) {

    }

    public MutableComponent getComponent() {
        ResourceLocation name = this.getRegistryName();

        if (name == null) {
            return Component.literal("");
        }
        return Component.translatable("modifier." + name.getNamespace() + "." + name.getPath());
    }

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

        return EnchantmentHelper.getEnchantments(stack).keySet().stream()
                .noneMatch(enchantment -> tagHolder.contains(enchantment.builtInRegistryHolder()));
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
