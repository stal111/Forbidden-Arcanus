package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModSoundsProvider extends SoundDefinitionsProvider {

    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    public ModSoundsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ForbiddenArcanus.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(ModSounds.ENERGY_BALL_LAUNCH, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "energy_ball_launch"), SoundDefinition.SoundType.SOUND)));
        this.add(ModSounds.ENERGY_BALL_HIT, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "energy_ball_hit"), SoundDefinition.SoundType.SOUND)));
    }
}
