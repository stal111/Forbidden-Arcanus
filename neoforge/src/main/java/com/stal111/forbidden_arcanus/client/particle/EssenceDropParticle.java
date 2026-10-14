package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
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
    private float pathProgress = 0.0f;

    protected EssenceDropParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, RandomSource randomSource, List<Vector3fc> path) {
        super(level, x, y, z, spriteSet.get(randomSource));
        this.spriteSet = spriteSet;
        this.path = path;

        this.hasPhysics = false;
        this.lifetime = (int) Math.ceil((this.path.size() - 1) / SPEED) + 5;

        if (!this.path.isEmpty()) {
            Vector3fc start = this.path.getFirst();
            this.x = start.x();
            this.y = start.y();
            this.z = start.z();
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
        }
    }

    protected EssenceDropParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, spriteSet.first());
        this.spriteSet = spriteSet;
        this.path = List.of();

        this.xd = xSpeed;
        this.yd = Math.abs(ySpeed) + 0.1D;
        this.zd = zSpeed;
        this.hasPhysics = true;
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
            if (this.path.size() < 2) {
                this.remove();
                return;
            }

            this.pathProgress += SPEED;

            if (this.pathProgress >= this.path.size() - 1) {
                this.remove();
                return;
            }

            Vector3f pos = this.getCatmullRomPos(this.pathProgress);
            this.x = pos.x();
            this.y = pos.y();
            this.z = pos.z();

            if (this.age++ >= this.lifetime) {
                this.remove();
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

    private Vector3f getCatmullRomPos(float t) {
        int i = Math.min((int) t, this.path.size() - 2);
        float frac = t - i;

        Vector3fc p1 = this.path.get(i);
        Vector3fc p2 = this.path.get(i + 1);

        Vector3fc p0 = (i > 0) ? this.path.get(i - 1) : null;
        float p0x = (p0 != null) ? p0.x() : 2.0f * p1.x() - p2.x();
        float p0y = (p0 != null) ? p0.y() : 2.0f * p1.y() - p2.y();
        float p0z = (p0 != null) ? p0.z() : 2.0f * p1.z() - p2.z();

        Vector3fc p3 = (i + 2 < this.path.size()) ? this.path.get(i + 2) : null;
        float p3x = (p3 != null) ? p3.x() : 2.0f * p2.x() - p1.x();
        float p3y = (p3 != null) ? p3.y() : 2.0f * p2.y() - p1.y();
        float p3z = (p3 != null) ? p3.z() : 2.0f * p2.z() - p1.z();

        return new Vector3f(
                catmullRom(p0x, p1.x(), p2.x(), p3x, frac),
                catmullRom(p0y, p1.y(), p2.y(), p3y, frac),
                catmullRom(p0z, p1.z(), p2.z(), p3z, frac)
        );
    }

    private static float catmullRom(float p0, float p1, float p2, float p3, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        return 0.5f * (
                (2.0f * p1) +
                        (-p0 + p2) * t +
                        (2.0f * p0 - 5.0f * p1 + 4.0f * p2 - p3) * t2 +
                        (-p0 + 3.0f * p1 - 3.0f * p2 + p3) * t3
        );
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
