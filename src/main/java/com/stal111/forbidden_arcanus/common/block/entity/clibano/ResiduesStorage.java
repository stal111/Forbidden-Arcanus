package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.SerializableComponent;

import java.util.Map;

/**
 * @author stal111
 * @since 2022-06-29
 */
public class ResiduesStorage implements SerializableComponent {

    private static final Codec<Map<ResidueType, Integer>> MAP_CODEC = Codec.unboundedMap(ResidueType.CODEC.xmap(Holder::value, Holder::direct), Codec.INT);

    public static final int MAX_AMOUNT = 64;

    private final Map<ResidueType, Integer> residueTypeAmountMap = new Object2IntArrayMap<>();

    private int totalAmount = 0;

    public void tick(Level level, ClibanoMainBlockEntity blockEntity) {
        this.residueTypeAmountMap.forEach((type, amount) -> {
            ResidueType.CombineInfo combineInfo = type.combineInfo();

            if (amount >= combineInfo.requiredAmount()) {
                ItemStack resultStack = blockEntity.getStack(ClibanoMenu.RESULT_SLOTS.getFirst());
                ItemStack secondResultStack = blockEntity.getStack(ClibanoMenu.RESULT_SLOTS.getSecond());

                ItemStack stack = combineInfo.result().copy();
                boolean flag = true;

                if (ItemStack.isSameItem(resultStack, stack) && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
                    resultStack.grow(stack.getCount());
                } else if (ItemStack.isSameItem(secondResultStack, stack) && secondResultStack.getCount() + stack.getCount() <= secondResultStack.getMaxStackSize()) {
                    secondResultStack.grow(stack.getCount());
                } else if (resultStack.isEmpty()) {
                    blockEntity.setStack(ClibanoMenu.RESULT_SLOTS.getFirst(), stack.copy());
                } else if (secondResultStack.isEmpty()) {
                    blockEntity.setStack(ClibanoMenu.RESULT_SLOTS.getSecond(), stack.copy());
                } else {
                    flag = false;
                }

                if (flag) {
                    this.residueTypeAmountMap.merge(type, -combineInfo.requiredAmount(), Integer::sum);

                    this.totalAmount -= combineInfo.requiredAmount();
                }
            }
        });
    }

    public void increaseType(Holder<ResidueType> residueType, int amount) {
        if (this.totalAmount >= MAX_AMOUNT) {
            return;
        }

        int amountToFill = Math.min(MAX_AMOUNT - this.totalAmount, amount);

        this.residueTypeAmountMap.merge(residueType.value(), amountToFill, Integer::sum);
        this.totalAmount += amountToFill;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        MAP_CODEC.encodeStart(NbtOps.INSTANCE, this.residueTypeAmountMap).result().ifPresent(listTag -> tag.put("Residues", listTag));

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        ListTag listTag = tag.getList("Residues", Tag.TAG_COMPOUND);

        this.residueTypeAmountMap.clear();
        this.totalAmount = 0;

        MAP_CODEC.decode(NbtOps.INSTANCE, listTag).resultOrPartial(Util.prefix("Residues Storage", ForbiddenArcanus.LOGGER::error)).ifPresent(mapTagPair -> {
            this.residueTypeAmountMap.putAll(mapTagPair.getFirst());
        });

        this.residueTypeAmountMap.forEach((residueType, amount) -> {
            this.totalAmount += amount;
        });
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
