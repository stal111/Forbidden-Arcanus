package com.stal111.forbidden_arcanus.common;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.AbstractLostSoul;
import com.stal111.forbidden_arcanus.common.item.crafting.FARecipePropertySets;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.other.CompostableRegistry;
import com.stal111.forbidden_arcanus.core.init.other.ModDispenseBehaviors;
import com.stal111.forbidden_arcanus.core.init.other.ModFlammables;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/**
 * Common Setup <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.CommonSetup
 *
 * @author stal111
 * @since 2021-08-07
 */
@EventBusSubscriber
public class CommonSetup {

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModWoodTypes.registerWoodTypes();

            CompostableRegistry.register();

            ModFlammables.registerFlammables();
            ModDispenseBehaviors.registerDispenseBehaviors();

            FlowerPotBlock flowerPotBlock = (FlowerPotBlock) Blocks.FLOWER_POT;

            flowerPotBlock.addPlant(ForbiddenArcanus.identifier("fungyss"), ModBlocks.POTTED_FUNGYSS);
            flowerPotBlock.addPlant(ForbiddenArcanus.identifier("aurum_sapling"), ModBlocks.POTTED_AURUM_SAPLING);
            flowerPotBlock.addPlant(ForbiddenArcanus.identifier("growing_edelwood"), ModBlocks.POTTED_GROWING_EDELWOOD);
            flowerPotBlock.addPlant(ForbiddenArcanus.identifier("yellow_orchid"), ModBlocks.POTTED_YELLOW_ORCHID);

            FARecipePropertySets.addToMap();
        });

        ModUtils.addStrippable(ModBlocks.AURUM_LOG.get(), ModBlocks.STRIPPED_AURUM_LOG.get());
        ModUtils.addStrippable(ModBlocks.AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_WOOD.get());
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LOST_SOUL.get(), AbstractLostSoul.createAttributes().build());
        event.put(ModEntities.CORRUPT_LOST_SOUL.get(), AbstractLostSoul.createAttributes().build());
        event.put(ModEntities.ENCHANTED_LOST_SOUL.get(), AbstractLostSoul.createAttributes().build());
        event.put(ModEntities.DARK_TRADER.get(), DarkTrader.createAttributes().build());
    }

    @SubscribeEvent
    private static void addFeaturePacks(AddPackFindersEvent event) {
        event.addPackFinders(ForbiddenArcanus.identifier("data/forbidden_arcanus/datapacks/preview"), PackType.SERVER_DATA, Component.literal("Forbidden Arcanus: Preview Features"), PackSource.FEATURE, false, Pack.Position.TOP);
    }
}
