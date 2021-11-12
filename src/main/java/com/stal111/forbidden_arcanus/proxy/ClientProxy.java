package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.util.BakedModelOverrideRegistry;
import com.stal111.forbidden_arcanus.util.FullbrightBakedModel;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {

    public static BakedModelOverrideRegistry bakedModelOverrideRegistry = new BakedModelOverrideRegistry();

    @Override
    public void init() {
        ModEntities.initModels();

        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "arcane_crystal_ore"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_ore/arcane_crystal_ore_layer")));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "arcane_crystal_block"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_block")));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "runestone"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runestone/runestone_layer")));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dark_runestone"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runestone/runestone_layer")));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "xpetrified_ore"), (base, registry) -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/xpetrified_ore_layer")));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "arcane_crystal_obelisk"), (base, registry) -> new FullbrightBakedModel(base, true,
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_lower_layer"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_middle"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_upper"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_top")
        ));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "runic_chiseled_polished_darkstone"), (base, registry) -> new FullbrightBakedModel(base, true, state -> state.getValue(ModBlockStateProperties.ACTIVATED), new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_chiseled_polished_darkstone_layer")));
        bakedModelOverrideRegistry.add(new ResourceLocation(ForbiddenArcanus.MOD_ID, "hephaestus_forge"), (base, registry) -> new FullbrightBakedModel(base, true,
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/hephaestus_forge_side_layer"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/hephaestus_forge_top_layer")
        ));

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::stitchTextures);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onModelBake);

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


        //  ClientRegistry.bindTileEntityRenderer(DarkBeaconTileEntity.class, new DarkBeaconTileEntityRenderer());
        //  ScreenManager.registerFactory(ModContainers.dark_beacon, DarkBeaconScreen::new);
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

    public void onModelBake(ModelBakeEvent e) {
        FullbrightBakedModel.invalidateCache();

        for (ResourceLocation id : e.getModelRegistry().keySet()) {
            BakedModelOverrideRegistry.BakedModelOverrideFactory factory = ClientProxy.bakedModelOverrideRegistry.get(new ResourceLocation(id.getNamespace(), id.getPath()));

            if (factory != null) {
                e.getModelRegistry().put(id, factory.create(e.getModelRegistry().get(id), e.getModelRegistry()));
            }
        }
    }

    public void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getMap().location().equals(Sheets.SIGN_SHEET)) {
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/signs/edelwood"));
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/signs/cherrywood"));
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/signs/mysterywood"));
        }
    }
}
