package com.stal111.forbidden_arcanus.setup;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Client Setup
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.setup.ClientSetup
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-13
 */
public class ClientSetup {

    public ClientSetup() {
        Minecraft minecraft = Minecraft.getInstance();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onModelBake);
        modEventBus.addListener(this::onTextureStitch);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {

    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        ResourceLocation textureLocation = event.getMap().getTextureLocation();

        if (textureLocation.equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/obsidian_skull_shield"));
        }
    }
}
