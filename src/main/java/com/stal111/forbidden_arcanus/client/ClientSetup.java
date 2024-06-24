package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.label.FlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.label.JarFlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.label.QuantumCatcherFlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.overlay.AurealMeterOverlay;
import com.stal111.forbidden_arcanus.client.gui.overlay.FlyingLabelOverlay;
import com.stal111.forbidden_arcanus.client.gui.overlay.SanityMeterOverlay;
import com.stal111.forbidden_arcanus.client.gui.screen.ClibanoScreen;
import com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen;
import com.stal111.forbidden_arcanus.client.gui.screen.research.ResearchScreen;
import com.stal111.forbidden_arcanus.client.particle.*;
import com.stal111.forbidden_arcanus.client.renderer.block.*;
import com.stal111.forbidden_arcanus.client.renderer.entity.*;
import com.stal111.forbidden_arcanus.client.tooltip.CapacityBucketTooltip;
import com.stal111.forbidden_arcanus.client.tooltip.ClientCapacityBucketTooltip;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.common.item.SpectralEyeAmuletItem;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;

import java.util.ArrayList;
import java.util.List;

import static com.stal111.forbidden_arcanus.client.model.FAModelLayers.DETAILED_OBSIDIAN_SKULL_LAYER;
import static com.stal111.forbidden_arcanus.client.model.FAModelLayers.OBSIDIAN_SKULL_LAYER;

/**
 * @author stal111
 * @since 2021-02-13
 */
public class ClientSetup {

    public static final List<FlyingLabel<?>> FLYING_LABELS = new ArrayList<>();

    public static final ItemPropertyFunction ESSENCE_AMOUNT_PROPERTY_FUNCTION = (stack, level, entity, seed) -> {
        return EssenceHelper.getEssenceStorage(stack).map(EssenceStorage::getFillPercentage).orElse(0.0F);
    };

    public ClientSetup(ClientSetupHelper helper, IEventBus modEventBus) {
        Minecraft minecraft = Minecraft.getInstance();

        helper.registerBlockEntityRenderer(ModBlockEntities.NIPA, NipaRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL, PedestalRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.BLACK_HOLE, BlackHoleRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.ESSENCE_UTREM_JAR, EssenceUtremJarRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.OBSIDIAN_SKULL, SkullBlockRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.HEPHAESTUS_FORGE, HephaestusForgeRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.RESEARCH_DESK, ResearchDeskRenderer::new);
        helper.registerBlockEntityRenderer(ModBlockEntities.QUANTUM_INJECTOR, QuantumInjectorRenderer::new);

        helper.registerEntityRenderer(ModEntities.BOOM_ARROW, BoomArrowRenderer::new);
        helper.registerEntityRenderer(ModEntities.DRACO_ARCANUS_ARROW, DracoArcanusArrowRenderer::new);
        helper.registerEntityRenderer(ModEntities.ENERGY_BALL, EnergyBallRenderer::new);
        helper.registerEntityRenderer(ModEntities.CRIMSON_LIGHTNING_BOLT, CrimsonLightningBoltRenderer::new);
        helper.registerEntityRenderer(ModEntities.BOAT, context -> new ModBoatRenderer(context, false));
        helper.registerEntityRenderer(ModEntities.CHEST_BOAT, context -> new ModBoatRenderer(context, true));
        helper.registerEntityRenderer(ModEntities.LOST_SOUL, LostSoulRenderer::new);
        helper.registerEntityRenderer(ModEntities.AUREAL_BOTTLE, ThrownItemRenderer::new);
        helper.registerEntityRenderer(ModEntities.DARK_TRADER, DarkTraderRenderer::new);

        helper.registerSkullModel(ObsidianSkullType.DEFAULT, (modelSet) -> new SkullModel(modelSet.bakeLayer(OBSIDIAN_SKULL_LAYER)));
        helper.registerSkullModel(ObsidianSkullType.CRACKED, (modelSet) -> new SkullModel(modelSet.bakeLayer(OBSIDIAN_SKULL_LAYER)));
        helper.registerSkullModel(ObsidianSkullType.FRAGMENTED, (modelSet) -> new SkullModel(modelSet.bakeLayer(OBSIDIAN_SKULL_LAYER)));
        helper.registerSkullModel(ObsidianSkullType.FADING, (modelSet) -> new SkullModel(modelSet.bakeLayer(OBSIDIAN_SKULL_LAYER)));
        helper.registerSkullModel(ObsidianSkullType.AUREALIC, (modelSet) -> new SkullModel(modelSet.bakeLayer(DETAILED_OBSIDIAN_SKULL_LAYER)));
        helper.registerSkullModel(ObsidianSkullType.ETERNAL, (modelSet) -> new SkullModel(modelSet.bakeLayer(DETAILED_OBSIDIAN_SKULL_LAYER)));

        helper.registerScreen(ModMenuTypes.HEPHAESTUS_FORGE, HephaestusForgeScreen::new);
        helper.registerScreen(ModMenuTypes.CLIBANO, ClibanoScreen::new);
        helper.registerScreen(ModMenuTypes.RESEARCH_DESK, ResearchScreen::new);

        modEventBus.addListener(this::onClientSetup);
        //modEventBus.addListener(this::onTextureStitch);
        modEventBus.addListener(this::onRegisterGuiOverlays);
        modEventBus.addListener(this::onRegisterTooltipComponents);
        modEventBus.addListener(this::onRegisterParticleProviders);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        this.registerCosmetics();

        event.enqueueWork(() -> {

            Sheets.addWoodType(ModWoodTypes.FUNGYSS);
            Sheets.addWoodType(ModWoodTypes.AURUM);
            Sheets.addWoodType(ModWoodTypes.EDELWOOD);

            for (ObsidianSkullType skullType : ObsidianSkullType.values()) {
                SkullBlockRenderer.SKIN_BY_TYPE.put(skullType, skullType.getTextureLocation());
            }
        });

        //ItemProperties.register(ModItems.FORBIDDENMICON.get(), new ResourceLocation("open"), (stack, world, entity) -> entity != null && ForbiddenmiconItem.isOpen(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.SPECTRAL_EYE_AMULET.get(), ForbiddenArcanus.location("deactivated"), (stack, level, entity, seed) -> entity != null && stack.getItem() instanceof SpectralEyeAmuletItem item && item.isDeactivated(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BLOOD_TEST_TUBE.get(), ForbiddenArcanus.location("amount"), ESSENCE_AMOUNT_PROPERTY_FUNCTION);

        ItemProperties.register(ModItems.AUREAL_TANK.get(), ForbiddenArcanus.location("amount"), ESSENCE_AMOUNT_PROPERTY_FUNCTION);
        ItemProperties.register(ModItems.AUREAL_TANK.get(), ForbiddenArcanus.location("max"), (stack, level, entity, seed) -> {
            return EssenceHelper.getEssenceStorage(stack).map(essenceStorage -> essenceStorage.limit() == AurealTankItem.MAX_CAPACITY ? 1.0F : 0.0F).orElse(0.0F);
        });

        FLYING_LABELS.add(new JarFlyingLabel());
        FLYING_LABELS.add(new QuantumCatcherFlyingLabel());
    }

