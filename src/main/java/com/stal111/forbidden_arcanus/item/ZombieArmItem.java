package com.stal111.forbidden_arcanus.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.NbtOps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.item.Item.Properties;

/**
 * Zombie Arm  Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.ZombieArmItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-24
 */
public class ZombieArmItem extends Item {

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ZombieArmItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 4.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.3F, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getHealth() == 0.0F) {
            Level world = attacker.getCommandSenderWorld();
            Mob entity = null;

            if (target.getType() == EntityType.HORSE) {
                entity = ((Mob) target).convertTo(EntityType.ZOMBIE_HORSE, false);

            } else if (target.getType() == EntityType.VILLAGER) {
                Villager villagerEntity = (Villager) target;

                entity = villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);

                ((ZombieVillager) entity).setVillagerData(villagerEntity.getVillagerData());
                ((ZombieVillager) entity).setGossips(villagerEntity.getGossips().store(NbtOps.INSTANCE).getValue());
                ((ZombieVillager) entity).setTradeOffers(villagerEntity.getOffers().createTag());
                ((ZombieVillager) entity).setVillagerXp(villagerEntity.getVillagerXp());
            }

            if (world instanceof ServerLevel && entity != null) {
                entity.finalizeSpawn((ServerLevelAccessor) world, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.CONVERSION, null, null);
            }
        }
        return true;
    }
}
