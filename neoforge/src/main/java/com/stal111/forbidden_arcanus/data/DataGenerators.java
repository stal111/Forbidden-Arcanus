package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * @author stal111
 * @since 2021-01-26
 */
@EventBusSubscriber
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();

        DataGenerator.PackGenerator featurePack = generator.getBuiltinDatapack(true, ForbiddenArcanus.MOD_ID, "preview");

        featurePack.addProvider(output1 -> PackMetadataGenerator.forFeaturePack(
                output1,
                Component.literal("Enable experimental features for Forbidden Arcanus"),
                FeatureFlagSet.of(ForbiddenArcanus.PREVIEW)
        ));
    }
}
