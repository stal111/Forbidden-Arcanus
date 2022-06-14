package com.stal111.forbidden_arcanus.common.integration;

//@JeiPlugin
//public class ForbiddenArcanusJEIPlugin implements IModPlugin {
//
//    public IRecipeCategory<?> hephaestusSmithing;
//
//    public static final RecipeType<Ritual> HEPHAESTUS_SMITHING = RecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_smithing", Ritual.class);
//
//    @Nonnull
//    @Override
//    public ResourceLocation getPluginUid() {
//        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "main");
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//        registration.addRecipes(RecipeTypes.SMITHING, ApplyModifierRecipeMaker.getRecipes());
//        registration.addRecipes(HEPHAESTUS_SMITHING, RitualLoader.getRituals());
//    }
//
//    @Override
//    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
//        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), HEPHAESTUS_SMITHING);
//    }
//
//    @Override
//    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
//        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
//
//        registration.addRecipeCategories(this.hephaestusSmithing = new HephaestusSmithingCategory(guiHelper));
//    }
//}