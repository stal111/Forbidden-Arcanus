package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.joml.Vector3fc;

import java.util.List;

/**
 * @author stal111
 * @since 11.06.2024
 */
public class EssenceDropParticle extends SingleQuadParticle {

    private static final float SPEED = 0.07f;

    private final SpriteSet spriteSet;
    private final List<Vector3fc> path;
    private int currentPathIndex = 0;
    private float progress = 0.0f;

    protected EssenceDropParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, RandomSource randomSource, List<Vector3fc> path) {
        super(level, x, y, z, spriteSet.get(randomSource));
        this.spriteSet = spriteSet;
        this.path = path;

        this.hasPhysics = false;
        this.setLifetime(30);
    }

    protected EssenceDropParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, spriteSet.first());
        this.spriteSet = spriteSet;
        this.path = List.of();

        this.xd = xSpeed;
        this.yd = Math.abs(ySpeed) + 0.1D;
        this.zd = zSpeed;
        this.lifetime = 16 + this.random.nextInt(8);
        this.quadSize = 0.12F * (this.random.nextFloat() * 0.3F + 0.8F);

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (!this.path.isEmpty()) {
            if (this.currentPathIndex >= this.path.size()) {
                this.remove();
            } else {
                Vector3fc currentPos = this.path.get(this.currentPathIndex);
                Vector3fc nextPos = this.path.get(Math.min(this.currentPathIndex + 1, this.path.size() - 1));

                this.x = currentPos.x() + (nextPos.x() - currentPos.x()) * this.progress;
                this.y = currentPos.y() + (nextPos.y() - currentPos.y()) * this.progress;
                this.z = currentPos.z() + (nextPos.z() - currentPos.z()) * this.progress;

                this.progress += SPEED;

                if (this.progress >= 1.0f) {
                    this.progress = 0.0f;
                    this.currentPathIndex++;
                }
            }
        } else {
            this.yd -= 0.03D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.96D;
            this.yd *= 0.96D;
            this.zd *= 0.96D;

            if (this.onGround) {
                this.xd *= 0.7D;
                this.zd *= 0.7D;
            }

            if (this.age++ >= this.lifetime) {
                this.remove();
                return;
            }

            this.setSpriteFromAge(this.spriteSet);
        }
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<EssenceDropParticleOption> {

        @Override
        public Particle createParticle(EssenceDropParticleOption particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            List<Vector3fc> path = particleType.path().path();

            if (path.isEmpty()) {
                return new EssenceDropParticle(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
            }

            return new EssenceDropParticle(level, x, y, z, this.spriteSet, random, path);
        }
    }
}
