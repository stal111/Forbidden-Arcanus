package com.stal111.forbidden_arcanus.common.item.crafting.cache;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.Collection;

public final class ClibanoRecipeCache {

    public static Collection<RecipeHolder<ClibanoMeltingRecipe>> meltingRecipes;
    public static Collection<RecipeHolder<ClibanoAlloyingRecipe>> alloyingRecipes;

    @EventBusSubscriber(modid = ForbiddenArcanus.MOD_ID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(ModRecipeTypes.CLIBANO_MELTING.get());
            event.sendRecipes(ModRecipeTypes.CLIBANO_ALLOYING.get());
        }
    }

    @EventBusSubscriber(modid = ForbiddenArcanus.MOD_ID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipesReceived(RecipesReceivedEvent event) {
            meltingRecipes = event.getRecipeMap().byType(ModRecipeTypes.CLIBANO_MELTING.get());
            alloyingRecipes = event.getRecipeMap().byType(ModRecipeTypes.CLIBANO_ALLOYING.get());
        }
    }
}
