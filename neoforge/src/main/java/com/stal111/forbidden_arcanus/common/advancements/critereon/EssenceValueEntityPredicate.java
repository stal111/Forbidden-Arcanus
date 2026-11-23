package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record EssenceValueEntityPredicate(EssenceValue essenceValue) implements EntitySubPredicate {

    public static final Codec<EssenceValueEntityPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceValue.CODEC.fieldOf("essence").forGetter(EssenceValueEntityPredicate::essenceValue)
    ).apply(instance, EssenceValueEntityPredicate::new));

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return EssenceHelper.getEssenceAccess(entity)
                .map(provider -> provider.getEssenceAmount(this.essenceValue.type()) >= this.essenceValue.amount())
                .orElse(false);
    }
}
