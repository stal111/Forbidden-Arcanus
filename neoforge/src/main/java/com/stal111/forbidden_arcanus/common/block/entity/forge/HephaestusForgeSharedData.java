package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.ActiveRitualData;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.SingleSlotResourceHandler;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.MultiEssenceStorage;
import com.stal111.forbidden_arcanus.util.CodecUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class HephaestusForgeSharedData implements EssenceAccess {

    public static final MapCodec<HephaestusForgeSharedData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            HephaestusForgeTier.CODEC.fieldOf("tier").forGetter(data -> data.tier),
            MultiEssenceStorage.CODEC.fieldOf("essences").forGetter(data -> data.essenceStorage),
            ItemStack.OPTIONAL_CODEC.fieldOf("main_item").forGetter(data -> data.mainSlotInventory.getStack()),
            Ritual.CODEC.optionalFieldOf("valid_ritual").forGetter(data -> Optional.ofNullable(data.validRitual)),
            ActiveRitualData.CODEC.optionalFieldOf("active_ritual").forGetter(data -> Optional.ofNullable(data.activeRitualData))
    ).apply(instance, CodecUtils.withNullable(HephaestusForgeSharedData::new)));

    private final SingleSlotResourceHandler mainSlotInventory = new SingleSlotResourceHandler(true, _ -> this.isDirty = true);

    private HephaestusForgeTier tier;
    private MultiEssenceStorage essenceStorage;
    private @Nullable Holder<Ritual> validRitual;
    private @Nullable ActiveRitualData activeRitualData;
    boolean isDirty;

    public HephaestusForgeSharedData(HephaestusForgeTier tier, MultiEssenceStorage essenceStorage, ItemStack mainItem, @Nullable Holder<Ritual> validRitual, @Nullable ActiveRitualData activeRitualData) {
        this.tier = tier;
        this.essenceStorage = essenceStorage;
        this.mainSlotInventory.setStack(mainItem);
        this.validRitual = validRitual;
        this.activeRitualData = activeRitualData;
        this.isDirty = false;
    }

    public HephaestusForgeSharedData(HephaestusForgeTier tier) {
        this.tier = tier;
        this.essenceStorage = MultiEssenceStorage.empty(this.tier.getMaxEssences());
    }

    public void setTier(HephaestusForgeTier tier) {
        this.tier = tier;

        this.essenceStorage = this.essenceStorage.setLimits(tier.getMaxEssences());
        this.isDirty = true;
    }

    public SingleSlotResourceHandler getMainSlotInventory() {
        return this.mainSlotInventory;
    }

    public HephaestusForgeTier getTier() {
        return this.tier;
    }

    public MultiEssenceStorage getEssenceStorage() {
        return this.essenceStorage;
    }

    public Optional<Holder<Ritual>> getValidRitual() {
        return Optional.ofNullable(this.validRitual);
    }

    public Optional<ActiveRitualData> getActiveRitualData() {
        return Optional.ofNullable(this.activeRitualData);
    }

    public void setValidRitual(@Nullable Holder<Ritual> validRitual) {
        if (!Objects.equals(this.validRitual, validRitual)) {
            this.validRitual = validRitual;
            this.isDirty = true;
        }
    }

    public void setActiveRitualData(@Nullable ActiveRitualData activeRitualData) {
        if (!Objects.equals(this.activeRitualData, activeRitualData)) {
            this.activeRitualData = activeRitualData;
            this.isDirty = true;
        }
    }

    void set(HephaestusForgeSharedData from) {
        this.tier = from.tier;
        this.essenceStorage = from.essenceStorage;
        this.mainSlotInventory.setStack(from.mainSlotInventory.getStack());
        this.validRitual = from.validRitual;
        if (this.activeRitualData == null || from.activeRitualData == null || !Objects.equals(this.activeRitualData.getRitual(), from.activeRitualData.getRitual())) {
            this.activeRitualData = from.activeRitualData;
        }
        this.isDirty = false;
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
