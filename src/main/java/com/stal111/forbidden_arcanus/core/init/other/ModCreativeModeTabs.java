package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-07
 */
public class ModCreativeModeTabs implements RegistryClass {

    public static final MappedRegistryHelper<CreativeModeTab> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(Registries.CREATIVE_MODE_TAB);

//    public static final RegistryObject<CreativeModeTab> MAIN = HELPER.register("main", CreativeTabFactory.create(builder -> {
//                builder.icon(() -> new ItemStack(ModBlocks.HAY_OAK_CHAIR.get()))
//                        .title(Component.translatable("itemGroup.valhelsia_furniture"))
//                        .displayItems((itemDisplayParameters, output) -> {
//                            ValhelsiaFurniture.REGISTRY_MANAGER.getItemHelper().getRegistryEntries().forEach(entry -> {
//                                output.accept(entry.get());
//                            });
//                        });
//            })
//    );

}
