package com.stal111.forbidden_arcanus.data.rituals;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 2023-04-29
 */
public class ModRituals extends DatapackRegistryClass<Ritual> {

    public static final DatapackRegistryHelper<Ritual> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(FARegistries.RITUAL);

    public static final ResourceKey<Ritual> ETERNAL_STELLA = HELPER.createKey("eternal_stella");
    public static final ResourceKey<Ritual> TERRASTOMP_PRISM = HELPER.createKey("terrastomp_prism");
    public static final ResourceKey<Ritual> SEA_PRISM = HELPER.createKey("sea_prism");
    public static final ResourceKey<Ritual> WHIRLWIND_PRISM = HELPER.createKey("whirlwind_prism");
    public static final ResourceKey<Ritual> SMELTER_PRISM = HELPER.createKey("smelter_prism");
    public static final ResourceKey<Ritual> FERROGNETIC_MIXTURE = HELPER.createKey("ferrognetic_mixture");
    public static final ResourceKey<Ritual> UPGRADE_TIER_2 = HELPER.createKey("upgrade_tier_2");
    public static final ResourceKey<Ritual> UPGRADE_TIER_3 = HELPER.createKey("upgrade_tier_3");
    public static final ResourceKey<Ritual> UPGRADE_TIER_4 = HELPER.createKey("upgrade_tier_4");
    public static final ResourceKey<Ritual> UPGRADE_TIER_5 = HELPER.createKey("upgrade_tier_5");

    public ModRituals(DataProviderInfo info, BootstapContext<Ritual> context) {
        super(info, context);
    }

    @Override
    public void bootstrap(BootstapContext<Ritual> context) {
        this.register(context, ETERNAL_STELLA, ModItems.ETERNAL_STELLA.get(), Items.DIAMOND, builder -> builder.input(Ingredient.of(ModItems.XPETRIFIED_ORB.get()), 3).input(Ingredient.of(ModItems.STELLARITE_PIECE.get())).aureal(82).souls(1).blood(1000).requirements(3));

        this.register(context, TERRASTOMP_PRISM, ModItems.TERRASTOMP_PRISM.get(), Blocks.DIAMOND_BLOCK, builder -> builder.input(Ingredient.of(Items.FLINT), 2).input(Ingredient.of(Blocks.DRIPSTONE_BLOCK), 2).input(Ingredient.of(Blocks.POINTED_DRIPSTONE), 2).aureal(300).souls(9).blood(1500).requirements(2));
        this.register(context, SEA_PRISM, ModItems.SEA_PRISM.get(), Items.HEART_OF_THE_SEA, builder -> builder.input(Ingredient.of(Items.PRISMARINE_SHARD), 2).input(Ingredient.of(Items.SCUTE), 2).input(Ingredient.of(Items.LAPIS_LAZULI), 2).aureal(1000).souls(8).blood(2000).requirements(3));
        this.register(context, WHIRLWIND_PRISM, ModItems.WHIRLWIND_PRISM.get(), Blocks.WHITE_WOOL, builder -> builder.input(Ingredient.of(ModItems.BAT_WING.get())).input(Ingredient.of(Items.FEATHER), 2).input(Ingredient.of(Items.PHANTOM_MEMBRANE), 3).aureal(1000).souls(3).blood(2250).requirements(3));
        this.register(context, SMELTER_PRISM, ModItems.SMELTER_PRISM.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get(), builder -> builder.input(Ingredient.of(Items.COAL), 2).input(Ingredient.of(Items.BLAZE_POWDER), 4).aureal(200).souls(4).blood(1250));
        this.register(context, FERROGNETIC_MIXTURE, ModItems.FERROGNETIC_MIXTURE.get(), Blocks.LODESTONE, builder -> builder.input(Ingredient.of(Items.CLAY_BALL), 2).input(Ingredient.of(ModItems.WAX.get()), 2).input(Ingredient.of(Items.SLIME_BALL), 2).input(Ingredient.of(Items.IRON_INGOT)).aureal(100).souls(2).blood(1250));

        this.register(context, UPGRADE_TIER_2, new UpgradeTierResult(1, 2), new ItemStack(ModBlocks.EDELWOOD_PLANKS.get()), builder -> builder.input(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 4).input(Ingredient.of(ModItems.SPAWNER_SCRAP.get()), 4).aureal(500).souls(10).blood(6000).magicCircle("origin", "pure"));
        this.register(context, UPGRADE_TIER_3, new UpgradeTierResult(2, 3), new ItemStack(ModBlocks.CHISELED_POLISHED_DARKSTONE.get()), builder -> builder.input(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 4).input(Ingredient.of(ModItems.DEORUM_INGOT.get()), 4).aureal(1000).souls(50).blood(9000).magicCircle("origin", "pure"));
        this.register(context, UPGRADE_TIER_4, new UpgradeTierResult(3, 4), new ItemStack(ModBlocks.CHISELED_POLISHED_DARKSTONE.get()), builder -> builder.input(Ingredient.of(ModItems.STELLARITE_PIECE.get()), 4).input(Ingredient.of(ModItems.RUNE.get()), 4).aureal(2000).souls(100).blood(12000).magicCircle("origin", "pure"));
        this.register(context, UPGRADE_TIER_5, new UpgradeTierResult(4, 5), new ItemStack(ModBlocks.STELLARITE_BLOCK.get()), builder -> builder.input(Ingredient.of(Blocks.SCULK_CATALYST), 4).input(Ingredient.of(ModItems.DARK_NETHER_STAR.get()), 2).input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 2).aureal(5000).souls(500).blood(20000).magicCircle("origin", "pactum"));
    }

    private void register(BootstapContext<Ritual> context, ResourceKey<Ritual> key, ItemLike result, ItemLike mainIngredient, UnaryOperator<RitualBuilder> builder) {
        context.register(key, builder.apply(new RitualBuilder(new ItemStack(mainIngredient), new CreateItemResult(new ItemStack(result)))).build());
    }

    private void register(BootstapContext<Ritual> context, ResourceKey<Ritual> key, ItemStack result, ItemStack mainIngredient, UnaryOperator<RitualBuilder> builder) {
        context.register(key, builder.apply(new RitualBuilder(mainIngredient, new CreateItemResult(result))).build());
    }

    private void register(BootstapContext<Ritual> context, ResourceKey<Ritual> key, RitualResult result, ItemStack mainIngredient, UnaryOperator<RitualBuilder> builder) {
        context.register(key, builder.apply(new RitualBuilder(mainIngredient, result)).build());
    }
}
