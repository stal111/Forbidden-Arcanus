package com.stal111.forbidden_arcanus.common;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.predicate.ModifierItemPredicate;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.CompostableRegistry;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import com.stal111.forbidden_arcanus.core.init.world.ModFoliagePlacers;
import com.stal111.forbidden_arcanus.core.init.world.ModTrunkPlacers;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Common Setup <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.CommonSetup
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-07
 */
public class CommonSetup {

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModWoodTypes.registerWoodTypes();

            ModTrunkPlacers.load();
            ModFoliagePlacers.load();

            CompostableRegistry.register();

            FlowerPotBlock flowerPotBlock = (FlowerPotBlock) Blocks.FLOWER_POT;

            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "fungyss"), ModBlocks.POTTED_FUNGYSS);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "cherry_sapling"), ModBlocks.POTTED_CHERRY_SAPLING);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aurum_sapling"), ModBlocks.POTTED_AURUM_SAPLING);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "growing_edelwood"), ModBlocks.POTTED_GROWING_EDELWOOD);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "yellow_orchid"), ModBlocks.POTTED_YELLOW_ORCHID);
        });

        ItemPredicate.register(new ResourceLocation(ForbiddenArcanus.MOD_ID, "modifier"), ModifierItemPredicate::fromJson);

        ModUtils.addStrippable(ModBlocks.CHERRY_LOG.get(), ModBlocks.STRIPPED_CHERRY_LOG.get());
        ModUtils.addStrippable(ModBlocks.CHERRY_WOOD.get(), ModBlocks.STRIPPED_CHERRY_WOOD.get());
        ModUtils.addStrippable(ModBlocks.AURUM_LOG.get(), ModBlocks.STRIPPED_AURUM_LOG.get());
        ModUtils.addStrippable(ModBlocks.AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_WOOD.get());

        PotionBrewing.addMix(Potions.WATER, ModItems.STRANGE_ROOT.get(), Potions.AWKWARD);
    }
}
