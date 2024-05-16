package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

/**
 * @author stal111
 * @since 2022-06-29
 */
public class ResiduesStorage {

    public static final Codec<Object2IntOpenHashMap<Holder<ResidueType>>> MAP_CODEC = Codec.unboundedMap(ResidueType.CODEC, Codec.INT).xmap(Object2IntOpenHashMap::new, Function.identity());

    public static final StreamCodec<RegistryFriendlyByteBuf, ResiduesStorage> STREAM_CODEC = ByteBufCodecs.map(
            Object2IntOpenHashMap::new,
            ByteBufCodecs.holderRegistry(FARegistries.RESIDUE_TYPE),
            ByteBufCodecs.INT
    ).map(ResiduesStorage::new, residuesStorage -> residuesStorage.residueTypeAmountMap);

    public static final int MAX_AMOUNT = 64;
    private static final String RESIDUES_TAG = "residues";

    private final Object2IntOpenHashMap<Holder<ResidueType>> residueTypeAmountMap;

    private int totalAmount = 0;

    public ResiduesStorage(Object2IntOpenHashMap<Holder<ResidueType>> map) {
        this.residueTypeAmountMap = map;
        this.totalAmount = map.values().intStream().sum();
    }

    public ResiduesStorage() {
        this.residueTypeAmountMap = new Object2IntOpenHashMap<>();
    }

    public void tick(ClibanoMainBlockEntity blockEntity) {
        this.residueTypeAmountMap.forEach((type, amount) -> {
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

        this.residueTypeAmountMap.merge(residueType, amountToFill, Integer::sum);
        this.totalAmount += amountToFill;
    }

    public CompoundTag save(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        MAP_CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), this.residueTypeAmountMap).result().ifPresent(listTag -> {
            tag.put(RESIDUES_TAG, listTag);
        });
        return tag;
    }

    public void load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.residueTypeAmountMap.clear();
        this.totalAmount = 0;

        MAP_CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), tag.getCompound(RESIDUES_TAG)).resultOrPartial(Util.prefix("Residues Storage: ", ForbiddenArcanus.LOGGER::error)).ifPresent(map -> {
            map.object2IntEntrySet().forEach(entry -> {
                this.residueTypeAmountMap.put(entry.getKey(), entry.getIntValue());
                this.totalAmount += entry.getIntValue();
            });
        });
    }

    public boolean shouldBeSaved() {
        return this.totalAmount != 0;
    }

    public int getTotalAmount() {
        return this.totalAmount;
    }

    public Object2IntOpenHashMap<Holder<ResidueType>> getResidueTypeAmountMap() {
        return this.residueTypeAmountMap;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
