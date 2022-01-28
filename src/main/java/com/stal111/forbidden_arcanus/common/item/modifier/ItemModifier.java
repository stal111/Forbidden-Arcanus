package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;
import java.util.function.Predicate;

/**
 * Item Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
 *
 * @author stal111
 * @version 1.18.1 - 2.0.3
 * @since 2021-11-24
 */
public class ItemModifier extends ForgeRegistryEntry<ItemModifier> {

    private final Predicate<ItemStack> predicate;

    private final Tag.Named<Item> incompatibleItems;
    private final Tag.Named<Enchantment> incompatibleEnchantments;

    private final Pair<Integer, Integer> tooltipColors;

    private List<ItemStack> cachedValidItems;

    public ItemModifier(Predicate<ItemStack> predicate, Tag.Named<Item> incompatibleItems, Tag.Named<Enchantment> incompatibleEnchantments, Pair<Integer, Integer> tooltipColors) {
        this.predicate = predicate;
        this.incompatibleItems = incompatibleItems;
        this.incompatibleEnchantments = incompatibleEnchantments;
        this.tooltipColors = tooltipColors;
    }

    public void onApplied(ItemStack stack) {

    }

    public TranslatableComponent getComponent() {
        if (this.getRegistryName() == null) {
            return new TranslatableComponent("");
        }
        return new TranslatableComponent("modifier." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath());
    }

    public Tag.Named<Item> getIncompatibleItems() {
        return this.incompatibleItems;
    }

    public Tag.Named<Enchantment> getIncompatibleEnchantments() {
        return this.incompatibleEnchantments;
    }

    public boolean canItemContainModifier(ItemStack stack) {
        if (stack.is(this.getIncompatibleItems()) || !this.predicate.test(stack)) {
            return false;
        }

        return EnchantmentHelper.getEnchantments(stack).keySet().stream()
                .noneMatch(enchantment -> enchantment.is(this.getIncompatibleEnchantments()));
    }

    public List<ItemStack> getValidItems() {
        if (this.cachedValidItems == null) {
            this.cachedValidItems = ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(this::canItemContainModifier).toList();
        }
        return this.cachedValidItems;
    }

    public void clearCachedValidItems() {
        this.cachedValidItems = null;
    }

    public Pair<Integer, Integer> getTooltipColors() {
        return this.tooltipColors;
    }
}
