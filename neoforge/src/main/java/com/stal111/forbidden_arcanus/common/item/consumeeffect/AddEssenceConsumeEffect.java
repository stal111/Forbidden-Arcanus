package com.stal111.forbidden_arcanus.common.item.consumeeffect;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.core.init.other.ModConsumeEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class AddEssenceConsumeEffect implements ConsumeEffect {

    public static final AddEssenceConsumeEffect INSTANCE = new AddEssenceConsumeEffect();

    public static final MapCodec<AddEssenceConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, AddEssenceConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.ADD_ESSENCE.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        Optional<EssenceAccess> essenceAccess = EssenceHelper.getEssenceAccess(entity);

        essenceAccess.ifPresent(access -> {
            EssenceHelper.getEssenceValue(stack).ifPresent(value -> {
                access.addEssence(value.type(), value.amount());
            });
        });

        return essenceAccess.isPresent();
    }
}
