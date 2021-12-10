package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.*;
import com.stal111.forbidden_arcanus.common.item.EdelwoodBucketItem;
import com.stal111.forbidden_arcanus.common.item.EdelwoodMilkBucketItem;
import com.stal111.forbidden_arcanus.common.item.EdelwoodSuspiciousStewBucketItem;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.block.EternalObsidianSkullItem;
import com.stal111.forbidden_arcanus.item.block.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.item.block.UtremJarItem;
import com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullItemRenderer;
import com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullShieldItemRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper;

/**
 * Mod Items <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.NewModItems
 *
 * @author Valhelsia Team
 * @version 16.2.0
 * @since 2021-01-26
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NewModItems {

    public static final ItemRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getItemHelper();

    public static final RegistryObject<Item> SANITY_METER = HELPER.register("sanity_meter", () -> new SanityMeterItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> LENS_OF_VERITATIS = HELPER.register("lens_of_veritatis", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> PURIFYING_SOAP = HELPER.register("purifying_soap", () -> new PurifyingSoapItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> WET_PURIFYING_SOAP = HELPER.register("wet_purifying_soap", () -> new WetPurifyingSoapItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<ObsidianSkullItem> OBSIDIAN_SKULL = HELPER.register("obsidian_skull", () -> new ObsidianSkullItem(NewModBlocks.OBSIDIAN_SKULL.get(), NewModBlocks.OBSIDIAN_WALL_SKULL.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<ObsidianSkullShieldItem> OBSIDIAN_SKULL_SHIELD = HELPER.register("obsidian_skull_shield", () -> new ObsidianSkullShieldItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).durability(1008).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<EternalObsidianSkullItem> ETERNAL_OBSIDIAN_SKULL = HELPER.register("eternal_obsidian_skull", () -> new EternalObsidianSkullItem(NewModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), NewModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<UtremJarItem> UTREM_JAR = HELPER.register("utrem_jar", () -> new UtremJarItem(NewModBlocks.UTREM_JAR.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<AurealBottleItem> AUREAL_BOTTLE = HELPER.register("aureal_bottle", () -> new AurealBottleItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<Item> ARCANE_CRYSTAL_DUST_SPECK = HELPER.register("arcane_crystal_dust_speck", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<ArcaneBoneMealItem> ARCANE_BONE_MEAL = HELPER.register("arcane_bone_meal", () -> new ArcaneBoneMealItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<ZombieArmItem> ZOMBIE_ARM = HELPER.register("zombie_arm", () -> new ZombieArmItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<ZombieArmItem> SHINY_ZOMBIE_ARM = HELPER.register("shiny_zombie_arm", () -> new ZombieArmItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> TEST_TUBE = HELPER.register("test_tube", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<BloodTestTubeItem> BLOOD_TEST_TUBE = HELPER.register("blood_test_tube", () -> new BloodTestTubeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLACKSMITH_GAVEL_HEAD = HELPER.register("blacksmith_gavel_head", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<BlacksmithGavelItem> WOODEN_BLACKSMITH_GAVEL = HELPER.register("wooden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.WOOD, 7, -3.25F, 1, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> STONE_BLACKSMITH_GAVEL = HELPER.register("stone_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.STONE, 7, -3.25F, 3, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> GOLDEN_BLACKSMITH_GAVEL = HELPER.register("golden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.GOLD, 7, -3.25F, 2, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> IRON_BLACKSMITH_GAVEL = HELPER.register("iron_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.IRON, 7, -3.25F, 10, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> DIAMOND_BLACKSMITH_GAVEL = HELPER.register("diamond_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.DIAMOND, 7, -3.25F, 30, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> NETHERITE_BLACKSMITH_GAVEL = HELPER.register("netherite_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.NETHERITE, 7, -3.25F, 60, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> ARCANE_GOLDEN_BLACKSMITH_GAVEL = HELPER.register("arcane_golden_blacksmith_gavel", () -> new BlacksmithGavelItem(ModItemTier.ARCANE_GOLDEN, 7, -3.25F, 15, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> REINFORCED_ARCANE_GOLDEN_BLACKSMITH_GAVEL = HELPER.register("reinforced_arcane_golden_blacksmith_gavel", () -> new BlacksmithGavelItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 7, -3.25F, 80, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> STELLARITE_PIECE = HELPER.register("stellarite_piece", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<SimpleFoiledItem> DARK_NETHER_STAR = HELPER.register("dark_nether_star", () -> new SimpleFoiledItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SMELTER_PRISM = HELPER.register("smelter_prism", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<EdelwoodBucketItem> EDELWOOD_BUCKET = HELPER.register("edelwood_bucket", () -> new EdelwoodBucketItem(() -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<EdelwoodBucketItem> EDELWOOD_WATER_BUCKET = HELPER.register("edelwood_water_bucket", () -> new EdelwoodBucketItem(() -> Fluids.WATER, 4, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodBucketItem> EDELWOOD_LAVA_BUCKET = HELPER.register("edelwood_lava_bucket", () -> new EdelwoodBucketItem(() -> Fluids.LAVA, 3, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMilkBucketItem> EDELWOOD_MILK_BUCKET = HELPER.register("edelwood_milk_bucket", () -> new EdelwoodMilkBucketItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<SolidEdelwoodBucketItem> EDELWOOD_POWDER_SNOW_BUCKET = HELPER.register("edelwood_powder_snow_bucket", () -> new SolidEdelwoodBucketItem(Blocks.POWDER_SNOW, SoundEvents.POWDER_SNOW_BREAK, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_PUFFERFISH_BUCKET = HELPER.register("edelwood_pufferfish_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.PUFFERFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_SALMON_BUCKET = HELPER.register("edelwood_salmon_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SALMON, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_COD_BUCKET = HELPER.register("edelwood_cod_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.COD, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_TROPICAL_FISH_BUCKET = HELPER.register("edelwood_tropical_fish_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.TROPICAL_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_BAT_BUCKET = HELPER.register("edelwood_bat_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.BAT, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_SQUID_BUCKET = HELPER.register("edelwood_squid_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SQUID, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_MAGMA_CUBE_BUCKET = HELPER.register("edelwood_magma_cube_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.MAGMA_CUBE, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_SLIME_BUCKET = HELPER.register("edelwood_slime_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SLIME, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_CHICKEN_BUCKET = HELPER.register("edelwood_chicken_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.CHICKEN, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_AXOLOTL_BUCKET = HELPER.register("edelwood_axolotl_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.AXOLOTL, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_AXOLOTL, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodSoupBucketItem> EDELWOOD_MUSHROOM_STEW_BUCKET = HELPER.register("edelwood_mushroom_stew_bucket", () -> new EdelwoodSoupBucketItem(() -> Items.MUSHROOM_STEW, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(Foods.MUSHROOM_STEW)));
    public static final RegistryObject<EdelwoodSuspiciousStewBucketItem> EDELWOOD_SUSPICIOUS_STEW_BUCKET = HELPER.register("edelwood_suspicious_stew_bucket", () -> new EdelwoodSuspiciousStewBucketItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(Foods.SUSPICIOUS_STEW)));
    public static final RegistryObject<EdelwoodSoupBucketItem> EDELWOOD_BEETROOT_SOUP_BUCKET = HELPER.register("edelwood_beetroot_soup_bucket", () -> new EdelwoodSoupBucketItem(() -> Items.BEETROOT_SOUP, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(Foods.BEETROOT_SOUP)));
    public static final RegistryObject<EdelwoodSoupBucketItem> EDELWOOD_BAT_SOUP_BUCKET = HELPER.register("edelwood_bat_soup_bucket", () -> new EdelwoodSoupBucketItem(ModItems.BAT_SOUP, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(new FoodProperties.Builder().nutrition(7).saturationMod (0.7F).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0), 1.0F).build())));

    public static class Stacks {
        public static final ItemStack LENS_OF_VERITATIS = new ItemStack(NewModItems.LENS_OF_VERITATIS.get());
        public static final ItemStack ORB_OF_TEMPORARY_FLIGHT = new ItemStack(ModItems.ORB_OF_TEMPORARY_FLIGHT.get());
        public static final ItemStack SANITY_METER = new ItemStack(NewModItems.SANITY_METER.get());
        public static final ItemStack ARCANE_CRYSTAL_DUST_SPECK = new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
    }
}