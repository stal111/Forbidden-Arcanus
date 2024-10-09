package com.stal111.forbidden_arcanus.data.enhancer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.TimeCondition;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.MultiplyRequiredEssenceEffect;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.MultiplySoulDurationEffect;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class ModEnhancerDefinitions extends DatapackRegistryClass<EnhancerDefinition> {

    public static final DatapackRegistryHelper<EnhancerDefinition> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ENHANCER_DEFINITION);

    public static final ResourceKey<EnhancerDefinition> ARTISAN_RELIC = HELPER.createKey("artisan_relic");
    public static final ResourceKey<EnhancerDefinition> CRESCENT_MOON = HELPER.createKey("crescent_moon");
    public static final ResourceKey<EnhancerDefinition> CRIMSON_STONE = HELPER.createKey("crimson_stone");
    public static final ResourceKey<EnhancerDefinition> SOUL_CRIMSON_STONE = HELPER.createKey("soul_crimson_stone");
    public static final ResourceKey<EnhancerDefinition> ELEMENTARIUM = HELPER.createKey("elementarium");
    public static final ResourceKey<EnhancerDefinition> DIVINE_PACT = HELPER.createKey("divine_pact");
    public static final ResourceKey<EnhancerDefinition> MALEDICTUS_PACT = HELPER.createKey("maledictus_pact");

    public ModEnhancerDefinitions(BootstrapContext<EnhancerDefinition> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<EnhancerDefinition> context) {
        context.register(ARTISAN_RELIC, EnhancerDefinition.create(this.generateDescription("artisan_relic", EnhancerTarget.HEPHAESTUS_FORGE, EnhancerTarget.CLIBANO), ModItems.ARTISAN_RELIC.get(), new MultiplyRequiredEssenceEffect(List.of(), EssenceType.EXPERIENCE, 0.75)));
        context.register(CRESCENT_MOON, EnhancerDefinition.create(this.generateDescription("crescent_moon", EnhancerTarget.HEPHAESTUS_FORGE), ModItems.CRESCENT_MOON.get(), new MultiplyRequiredEssenceEffect(List.of(new TimeCondition(23000, 23999)), EssenceType.AUREAL, 0.9), new MultiplyRequiredEssenceEffect(List.of(new TimeCondition(0, 12999)), EssenceType.AUREAL, 0.9), new MultiplyRequiredEssenceEffect(List.of(new TimeCondition(13000, 22999)), EssenceType.AUREAL, 0.99)));
        context.register(CRIMSON_STONE, EnhancerDefinition.create(this.generateDescription("crimson_stone", EnhancerTarget.HEPHAESTUS_FORGE, EnhancerTarget.CLIBANO), ModItems.CRIMSON_STONE.get(), new MultiplyRequiredEssenceEffect(List.of(), EssenceType.SOULS, 0.5), new MultiplySoulDurationEffect(List.of(), 1.3D)));
        context.register(SOUL_CRIMSON_STONE, EnhancerDefinition.create(this.generateDescription("soul_crimson_stone", EnhancerTarget.HEPHAESTUS_FORGE), ModItems.SOUL_CRIMSON_STONE.get(), new MultiplyRequiredEssenceEffect(List.of(), EssenceType.AUREAL, 0.0), new MultiplyRequiredEssenceEffect(List.of(), EssenceType.SOULS, 0.0), new MultiplyRequiredEssenceEffect(List.of(), EssenceType.BLOOD, 0.0), new MultiplyRequiredEssenceEffect(List.of(), EssenceType.EXPERIENCE, 0.0)));
        context.register(ELEMENTARIUM, EnhancerDefinition.create(this.generateDescription("elementarium", EnhancerTarget.HEPHAESTUS_FORGE), ModItems.ELEMENTARIUM.get()));
        context.register(DIVINE_PACT, EnhancerDefinition.create(this.generateDescription("divine_pact", EnhancerTarget.HEPHAESTUS_FORGE), ModItems.DIVINE_PACT.get()));
        context.register(MALEDICTUS_PACT, EnhancerDefinition.create(this.generateDescription("maledictus_pact", EnhancerTarget.HEPHAESTUS_FORGE), ModItems.MALEDICTUS_PACT.get()));
    }

    private Map<EnhancerTarget, Component> generateDescription(String key, EnhancerTarget... targets) {
        return Arrays.stream(targets).collect(Collectors.toMap(Function.identity(), enhancerTarget -> Component.translatable("item.forbidden_arcanus.enhancer." + key + "." + enhancerTarget.getSerializedName())));
    }
}
