package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.ThrownAurealBottle;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2022-10-22
 */
public class SplashAurealBottleItem extends Item {

    public SplashAurealBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(this::createProjectile, serverLevel, stack, player, -20.0F, 0.7F, 1.0F);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);

        return InteractionResult.SUCCESS;
    }

    protected ThrownAurealBottle createProjectile(ServerLevel level, LivingEntity owner, ItemStack itemStack) {
        return new ThrownAurealBottle(level, owner, itemStack);
    }
}
