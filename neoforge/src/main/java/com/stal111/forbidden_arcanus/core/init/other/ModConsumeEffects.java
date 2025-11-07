package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.consumeeffect.AddEssenceConsumeEffect;
import com.stal111.forbidden_arcanus.common.item.consumeeffect.GiveExperienceConsumeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

public class ModConsumeEffects {

    public static final MappedRegistryHelper<ConsumeEffect.Type<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CONSUME_EFFECT_TYPE);

    public static final RegistryEntry<ConsumeEffect.Type<?>, ConsumeEffect.Type<AddEssenceConsumeEffect>> ADD_ESSENCE = HELPER.register("add_essence", () -> new ConsumeEffect.Type<>(AddEssenceConsumeEffect.CODEC, AddEssenceConsumeEffect.STREAM_CODEC));
    public static final RegistryEntry<ConsumeEffect.Type<?>, ConsumeEffect.Type<GiveExperienceConsumeEffect>> GIVE_EXPERIENCE = HELPER.register("give_experience", () -> new ConsumeEffect.Type<>(GiveExperienceConsumeEffect.CODEC, GiveExperienceConsumeEffect.STREAM_CODEC));
}
