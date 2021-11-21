package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ModEntities.initModels();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::stitchTextures);

        //ItemProperties.register(ModItems.FORBIDDENMICON.get(), new ResourceLocation("open"), (stack, world, entity) -> entity != null && ForbiddenmiconItem.isOpen(stack) ? 1.0F : 0.0F);
       // ItemProperties.register(ModItems.SPECTRAL_EYE_AMULET.get(), new ResourceLocation("deactivated"), (stack, world, entity) -> entity != null && SpectralEyeAmuletItem.isDeactivated(stack) ? 1.0F : 0.0F);
       // ItemProperties.register(NewModItems.OBSIDIAN_SKULL_SHIELD.get(), new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent e) {
        for (ModBlocks block : ModBlocks.values()) {
            if (block.needsSpecialRender()) {
                RenderUtils.setRenderLayer(block, block.getRenderType());
            }
        }

      //  ClientRegistry.bindTileEntityRenderer(ModTileEntities.SIGN.get(), SignRenderer::new);
       // ClientRegistry.bindTileEntityRenderer(ModTileEntities.BLACK_HOLE.get(), BlackHoleTileEntityRenderer::new);
       // ClientRegistry.bindTileEntityRenderer(ModTileEntities.OBSIDIAN_SKULL.get(), ObsidianSkullTileEntityRenderer::new);
    }

    @Override
    public Level getClientWorld() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public void displayForbiddenmiconScreen(ItemStack stack) {
        Minecraft.getInstance().setScreen(new ForbiddenmiconScreen(stack));
    }

    public void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getMap().location().equals(Sheets.SIGN_SHEET)) {
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/signs/edelwood"));
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/signs/cherrywood"));
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/signs/mysterywood"));
        }
    }
}
