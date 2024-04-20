package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.Map;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class CreateItemResult extends RitualResult {

    public static final Codec<CreateItemResult> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ItemStack.CODEC.fieldOf("result_item").forGetter(result -> result.result), Codec.BOOL.fieldOf("copy_nbt").orElse(false).forGetter(result -> result.copyNBT)
    ).apply(instance, CreateItemResult::new));

    private final ItemStack result;
    private final boolean copyNBT;

    public CreateItemResult(ItemStack result, boolean copyNBT) {
        this.result = result;
        this.copyNBT = copyNBT;
    }

    @Override
    public void apply(RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos) {
        ItemStack mainIngredient = accessor.get();
        ItemStack result = this.result.copy();
        if (this.copyNBT) {
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(mainIngredient);
            Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(result);
            
            for(Enchantment enchantment1 : map1.keySet()) {
                if (enchantment1 != null) {
                    int i2 = map.getOrDefault(enchantment1, 0);
                    int j2 = map1.get(enchantment1);
                    j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                    boolean flag1 = enchantment1.canEnchant(result);
                    for(Enchantment enchantment : map.keySet()) {
                        if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                            flag1 = false;
                        }
                        if (flag1) {
                            if (j2 > enchantment1.getMaxLevel()) {
                                j2 = enchantment1.getMaxLevel();
                            }
                            map.put(enchantment1, j2);
                        }
                    }
                }
            }
            result.getOrCreateTag().merge(mainIngredient.getOrCreateTag());
            EnchantmentHelper.setEnchantments(map, result);
        }
        accessor.set(result);
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.CREATE_ITEM.get();
    }
}
