package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.overlay.AurealMeterOverlay;
import com.stal111.forbidden_arcanus.client.gui.overlay.FlightTimerOverlay;
import com.stal111.forbidden_arcanus.client.gui.overlay.SanityMeterOverlay;
import com.stal111.forbidden_arcanus.client.gui.screen.ClibanoScreen;
import com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen;
import com.stal111.forbidden_arcanus.client.particle.AurealMoteParticle;
import com.stal111.forbidden_arcanus.client.particle.HugeMagicExplosionParticle;
import com.stal111.forbidden_arcanus.client.particle.MagneticGlowProvider;
import com.stal111.forbidden_arcanus.client.particle.SoulParticle;
import com.stal111.forbidden_arcanus.client.tooltip.ClientEdelwoodBucketTooltip;
import com.stal111.forbidden_arcanus.client.tooltip.EdelwoodBucketTooltip;
import com.stal111.forbidden_arcanus.common.aureal.ItemAurealProvider;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.common.item.SpectralEyeAmuletItem;
import com.stal111.forbidden_arcanus.common.item.UtremJarItem;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;

import java.util.Optional;

/**
 * @author stal111
 * @since 2021-02-13
 */
public class ClientSetup {

    public ClientSetup(ClientSetupHelper helper) {
        Minecraft minecraft = Minecraft.getInstance();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

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
            MenuScreens.register(ModMenuTypes.HEPHAESTUS_FORGE.get(), HephaestusForgeScreen::new);
            MenuScreens.register(ModMenuTypes.CLIBANO.get(), ClibanoScreen::new);

            Sheets.addWoodType(ModWoodTypes.FUNGYSS);
            Sheets.addWoodType(ModWoodTypes.AURUM);
            Sheets.addWoodType(ModWoodTypes.EDELWOOD);

            SkullBlockRenderer.SKIN_BY_TYPE.put(ObsidianSkullType.DEFAULT, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull/obsidian_skull.png"));
            SkullBlockRenderer.SKIN_BY_TYPE.put(ObsidianSkullType.CRACKED, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull/cracked_obsidian_skull.png"));
            SkullBlockRenderer.SKIN_BY_TYPE.put(ObsidianSkullType.FRAGMENTED, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull/fragmented_obsidian_skull.png"));
            SkullBlockRenderer.SKIN_BY_TYPE.put(ObsidianSkullType.FADING, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull/fading_obsidian_skull.png"));
            SkullBlockRenderer.SKIN_BY_TYPE.put(ObsidianSkullType.ETERNAL, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull/eternal_obsidian_skull.png"));
        });

        //ItemProperties.register(ModItems.FORBIDDENMICON.get(), new ResourceLocation("open"), (stack, world, entity) -> entity != null && ForbiddenmiconItem.isOpen(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.SPECTRAL_EYE_AMULET.get(), new ResourceLocation("deactivated"), (stack, level, entity, seed) -> entity != null && stack.getItem() instanceof SpectralEyeAmuletItem item && item.isDeactivated(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.UTREM_JAR.get(), new ResourceLocation("water"), (stack, level, entity, seed) -> stack.getItem() instanceof UtremJarItem item && item.getFluid(stack) == Fluids.WATER ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.UTREM_JAR.get(), new ResourceLocation("lava"), (stack, level, entity, seed) -> stack.getItem() instanceof UtremJarItem item && item.getFluid(stack) == Fluids.LAVA ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BLOOD_TEST_TUBE.get(), new ResourceLocation("amount"), (stack, level, entity, seed) -> (BloodTestTubeItem.getBlood(stack) / (float) BloodTestTubeItem.MAX_BLOOD));
        ItemProperties.register(ModBlocks.HEPHAESTUS_FORGE.get().asItem(), new ResourceLocation("tier"), (stack, level, entity, seed) -> {
            CompoundTag tag = stack.getTagElement("BlockStateTag");

            if (tag != null) {
                Tag tier = tag.get(ModBlockStateProperties.TIER.getName());

                if (tier != null) {
                    return Integer.parseInt(tier.getAsString()) / 5.0F;
                }
            }

            return 0.0F;
        });

        ItemProperties.register(ModItems.AUREAL_TANK.get(), new ResourceLocation("amount"), (stack, level, entity, seed) -> {
            Optional<ItemAurealProvider> optional = stack.getCapability(ItemAurealProvider.AUREAL).resolve();

            return optional.map(provider -> (float) provider.getAureal() / provider.getAurealLimit()).orElse(0.0F);
        });
        ItemProperties.register(ModItems.AUREAL_TANK.get(), new ResourceLocation("max"), (stack, level, entity, seed) -> {
            Optional<ItemAurealProvider> optional = stack.getCapability(ItemAurealProvider.AUREAL).resolve();

            return optional.map(provider -> provider.getAurealLimit() == AurealTankItem.MAX_CAPACITY ? 1.0F : 0.0F).orElse(0.0F);
        });
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
    public void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "flight_timer", new FlightTimerOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "sanity_meter", new SanityMeterOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "aureal_meter", new AurealMeterOverlay());
    }

    @SubscribeEvent
    public void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(EdelwoodBucketTooltip.class, ClientEdelwoodBucketTooltip::new);
    }

    //TODO
//    @SubscribeEvent
//    public void onTextureStitch(TextureStitchEvent.Pre event) {
//        ResourceLocation textureLocation = event.getAtlas().location();
//
//        if (textureLocation.equals(TextureAtlas.LOCATION_BLOCKS)) {
//            event.addSprite(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/obsidian_skull_shield"));
//        }
//    }

    @SubscribeEvent
    public void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SOUL.get(), SoulParticle.Factory::new);
        event.registerSpriteSet(ModParticles.AUREAL_MOTE.get(), AurealMoteParticle.Factory::new);
        event.registerSpriteSet(ModParticles.MAGIC_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        event.registerSpecial(ModParticles.HUGE_MAGIC_EXPLOSION.get(), new HugeMagicExplosionParticle.Factory());
        event.registerSpriteSet(ModParticles.MAGNETIC_GLOW.get(), MagneticGlowProvider::new);
    }
}
