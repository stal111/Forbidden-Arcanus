package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.overlay.FlightTimerOverlay;
import com.stal111.forbidden_arcanus.client.gui.overlay.ObsidianSkullOverlay;
import com.stal111.forbidden_arcanus.client.gui.overlay.SanityMeterOverlay;
import com.stal111.forbidden_arcanus.client.gui.screen.ClibanoScreen;
import com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen;
import com.stal111.forbidden_arcanus.client.tooltip.ClientEdelwoodBucketTooltip;
import com.stal111.forbidden_arcanus.client.tooltip.EdelwoodBucketTooltip;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.common.item.SpectralEyeAmuletItem;
import com.stal111.forbidden_arcanus.common.item.UtremJarItem;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModContainers;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import com.stal111.forbidden_arcanus.util.FullbrightBakedModel;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author stal111
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
        modEventBus.addListener(this::onRegisterGuiOverlays);
        modEventBus.addListener(this::onRegisterTooltipComponents);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContainers.HEPHAESTUS_FORGE.get(), HephaestusForgeScreen::new);
            MenuScreens.register(ModContainers.CLIBANO.get(), ClibanoScreen::new);

            Sheets.addWoodType(ModWoodTypes.FUNGYSS);
            Sheets.addWoodType(ModWoodTypes.CHERRY);
            Sheets.addWoodType(ModWoodTypes.AURUM);
            Sheets.addWoodType(ModWoodTypes.EDELWOOD);
        });

        this.registerModelOverride(ModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE, StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.ACTIVATED, true).build(), base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_chiseled_polished_darkstone_layer")));
        this.registerModelOverride(ModBlocks.XPETRIFIED_ORE, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/xpetrified_ore_layer")));
        this.registerModelOverride(ModBlocks.ARCANE_CRYSTAL_ORE, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_ore/arcane_crystal_ore_layer")));
        this.registerModelOverride(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_ore/arcane_crystal_ore_layer")));
        this.registerModelOverride(ModBlocks.RUNIC_STONE, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_stone/rune_layer")));
        this.registerModelOverride(ModBlocks.RUNIC_DEEPSLATE, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_stone/rune_layer")));
        this.registerModelOverride(ModBlocks.RUNIC_DARKSTONE, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/runic_stone/rune_layer")));
        this.registerModelOverride(ModBlocks.ARCANE_CRYSTAL_BLOCK, base -> new FullbrightBakedModel(base, new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_block")));
        this.registerModelOverride(ModBlocks.ARCANE_CRYSTAL_OBELISK, base -> new FullbrightBakedModel(base,
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_lower_layer"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_middle"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_upper"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/arcane_crystal_obelisk_top")
        ));
        this.registerModelOverride(ModBlocks.HEPHAESTUS_FORGE, base -> new FullbrightBakedModel(base,
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/hephaestus_forge_side_layer"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/hephaestus_forge_top_layer")
        ));

        //ItemProperties.register(ModItems.FORBIDDENMICON.get(), new ResourceLocation("open"), (stack, world, entity) -> entity != null && ForbiddenmiconItem.isOpen(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.SPECTRAL_EYE_AMULET.get(), new ResourceLocation("deactivated"), (stack, world, entity, seed) -> entity != null && stack.getItem() instanceof SpectralEyeAmuletItem item && item.isDeactivated(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.OBSIDIAN_SKULL_SHIELD.get(), new ResourceLocation("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.UTREM_JAR.get(), new ResourceLocation("water"), (stack, world, entity, seed) -> stack.getItem() instanceof UtremJarItem item && item.getFluid(stack) == Fluids.WATER ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.UTREM_JAR.get(), new ResourceLocation("lava"), (stack, world, entity, seed) -> stack.getItem() instanceof UtremJarItem item && item.getFluid(stack) == Fluids.LAVA ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BLOOD_TEST_TUBE.get(), new ResourceLocation("amount"), (stack, world, entity, seed) -> (BloodTestTubeItem.getBlood(stack) / (float) BloodTestTubeItem.MAX_BLOOD));
    }

    @SubscribeEvent
    public void onModelBake(ModelEvent.BakingCompleted event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();

        FullbrightBakedModel.invalidateCache();

        for (Triple<Block, StatePropertiesPredicate, Function<BakedModel, BakedModel>> triple : this.bakedModelOverrideRegistry) {
            triple.getLeft().getStateDefinition().getPossibleStates().stream().filter(state -> triple.getMiddle().matches(state)).map(BlockModelShaper::stateToModelLocation).forEach(modelResourceLocation -> {
                modelRegistry.put(modelResourceLocation, triple.getRight().apply(modelRegistry.get(modelResourceLocation)));
            });
        }
    }

    @SubscribeEvent
    public void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "flight_timer", new FlightTimerOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "sanity_meter", new SanityMeterOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "obsidian_skull", new ObsidianSkullOverlay());
    }

    @SubscribeEvent
    public void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(EdelwoodBucketTooltip.class, ClientEdelwoodBucketTooltip::new);
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        ResourceLocation textureLocation = event.getAtlas().location();

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
