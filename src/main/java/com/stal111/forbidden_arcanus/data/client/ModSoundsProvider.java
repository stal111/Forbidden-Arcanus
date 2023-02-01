package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;

import java.util.Objects;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModSoundsProvider extends SoundDefinitionsProvider {

    public ModSoundsProvider(DataProviderInfo info) {
        super(info.output(), ForbiddenArcanus.MOD_ID, Objects.requireNonNull(info.fileHelper()));
    }

    @Override
    public void registerSounds() {
        this.add(ModSounds.ENERGY_BALL_LAUNCH, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "energy_ball_launch"), SoundDefinition.SoundType.SOUND)));
        this.add(ModSounds.ENERGY_BALL_HIT, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "energy_ball_hit"), SoundDefinition.SoundType.SOUND)));
        this.add(ModSounds.QUANTUM_CATCHER_PICK_UP, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "quantum_catcher_pick_up"), SoundDefinition.SoundType.SOUND)));
        this.add(ModSounds.QUANTUM_CATCHER_RELEASE, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "quantum_catcher_release"), SoundDefinition.SoundType.SOUND)));
        this.add(ModSounds.FERROGNETIC_MIXTURE_APPLY, SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "ferrognetic_mixture_apply_1"), SoundDefinition.SoundType.SOUND)).with(SoundDefinition.Sound.sound(new ResourceLocation(ForbiddenArcanus.MOD_ID, "ferrognetic_mixture_apply_2"), SoundDefinition.SoundType.SOUND)));
    }
}
