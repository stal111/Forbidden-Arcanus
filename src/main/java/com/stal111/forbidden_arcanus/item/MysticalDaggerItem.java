package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class MysticalDaggerItem extends SwordItem {

    public MysticalDaggerItem(String name, IItemTier tier, float attackDamage, float attackSpeed) {
        super(tier, (int) attackDamage, attackSpeed, new Item.Properties().group(Main.FORBIDDEN_ARCANUS).maxStackSize(1));
        this.setRegistryName(ModUtils.location(name));
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(this);
        stack.setDamage(itemStack.getDamage() + 1);
        return stack;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.hitEntity(stack, target, attacker);
    }
}
