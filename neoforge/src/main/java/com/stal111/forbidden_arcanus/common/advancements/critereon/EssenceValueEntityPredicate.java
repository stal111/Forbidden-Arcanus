package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record EssenceValueEntityPredicate(EssenceValue essenceValue) implements EntitySubPredicate {

    public static final MapCodec<EssenceValueEntityPredicate> MAP_CODEC = EssenceValue.MAP_CODEC.xmap(EssenceValueEntityPredicate::new, EssenceValueEntityPredicate::essenceValue);

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return EssenceHelper.getEssenceAccess(entity)
                .map(provider -> provider.getEssenceAmount(this.essenceValue.type()) >= this.essenceValue.amount())
                .orElse(false);
    }
}
