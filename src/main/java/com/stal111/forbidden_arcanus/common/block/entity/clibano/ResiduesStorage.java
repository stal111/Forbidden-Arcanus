package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.SerializableComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2022-06-29
 */
public class ResiduesStorage implements SerializableComponent {

    public static final Codec<Map<Holder<ResidueType>, Integer>> MAP_CODEC = Codec.unboundedMap(ResidueType.CODEC, Codec.INT);

    public static final int MAX_AMOUNT = 64;
    private static final String RESIDUES_TAG = "Residues";

    private Supplier<Map<Holder<ResidueType>, Integer>> residueTypeAmountMap = Suppliers.memoize(HashMap::new);

    private int totalAmount = 0;
    private final Supplier<Level> levelAccessor;

    public ResiduesStorage(Supplier<Level> levelAccessor) {
        this.levelAccessor = levelAccessor;
    }

    public void tick(ClibanoMainBlockEntity blockEntity) {
        this.residueTypeAmountMap.get().forEach((type, amount) -> {
            ResidueType.CombineInfo combineInfo = type.value().combineInfo();

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
                    this.residueTypeAmountMap.get().merge(type, -combineInfo.requiredAmount(), Integer::sum);

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

        this.residueTypeAmountMap.get().merge(residueType, amountToFill, Integer::sum);
        this.totalAmount += amountToFill;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        MAP_CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, this.levelAccessor.get().registryAccess()), this.residueTypeAmountMap.get()).result().ifPresent(listTag -> {
            tag.put(RESIDUES_TAG, listTag);
        });
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        this.totalAmount = 0;

        this.residueTypeAmountMap = Suppliers.memoize(() -> {
            Optional<Map<Holder<ResidueType>, Integer>> optional = MAP_CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, this.levelAccessor.get().registryAccess()), tag.getCompound(RESIDUES_TAG)).resultOrPartial(Util.prefix("Residues Storage: ", ForbiddenArcanus.LOGGER::error));

            optional.ifPresent(holderIntegerMap -> {
                holderIntegerMap.forEach((residueType, amount) -> {
                    this.totalAmount += amount;
                });
            });

            return optional.map(HashMap::new).orElseGet(HashMap::new);
        });
    }

    @Override
    public boolean shouldBeSaved() {
        return this.totalAmount != 0;
    }

    public int getTotalAmount() {
        return this.totalAmount;
    }

    public Map<Holder<ResidueType>, Integer> getResidueTypeAmountMap() {
        return this.residueTypeAmountMap.get();
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
