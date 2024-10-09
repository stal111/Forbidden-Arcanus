package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.client.renderer.block.EssenceUtremJarRenderer;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 20.07.2024
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class RegisterExtensionEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(() -> new EssenceUtremJarRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer.get();
            }
        }, ModItems.ESSENCE_UTREM_JAR.get());
    }
}
