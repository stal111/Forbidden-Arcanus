package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 2022-06-29
 */
public class ResiduesStorage implements NeedsStoring {

    public static final List<ResidueType> RESIDUE_TYPES = new ArrayList<>();

    public static final int MAX_AMOUNT = 64;

    private final Map<ResidueType, Integer> residueTypeAmountMap = new HashMap<>();

    private int totalAmount = 0;

    public void tick(Level level, ClibanoMainBlockEntity blockEntity) {
        level.getRecipeManager().getAllRecipesFor(ModRecipes.COMBINE_RESIDUES.get()).forEach(recipe -> {
            ResidueType type = new ResidueType(recipe.getResidue());

            if (this.residueTypeAmountMap.getOrDefault(type, 0) >= recipe.getResidueAmount()) {
                ItemStack resultStack = blockEntity.getItem(ClibanoMenu.RESULT_SLOTS.getFirst());
                ItemStack secondResultStack = blockEntity.getItem(ClibanoMenu.RESULT_SLOTS.getSecond());

                ItemStack stack = recipe.getResultItem();
                boolean flag = true;

                if (resultStack.sameItem(stack) && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
                    resultStack.grow(stack.getCount());
                } else if (secondResultStack.sameItem(stack) && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
                    secondResultStack.grow(stack.getCount());
                } else if (resultStack.isEmpty()) {
                    blockEntity.setItem(ClibanoMenu.RESULT_SLOTS.getFirst(), stack.copy());
                } else if (secondResultStack.isEmpty()) {
                    blockEntity.setItem(ClibanoMenu.RESULT_SLOTS.getSecond(), stack.copy());
                } else {
                    flag = false;
                }

                if (flag) {
                    this.residueTypeAmountMap.computeIfPresent(type, (residueType, value) -> {
                        this.totalAmount -= recipe.getResidueAmount();

                        return value - recipe.getResidueAmount();
                    });
                }
            }
        });
    }

    public void increaseType(ResidueType residueType, int amount) {
        if (this.totalAmount >= MAX_AMOUNT) {
            return;
        }

        int amountToFill = Math.min(MAX_AMOUNT - this.totalAmount, amount);

        this.residueTypeAmountMap.put(residueType, this.residueTypeAmountMap.getOrDefault(residueType, 0) + amountToFill);
        this.totalAmount += amountToFill;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listTag = new ListTag();

        this.residueTypeAmountMap.forEach((residueType, integer) -> {
            CompoundTag residue = new CompoundTag();

            residue.putString("Name", residueType.name());
            residue.putInt("Amount", integer);

            listTag.add(residue);
        });
        return (CompoundTag) tag.put("Residues", listTag);
    }

    @Override
    public void load(CompoundTag tag) {
        ListTag listTag = tag.getList("Residues", Tag.TAG_COMPOUND);

        int totalAmount = 0;

        for (Tag entry : listTag) {
            if (entry instanceof CompoundTag residue) {
                int amount = residue.getInt("Amount");

                this.residueTypeAmountMap.put(new ResidueType(residue.getString("Name")), amount);
                totalAmount += amount;
            }
        }

        this.totalAmount = totalAmount;
    }

    @Override
    public boolean shouldBeSaved() {
        return this.totalAmount != 0;
    }

    public int getTotalAmount() {
        return this.totalAmount;
    }

    public Map<ResidueType, Integer> getResidueTypeAmountMap() {
        return this.residueTypeAmountMap;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
