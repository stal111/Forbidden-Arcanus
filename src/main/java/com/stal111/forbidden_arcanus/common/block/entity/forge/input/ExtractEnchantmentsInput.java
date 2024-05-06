package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * @author stal111
 * @since 2021-07-07
 */
public class ExtractEnchantmentsInput extends HephaestusForgeInput {

    public static final MapCodec<ExtractEnchantmentsInput> CODEC = MapCodec.unit(ExtractEnchantmentsInput::new);

    @Override
    public boolean canInput(EssenceType type, ItemStack stack) {
        return type == EssenceType.EXPERIENCE && stack.isEnchanted();
    }

    @Override
    public EssenceData getInputValue(ItemStack stack, RandomSource random) {
        int xp = this.getExperienceFromItem(stack);

        if (xp <= 0) {
            return EssenceData.EMPTY;
        }

        int i = (int) Math.ceil((double) xp / 2.0D);

        return EssenceData.of(EssenceType.EXPERIENCE, i + random.nextInt(i));
    }

    @Override
    public ItemStack finishInput(ItemStack stack, int inputValue) {
        if (inputValue != 0) {
            return this.removeNonCursesFrom(stack);
        }

        return stack;
    }

    private int getExperienceFromItem(ItemStack stack) {
        int xp = 0;
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);

        for (Object2IntOpenHashMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey().value();
            int level = entry.getIntValue();

            if (!enchantment.isCurse()) {
                xp += enchantment.getMinCost(level);
            }
        }

        return xp;
    }

    private ItemStack removeNonCursesFrom(ItemStack stack) {
        ItemEnchantments enchantments = EnchantmentHelper.updateEnchantments(
                stack, mutable -> mutable.removeIf(enchantmentHolder -> !enchantmentHolder.value().isCurse())
        );

        if (stack.is(Items.ENCHANTED_BOOK) && enchantments.isEmpty()) {
            stack = stack.transmuteCopy(Items.BOOK, stack.getCount());
        }

        int repairCost = 0;

        for (int j = 0; j < enchantments.size(); j++) {
            repairCost = AnvilMenu.calculateIncreasedRepairCost(repairCost);
        }

        stack.set(DataComponents.REPAIR_COST, repairCost);

        return stack;
    }
}
