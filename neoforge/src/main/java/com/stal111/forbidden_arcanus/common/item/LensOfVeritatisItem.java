package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;

/**
 * Lens Of Veritatis Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.LensOfVeritatisItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-02-06
 */
public class LensOfVeritatisItem extends Item {

    public static final String LENS_SLOT = "LensSlot";

    public static final int PARTICLE_RANGE = 20;

    public LensOfVeritatisItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof ServerPlayer player && level instanceof ServerLevel serverLevel) {
            CompoundTag persistentData = player.getPersistentData();

            int slot = persistentData.getInt(LENS_SLOT);

            if (slot != slotId && player.getInventory().getItem(slot).is(ModItems.LENS_OF_VERITATIS.get())) {
                return;
            }

            RandomSource random = level.getRandom();

            level.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(PARTICLE_RANGE)).stream()
                    .filter(livingEntity -> livingEntity.getPersistentData().getBoolean("aureal"))
                    .forEach(livingEntity -> {
                        int j = random.nextInt(2) * 2 - 1;
                        int k = random.nextInt(2) * 2 - 1;
                        double posX = livingEntity.getX() + random.nextFloat() * j;
                        double posY = livingEntity.getY() + random.nextFloat();
                        double posZ = livingEntity.getZ() + random.nextFloat() * k;
                        float ySpeed = (random.nextFloat() - 0.4F) * 0.125F;

                        //TODO: Fix particles
                        ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(ModParticles.AUREAL_MOTE.get(), false, posX, posY, posZ, 0.0F, ySpeed, 0.0F, 1.0F, 0);
                       // serverLevel.sendParticles(player, false, posX, posY, posZ, packet);
                    });

            persistentData.putInt(LENS_SLOT, slotId);
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
