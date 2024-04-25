package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Mod Tiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ModTiers
 *
 * @author stal111
 * @since 2021-12-17
 */
public enum ModTiers implements Tier {
    DRACO_ARCANUS(2661, 12.0F, 7.0F, 20, () -> Ingredient.of(ModItems.DRAGON_SCALE.get())),
    REINFORCED_DEORUM(2561, 9.0F, 3.5F, 26, () ->  Ingredient.of(ModItems.STELLARITE_PIECE.get())),
    BONE(131, 4.0F, 1.0F, 5, () ->  Ingredient.of(Items.BONE)),
    SLIMEC(2061, 13.0F, 2.5F, 20, () ->  Ingredient.of(ModItems.DEORUM_INGOT.get())),
    MYSTICAL_DAGGER(561, 4.0F, 1.0F, 5, () ->  Ingredient.of(ModItems.DARK_RUNE.get()));

    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModTiers(int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        //TODO
        return null;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
