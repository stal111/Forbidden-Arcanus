package com.stal111.forbidden_arcanus.common;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.CompostableRegistry;
import com.stal111.forbidden_arcanus.core.init.other.ModDispenseBehaviors;
import com.stal111.forbidden_arcanus.core.init.other.ModFlammables;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/**
 * Common Setup <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.CommonSetup
 *
 * @author stal111
 * @since 2021-08-07
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {

    public static final BlockPathTypes DANGER_SOUL_FIRE = BlockPathTypes.create("danger_soul_fire", 8.0F);
    public static final BlockPathTypes DAMAGE_SOUL_FIRE = BlockPathTypes.create("damage_soul_fire", 16.0F);

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModWoodTypes.registerWoodTypes();

            CompostableRegistry.register();

            ModFlammables.registerFlammables();
            ModDispenseBehaviors.registerDispenseBehaviors();

            FlowerPotBlock flowerPotBlock = (FlowerPotBlock) Blocks.FLOWER_POT;

            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "fungyss"), ModBlocks.POTTED_FUNGYSS);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aurum_sapling"), ModBlocks.POTTED_AURUM_SAPLING);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "growing_edelwood"), ModBlocks.POTTED_GROWING_EDELWOOD);
            flowerPotBlock.addPlant(new ResourceLocation(ForbiddenArcanus.MOD_ID, "yellow_orchid"), ModBlocks.POTTED_YELLOW_ORCHID);
        });

        NetworkHandler.init();

        ModUtils.addStrippable(ModBlocks.AURUM_LOG.get(), ModBlocks.STRIPPED_AURUM_LOG.get());
        ModUtils.addStrippable(ModBlocks.AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_WOOD.get());

        //TODO
       // PotionBrewing.addMix(Potions.WATER, ModItems.STRANGE_ROOT.get(), Potions.AWKWARD);

        BrewingRecipeRegistry.addRecipe(Ingredient.of(ModItems.AUREAL_BOTTLE.get()), Ingredient.of(Tags.Items.GUNPOWDER), new ItemStack(ModItems.SPLASH_AUREAL_BOTTLE.get()));
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LOST_SOUL.get(), LostSoul.createAttributes().build());
        event.put(ModEntities.DARK_TRADER.get(), DarkTrader.createAttributes().build());
    }
}
