package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.recipe.CombineResiduesRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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

    private static final Codec<Object2IntMap.Entry<ResidueType>> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResidueType.CODEC.fieldOf("type").forGetter(residueTypeBasicEntry -> Holder.direct(residueTypeBasicEntry.getKey())),
            Codec.INT.fieldOf("amount").forGetter(Object2IntMap.Entry::getIntValue)
    ).apply(instance, (residueTypeHolder, amount) -> new AbstractObject2IntMap.BasicEntry<>(residueTypeHolder.value(), amount)));

    public static final int MAX_AMOUNT = 64;

    private final Map<ResidueType, Integer> residueTypeAmountMap = new Object2IntArrayMap<>();

    private int totalAmount = 0;

    public void tick(Level level, ClibanoMainBlockEntity blockEntity) {
        level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COMBINE_RESIDUES.get()).forEach(holder -> {
            CombineResiduesRecipe recipe = holder.value();
            ResidueType type = recipe.getResidue();

            if (this.residueTypeAmountMap.getOrDefault(type, 0) >= recipe.getResidueAmount()) {
                ItemStack resultStack = blockEntity.getStack(ClibanoMenu.RESULT_SLOTS.getFirst());
                ItemStack secondResultStack = blockEntity.getStack(ClibanoMenu.RESULT_SLOTS.getSecond());

                ItemStack stack = recipe.getResultItem(level.registryAccess());
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
                    this.residueTypeAmountMap.computeIfPresent(type, (residueType, value) -> {
                        this.totalAmount -= recipe.getResidueAmount();

                        return value - recipe.getResidueAmount();
                    });
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
