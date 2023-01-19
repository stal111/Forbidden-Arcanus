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
                .displayItems((featureFlags, output, hasOp) -> {
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
                    output.accept(ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
                    output.accept(ModBlocks.ARCANE_CRYSTAL_OBELISK.get());
                    output.accept(ModItems.ARCANE_CRYSTAL.get());
                    output.accept(ModItems.ARCANE_CRYSTAL_DUST.get());
                    output.accept(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
                    output.accept(ModItems.ARCANE_BONE_MEAL.get());
                })
        );
    }
}
