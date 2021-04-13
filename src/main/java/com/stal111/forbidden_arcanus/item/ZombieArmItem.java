package com.stal111.forbidden_arcanus.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.3F, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getHealth() == 0.0F) {
            World world = attacker.getEntityWorld();
            MobEntity entity = null;

            if (target.getType() == EntityType.HORSE) {
                entity = ((MobEntity) target).func_233656_b_(EntityType.ZOMBIE_HORSE, false);

            } else if (target.getType() == EntityType.VILLAGER) {
                VillagerEntity villagerEntity = (VillagerEntity) target;

                entity = villagerEntity.func_233656_b_(EntityType.ZOMBIE_VILLAGER, false);

                ((ZombieVillagerEntity) entity).setVillagerData(villagerEntity.getVillagerData());
                ((ZombieVillagerEntity) entity).setGossips(villagerEntity.getGossip().write(NBTDynamicOps.INSTANCE).getValue());
                ((ZombieVillagerEntity) entity).setOffers(villagerEntity.getOffers().write());
                ((ZombieVillagerEntity) entity).setEXP(villagerEntity.getXp());
            }

            if (world instanceof ServerWorld && entity != null) {
                entity.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(entity.getPosition()), SpawnReason.CONVERSION, null, null);
            }
        }
        return true;
    }
}
