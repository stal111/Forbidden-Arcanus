package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Mortem Armor Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.MortemArmorItem
 *
 * @author stal111
 * @since 2021-12-11
 */
public class MortemArmorItem extends DyeableArmorItem {

    public MortemArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public int getColor(@Nonnull ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : FastColor.ARGB32.color(255, 199, 198, 191);
    }
}
