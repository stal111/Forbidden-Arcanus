package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Function;

/**
 * @author stal111
 * @since 2023-01-03
 */
public class CreativeModeTabEvents {

    @SubscribeEvent
    public void registerTabs(CreativeModeTabEvent.Register event) {
        Function<String, ResourceLocation> path = s -> new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/creative_inventory/" + s);

        event.registerCreativeModeTab(new ResourceLocation(ForbiddenArcanus.MOD_ID, "main"), builder -> builder
                .icon(() -> new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()))
                .title(Component.translatable("itemGroup.forbidden_arcanus"))
                .hideTitle()
                .withSearchBar(88)
                .withTabsImage(path.apply("forbidden_arcanus_tabs.png"))
                .withBackgroundLocation(path.apply("tab_forbidden_arcanus.png"))
                .displayItems((displayParameters, output) -> {
                    output.accept(ModItems.FERROGNETIC_MIXTURE.get());
                    output.accept(ModBlocks.DARKSTONE_PEDESTAL.get());
                    output.accept(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get());
                    for (HephaestusForgeLevel level : HephaestusForgeLevel.values()) {
                        output.accept(HephaestusForgeBlock.setTierOnStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), level.getAsInt()));
                    }
                    output.accept(ModItems.AUREAL_BOTTLE.get());
                    output.accept(ModItems.SPLASH_AUREAL_BOTTLE.get());
                    output.accept(ModItems.TEST_TUBE.get());
                    output.accept(BloodTestTubeItem.setBlood(new ItemStack(ModItems.BLOOD_TEST_TUBE.get()), BloodTestTubeItem.MAX_BLOOD));
                    output.accept(ModBlocks.ARCANE_CRYSTAL_ORE.get());
                    output.accept(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get());
                    output.accept(ModBlocks.RUNIC_STONE.get());
                    output.accept(ModBlocks.RUNIC_DEEPSLATE.get());
                    output.accept(ModBlocks.RUNIC_DARKSTONE.get());
                    output.accept(ModBlocks.STELLA_ARCANUM.get());
                    output.accept(ModBlocks.XPETRIFIED_ORE.get());
                    output.accept(ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
                    output.accept(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get());
                    output.accept(ModBlocks.RUNE_BLOCK.get());
                    output.accept(ModBlocks.DARK_RUNE_BLOCK.get());
                    output.accept(ModBlocks.STELLARITE_BLOCK.get());
                    output.accept(ModBlocks.DEORUM_BLOCK.get());
                    output.accept(ModBlocks.PROCESSED_OBSIDIAN_BLOCK.get());
                    output.accept(ModBlocks.ARCANE_CRYSTAL_OBELISK.get());
                    output.accept(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get());
                    output.accept(ModItems.ARCANE_CRYSTAL.get());
                    output.accept(ModItems.CORRUPTED_ARCANE_CRYSTAL.get());
                    output.accept(ModItems.RUNE.get());
                    output.accept(ModItems.DARK_RUNE.get());
                    output.accept(ModItems.STELLARITE_PIECE.get());
                    output.accept(ModItems.XPETRIFIED_ORB.get());
                    output.accept(ModItems.DEORUM_INGOT.get());
                    output.accept(ModItems.OBSIDIAN_INGOT.get());
                    output.accept(ModItems.ARCANE_CRYSTAL_DUST.get());
                    output.accept(ModItems.MUNDABITUR_DUST.get());
                    output.accept(ModItems.CORRUPTI_DUST.get());
                    output.accept(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
                    output.accept(ModItems.ARCANE_BONE_MEAL.get());
                    output.accept(ModItems.TERRASTOMP_PRISM.get());
                    output.accept(ModItems.SEA_PRISM.get());
                    output.accept(ModItems.WHIRLWIND_PRISM.get());
                    output.accept(ModItems.SMELTER_PRISM.get());
                    output.accept(ModItems.ARTISAN_RELIC.get());
                    output.accept(ModItems.CRESCENT_MOON.get());
                    output.accept(ModItems.CRIMSON_STONE.get());
                    output.accept(ModItems.SOUL_CRIMSON_STONE.get());
                    output.accept(ModItems.ELEMENTARIUM.get());
                    output.accept(ModItems.DIVINE_PACT.get());
                    output.accept(ModItems.MALEDICTUS_PACT.get());
                })
        );
    }
}
