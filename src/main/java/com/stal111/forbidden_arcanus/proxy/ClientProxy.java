package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.util.BakedModelOverrideRegistry;
import com.stal111.forbidden_arcanus.util.FullbrightBakedModel;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {

    public static BakedModelOverrideRegistry bakedModelOverrideRegistry = new BakedModelOverrideRegistry();

    @Override
    public void init() {
        ModEntities.initModels();
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.SIGN, SignTileEntityRenderer::new);
      //  ClientRegistry.bindTileEntityRenderer(DarkBeaconTileEntity.class, new DarkBeaconTileEntityRenderer());
      //  ScreenManager.registerFactory(ModContainers.dark_beacon, DarkBeaconScreen::new);

        bakedModelOverrideRegistry.add(new ResourceLocation(Main.MOD_ID, "arcane_crystal_ore"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(Main.MOD_ID, "block/arcane_crystal_ore/arcane_crystal_ore_layer")));
        bakedModelOverrideRegistry.add(new ResourceLocation(Main.MOD_ID, "arcane_crystal_block"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(Main.MOD_ID, "block/arcane_crystal_block")));
        bakedModelOverrideRegistry.add(new ResourceLocation(Main.MOD_ID, "runestone"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(Main.MOD_ID, "block/runestone/cutout")));
        bakedModelOverrideRegistry.add(new ResourceLocation(Main.MOD_ID, "dark_runestone"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(Main.MOD_ID, "block/runestone/cutout")));
        bakedModelOverrideRegistry.add(new ResourceLocation(Main.MOD_ID, "arcane_crystal_obelisk"), (base, registry) -> new FullbrightBakedModel(base, true,
                new ResourceLocation(Main.MOD_ID, "block/arcane_crystal_obelisk_lower_layer"),
                new ResourceLocation(Main.MOD_ID, "block/arcane_crystal_obelisk_middle"),
                new ResourceLocation(Main.MOD_ID, "block/arcane_crystal_obelisk_upper"),
                new ResourceLocation(Main.MOD_ID, "block/arcane_crystal_obelisk_top")));

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onModelBake);

        for (ModBlocks block : ModBlocks.values()) {
            if (block.needsSpecialRender()) {
                RenderUtils.setRenderLayer(block, block.getRenderType());
            }
        }
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent e) {
        for (ResourceLocation id : e.getModelRegistry().keySet()) {
            BakedModelOverrideRegistry.BakedModelOverrideFactory factory = ClientProxy.bakedModelOverrideRegistry.get(new ResourceLocation(id.getNamespace(), id.getPath()));

            if (factory != null) {
                e.getModelRegistry().put(id, factory.create(e.getModelRegistry().get(id), e.getModelRegistry()));
            }
        }
    }
}
