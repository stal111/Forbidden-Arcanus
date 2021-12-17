package com.stal111.forbidden_arcanus.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Zombie Arm Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ZombieArmItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-24
 */
public class ZombieArmItem extends Item {

    private static final double ATTACK_DAMAGE = 4.0D;
    private static final double ATTACK_SPEED = -2.3F;

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ZombieArmItem(Properties properties) {
        super(properties);
        this.attributeModifiers = ImmutableMultimap.<Attribute, AttributeModifier>builder()
                .put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION))
                .put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", ATTACK_SPEED, AttributeModifier.Operation.ADDITION))
                .build();
    }

    @Override
    public boolean canAttackBlock(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, LivingEntity target, @Nonnull LivingEntity attacker) {
        if (target.getHealth() != 0.0F) {
            return true;
        }

        Level level = attacker.getCommandSenderWorld();
        Mob entity = null;

        if (target.getType() == EntityType.HORSE) {
            entity = ((Mob) target).convertTo(EntityType.ZOMBIE_HORSE, false);

        } else if (target instanceof Villager villager) {
            entity = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);

            if (entity != null) {
                ((ZombieVillager) entity).setVillagerData(villager.getVillagerData());
                ((ZombieVillager) entity).setGossips(villager.getGossips().store(NbtOps.INSTANCE).getValue());
                ((ZombieVillager) entity).setTradeOffers(villager.getOffers().createTag());
                ((ZombieVillager) entity).setVillagerXp(villager.getVillagerXp());
            }
        }

        if (level instanceof ServerLevel serverLevel && entity != null) {
            entity.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.CONVERSION, null, null);
        }

        return true;
    }
}
