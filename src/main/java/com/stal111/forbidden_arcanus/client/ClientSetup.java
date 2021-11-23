package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.other.ModContainers;
import com.stal111.forbidden_arcanus.init.other.ModWoodTypes;
import com.stal111.forbidden_arcanus.util.FullbrightBakedModel;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Client Setup
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.ClientSetup
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-13
 */
public class ClientSetup {

    private final List<Triple<Block, StatePropertiesPredicate, Function<BakedModel, BakedModel>>> bakedModelOverrideRegistry = new ArrayList<>();

    public ClientSetup() {
        Minecraft minecraft = Minecraft.getInstance();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onModelBake);
        modEventBus.addListener(this::onTextureStitch);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContainers.HEPHAESTUS_FORGE.get(), HephaestusForgeScreen::new);

            Sheets.addWoodType(ModWoodTypes.FUNGYSS);
            Sheets.addWoodType(ModWoodTypes.CHERRYWOOD);
            Sheets.addWoodType(ModWoodTypes.MYSTERYWOOD);
            Sheets.addWoodType(ModWoodTypes.EDELWOOD);
        });

        this.registerModelOverride(NewModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE, StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.ACTIVATED, true).build(), base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_chiseled_polished_darkstone_layer")));
        this.registerModelOverride(NewModBlocks.XPETRIFIED_ORE, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/xpetrified_ore_layer")));
        this.registerModelOverride(NewModBlocks.ARCANE_CRYSTAL_ORE, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_ore/arcane_crystal_ore_layer")));
        this.registerModelOverride(NewModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_ore/arcane_crystal_ore_layer")));
        this.registerModelOverride(NewModBlocks.RUNIC_STONE, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_stone/rune_layer")));
        this.registerModelOverride(NewModBlocks.RUNIC_DEEPSLATE, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_stone/rune_layer")));
        this.registerModelOverride(NewModBlocks.RUNIC_DARKSTONE, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_stone/rune_layer")));
        this.registerModelOverride(NewModBlocks.ARCANE_CRYSTAL_BLOCK, base -> new FullbrightBakedModel(base, true, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_block")));
        this.registerModelOverride(NewModBlocks.ARCANE_CRYSTAL_OBELISK, base -> new FullbrightBakedModel(base, true,
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_lower_layer"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_middle"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_upper"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_top")
        ));
        this.registerModelOverride(NewModBlocks.HEPHAESTUS_FORGE, base -> new FullbrightBakedModel(base, true,
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/hephaestus_forge_side_layer"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/hephaestus_forge_top_layer")
        ));

//        ClientRegistry.bindTileEntityRenderer(ModTileEntities.UTREM_JAR.get(), UtremJarTileEntityRenderer::new);
//        ClientRegistry.bindTileEntityRenderer(ModTileEntities.NIPA.get(), NipaTileEntityRenderer::new);
//        ClientRegistry.bindTileEntityRenderer(ModTileEntities.PEDESTAL.get(), PedestalTileEntityRenderer::new);
//        ClientRegistry.bindTileEntityRenderer(ModTileEntities.HEPHAESTUS_FORGE.get(), HephaestusForgeTileEntityRenderer::new);
//
//        ClientHelper.registerTileEntityUpdatePacket(tileEntity -> tileEntity instanceof UtremJarTileEntity);
//        ClientHelper.registerTileEntityUpdatePacket(tileEntity -> tileEntity instanceof NipaTileEntity);

      //  ItemProperties.register(NewModItems.UTREM_JAR.get(), new ResourceLocation("water"), (stack, world, entity) -> UtremJarItem.getFluid(stack) == Fluids.WATER ? 1.0F : 0.0F);
       // ItemProperties.register(NewModItems.UTREM_JAR.get(), new ResourceLocation("lava"), (stack, world, entity) -> UtremJarItem.getFluid(stack) == Fluids.LAVA ? 1.0F : 0.0F);
       // ItemProperties.register(NewModItems.BLOOD_TEST_TUBE.get(), new ResourceLocation("amount"), (stack, world, entity) -> (BloodTestTubeItem.getBlood(stack) / (float) BloodTestTubeItem.MAX_BLOOD));
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();

        FullbrightBakedModel.invalidateCache();

        for (Triple<Block, StatePropertiesPredicate, Function<BakedModel, BakedModel>> triple : this.bakedModelOverrideRegistry) {
            triple.getLeft().getStateDefinition().getPossibleStates().stream().filter(state -> triple.getMiddle().matches(state)).map(BlockModelShaper::stateToModelLocation).forEach(modelResourceLocation -> {
                modelRegistry.put(modelResourceLocation, triple.getRight().apply(modelRegistry.get(modelResourceLocation)));
            });
        }
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        ResourceLocation textureLocation = event.getMap().location();

        if (textureLocation.equals(TextureAtlas.LOCATION_BLOCKS)) {
            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/obsidian_skull_shield"));
        }
    }

    /**
     * Registers a model override under the mods MOD_ID. All states of the Block will be overridden.
     */
    private<T extends Block> void registerModelOverride(RegistryObject<T> block, Function<BakedModel, BakedModel> function) {
        this.registerModelOverride(block, StatePropertiesPredicate.ANY, function);
    }

    /**
     * Registers a model override under the mods MOD_ID.
     */
    private<T extends Block> void registerModelOverride(RegistryObject<T> block, StatePropertiesPredicate predicate, Function<BakedModel, BakedModel> function) {
        this.bakedModelOverrideRegistry.add(Triple.of(block.get(), predicate, function));
    }
}
