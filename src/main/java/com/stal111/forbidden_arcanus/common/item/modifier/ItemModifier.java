package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

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

    private final Predicate<ItemStack> predicate;

    private final TagKey<Item> incompatibleItems;
    private final TagKey<Enchantment> incompatibleEnchantments;

    private final Pair<Integer, Integer> tooltipColors;

    private List<ItemStack> cachedValidItems;
    public ItemModifier(Predicate<ItemStack> predicate, TagKey<Item> incompatibleItems, TagKey<Enchantment> incompatibleEnchantments, Pair<Integer, Integer> tooltipColors) {
        this.predicate = predicate;
        this.incompatibleItems = incompatibleItems;
        this.incompatibleEnchantments = incompatibleEnchantments;
        this.tooltipColors = tooltipColors;
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
        return ForbiddenArcanus.ITEM_MODIFIER_REGISTRY.get().getKey(this);
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

        ITagManager<Enchantment> tagManager = ForgeRegistries.ENCHANTMENTS.tags();

        if (tagManager == null) {
            return false;
        }

        return EnchantmentHelper.getEnchantments(stack).keySet().stream()
                .noneMatch(enchantment -> tagManager.getTag(this.getIncompatibleEnchantments()).contains(enchantment));
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
