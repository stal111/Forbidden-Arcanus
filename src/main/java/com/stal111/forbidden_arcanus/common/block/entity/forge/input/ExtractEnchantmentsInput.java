package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.other.ModForgeInputTypes;
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

    public ExtractEnchantmentsInput() {
        super(EssenceType.EXPERIENCE);
    }

    @Override
    public boolean canInputStack(ItemStack stack) {
        return stack.isEnchanted();
    }

    @Override
    public int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random) {
        int xp = this.getExperienceFromItem(stack);

        if (xp <= 0) {
            return 0;
        }

        int i = (int) Math.ceil((double) xp / 2.0D);

        return i + random.nextInt(i);
    }

    @Override
    public void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            tileEntity.setStack(slot, this.removeNonCursesFrom(stack));
        }
    }

    @Override
    public HephaestusForgeInputType<?> type() {
        return ModForgeInputTypes.EXTRACT_ENCHANTMENTS.get();
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
