package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 20.05.2024
 */
public class LangProvider extends LanguageProvider {

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

        this.add(ModEntities.LOST_SOUL.get(), "Lost Soul");
        this.add(ModEntities.DARK_TRADER.get(), "Dark Trader");

       // this.add(ModEnchantments.AUREAL_RESERVOIR.get(), "Aureal Reservoir");

        this.add(ModBlocks.QUANTUM_CORE.get(), "Quantum Core");
        this.add(ModBlocks.QUANTUM_INJECTOR.get(), "Quantum Injector");

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
