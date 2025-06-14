package com.stal111.forbidden_arcanus.client.particle;

import com.stal111.forbidden_arcanus.core.mixin.client.HugeExplosionParticleAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ColorParticleOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpellExplosionProvider implements ParticleProvider<ColorParticleOption> {

    private final SpriteSet spriteSet;

    public SpellExplosionProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public @Nullable Particle createParticle(@NotNull ColorParticleOption particleOption, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        Particle particle = HugeExplosionParticleAccessor.createParticle(level, x, y, z, xSpeed, this.spriteSet);
        particle.setColor(particleOption.getRed(), particleOption.getGreen(), particleOption.getBlue());

        return particle;
    }
}
