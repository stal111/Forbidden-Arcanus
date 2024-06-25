package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 20.05.2024
 */
public class LangProvider extends LanguageProvider {

    public static final Map<DyeColor, String> COLOR_TO_STRING = Util.make(new EnumMap<>(DyeColor.class), map -> {
        map.put(DyeColor.WHITE, "White");
        map.put(DyeColor.ORANGE, "Orange");
        map.put(DyeColor.MAGENTA, "Magenta");
        map.put(DyeColor.LIGHT_BLUE, "Light Blue");
        map.put(DyeColor.YELLOW, "Yellow");
        map.put(DyeColor.LIME, "Lime");
        map.put(DyeColor.PINK, "Pink");
        map.put(DyeColor.GRAY, "Gray");
        map.put(DyeColor.LIGHT_GRAY, "Light Gray");
        map.put(DyeColor.CYAN, "Cyan");
        map.put(DyeColor.PURPLE, "Purple");
        map.put(DyeColor.BLUE, "Blue");
        map.put(DyeColor.BROWN, "Brown");
        map.put(DyeColor.GREEN, "Green");
        map.put(DyeColor.RED, "Red");
        map.put(DyeColor.BLACK, "Black");
    });

    public LangProvider(PackOutput output) {
        super(output, ForbiddenArcanus.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(Util.makeDescriptionId("itemGroup", ForbiddenArcanus.location("main")), "Forbidden & Arcanus");

        this.add(ModItemModifiers.ETERNAL, "Eternal");
        this.add(ModItemModifiers.FIERY, "Fiery");
        this.add(ModItemModifiers.MAGNETIZED, "Magnetized");
        this.add(ModItemModifiers.DEMOLISHING, "Demolishing");
        this.add(ModItemModifiers.AQUATIC, "Aquatic");
        this.add(ModItemModifiers.SOULBOUND, "Soulbound");

        this.add("essence", EssenceType.AUREAL, "Aureal");
        this.add("essence", EssenceType.SOULS, "Souls");
        this.add("essence", EssenceType.BLOOD, "Blood");
        this.add("essence", EssenceType.EXPERIENCE, "Experience");

        this.add(Util.makeDescriptionId("container", ForbiddenArcanus.location("hephaestus_forge")), "Hephaestus Forge");
        this.add(Util.makeDescriptionId("container", ForbiddenArcanus.location("clibano")), "Clibano");

        this.addEntityType(ModEntities.LOST_SOUL, "Lost Soul");
        this.addEntityType(ModEntities.DARK_TRADER, "Dark Trader");

       // this.add(ModEnchantments.AUREAL_RESERVOIR.get(), "Aureal Reservoir");

        this.addBlock(ModBlocks.QUANTUM_CORE, "Quantum Core");
        this.addBlock(ModBlocks.QUANTUM_INJECTOR, "Quantum Injector");
        this.addItem(ModItems.QUANTUM_CATCHER, "Quantum Catcher");

        for (DyeColor color : DyeColor.values()) {
            this.addItem(ModItems.DYED_QUANTUM_CATCHERS.get(color), COLOR_TO_STRING.get(color) + " Quantum Catcher");
        }

        this.add("block", "hephaestus_forge.tier.match_exact", "Requires exactly tier %s");
        this.add("block", "hephaestus_forge.tier.at_least", "Requires at least tier %s");
    }

    private void add(Supplier<? extends ItemModifier> modifier, String name) {
        this.add(modifier.get().getTranslationKey(), name);
    }

    private <T extends StringRepresentable> void add(String category, T value, String name) {
        this.add(Util.makeDescriptionId(category, ForbiddenArcanus.location(value.getSerializedName())), name);
    }

    public void add(String category, String path, String name) {
        this.add(Util.makeDescriptionId(category, ForbiddenArcanus.location(path)), name);
    }
}
