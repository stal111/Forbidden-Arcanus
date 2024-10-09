package com.stal111.forbidden_arcanus.common.block.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.client.particle.EssenceDropParticleOption;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorBlockEntity extends BlockEntity {

    private static final Map<EssenceType, Holder<PoiType>> ESSENCE_TYPE_TO_POI_TYPE = Map.of(
            EssenceType.AUREAL, ModPOITypes.AUREAL_UTREM_JAR,
            EssenceType.SOULS, ModPOITypes.SOULS_UTREM_JAR,
            EssenceType.BLOOD, ModPOITypes.BLOOD_UTREM_JAR,
            EssenceType.EXPERIENCE, ModPOITypes.EXPERIENCE_UTREM_JAR
    );

    private static final int TICKS_BETWEEN_ACTIONS = 5;
    private static final int TRANSFORM_ANIMATION_DURATION = 60;

    public final AnimationState transformAnimation = new AnimationState();
    public final AnimationState rotateAnimation = new AnimationState();

    private @Nullable ParticlePath particlePath;

    private @Nullable HephaestusForgeBlockEntity forgeBlockEntity;
    private @Nullable EssenceUtremJarBlockEntity jarBlockEntity;

    private boolean playAnimation = false;
    private int tickCount;

    public QuantumInjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUANTUM_INJECTOR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        blockEntity.transformAnimation.animateWhen(state.getValue(BlockStateProperties.ENABLED) && blockEntity.playAnimation, blockEntity.tickCount);
        blockEntity.rotateAnimation.animateWhen(state.getValue(BlockStateProperties.ENABLED) && !blockEntity.transformAnimation.isStarted(), blockEntity.tickCount);

        if (blockEntity.playAnimation && blockEntity.tickCount >= TRANSFORM_ANIMATION_DURATION) {
            blockEntity.playAnimation = false;
        }

        blockEntity.tickCount++;

        ParticlePath particlePath = blockEntity.particlePath;
        if (particlePath != null && blockEntity.tickCount % 10 == 0) {
            BlockPos jarPos = particlePath.start;

            level.addParticle(new EssenceDropParticleOption(particlePath.essenceType, particlePath.get(level.random)), jarPos.getX() + 0.5, jarPos.getY() + 0.5, jarPos.getZ() + 0.5, 0, 0, 0);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        if (level.getGameTime() % TICKS_BETWEEN_ACTIONS != 0 || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (blockEntity.forgeBlockEntity == null || blockEntity.forgeBlockEntity.isRemoved()) {
            blockEntity.forgeBlockEntity = null;

            serverLevel.getPoiManager()
                    .findClosest(holder -> holder.value() == ModPOITypes.HEPHAESTUS_FORGE.get(), pos, 4, PoiManager.Occupancy.ANY)
                    .flatMap(forgePos -> serverLevel.getBlockEntity(forgePos, ModBlockEntities.HEPHAESTUS_FORGE.get()))
                    .ifPresent(forgeBlockEntity -> blockEntity.forgeBlockEntity = forgeBlockEntity);
        }

        blockEntity.jarBlockEntity = null;

        if (blockEntity.forgeBlockEntity != null) {
            for (EssenceType type : EssenceType.values()) {
                if (blockEntity.jarBlockEntity != null) {
                    break;
                }

                if (!blockEntity.forgeBlockEntity.getEssenceManager().isEssenceFull(type)) {
                    serverLevel.getPoiManager()
                            .findClosest(holder -> holder.value() == ESSENCE_TYPE_TO_POI_TYPE.get(type).value(), pos, 8, PoiManager.Occupancy.ANY)
                            .flatMap(jarPos -> level.getBlockEntity(jarPos, ModBlockEntities.ESSENCE_UTREM_JAR.get()))
                            .ifPresent(jarBlockEntity -> blockEntity.jarBlockEntity = jarBlockEntity);
                }
            }
        }

        blockEntity.transferEssence(serverLevel, pos);
    }

    private void transferEssence(ServerLevel level, BlockPos pos) {
        if (this.forgeBlockEntity == null || this.jarBlockEntity == null) {
            if (this.particlePath != null) {
                this.particlePath = null;

                level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
            }

            return;
        }

        EssenceType essenceType = this.jarBlockEntity.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE);

        this.particlePath = new ParticlePath(essenceType, this.jarBlockEntity.getBlockPos(), this.forgeBlockEntity.getBlockPos());

        this.forgeBlockEntity.getEssenceManager().increaseEssence(essenceType, 5);
        this.jarBlockEntity.addEssence(-5);

        level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void onLoad() {
        if (this.level != null && this.level.isClientSide() && this.getBlockState().getValue(BlockStateProperties.ENABLED)) {
            if (this.playAnimation) {
                this.transformAnimation.startIfStopped(this.tickCount);
            } else {
                this.rotateAnimation.startIfStopped(this.tickCount);
            }
        }
    }

    public void startAnimation() {
        this.playAnimation = true;
        this.tickCount = 0;
    }

    public int getTickCount() {
        return this.tickCount;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);

        tag.putBoolean("has_particle_path", this.particlePath != null);

        if (this.particlePath != null) {
            ParticlePath.CODEC.encodeStart(NbtOps.INSTANCE, this.particlePath).result().ifPresent(particlePath -> tag.put("particle_path", particlePath));
        }

        return tag;
    }

    @Override
    public void onDataPacket(@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket packet, HolderLookup.@NotNull Provider lookupProvider) {
        this.handleUpdateTag(packet.getTag(), lookupProvider);
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.handleUpdateTag(tag, provider);

        this.particlePath = ParticlePath.CODEC.parse(NbtOps.INSTANCE, tag.get("particle_path")).result().orElse(null);

        System.out.printf("Particle Path: %s%n", this.particlePath);
    }

    static class ParticlePath {

        public static final Codec<ParticlePath> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EssenceType.CODEC.fieldOf("essence_type").forGetter(o -> o.essenceType),
                BlockPos.CODEC.fieldOf("jar_pos").forGetter(o -> o.start),
                BlockPos.CODEC.fieldOf("forge_pos").forGetter(o -> o.end)
        ).apply(instance, ParticlePath::new));

        private final EssenceType essenceType;
        private final BlockPos start;
        private final BlockPos end;

        private List<Vector3f> particlePath;

        public ParticlePath(EssenceType essenceType, BlockPos jarPos, BlockPos forgePos) {
            this.essenceType = essenceType;
            this.start = jarPos;
            this.end = forgePos;
        }

        public List<Vector3f> get(RandomSource random) {
            if (this.particlePath == null) {
                this.particlePath = this.calculatePath(random);
            }

            return this.particlePath;
        }

        private List<Vector3f> calculatePath(RandomSource random) {
            Vector3f direction = new Vector3f(this.end.getX() - this.start.getX(), this.end.getY() + 0.4F - this.start.getY(), this.end.getZ() - this.start.getZ());
            double distance = direction.length();
            direction.normalize();

            Vector3f vertical = new Vector3f(0, 1, 0);
            float displacementStrength = random.nextFloat() + 0.5F;

            List<Vector3f> path = new ArrayList<>();

            for (float i = 0; i < distance; i += 0.3F) {
                Vector3f currentPos = new Vector3f(this.start.getX() + 0.5F, this.start.getY() + 0.5F, this.start.getZ() + 0.5F).add(new Vector3f(direction).mul(i));
                float angle = (float) (i * Math.PI / distance);
                Vector3f displacement = new Vector3f(vertical).mul((float) (Math.sin(angle) * displacementStrength));
                Vector3f particlePos = currentPos.add(displacement);

                path.add(particlePos);
            }

            return ImmutableList.copyOf(path);
        }
    }
}
