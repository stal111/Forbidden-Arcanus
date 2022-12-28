package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaItemModelProvider;

import java.util.Arrays;
import java.util.Objects;

/**
 * Mod Item Model Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModItemModelProvider
 *
 * @author stal111
 * @since 2021-01-26
 */
public class ModItemModelProvider extends ValhelsiaItemModelProvider {

    public ModItemModelProvider(DataProviderInfo info) {
        super(info.output(), ForbiddenArcanus.REGISTRY_MANAGER, info.fileHelper());
    }

    @Override
    protected void registerModels() {
        //Block Items
        getRemainingBlockItems().removeIf(item -> ((BlockItem) item.get()).getBlock() instanceof ObsidianSkullBlock);

        takeBlockItem(this::simpleModel,
                ModBlocks.PIXIE_UTREM_JAR,
                ModBlocks.CORRUPTED_PIXIE_UTREM_JAR,
                ModBlocks.NIPA,
                ModBlocks.ARCANE_CRYSTAL_OBELISK,
                ModBlocks.DEORUM_CHAIN,
                ModBlocks.ARCANE_DRAGON_EGG,
                ModBlocks.DEORUM_LANTERN,
                ModBlocks.DEORUM_SOUL_LANTERN
        );
        takeBlockItem(this::simpleModel,
                ModItems.EDELWOOD_POWDER_SNOW_BUCKET,
                ModItems.STRANGE_ROOT,
                ModItems.GOLDEN_ORCHID_SEEDS
        );
        takeBlockItem(this::simpleModelBlockTexture,
                ModBlocks.FUNGYSS,
                ModBlocks.GROWING_EDELWOOD,
                ModBlocks.EDELWOOD_LADDER,
                ModBlocks.YELLOW_ORCHID,
                ModBlocks.CHERRY_FLOWER_VINES
        );
        takeBlockItem(this::withParentInventory,
                ModBlocks.FUNGYSS_BLOCK,
                ModBlocks.FUNGYSS_BUTTON,
                ModBlocks.POLISHED_DARKSTONE_BUTTON,
                ModBlocks.CHERRY_LEAF_CARPET
        );
        takeBlockItem(this::utremJarModel, ModBlocks.UTREM_JAR);

        forEachBlockItem(item -> item.getBlock() instanceof DoorBlock, this::simpleModel);
        forEachBlockItem(item -> item.getBlock() instanceof TrapDoorBlock, item -> withParent(item, getName(item) + "_bottom"));
        forEachBlockItem(item -> item.getBlock() instanceof IronBarsBlock, item -> simpleModelBlockTexture(item, getName(item).substring(0, getName(item).length() - 5)));
        forEachBlockItem(item -> item.getBlock() instanceof WallBlock, this::withParentInventory);
        forEachBlockItem(item -> item.getBlock() instanceof StandingSignBlock, this::simpleModel);
        forEachBlockItem(item -> item.getBlock() instanceof SaplingBlock, this::simpleModelBlockTexture);
        forEachBlockItem(item -> item.getBlock() instanceof FenceBlock, this::withParentInventory);
        forEachBlockItem(item -> item.getBlock() instanceof ButtonBlock, this::withParentInventory);

        forEachBlockItem(this::withParent);

        //Items
        Arrays.asList(
                ModItems.LENS_OF_VERITATIS,
                ModItems.OBSIDIAN_SKULL_SHIELD,
                ModItems.QUANTUM_CATCHER,
                ModItems.ZOMBIE_ARM,
                ModItems.SHINY_ZOMBIE_ARM,
                ModItems.SPECTRAL_EYE_AMULET,
                ModItems.SOUL_EXTRACTOR
        ).forEach(getRemainingItems()::remove);

        takeItem(this::bloodTestTubeModel, ModItems.BLOOD_TEST_TUBE);
        takeItem(this::toolItem, ModItems.DRACO_ARCANUS_STAFF, ModItems.DRACO_ARCANUS_SCEPTER);

        forEachItem(item -> item instanceof DiggerItem || item instanceof SwordItem, this::toolItem);
        forEachItem(item -> item instanceof ArmorItem, this::armorItem);
        forEachItem(this::simpleModel);
    }

    public <T extends Item> void toolItem(T item) {
        String name = this.getName(item);
        this.getBuilder(name).parent(this.getExistingFile(this.mcLoc("item/handheld"))).texture("layer0", "item/tool/" + name);
    }

    public <T extends Item> void armorItem(T item) {
        String name = this.getName(item);

        if (item instanceof DyeableArmorItem) {
            this.getBuilder(name).parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", "item/armor/" + name).texture("layer1", "item/armor/" + name + "_overlay");
        } else {
            this.getBuilder(name).parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", "item/armor/" + name);
        }
    }

    public <T extends Item> void utremJarModel(T item) {
        String name = this.getName(item);
        ModelFile waterModel = this.getBuilder("utrem_jar_water").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/utrem_jar_water");
        ModelFile lavaModel = this.getBuilder("utrem_jar_lava").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/utrem_jar_lava");

        this.getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name)
                .override().predicate(new ResourceLocation("water"), 1.0F).predicate(new ResourceLocation("lava"), 0.0F).model(waterModel).end()
                .override().predicate(new ResourceLocation("water"), 0.0F).predicate(new ResourceLocation("lava"), 1.0F).model(lavaModel).end();
    }

    public <T extends Item> void bloodTestTubeModel(T item) {
        String name = this.getName(item);

        ModelFile model0 = this.getBuilder("blood_test_tube_0").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/blood_test_tube_0");
        ModelFile model1 = this.getBuilder("blood_test_tube_1").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/blood_test_tube_1");
        ModelFile model2 = this.getBuilder("blood_test_tube_2").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/blood_test_tube_2");
        ModelFile model3 = this.getBuilder("blood_test_tube_3").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/blood_test_tube_3");
        ModelFile model4 = this.getBuilder("blood_test_tube_4").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/blood_test_tube_4");
        ModelFile model5 = this.getBuilder("blood_test_tube_5").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/blood_test_tube_5");

        this.getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/test_tube")
                .override().predicate(new ResourceLocation("amount"), 0.1F).model(model0).end()
                .override().predicate(new ResourceLocation("amount"), 0.25F).model(model1).end()
                .override().predicate(new ResourceLocation("amount"), 0.5F).model(model2).end()
                .override().predicate(new ResourceLocation("amount"), 0.75F).model(model3).end()
                .override().predicate(new ResourceLocation("amount"), 0.9F).model(model4).end()
                .override().predicate(new ResourceLocation("amount"), 1.0F).model(model5).end();
    }

    private String getName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }
}
