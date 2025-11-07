package com.stal111.forbidden_arcanus.common.item.consumeeffect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.essence.source.EssenceSource;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.core.init.other.ModConsumeEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record AddEssenceConsumeEffect(EssenceSource source) implements ConsumeEffect {

    public static final MapCodec<AddEssenceConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceSource.CODEC.fieldOf("source").forGetter(AddEssenceConsumeEffect::source)
    ).apply(instance, AddEssenceConsumeEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AddEssenceConsumeEffect> STREAM_CODEC = EssenceSource.STREAM_CODEC.map(AddEssenceConsumeEffect::new, AddEssenceConsumeEffect::source);

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.ADD_ESSENCE.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        Optional<EssenceAccess> essenceAccess = EssenceHelper.getEssenceAccess(entity);

        essenceAccess.ifPresent(access -> {
            EssenceValue value = this.source().getEssenceValue(stack, level.getRandom());

            if (value != null) {
                access.addEssence(value.type(), value.amount());
            }
        });

        return essenceAccess.isPresent();
    }
}
