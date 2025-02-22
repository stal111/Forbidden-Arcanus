package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record EssenceDataEntityPredicate(EssenceData essenceData) implements EntitySubPredicate {

    public static final MapCodec<EssenceDataEntityPredicate> MAP_CODEC = EssenceData.MAP_CODEC.xmap(EssenceDataEntityPredicate::new, EssenceDataEntityPredicate::essenceData);

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return EssenceHelper.getEssenceProvider(entity)
                .map(provider -> provider.getAmount(this.essenceData.type()) >= this.essenceData.amount())
                .orElse(false);
    }
}
