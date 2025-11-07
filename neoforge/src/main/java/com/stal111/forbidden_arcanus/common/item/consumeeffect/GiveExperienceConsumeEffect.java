package com.stal111.forbidden_arcanus.common.item.consumeeffect;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.init.other.ModConsumeEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class GiveExperienceConsumeEffect implements ConsumeEffect {

    public static final GiveExperienceConsumeEffect INSTANCE = new GiveExperienceConsumeEffect();

    public static final MapCodec<GiveExperienceConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, GiveExperienceConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.GIVE_EXPERIENCE.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }

        Optional<EssenceValue> essenceValue = EssenceHelper.getEssenceValue(stack);

        essenceValue.ifPresent(essence -> {
           player.giveExperiencePoints(essence.amount());
        });

        return essenceValue.isPresent();
    }
}