    private void registerCosmetics() {
//        var source = new FACosmeticsSource(ForbiddenArcanus.MOD_ID);
//
//        CosmeticsRegistry.addSource(source);
//
//        CosmeticsRegistry.registerType(source, CosmeticType.builder(CosmeticsCategory.BACK, DracoAurumWingsModel::new)
//                .exactName("draco_aurum_wings")
//                .elytraModifier(CancelRenderingModifier.INSTANCE));
//        CosmeticsRegistry.registerType(source, CosmeticType.builder(CosmeticsCategory.HAT, DracoAurumHeadModel::new)
//                .exactName("draco_aurum_head"));
    }

    @SubscribeEvent
    public void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, ForbiddenArcanus.location("sanity_meter"), new SanityMeterOverlay());
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, ForbiddenArcanus.location("aureal_meter"), new AurealMeterOverlay());
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, ForbiddenArcanus.location("flying_label"), new FlyingLabelOverlay());
    }

    @SubscribeEvent
    public void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(CapacityBucketTooltip.class, ClientCapacityBucketTooltip::new);
    }

    @SubscribeEvent
    public void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SOUL.get(), SoulParticle.Factory::new);
        event.registerSpriteSet(ModParticles.AUREAL_MOTE.get(), AurealMoteParticle.Factory::new);
        event.registerSpriteSet(ModParticles.MAGIC_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        event.registerSpecial(ModParticles.HUGE_MAGIC_EXPLOSION.get(), new HugeMagicExplosionParticle.Factory());
        event.registerSpriteSet(ModParticles.MAGNETIC_GLOW.get(), MagneticGlowProvider::new);
        event.registerSpriteSet(ModParticles.AUREAL_DROP.get(), EssenceDropParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SOULS_DROP.get(), EssenceDropParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLOOD_DROP.get(), EssenceDropParticle.Provider::new);
        event.registerSpriteSet(ModParticles.EXPERIENCE_DROP.get(), EssenceDropParticle.Provider::new);
    }
}
