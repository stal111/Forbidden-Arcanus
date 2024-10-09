package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 14.05.2024
 */
public record ItemModifierPredicate(HolderSet<ItemModifier> modifiers) implements SingleComponentItemPredicate<Holder<ItemModifier>> {

    public static final Codec<ItemModifierPredicate> CODEC = RegistryCodecs.homogeneousList(FARegistries.ITEM_MODIFIER).xmap(ItemModifierPredicate::new, ItemModifierPredicate::modifiers);

    @Override
    public @NotNull DataComponentType<Holder<ItemModifier>> componentType() {
        return ModDataComponents.ITEM_MODIFIER.get();
    }

    @Override
    public boolean matches(@NotNull ItemStack stack, @NotNull Holder<ItemModifier> modifier) {
        return this.modifiers.contains(modifier);
    }

    public static ItemModifierPredicate modifier(Holder<ItemModifier> modifier) {
        return modifiers(HolderSet.direct(modifier));
    }

    public static ItemModifierPredicate modifiers(HolderSet<ItemModifier> modifiers) {
        return new ItemModifierPredicate(modifiers);
    }
}
