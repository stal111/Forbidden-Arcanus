package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.block.EternalObsidianSkullItem;
import com.stal111.forbidden_arcanus.item.block.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.item.block.UtremJarItem;
import com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullItemRenderer;
import com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullShieldItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.registry.ItemRegistryHelper;

/**
 * Mod Items
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.NewModItems
 *
 * @author Valhelsia Team
 * @version 16.2.0
 * @since 2021-01-26
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NewModItems {

    public static final ItemRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getItemHelper();

    public static final RegistryObject<Item> SANITY_METER = HELPER.register("sanity_meter", () -> new SanityMeterItem(new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(1)));
    public static final RegistryObject<Item> LENS_OF_VERITATIS = HELPER.register("lens_of_veritatis", () -> new Item(new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(1)));
    public static final RegistryObject<Item> PURIFYING_SOAP = HELPER.register("purifying_soap", () -> new PurifyingSoapItem(new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(1)));
    public static final RegistryObject<Item> WET_PURIFYING_SOAP = HELPER.register("wet_purifying_soap", () -> new WetPurifyingSoapItem(new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(1)));
    public static final RegistryObject<ObsidianSkullItem> OBSIDIAN_SKULL = HELPER.register("obsidian_skull", () -> new ObsidianSkullItem(NewerModBlocks.OBSIDIAN_SKULL.get(), NewerModBlocks.OBSIDIAN_WALL_SKULL.get(), new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(1).rarity(Rarity.UNCOMMON).setISTER(() -> ObsidianSkullItemRenderer::new).isImmuneToFire()));
    public static final RegistryObject<ObsidianSkullShieldItem> OBSIDIAN_SKULL_SHIELD = HELPER.register("obsidian_skull_shield", () -> new ObsidianSkullShieldItem(new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxDamage(1008).rarity(Rarity.UNCOMMON).setISTER(() -> ObsidianSkullShieldItemRenderer::new).isImmuneToFire()));
    public static final RegistryObject<EternalObsidianSkullItem> ETERNAL_OBSIDIAN_SKULL = HELPER.register("eternal_obsidian_skull", () -> new EternalObsidianSkullItem(NewerModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), NewerModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get(), new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(1).rarity(Rarity.RARE).setISTER(() -> ObsidianSkullItemRenderer::new).isImmuneToFire()));
    public static final RegistryObject<UtremJarItem> UTREM_JAR = HELPER.register("utrem_jar", () -> new UtremJarItem(NewerModBlocks.UTREM_JAR.get(), new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<AurealBottleItem> AUREAL_BOTTLE = HELPER.register("aureal_bottle", () -> new AurealBottleItem(new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).maxStackSize(16)));
}