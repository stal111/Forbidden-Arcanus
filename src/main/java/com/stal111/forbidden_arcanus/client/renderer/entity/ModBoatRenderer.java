package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.CustomBoat;
import com.stal111.forbidden_arcanus.common.entity.ModBoat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stal111
 * @since 2022-06-25
 */
public class ModBoatRenderer extends BoatRenderer {

    private final Map<ModBoat.Type, Pair<ResourceLocation, BoatModel>> boatResources = new HashMap<>();

    public ModBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context, hasChest);

        for(ModBoat.Type type : ModBoat.Type.values()) {
            boatResources.put(type, Pair.of(type.getTexture(hasChest), new BoatModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, hasChest ? type.getChestModelLocation() : type.getModelLocation()), "main")), hasChest)));
        }
    }

    @Nonnull
    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(@Nonnull Boat boat) {
        if (boat instanceof CustomBoat customBoat) {
            return this.boatResources.get(customBoat.getWoodType());
        }
        return super.getModelWithLocation(boat);
    }
}
