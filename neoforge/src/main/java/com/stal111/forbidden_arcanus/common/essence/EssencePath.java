package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

public record EssencePath(EssenceType essenceType, List<Vector3fc> path) {

    public static final MapCodec<EssencePath> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("essence_type").forGetter(EssencePath::essenceType),
            ExtraCodecs.VECTOR3F.listOf().fieldOf("path").forGetter(EssencePath::path)
    ).apply(instance, EssencePath::new));

    public static final StreamCodec<FriendlyByteBuf, EssencePath> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssencePath::essenceType,
            ByteBufCodecs.VECTOR3F.apply(ByteBufCodecs.list()),
            EssencePath::path,
            EssencePath::new
    );

    public static EssencePath create(EssenceType essenceType, BlockPos start, BlockPos end, RandomSource randomSource) {
        return new EssencePath(essenceType, calculatePath(start.getCenter(), end.getCenter(), randomSource));
    }

    private static List<Vector3fc> calculatePath(Vec3 start, Vec3 end, RandomSource random) {
        Vector3f direction = new Vector3f((float) (end.x() - start.x()), (float) (end.y() + 0.4F - start.y()), (float) (end.z() - start.z()));
        double distance = direction.length();
        direction.normalize();

        Vector3f vertical = new Vector3f(0, 1, 0);
        float displacementStrength = random.nextFloat() + 0.5F;

        List<Vector3fc> path = new ArrayList<>();

        for (float i = 0; i < distance; i += 0.3F) {
            Vector3f currentPos = new Vector3f((float) start.x(), (float) start.y(), (float) start.z()).add(new Vector3f(direction).mul(i));
            float angle = (float) (i * Math.PI / distance);
            Vector3f displacement = new Vector3f(vertical).mul((float) (Math.sin(angle) * displacementStrength));
            Vector3f particlePos = currentPos.add(displacement);

            path.add(particlePos);
        }

        return path;
    }
}
