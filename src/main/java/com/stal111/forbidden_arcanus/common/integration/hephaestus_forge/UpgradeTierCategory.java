package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.common.integration.ForbiddenArcanusJEIPlugin;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-06-05
 */
public class UpgradeTierCategory extends HephaestusForgeCategory {

    private static final String NAME = "hephaestus_forge_upgrading";
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/jei/hephaestus_forge/forge_upgrading.png");

    private static final IntIntPair REQUIRED_TIER_POSITION = IntIntPair.of(7, 35);
    private static final IntIntPair UPGRADED_TIER_POSITION = IntIntPair.of(123, 36);

    public UpgradeTierCategory(IGuiHelper guiHelper) {
        super(NAME, guiHelper, TEXTURE, 41, 79);
    }

    @Override
    public @NotNull RecipeType<Ritual> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.HEPHAESTUS_FORGE_UPGRADING;
    }

    @Override
    protected void buildRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Ritual ritual) {
        if (ritual.result() instanceof UpgradeTierResult result) {
            builder.addSlot(RecipeIngredientRole.INPUT, REQUIRED_TIER_POSITION.firstInt(), REQUIRED_TIER_POSITION.secondInt())
                    .addItemStack(HephaestusForgeBlock.setTierOnStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), result.getRequiredTier()));

            builder.addSlot(RecipeIngredientRole.OUTPUT, UPGRADED_TIER_POSITION.firstInt(), UPGRADED_TIER_POSITION.secondInt())
                    .addItemStack(HephaestusForgeBlock.setTierOnStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), result.getUpgradedTier()));
        }
    }

    @Override
    protected boolean displayEnhancers() {
         return false;
    }
}
