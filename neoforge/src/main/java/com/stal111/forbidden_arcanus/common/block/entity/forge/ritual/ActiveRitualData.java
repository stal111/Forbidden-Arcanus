package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents the data for an active ritual.
 * It includes the ritual itself, a counter, and a lightning counter.
 *
 * @author stal111
 * @since 21.04.2024
 */
public class ActiveRitualData {

    public static final Codec<ActiveRitualData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ritual.CODEC.fieldOf("ritual").forGetter(data -> data.ritual),
            Codec.INT.fieldOf("counter").forGetter(data -> data.counter),
            Codec.INT.fieldOf("lightning_counter").forGetter(data -> data.lightningCounter),
            EntityReference.<ServerPlayer>codec().fieldOf("started_by").forGetter(data -> data.startedBy)
    ).apply(instance, ActiveRitualData::new));

    private final Holder<Ritual> ritual;
    private int counter;
    private int lightningCounter;
    private final EntityReference<ServerPlayer> startedBy;

    /**
     * @param ritual The ritual being performed
     * @param counter A counter for the ritual
     * @param lightningCounter A counter for the lightning
     */
    public ActiveRitualData(Holder<Ritual> ritual, int counter, int lightningCounter, EntityReference<ServerPlayer> startedBy) {
        this.ritual = ritual;
        this.counter = counter;
        this.lightningCounter = lightningCounter;
        this.startedBy = startedBy;
    }

    /**
     * Factory method to create a new ActiveRitualData with counters set to 0
     *
     * @param ritual The ritual being performed
     * @return A new ActiveRitualData instance
     */
    public static ActiveRitualData create(Holder<Ritual> ritual, ServerPlayer startedBy) {
        return new ActiveRitualData(ritual, 0, 0, EntityReference.of(startedBy));
    }

    /**
     * Calculate the progress of the ritual
     *
     * @return The progress of the ritual as a float between 0 and 1
     */
    public float calculateRitualProgress() {
        return this.counter / (float) this.ritual.value().duration();
    }

    /**
     * Get the ritual being performed
     */
    public Ritual getRitual() {
        return this.ritual.value();
    }

    /**
     * Get the ritual counter
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * Increment the ritual counter
     */
    public void incrementCounter() {
        this.counter++;
    }

    /**
     * Get the lightning counter
     */
    public int getLightningCounter() {
        return this.lightningCounter;
    }

    /**
     * Increment the lightning counter
     */
    public void incrementLightningCounter() {
        this.lightningCounter++;
    }

    public ServerPlayer getStartedBy(Level level) {
        return EntityReference.get(this.startedBy, level, ServerPlayer.class);
    }

    @Nullable
    public ResourceLocation getRitualId() {
        return this.ritual.unwrapKey().map(ResourceKey::location).orElse(null);
    }
}
