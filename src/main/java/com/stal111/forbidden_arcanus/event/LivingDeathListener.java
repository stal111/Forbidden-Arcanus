package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.config.AurealConfig;
import com.stal111.forbidden_arcanus.network.AurealUpdatePacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

/**
 * Living Death Listener
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.event.LivingDeathListener
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-27
 */
@Mod.EventBusSubscriber
public class LivingDeathListener {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType().getClassification() == EntityClassification.AMBIENT || entity.getType().getClassification() == EntityClassification.CREATURE) {
            if (event.getSource().getDamageType().equals("player")) {
                PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();

                if (player == null || player instanceof FakePlayer) {
                    return;
                }

                boolean aurealEntity = entity.getPersistentData().contains("aureal") && entity.getPersistentData().getBoolean("aureal");

                double chance = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_CHANCE.get(): AurealConfig.ENTITY_DEATH_INCREASEMENT_CHANCE.get();
                int amount = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_AMOUNT.get() : AurealConfig.ENTITY_DEATH_INCREASEMENT_AMOUNT.get();

                if (new Random().nextDouble() <= chance) {
                    AurealHelper.increaseCorruption(player, amount);
                    NetworkHandler.sendTo(player, new AurealUpdatePacket(player));
                }
            }
        }
    }
}
