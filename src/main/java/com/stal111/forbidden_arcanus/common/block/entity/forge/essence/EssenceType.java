package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-01-04
 */
public enum EssenceType implements StringRepresentable {
    AUREAL("aureal", 7, ModParticles.AUREAL_DROP),
    SOULS("souls", 0, ModParticles.SOULS_DROP),
    BLOOD("blood", 0, ModParticles.BLOOD_DROP),
    EXPERIENCE("experience", 13, ModParticles.EXPERIENCE_DROP);

    public static final StringRepresentable.EnumCodec<EssenceType> CODEC = StringRepresentable.fromEnum(EssenceType::values);

    public static final StreamCodec<FriendlyByteBuf, EssenceType> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(EssenceType.class);

    private final String name;
    private final int lightEmission;
    private final Holder<ParticleType<?>> particleType;
    private final Component component;
    private final ResourceLocation spriteLocation;

    EssenceType(String name, int lightEmission, Holder<ParticleType<?>> particleType) {
        this.name = name;
        this.lightEmission = lightEmission;
        this.particleType = particleType;
        this.component = Component.translatable(Util.makeDescriptionId("essence", new ResourceLocation(ForbiddenArcanus.MOD_ID, name)));
        this.spriteLocation = new ResourceLocation(ForbiddenArcanus.MOD_ID, "icon/" + name);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public Component getComponent() {
        return this.component;
    }

    public int getLightEmission() {
        return this.lightEmission;
    }

    public ParticleType<?> getParticleType() {
        return this.particleType.value();
    }

    public ResourceLocation getSpriteLocation() {
        return this.spriteLocation;
    }
}
