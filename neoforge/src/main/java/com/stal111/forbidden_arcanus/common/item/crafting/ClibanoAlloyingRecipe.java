package com.stal111.forbidden_arcanus.common.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.item.crafting.input.ClibanoAlloyingRecipeInput;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record ClibanoAlloyingRecipe(
        List<MoltenMaterial> requiredMaterials,
        ItemStackTemplate result,
        int duration
) implements Recipe<ClibanoAlloyingRecipeInput> {

    private static final MapCodec<ClibanoAlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            MoltenMaterial.CODEC.listOf().fieldOf("required_materials").forGetter(recipe -> recipe.requiredMaterials),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            ExtraCodecs.POSITIVE_INT.fieldOf("duration").forGetter(recipe -> recipe.duration)
    ).apply(instance, ClibanoAlloyingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClibanoAlloyingRecipe> STREAM_CODEC = StreamCodec.composite(
            MoltenMaterial.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ClibanoAlloyingRecipe::requiredMaterials,
            ItemStackTemplate.STREAM_CODEC,
            ClibanoAlloyingRecipe::result,
            ByteBufCodecs.INT,
            ClibanoAlloyingRecipe::duration,
            ClibanoAlloyingRecipe::new
    );

    public static final RecipeSerializer<ClibanoAlloyingRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    @Override
    public boolean matches(ClibanoAlloyingRecipeInput input, Level level) {
        for (MoltenMaterial requiredMaterial : this.requiredMaterials) {
            if (input.materials().stream().noneMatch(material -> material.type().equals(requiredMaterial.type()) && material.amount() >= requiredMaterial.amount())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(ClibanoAlloyingRecipeInput input) {
        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends Recipe<ClibanoAlloyingRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<ClibanoAlloyingRecipeInput>> getType() {
        return ModRecipeTypes.CLIBANO_ALLOYING.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }
}
