package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.source.CosmeticsSource;

import java.util.List;
import java.util.UUID;

/**
 * @author stal111
 * @since 2022-07-31
 */
public class FACosmeticsSource extends CosmeticsSource {

    public FACosmeticsSource(String name) {
        super(name);
    }

    @Override
    public List<CosmeticKey> loadCosmeticsFor(UUID uuid) {
        return List.of(new CosmeticKey(this, "draco_aurum_wings"), new CosmeticKey(this, "draco_aurum_head"));
    }

    @Override
    public void loadTextures(CosmeticKey key) {
        this.registerMainTexture(key, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/cosmetic/" + key.name() + ".png"));
    }
}
