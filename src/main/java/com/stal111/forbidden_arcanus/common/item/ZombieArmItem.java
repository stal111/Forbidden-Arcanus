package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2021-03-24
 */
public class ZombieArmItem extends Item {

    private static final double ATTACK_DAMAGE = 4.0D;
    private static final double ATTACK_SPEED = -2.3F;

//    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ZombieArmItem(Properties properties) {
        super(properties);
//        this.attributeModifiers = ImmutableMultimap.<Attribute, AttributeModifier>builder()
//                .put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE))
//                .put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE))
//                .build();
    }

    @Override
    public boolean canAttackBlock(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public @NotNull ItemAttributeModifiers getAttributeModifiers(@NotNull ItemStack stack) {
        return super.getAttributeModifiers(stack);
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, LivingEntity target, @Nonnull LivingEntity attacker) {
        if (!target.isDeadOrDying() || !(attacker.level() instanceof ServerLevel level)) {
            return true;
        }

        if (target.getType() == EntityType.HORSE) {
            Mob entity = ((Mob) target).convertTo(EntityType.ZOMBIE_HORSE, false);

            if (entity != null) {
                entity.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.CONVERSION, null);

                EventHooks.onLivingConvert(target, entity);
            }

            return true;
        }

        if (target instanceof Villager villager) {
            ZombieVillager entity = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);

            if (entity != null) {
                entity.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true));
                entity.setVillagerData(villager.getVillagerData());
                entity.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                entity.setTradeOffers(villager.getOffers());
                entity.setVillagerXp(villager.getVillagerXp());

                EventHooks.onLivingConvert(villager, entity);
            }
        }

        return true;
    }
}
