package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record FireDegradation(Holder<Item> nextItem, IntProvider stageDurationSeconds, Holder<SoundEvent> soundEvent) {

    public static final Codec<FireDegradation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Item.CODEC.fieldOf("next_item").forGetter(FireDegradation::nextItem),
            IntProviders.POSITIVE_CODEC.fieldOf("stage_duration_seconds").forGetter(FireDegradation::stageDurationSeconds),
            SoundEvent.CODEC.fieldOf("sound_event").forGetter(FireDegradation::soundEvent)
    ).apply(instance, FireDegradation::new));

    public static final UniformInt DEFAULT_STAGE_DURATION = UniformInt.of(8, 13);

    public static FireDegradation obsidianSkull(HolderLookup.Provider context, ResourceKey<Item> nextItem) {
        return new FireDegradation(context.getOrThrow(nextItem), DEFAULT_STAGE_DURATION, ModSounds.OBSIDIAN_SKULL_CRACK);
    }

    public void tick(ItemStack stack, LivingEntity entity, EquipmentSlot slot) {
        if (entity.tickCount % 20 != 0) {
            return;
        }

        Integer remainingSeconds = stack.get(ModDataComponents.SECONDS_TILL_NEXT_STAGE);

        if (remainingSeconds == null) {
            remainingSeconds = this.stageDurationSeconds.sample(entity.getRandom());
        }

        remainingSeconds--;

        if (remainingSeconds <= 0) {
            stack.remove(ModDataComponents.SECONDS_TILL_NEXT_STAGE);

            entity.setItemSlot(slot, stack.transmuteCopy(this.nextItem.value()));

            entity.level().playSound(null, entity, this.soundEvent.value(), entity.getSoundSource(), 0.9F, entity.level().getRandom().nextFloat() * 0.15F + 0.9F);

            return;
        }

        stack.set(ModDataComponents.SECONDS_TILL_NEXT_STAGE, remainingSeconds);
    }
}
