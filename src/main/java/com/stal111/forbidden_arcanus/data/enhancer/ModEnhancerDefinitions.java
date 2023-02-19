package com.stal111.forbidden_arcanus.data.enhancer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.MultiplyRequiredEssenceEffect;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

import java.util.List;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class ModEnhancerDefinitions extends DatapackRegistryClass<EnhancerDefinition> {

    public static final DatapackRegistryHelper<EnhancerDefinition> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(FARegistries.ENHANCER_DEFINITION);

    public static final ResourceKey<EnhancerDefinition> ARTISAN_RELIC = HELPER.createKey("artisan_relic");

    public ModEnhancerDefinitions(DataProviderInfo info, BootstapContext<EnhancerDefinition> context) {
        super(info, context);
    }

    @Override
    public void bootstrap(BootstapContext<EnhancerDefinition> context) {
        context.register(ARTISAN_RELIC, new EnhancerDefinition(ModItems.ARTISAN_RELIC.get(), Component.translatable("tooltip.forbidden_arcanus.artisan_relic"), List.of(new MultiplyRequiredEssenceEffect(EssenceType.EXPERIENCE, 0.75))));
    }
}
