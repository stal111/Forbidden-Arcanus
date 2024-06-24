package com.stal111.forbidden_arcanus.common;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.other.CompostableRegistry;
import com.stal111.forbidden_arcanus.core.init.other.ModDispenseBehaviors;
import com.stal111.forbidden_arcanus.core.init.other.ModFlammables;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/**
 * Common Setup <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.CommonSetup
 *
 * @author stal111
 * @since 2021-08-07
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CommonSetup {

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModWoodTypes.registerWoodTypes();

            CompostableRegistry.register();

            ModFlammables.registerFlammables();
            ModDispenseBehaviors.registerDispenseBehaviors();

            FlowerPotBlock flowerPotBlock = (FlowerPotBlock) Blocks.FLOWER_POT;

            flowerPotBlock.addPlant(ForbiddenArcanus.location("fungyss"), ModBlocks.POTTED_FUNGYSS);
            flowerPotBlock.addPlant(ForbiddenArcanus.location( "aurum_sapling"), ModBlocks.POTTED_AURUM_SAPLING);
            flowerPotBlock.addPlant(ForbiddenArcanus.location( "growing_edelwood"), ModBlocks.POTTED_GROWING_EDELWOOD);
            flowerPotBlock.addPlant(ForbiddenArcanus.location( "yellow_orchid"), ModBlocks.POTTED_YELLOW_ORCHID);
        });

        ModUtils.addStrippable(ModBlocks.AURUM_LOG.get(), ModBlocks.STRIPPED_AURUM_LOG.get());
        ModUtils.addStrippable(ModBlocks.AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_WOOD.get());
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LOST_SOUL.get(), LostSoul.createAttributes().build());
        event.put(ModEntities.DARK_TRADER.get(), DarkTrader.createAttributes().build());
    }
}
