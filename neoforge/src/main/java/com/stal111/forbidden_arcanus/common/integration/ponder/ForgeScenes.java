package com.stal111.forbidden_arcanus.common.integration.ponder;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;

import com.stal111.forbidden_arcanus.common.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;

import java.util.Arrays;
import java.util.List;

public class ForgeScenes {

    public static void building(SceneBuilder scene, SceneBuildingUtil util) {

        scene.title("forge_building", "Building the Hephaestus Forge");

        for (int x = 0; x < 9; x++) {
            for (int z = 0; z < 9; z++) {
                scene.world().showSection(util.select().position(x, 0, z), Direction.DOWN);
                scene.idle(2);
            }
            if (x == 1) {
                scene.overlay()
                        .showText(30)
                        .text("Build the base structure");
            }
            if (x == 2) {
                scene.overlay()
                        .showText(60)
                        .pointAt(util.vector().of(2.5, 1, 2.5))
                        .text("9x Gildede Chiseled Polished Darkstone");
            }
            if (x == 3) {
                scene.overlay()
                        .showText(60)
                        .pointAt(util.vector().of(3.5, 1, 4.5))
                        .text("4x Chiseled Arcane Polished Darkstone");
            }
            if (x == 4) {
                scene.overlay()
                        .showText(60)
                        .pointAt(util.vector().of(4.5, 1, 6.5))
                        .text("45x Polished Darkstone");
            }
        }

        scene.idle(20);
        scene.addKeyframe();
        scene.world().setBlock(util.grid().at(4, 1, 4), Blocks.SMITHING_TABLE.defaultBlockState(), false);
        scene.world().showSection(util.select().position(4, 1, 4), Direction.DOWN);
        
        scene.overlay()
                .showText(40)
                .pointAt(util.vector().of(4.5, 2, 4.5))
                .placeNearTarget()
                .text("Place down a Smithing Table in the center");

        scene.idle(40);
        Selection bbox = util.select().fromTo(1, 1, 1, 7, 1, 7);
        bbox = bbox.add(util.select().fromTo(0, 1, 3, 8, 1, 5));
        bbox = bbox.add(util.select().fromTo(3, 1, 0, 5, 1, 8));
        scene.addKeyframe();
        scene.overlay().showOutline(PonderPalette.GREEN, "airgap", bbox, 60);

        scene.overlay()
                .showText(40)
                .text("This area must be clear for the transformation")
                .placeNearTarget();
        scene.idle(40);
        scene.overlay().showControls(util.vector().of(4.5, 2, 4.5), Pointing.DOWN, 60)
                .rightClick()
                .withItem(new ItemStack(ModItems.MUNDABITUR_DUST.asItem(), 1))
                .whileSneaking();

        scene.idle(20);
        scene.world().modifyBlock(util.grid().at(4, 1, 4), state -> ModBlocks.HEPHAESTUS_FORGE_TIER_1.get().defaultBlockState(), true);
    }

    public static void usage(SceneBuilder scene, SceneBuildingUtil util) {

        scene.title("forge_usage", "Using the Hephaestus Forge");

        scene.world().showSection(util.select().fromTo(0, 0, 0, 9, 0, 9), Direction.DOWN);
        scene.world().showSection(util.select().position(4, 1, 4), Direction.DOWN);
        scene.idle(10);
        scene.overlay()
                .showText(80)
                .text("Darkstone Pedestals can be placed like so, they hold items for the ritual")
                .pointAt(util.vector().of(2.5, 1, 6.5));

        List<int[]> positions = Arrays.asList(
                new int[]{2, 1, 6},
                new int[]{4, 1, 7},
                new int[]{6, 1, 6},
                new int[]{7, 1, 4},
                new int[]{6, 1, 2},
                new int[]{4, 1, 1},
                new int[]{2, 1, 2},
                new int[]{1, 1, 4}
        );

        positions.forEach(pos -> {
            scene.world().setBlock(util.grid().at(pos[0], pos[1], pos[2]), ModBlocks.DARKSTONE_PEDESTAL.get().defaultBlockState(), false);
            scene.world().showSection(util.select().position(pos[0], pos[1], pos[2]), Direction.DOWN);
            scene.idle(3);
        });

        scene.idle(30);

        positions.forEach(pos -> {
            scene.world().hideSection(util.select().position(pos[0], pos[1], pos[2]), Direction.DOWN);
            scene.idle(3);
        });

        scene.idle(30);

        scene.overlay()
                .showText(80)
                .text("Arcane Crystal Obelisks can be placed in the same spaces and passively generate Aureal")
                .pointAt(util.vector().of(2.5, 1, 6.5));

        scene.world().setBlock(util.grid().at(2, 1, 6), ModBlocks.ARCANE_POLISHED_DARKSTONE.get().defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 2, 6), ModBlocks.ARCANE_CRYSTAL_BLOCK.get().defaultBlockState(), false);
        scene.world().setBlock(util.grid().at(2, 3, 6), ModBlocks.ARCANE_CRYSTAL_BLOCK.get().defaultBlockState(), false);

        for (int i = 1; i < 4; i++) {
            scene.world().showSection(util.select().position(2, i, 6), Direction.DOWN);
            scene.idle(3);
        }
        scene.idle(10);

        scene.overlay()
                .showControls(util.vector().of(2.5, 1, 6.5), Pointing.LEFT, 60)
                .rightClick()
                .withItem(new ItemStack(ModItems.MUNDABITUR_DUST.asItem(), 1));

        scene.idle(30);

        scene.world().replaceBlocks(util.select().position(2, 1, 6), ModBlocks.ARCANE_CRYSTAL_OBELISK.get().defaultBlockState().setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.LOWER), true);
        scene.world().replaceBlocks(util.select().position(2, 2, 6), ModBlocks.ARCANE_CRYSTAL_OBELISK.get().defaultBlockState().setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.MIDDLE), true);
        scene.world().replaceBlocks(util.select().position(2, 3, 6), ModBlocks.ARCANE_CRYSTAL_OBELISK.get().defaultBlockState().setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.UPPER), true);
    }
}
