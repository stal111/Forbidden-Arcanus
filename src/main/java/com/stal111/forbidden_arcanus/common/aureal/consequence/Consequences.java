package com.stal111.forbidden_arcanus.common.aureal.consequence;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Consequences <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.consequence.Consequences
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-04
 */
public class Consequences {

    private static final List<ConsequenceType<?>> CONSEQUENCES = new ArrayList<>();

    public static final ConsequenceType<ChangeWeatherConsequence> CHANGE_WEATHER = new ConsequenceType<>("change_weather", ChangeWeatherConsequence::new);
    public static final ConsequenceType<EffectConsequence> EFFECT = new ConsequenceType<>("effect", EffectConsequence::new);
    public static final ConsequenceType<SoundConsequence> SOUND = new ConsequenceType<>("sound", SoundConsequence::new);

    public static void registerConsequences() {
        register(CHANGE_WEATHER);
        register(EFFECT);
        register(SOUND);
    }

    private static void register(ConsequenceType<?> consequence) {
        CONSEQUENCES.add(consequence);
    }

    public static List<ConsequenceType<?>> getConsequences() {
        return CONSEQUENCES;
    }

    public static ConsequenceType<?> getRandomConsequence(Random random) {
        return getConsequences().get(random.nextInt(getConsequences().size()));
    }

    public static ConsequenceType<?> getByName(ResourceLocation name) {
        for (ConsequenceType<?> consequence : getConsequences()) {
            if (consequence.name().equals(name)) {
                return consequence;
            }
        }
        return null;
    }
}
