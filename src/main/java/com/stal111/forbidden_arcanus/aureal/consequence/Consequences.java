package com.stal111.forbidden_arcanus.aureal.consequence;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Consequences
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.consequence.Consequences
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-04
 */
public class Consequences {

    private static final List<IConsequenceType> CONSEQUENCES = new ArrayList<>();

    public static final ChangeWeatherConsequence.Type CHANGE_WEATHER = new ChangeWeatherConsequence.Type();
    public static final EffectConsequence.Type EFFECT = new EffectConsequence.Type();
    public static final SoundConsequence.Type SOUND = new SoundConsequence.Type();

    public static void registerConsequences() {
        register(CHANGE_WEATHER);
        register(EFFECT);
        register(SOUND);
    }

    private static void register(IConsequenceType consequence) {
        CONSEQUENCES.add(consequence);
    }

    public static List<IConsequenceType> getConsequences() {
        return CONSEQUENCES;
    }

    public static IConsequenceType getRandomConsequence(Random random) {
        return getConsequences().get(random.nextInt(getConsequences().size()));
    }

    public static IConsequenceType getByName(ResourceLocation name) {
        for (IConsequenceType consequence : getConsequences()) {
            if (consequence.getName().equals(name)) {
                return consequence;
            }
        }
        return null;
    }
}
