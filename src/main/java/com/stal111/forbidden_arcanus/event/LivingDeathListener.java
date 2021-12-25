package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.core.config.AurealConfig;
import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        if (entity.getType().getCategory() == MobCategory.AMBIENT || entity.getType().getCategory() == MobCategory.CREATURE) {
            if (event.getSource().getMsgId().equals("player")) {
                Player player = (Player) event.getSource().getEntity();

                if (player == null || player instanceof FakePlayer) {
                    return;
                }

                boolean aurealEntity = entity.getPersistentData().contains("aureal") && entity.getPersistentData().getBoolean("aureal");

                double chance = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_CHANCE.get(): AurealConfig.ENTITY_DEATH_INCREASEMENT_CHANCE.get();
                int amount = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_AMOUNT.get() : AurealConfig.ENTITY_DEATH_INCREASEMENT_AMOUNT.get();

                if (entity.getRandom().nextDouble() <= chance) {
                    AurealHelper.increaseCorruption(player, amount);
                }
            }
        }
    }
}
