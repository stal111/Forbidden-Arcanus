package com.stal111.forbidden_arcanus.common.item.consumeeffect;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.essence.source.EssenceSource;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.core.init.other.ModConsumeEffects;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record AddEssenceConsumeEffect(Holder<EssenceSource> source) implements ConsumeEffect {

    public static final MapCodec<AddEssenceConsumeEffect> CODEC = EssenceSource.CODEC.xmap(AddEssenceConsumeEffect::new, AddEssenceConsumeEffect::source).fieldOf("source");
    public static final StreamCodec<RegistryFriendlyByteBuf, AddEssenceConsumeEffect> STREAM_CODEC = EssenceSource.STREAM_CODEC.map(AddEssenceConsumeEffect::new, AddEssenceConsumeEffect::source);

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.ADD_ESSENCE.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        Optional<EssenceAccess> essenceAccess = EssenceHelper.getEssenceAccess(entity);

        essenceAccess.ifPresent(access -> {
            EssenceValue value = this.source().value().extractEssence(stack, entity.hasInfiniteMaterials());

            if (value != null) {
                access.addEssence(value.type(), value.amount());
            }
        });

        return essenceAccess.isPresent();
    }
}
