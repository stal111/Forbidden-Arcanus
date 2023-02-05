package com.stal111.forbidden_arcanus.data.rituals;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.BuiltinRituals;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaDataProvider;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 2023-01-03
 */
public class RitualDataProvider implements DataProvider, ValhelsiaDataProvider {

    private final PackOutput.PathProvider pathProvider;
    private final RegistryManager registryManager;

    private final Map<ResourceLocation, Ritual> builders = new HashMap<>();

    public RitualDataProvider(DataProviderInfo info) {
        this.pathProvider = info.output().createPathProvider(PackOutput.Target.DATA_PACK, "hephaestus_forge/rituals");
        this.registryManager = info.registryManager();
    }

    protected void registerRituals() {
        this.register(BuiltinRituals.ETERNAL_STELLA, ModItems.ETERNAL_STELLA.get(), Items.DIAMOND, builder -> builder.input(Ingredient.of(ModItems.XPETRIFIED_ORB.get()), 3).input(Ingredient.of(ModItems.STELLARITE_PIECE.get())).aureal(82).souls(1).blood(1000));
        this.register(BuiltinRituals.SMELTER_PRISM, ModItems.SMELTER_PRISM.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get(), builder -> builder.input(Ingredient.of(Items.COAL), 2).input(Ingredient.of(Items.BLAZE_POWDER), 4).aureal(82).souls(1).blood(1000));
    }

    private void register(ResourceLocation name, ItemLike result, ItemLike mainIngredient, UnaryOperator<RitualBuilder> builder) {
        this.builders.put(name, builder.apply(new RitualBuilder(new ItemStack(mainIngredient), new CreateItemResult(new ItemStack(result)))).build());
    }

    private void register(ResourceLocation name, ItemStack result, ItemStack mainIngredient, UnaryOperator<RitualBuilder> builder) {
        this.builders.put(name, builder.apply(new RitualBuilder(mainIngredient, new CreateItemResult(result))).build());
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        this.registerRituals();

        return CompletableFuture.allOf(this.builders.entrySet().stream().map(entry -> {
            JsonElement element = Ritual.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue()).getOrThrow(false, LOGGER::error);

            return DataProvider.saveStable(output, element, this.pathProvider.json(entry.getKey()));
        }).toArray(CompletableFuture[]::new));
    }

    private ResourceLocation modLoc(String path) {
        return new ResourceLocation(this.getModId(), path);
    }

    @Override
    public @NotNull String getName() {
        return ForbiddenArcanus.MOD_ID + " - Rituals";
    }

    @Override
    public String getModId() {
        return this.registryManager.modId();
    }
}
