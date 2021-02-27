package com.stal111.forbidden_arcanus.setup;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.UtremJarTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.render.UtremJarTileEntityRenderer;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.block.UtremJarItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.helper.ClientHelper;

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
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.UTREM_JAR.get(), UtremJarTileEntityRenderer::new);

        ClientHelper.registerTileEntityUpdatePacket(tileEntity -> tileEntity instanceof UtremJarTileEntity);

        ItemModelsProperties.registerProperty(NewModItems.UTREM_JAR.get(), new ResourceLocation("water"), (stack, world, entity) -> UtremJarItem.getFluid(stack) == Fluids.WATER ? 1.0F : 0.0F);
        ItemModelsProperties.registerProperty(NewModItems.UTREM_JAR.get(), new ResourceLocation("lava"), (stack, world, entity) -> UtremJarItem.getFluid(stack) == Fluids.LAVA ? 1.0F : 0.0F);
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
