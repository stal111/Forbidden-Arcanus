package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

/**
 * @author stal111
 * @since 11.06.2024
 */
public class EssenceDropParticle extends TextureSheetParticle {

    private static final float SPEED = 0.07f;

    private final List<Vector3f> path;
    private int currentPathIndex = 0;
    private float progress = 0.0f;

    protected EssenceDropParticle(ClientLevel level, double x, double y, double z, List<Vector3f> path) {
        super(level, x, y, z);
        this.path = path;

        this.hasPhysics = false;
        this.setLifetime(30);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.currentPathIndex >= this.path.size()) {
            this.remove();
        } else {
            Vector3f currentPos = this.path.get(this.currentPathIndex);
            Vector3f nextPos = this.path.get(Math.min(this.currentPathIndex + 1, this.path.size() - 1));

            this.x = currentPos.x() + (nextPos.x() - currentPos.x()) * this.progress;
            this.y = currentPos.y() + (nextPos.y() - currentPos.y()) * this.progress;
            this.z = currentPos.z() + (nextPos.z() - currentPos.z()) * this.progress;

            this.progress += SPEED;

            if (this.progress >= 1.0f) {
                this.progress = 0.0f;
                this.currentPathIndex++;
            }
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<EssenceDropParticleOption> {

        @Override
        public Particle createParticle(@NotNull EssenceDropParticleOption type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            EssenceDropParticle particle = new EssenceDropParticle(level, x, y, z, type.path());
            particle.pickSprite(this.spriteSet);

            return particle;
        }
    }
}
