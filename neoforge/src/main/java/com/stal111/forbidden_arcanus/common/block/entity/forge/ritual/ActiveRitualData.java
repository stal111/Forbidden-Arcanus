package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.advancements.critereon.FACriteriaTriggers;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class ActiveRitualData {

    public static final Codec<ActiveRitualData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ritual.CODEC.fieldOf("ritual").forGetter(data -> data.ritual),
            Codec.INT.fieldOf("ticks").forGetter(data -> data.ticks)
    ).apply(instance, ActiveRitualData::new));

    private final Holder<Ritual> ritual;
    private int ticks;

    /**
     * @param ritual The ritual being performed
     * @param ticks The elapsed ticks for the ritual
     */
    public ActiveRitualData(Holder<Ritual> ritual, int ticks) {
        this.ritual = ritual;
        this.ticks = ticks;
    }

    /**
     * Factory method to create a new ActiveRitualData with ticks set to 0
     *
     * @param ritual The ritual being performed
     * @return A new ActiveRitualData instance
     */
    public static ActiveRitualData create(Holder<Ritual> ritual) {
        return new ActiveRitualData(ritual, 0);
    }

    /**
     * Calculate the progress of the ritual
     *
     * @return The progress of the ritual as a float between 0 and 1
     */
    public float calculateRitualProgress() {
        return this.ticks / (float) this.ritual.value().duration();
    }

    /**
     * Get the ritual being performed
     */
    public Ritual getRitual() {
        return this.ritual.value();
    }

    /**
     * Get the elapsed ritual ticks
     */
    public int getTicks() {
        return this.ticks;
    }

    /**
     * Increment the ritual ticks
     */
    public void incrementTicks() {
        this.ticks++;
    }

    @Nullable
    public Identifier getRitualId() {
        return this.ritual.unwrapKey().map(ResourceKey::identifier).orElse(null);
    }

    public ItemStack finish(ServerLevel level, BlockPos pos, @Nullable ServerPlayer startedBy, ItemStack mainIngredient) {
        if (startedBy != null && this.getRitualId() != null) {
            FACriteriaTriggers.RITUAL.get().trigger(startedBy, this.getRitualId());
        }

        RitualResult result = this.getRitual().result();

        result.executeLevelEffect(level, pos);

        return result.getResultItem(mainIngredient);
    }

    public boolean isStillValid(ItemStack mainItem, Collection<ItemStack> pedestalItems) {
        return this.getRitual().checkIngredients(pedestalItems, mainItem);
    }
}
