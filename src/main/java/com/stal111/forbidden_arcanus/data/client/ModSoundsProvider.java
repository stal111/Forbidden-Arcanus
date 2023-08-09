package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.valhelsia.valhelsia_core.api.datagen.DataProviderContext;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModSoundsProvider extends SoundDefinitionsProvider {

    public ModSoundsProvider(DataProviderContext context, ExistingFileHelper fileHelper) {
        super(context.output(), ForbiddenArcanus.MOD_ID, fileHelper);
    }

    @Override
    public void registerSounds() {
        this.add(ModSounds.ENERGY_BALL_LAUNCH, SoundDefinition.definition().with(this.simpleSound("energy_ball_launch")));
        this.add(ModSounds.ENERGY_BALL_HIT, SoundDefinition.definition().with(this.simpleSound("energy_ball_hit")));
        this.add(ModSounds.QUANTUM_CATCHER_PICK_UP, SoundDefinition.definition().with(this.simpleSound("quantum_catcher_pick_up")));
        this.add(ModSounds.QUANTUM_CATCHER_RELEASE, SoundDefinition.definition().with(this.simpleSound("quantum_catcher_release")));
        this.add(ModSounds.FERROGNETIC_MIXTURE_APPLY, SoundDefinition.definition().with(this.simpleSound("ferrognetic_mixture_apply_1")).with(this.simpleSound("ferrognetic_mixture_apply_2")));
        this.add(ModSounds.CLIBANO_FIRE_CRACKLE, SoundDefinition.definition().with(this.simpleSound("clibano_fire_crackle_1")).with(this.simpleSound("clibano_fire_crackle_2")).with(this.simpleSound("clibano_fire_crackle_3")).with(this.simpleSound("clibano_fire_crackle_4")).with(this.simpleSound("clibano_fire_crackle_5")));
        this.add(ModSounds.CLIBANO_SOUL_FIRE_CRACKLE, SoundDefinition.definition().with(this.simpleSound("clibano_soul_fire_crackle_1")).with(this.simpleSound("clibano_soul_fire_crackle_2")).with(this.simpleSound("clibano_soul_fire_crackle_3")).with(this.simpleSound("clibano_soul_fire_crackle_4")).with(this.simpleSound("clibano_soul_fire_crackle_5")));
        this.add(ModSounds.BLACKSMITH_GAVEL_RITUAL_START, SoundDefinition.definition().with(this.simpleSound("blacksmith_gavel_ritual_start")));
    }

    private SoundDefinition.Sound simpleSound(String name) {
        return SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, name), SoundDefinition.SoundType.SOUND);
    }
}
