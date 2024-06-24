package com.stal111.forbidden_arcanus.data.hephaestus_forge;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * @author stal111
 * @since 14.04.2024
 */
public class ModMagicCircles extends DatapackRegistryClass<MagicCircleType> {

    public static final DatapackRegistryHelper<MagicCircleType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.MAGIC_CIRCLE);

    //INNER TEXTURES
    private static final ResourceLocation UNION_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/inner/union.png");
    private static final ResourceLocation ORIGIN_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/inner/origin.png");

    //OUTER TEXTURES
    private static final ResourceLocation PURE_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/outer/pure.png");
    private static final ResourceLocation PACTUM_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/outer/pactum.png");

    public static final ResourceKey<MagicCircleType> CREATE_ITEM = HELPER.createKey("create_item");
    public static final ResourceKey<MagicCircleType> UPGRADE_TIER = HELPER.createKey("upgrade_tier");
    public static final ResourceKey<MagicCircleType> UPGRADE_FINAL_TIER = HELPER.createKey("upgrade_final_tier");

    public ModMagicCircles(BootstrapContext<MagicCircleType> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<MagicCircleType> context) {
        context.register(CREATE_ITEM, new MagicCircleType(UNION_TEXTURE, PURE_TEXTURE));
        context.register(UPGRADE_TIER, new MagicCircleType(ORIGIN_TEXTURE, PURE_TEXTURE));
        context.register(UPGRADE_FINAL_TIER, new MagicCircleType(ORIGIN_TEXTURE, PACTUM_TEXTURE));
    }
}
