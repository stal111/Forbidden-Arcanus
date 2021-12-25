package com.stal111.forbidden_arcanus.common;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.predicate.ModifierItemPredicate;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
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

            FlowerPotBlock flowerPotBlock = (FlowerPotBlock) Blocks.FLOWER_POT;

            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "cherrywood_sapling"), ModBlocks.POTTED_CHERRYWOOD_SAPLING);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "mysterywood_sapling"), ModBlocks.POTTED_MYSTERYWOOD_SAPLING);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "yellow_orchid"), ModBlocks.POTTED_YELLOW_ORCHID);
        });

        ItemPredicate.register(new ResourceLocation(ForbiddenArcanus.MOD_ID, "modifier"), ModifierItemPredicate::fromJson);

        ModUtils.addStrippable(ModBlocks.CHERRYWOOD_LOG.get(), ModBlocks.STRIPPED_CHERRYWOOD_LOG.get());
        ModUtils.addStrippable(ModBlocks.CHERRYWOOD.get(), ModBlocks.STRIPPED_CHERRYWOOD.get());
        ModUtils.addStrippable(ModBlocks.MYSTERYWOOD_LOG.get(), ModBlocks.STRIPPED_MYSTERYWOOD_LOG.get());
        ModUtils.addStrippable(ModBlocks.MYSTERYWOOD.get(), ModBlocks.STRIPPED_MYSTERYWOOD.get());

        PotionBrewing.addMix(Potions.WATER, ModItems.STRANGE_ROOT.get(), Potions.AWKWARD);
    }
}
