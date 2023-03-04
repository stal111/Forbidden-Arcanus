package com.stal111.forbidden_arcanus.data.enhancer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.common.item.enhancer.MultiplyRequiredEssenceEffect;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.TimeCondition;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

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

    public static final DatapackRegistryHelper<EnhancerDefinition> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(FARegistries.ENHANCER_DEFINITION);

    public static final ResourceKey<EnhancerDefinition> ARTISAN_RELIC = HELPER.createKey("artisan_relic");
    public static final ResourceKey<EnhancerDefinition> CRESCENT_MOON = HELPER.createKey("crescent_moon");
    public static final ResourceKey<EnhancerDefinition> CRIMSON_STONE = HELPER.createKey("crimson_stone");

    public ModEnhancerDefinitions(DataProviderInfo info, BootstapContext<EnhancerDefinition> context) {
        super(info, context);
    }

    @Override
    public void bootstrap(BootstapContext<EnhancerDefinition> context) {
        context.register(ARTISAN_RELIC, new EnhancerDefinition(ModItems.ARTISAN_RELIC.get(), this.generateDescription("artisan_relic", EnhancerTarget.HEPHAESTUS_FORGE, EnhancerTarget.CLIBANO), List.of(new MultiplyRequiredEssenceEffect(List.of(), EssenceType.EXPERIENCE, 0.75))));
        context.register(CRESCENT_MOON, new EnhancerDefinition(ModItems.CRESCENT_MOON.get(), this.generateDescription("crescent_moon", EnhancerTarget.HEPHAESTUS_FORGE), List.of(new MultiplyRequiredEssenceEffect(List.of(new TimeCondition(23000, 23999)), EssenceType.AUREAL, 0.9), new MultiplyRequiredEssenceEffect(List.of(new TimeCondition(0, 12999)), EssenceType.AUREAL, 0.9), new MultiplyRequiredEssenceEffect(List.of(new TimeCondition(13000, 22999)), EssenceType.AUREAL, 0.99))));
        context.register(CRIMSON_STONE, new EnhancerDefinition(ModItems.CRIMSON_STONE.get(), this.generateDescription("crimson_stone", EnhancerTarget.HEPHAESTUS_FORGE, EnhancerTarget.CLIBANO), List.of(new MultiplyRequiredEssenceEffect(List.of(), EssenceType.SOULS, 0.5))));
    }

    private Map<EnhancerTarget, Component> generateDescription(String key, EnhancerTarget... targets) {
        return Arrays.stream(targets).collect(Collectors.toMap(Function.identity(), enhancerTarget -> Component.translatable("item.forbidden_arcanus.enhancer." + key + "." + enhancerTarget.getSerializedName())));
    }
}
