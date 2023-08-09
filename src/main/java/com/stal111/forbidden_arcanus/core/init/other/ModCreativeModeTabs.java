package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.api.common.item.tab.CreativeTabFactory;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-07
 */
public class ModCreativeModeTabs implements RegistryClass {

    public static final MappedRegistryHelper<CreativeModeTab> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CREATIVE_MODE_TAB);

    public static final RegistryEntry<CreativeModeTab> MAIN = HELPER.register("main", CreativeTabFactory.create(builder -> {
                builder.icon(() -> new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()))
                        .title(Component.translatable("itemGroup.valhelsia_furniture"))
                        .displayItems((itemDisplayParameters, output) -> {
                            ForbiddenArcanus.REGISTRY_MANAGER.getItemHelper().getRegistryEntries().forEach(entry -> {
                                output.accept(entry.get());
                            });
                        });
            })
    );

}
