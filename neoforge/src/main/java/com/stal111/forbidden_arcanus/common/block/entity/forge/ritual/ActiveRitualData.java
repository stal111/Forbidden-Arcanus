package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;

/**
 * This class represents the data for an active ritual.
 * It includes the ritual itself, a counter, and a lightning counter.
 *
 * @author stal111
 * @since 21.04.2024
 */
public class ActiveRitualData {

    public static final Codec<ActiveRitualData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ritual.CODEC.fieldOf("ritual").forGetter(data -> {
                return data.ritual;
            }),
            Codec.INT.fieldOf("counter").forGetter(data -> {
                return data.counter;
            }),
            Codec.INT.fieldOf("lightning_counter").forGetter(data -> {
                return data.lightningCounter;
            })
    ).apply(instance, ActiveRitualData::new));

    private final Holder<Ritual> ritual;
    private int counter;
    private int lightningCounter;

    /**
     * @param ritual The ritual being performed
     * @param counter A counter for the ritual
     * @param lightningCounter A counter for the lightning
     */
    public ActiveRitualData(Holder<Ritual> ritual, int counter, int lightningCounter) {
        this.ritual = ritual;
        this.counter = counter;
        this.lightningCounter = lightningCounter;
    }

    /**
     * Factory method to create a new ActiveRitualData with counters set to 0
     *
     * @param ritual The ritual being performed
     * @return A new ActiveRitualData instance
     */
    public static ActiveRitualData create(Holder<Ritual> ritual) {
        return new ActiveRitualData(ritual, 0, 0);
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
}
