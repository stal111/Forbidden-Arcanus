package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceCost;
import com.stal111.forbidden_arcanus.common.essence.EssenceCosts;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record EntityCatcher(
        HolderSet<EntityType<?>> blacklistedEntities,
        Holder<SoundEvent> pickUpSound,
        Holder<SoundEvent> releaseSound,
        CatchCost catchCost
) {

    public static final Codec<EntityCatcher> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("blacklisted_entities").forGetter(EntityCatcher::blacklistedEntities),
            SoundEvent.CODEC.optionalFieldOf("pick_up_sound", ModSounds.QUANTUM_CATCHER_PICK_UP).forGetter(EntityCatcher::pickUpSound),
            SoundEvent.CODEC.optionalFieldOf("release_sound", ModSounds.QUANTUM_CATCHER_RELEASE).forGetter(EntityCatcher::releaseSound),
            CatchCost.CODEC.optionalFieldOf("catch_cost", CatchCost.DEFAULT).forGetter(EntityCatcher::catchCost)
    ).apply(instance, EntityCatcher::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, EntityCatcher> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderSet(Registries.ENTITY_TYPE),
            EntityCatcher::blacklistedEntities,
            SoundEvent.STREAM_CODEC,
            EntityCatcher::pickUpSound,
            SoundEvent.STREAM_CODEC,
            EntityCatcher::releaseSound,
            CatchCost.STREAM_CODEC,
            EntityCatcher::catchCost,
            EntityCatcher::new
    );

    public EntityCatcher(HolderSet<EntityType<?>> blacklistedEntities) {
        this(blacklistedEntities, ModSounds.QUANTUM_CATCHER_PICK_UP, ModSounds.QUANTUM_CATCHER_RELEASE, CatchCost.DEFAULT);
    }

    public InteractionResult releaseEntity(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        StoredEntity storedEntity = stack.get(ModDataComponents.STORED_ENTITY);

        if (storedEntity == null) {
            return InteractionResult.PASS;
        }

        Level level = context.getLevel();

        if (!level.isClientSide() && !this.summonEntity(storedEntity, context)) {
            return InteractionResult.FAIL;
        }

        playSound(level, context.getPlayer(), context.getClickedPos(), this.releaseSound);

        return InteractionResult.SUCCESS;
    }

    private boolean summonEntity(StoredEntity storedEntity, UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Entity entity = storedEntity.createEntity(level);

        if (!level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
            pos = pos.relative(context.getClickedFace());
        }

        if (entity == null || !level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
            return false;
        }

        entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

        if (context.getPlayer() != null) {
            entity.lookAt(EntityAnchorArgument.Anchor.EYES, context.getPlayer().position());
        }
        level.addFreshEntity(entity);

        context.getItemInHand().remove(ModDataComponents.STORED_ENTITY);

        return true;
    }

    public InteractionResult onEntityInteract(ItemStack stack, Player player, LivingEntity target) {
        Level level = player.level();
        EssenceCost cost = this.calculateCost(target);
        boolean hasCost = cost.value().amount() > 0;

        if (!this.isValidEntity(target) || stack.has(ModDataComponents.STORED_ENTITY)) {
            return InteractionResult.PASS;
        }

        if (hasCost && !cost.hasEnough(player)) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            if (stack.getCount() == 1) {
                stack.set(ModDataComponents.STORED_ENTITY, StoredEntity.of(target));
            } else {
                ItemStack captured = stack.copyWithCount(1);
                captured.set(ModDataComponents.STORED_ENTITY, StoredEntity.of(target));

                stack.consume(1, player);

                if (!player.addItem(captured)) {
                    player.drop(captured, false);
                }
            }

            target.discard();

            if (hasCost) {
                cost.consume(player);
            }
        }

        playSound(level, player, target.blockPosition(), this.pickUpSound);

        return InteractionResult.SUCCESS;
    }

    public boolean isValidEntity(LivingEntity entity) {
        return !entity.is(this.blacklistedEntities) && entity.isAlive();
    }

    public EssenceCost calculateCost(LivingEntity entity) {
        return this.catchCost.calculate(entity);
    }

    private static void playSound(Level level, @Nullable Player player, BlockPos pos, Holder<SoundEvent> sound) {
        BlockPos soundPos = player == null ? pos : player.blockPosition();

        level.playSound(player, soundPos.getX() + 0.5D, soundPos.getY() + 0.5D, soundPos.getZ() + 0.5D, sound.value(), SoundSource.PLAYERS, 0.75F, level.getRandom().nextFloat() * 0.15F + 0.9F);
    }

    public record CatchCost(
            EssenceType essenceType,
            int baseCost,
            float friendlyMultiplier,
            float nonFriendlyMultiplier,
            Optional<Integer> maxCost,
            Component description
    ) {
        public static final CatchCost DEFAULT = new CatchCost(
                EssenceType.AUREAL,
                0,
                0.5F,
                1.1F,
                Optional.empty(),
                EssenceCosts.CATCH_COMPONENT
        );

        public static final Codec<CatchCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EssenceType.CODEC.optionalFieldOf("essence_type", EssenceType.AUREAL).forGetter(CatchCost::essenceType),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("base_cost", 0).forGetter(CatchCost::baseCost),
                Codec.FLOAT.optionalFieldOf("friendly_multiplier", 0.5F).forGetter(CatchCost::friendlyMultiplier),
                Codec.FLOAT.optionalFieldOf("non_friendly_multiplier", 1.1F).forGetter(CatchCost::nonFriendlyMultiplier),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_cost").forGetter(CatchCost::maxCost),
                ComponentSerialization.CODEC.optionalFieldOf("description", EssenceCosts.CATCH_COMPONENT).forGetter(CatchCost::description)
        ).apply(instance, CatchCost::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CatchCost> STREAM_CODEC = StreamCodec.composite(
                EssenceType.STREAM_CODEC,
                CatchCost::essenceType,
                ByteBufCodecs.VAR_INT,
                CatchCost::baseCost,
                ByteBufCodecs.FLOAT,
                CatchCost::friendlyMultiplier,
                ByteBufCodecs.FLOAT,
                CatchCost::nonFriendlyMultiplier,
                ByteBufCodecs.optional(ByteBufCodecs.VAR_INT),
                CatchCost::maxCost,
                ComponentSerialization.STREAM_CODEC,
                CatchCost::description,
                CatchCost::new
        );

        public EssenceCost calculate(LivingEntity entity) {
            int health = Math.round(entity.getMaxHealth());
            float multiplier = entity.getType().getCategory().isFriendly() ? this.friendlyMultiplier : this.nonFriendlyMultiplier;

            int amount = Math.max(0, this.baseCost + (int) (health * multiplier));

            if (this.maxCost.isPresent()) {
                amount = Math.min(amount, this.maxCost.get());
            }

            return EssenceCost.of(this.essenceType, amount, this.description);
        }
    }
}
