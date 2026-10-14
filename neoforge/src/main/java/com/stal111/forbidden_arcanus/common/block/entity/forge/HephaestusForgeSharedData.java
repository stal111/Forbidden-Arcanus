package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.SingleSlotResourceHandler;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.MultiEssenceStorage;
import net.minecraft.world.item.ItemStack;

import java.util.function.UnaryOperator;

public class HephaestusForgeSharedData implements EssenceAccess {

    public static final MapCodec<HephaestusForgeSharedData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            HephaestusForgeLevel.CODEC.fieldOf("tier").forGetter(predicate -> predicate.forgeLevel),
            MultiEssenceStorage.CODEC.fieldOf("essences").forGetter(predicate -> predicate.essenceStorage),
            ItemStack.OPTIONAL_CODEC.fieldOf("main_item").forGetter(predicate -> predicate.mainSlotInventory.getStack())
    ).apply(instance, HephaestusForgeSharedData::new));

    private final SingleSlotResourceHandler mainSlotInventory = new SingleSlotResourceHandler(true, _ -> this.isDirty = true);

    private HephaestusForgeLevel forgeLevel;
    private MultiEssenceStorage essenceStorage;
    boolean isDirty;

    public HephaestusForgeSharedData(HephaestusForgeLevel forgeLevel, MultiEssenceStorage essenceStorage, ItemStack mainItem) {
        this.forgeLevel = forgeLevel;
        this.essenceStorage = essenceStorage;
        this.mainSlotInventory.setStack(mainItem);
    }

    public HephaestusForgeSharedData(HephaestusForgeLevel forgeLevel) {
        this.forgeLevel = forgeLevel;
        this.essenceStorage = MultiEssenceStorage.empty(this.forgeLevel.getMaxEssences());
    }

    public void updateForgeTier(HephaestusForgeLevel forgeLevel) {
        this.forgeLevel = forgeLevel;

        for (EssenceType type : EssenceType.values()) {
            this.setEssenceLimit(type, forgeLevel.getMaxAmount(type));
        }
    }

    public SingleSlotResourceHandler getMainSlotInventory() {
        return this.mainSlotInventory;
    }

    public HephaestusForgeLevel getForgeLevel() {
        return this.forgeLevel;
    }

    public MultiEssenceStorage getEssenceStorage() {
        return this.essenceStorage;
    }

    void set(HephaestusForgeSharedData from) {
        this.forgeLevel = from.forgeLevel;
        this.essenceStorage = from.essenceStorage;
        this.mainSlotInventory.setStack(from.mainSlotInventory.getStack());
    }

    @Override
    public EssenceStorage getEssence(EssenceType type) {
        return this.essenceStorage.getStorage(type);
    }

    @Override
    public void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        this.essenceStorage = this.essenceStorage.updateEssence(type, updater);

        this.isDirty = true;
    }
}
