package com.stal111.forbidden_arcanus.data.hephaestus_forge.rituals;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.data.enhancer.ModEnhancerDefinitions;
import com.stal111.forbidden_arcanus.data.hephaestus_forge.ModMagicCircles;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 2023-04-29
 */
public class ModRituals extends DatapackRegistryClass<Ritual> {

    public static final DatapackRegistryHelper<Ritual> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.RITUAL);

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

    private HolderGetter<MagicCircleType> magicCircleLookup;

    public ModRituals(BootstrapContext<Ritual> context) {
        super(context);
    }

    @Override
    public void init(BootstrapContext<Ritual> context) {
        this.magicCircleLookup = context.lookup(FARegistries.MAGIC_CIRCLE);
    }

    @Override
    public void bootstrap(BootstrapContext<Ritual> context) {
        Holder<EnhancerDefinition> elementarium = context.lookup(FARegistries.ENHANCER_DEFINITION).getOrThrow(ModEnhancerDefinitions.ELEMENTARIUM);

        // Requirements
        var eternalStella = this.requirements(EssencesDefinition.of(82, 1, 1000, 0), builder -> builder.tier(TierPredicate.min(3)));
        var terrastompPrism = this.requirements(EssencesDefinition.of(300, 9, 1500, 0), builder -> builder.tier(TierPredicate.min(2)).enhancer(elementarium));
        var seaPrism = this.requirements(EssencesDefinition.of(1000, 8, 2000, 0), builder -> builder.tier(TierPredicate.min(3)).enhancer(elementarium));
        var whirlwindPrism = this.requirements(EssencesDefinition.of(1000, 3, 2250, 0), builder -> builder.tier(TierPredicate.min(3)).enhancer(elementarium));
        var smelterPrism = this.requirements(EssencesDefinition.of(200, 4, 1250, 0), builder -> builder.enhancer(elementarium));
        var ferroneticMixture = this.requirements(EssencesDefinition.of(100, 2, 1250, 0), UnaryOperator.identity());
        var tier2 = this.requirements(EssencesDefinition.of(500, 10, 6000, 0), builder -> builder.tier(TierPredicate.exact(1)));
        var tier3 = this.requirements(EssencesDefinition.of(1000, 50, 9000, 0), builder -> builder.tier(TierPredicate.exact(2)));
        var tier4 = this.requirements(EssencesDefinition.of(2000, 100, 12000, 0), builder -> builder.tier(TierPredicate.exact(3)));
        var tier5 = this.requirements(EssencesDefinition.of(5000, 500, 20000, 0), builder -> builder.tier(TierPredicate.exact(4)));

        this.register(ETERNAL_STELLA, ModItems.ETERNAL_STELLA.get(), Items.DIAMOND, builder -> builder.input(Ingredient.of(ModItems.XPETRIFIED_ORB.get()), 3).input(Ingredient.of(ModItems.STELLARITE_PIECE.get())).requirements(eternalStella));

        this.register(TERRASTOMP_PRISM, ModItems.TERRASTOMP_PRISM.get(), Blocks.DIAMOND_BLOCK, builder -> builder.input(Ingredient.of(Items.FLINT), 2).input(Ingredient.of(Blocks.DRIPSTONE_BLOCK), 2).input(Ingredient.of(Blocks.POINTED_DRIPSTONE), 2).requirements(terrastompPrism));
        this.register(SEA_PRISM, ModItems.SEA_PRISM.get(), Items.HEART_OF_THE_SEA, builder -> builder.input(Ingredient.of(Items.PRISMARINE_SHARD), 2).input(Ingredient.of(Items.TURTLE_SCUTE), 2).input(Ingredient.of(Items.LAPIS_LAZULI), 2).requirements(seaPrism));
        this.register(WHIRLWIND_PRISM, ModItems.WHIRLWIND_PRISM.get(), Blocks.WHITE_WOOL, builder -> builder.input(Ingredient.of(ModItems.BAT_WING.get())).input(Ingredient.of(Items.FEATHER), 2).input(Ingredient.of(Items.PHANTOM_MEMBRANE), 3).requirements(whirlwindPrism));
        this.register(SMELTER_PRISM, ModItems.SMELTER_PRISM.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get(), builder -> builder.input(Ingredient.of(Items.COAL), 2).input(Ingredient.of(Items.BLAZE_POWDER), 4).requirements(smelterPrism));
        this.register(FERROGNETIC_MIXTURE, ModItems.FERROGNETIC_MIXTURE.get(), Blocks.LODESTONE, builder -> builder.input(Ingredient.of(Items.CLAY_BALL), 2).input(Ingredient.of(ModItems.WAX.get()), 2).input(Ingredient.of(Items.SLIME_BALL), 2).input(Ingredient.of(Items.IRON_INGOT)).requirements(ferroneticMixture));

        this.register(UPGRADE_TIER_2, UpgradeTierResult.INSTANCE, new ItemStack(ModBlocks.EDELWOOD_PLANKS.get()), builder -> builder.input(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 4).input(Ingredient.of(ModItems.SPAWNER_SCRAP.get()), 4).requirements(tier2).magicCircle(ModMagicCircles.UPGRADE_TIER));
        this.register(UPGRADE_TIER_3, UpgradeTierResult.INSTANCE, new ItemStack(ModBlocks.CHISELED_POLISHED_DARKSTONE.get()), builder -> builder.input(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 4).input(Ingredient.of(ModItems.DEORUM_INGOT.get()), 4).requirements(tier3).magicCircle(ModMagicCircles.UPGRADE_TIER));
        this.register(UPGRADE_TIER_4, UpgradeTierResult.INSTANCE, new ItemStack(ModBlocks.CHISELED_POLISHED_DARKSTONE.get()), builder -> builder.input(Ingredient.of(ModItems.STELLARITE_PIECE.get()), 4).input(Ingredient.of(ModItems.RUNE.get()), 4).requirements(tier4).magicCircle(ModMagicCircles.UPGRADE_TIER));
        this.register(UPGRADE_TIER_5, UpgradeTierResult.INSTANCE, new ItemStack(ModBlocks.STELLARITE_BLOCK.get()), builder -> builder.input(Ingredient.of(Blocks.SCULK_CATALYST), 4).input(Ingredient.of(ModItems.DARK_NETHER_STAR.get()), 2).input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 2).requirements(tier5).magicCircle(ModMagicCircles.UPGRADE_FINAL_TIER));
    }

    private RitualRequirements requirements(EssencesDefinition essences, UnaryOperator<RitualRequirements.Builder> builder) {
        return builder.apply(RitualRequirements.builder(essences)).build();
    }

    private void register(ResourceKey<Ritual> key, ItemLike result, ItemLike mainIngredient, UnaryOperator<RitualBuilder> builder) {
        this.getContext().register(key, builder.apply(new RitualBuilder(new ItemStack(mainIngredient), new CreateItemResult(new ItemStack(result)), this.magicCircleLookup)).build());
    }

    private void register(ResourceKey<Ritual> key, ItemStack result, ItemStack mainIngredient, UnaryOperator<RitualBuilder> builder) {
        this.getContext().register(key, builder.apply(new RitualBuilder(mainIngredient, new CreateItemResult(result), this.magicCircleLookup)).build());
    }

    private void register(ResourceKey<Ritual> key, RitualResult result, ItemStack mainIngredient, UnaryOperator<RitualBuilder> builder) {
        this.getContext().register(key, builder.apply(new RitualBuilder(mainIngredient, result, this.magicCircleLookup)).build());
    }
}
