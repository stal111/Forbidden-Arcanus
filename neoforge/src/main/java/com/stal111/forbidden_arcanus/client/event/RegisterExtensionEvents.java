package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.client.renderer.item.EctoBlasterClientItemExtensions;
import com.stal111.forbidden_arcanus.client.renderer.item.WandClientItemExtensions;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryEntry;

/**
 * @author stal111
 * @since 20.07.2024
 */
@EventBusSubscriber(value = Dist.CLIENT)
public class RegisterExtensionEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new WandClientItemExtensions(),
                ModItems.WANDS.stream().map(ItemRegistryEntry::get).toArray(Item[]::new)
        );
        event.registerItem(new EctoBlasterClientItemExtensions(), ModItems.ECTO_BLASTER.get());
    }
}
