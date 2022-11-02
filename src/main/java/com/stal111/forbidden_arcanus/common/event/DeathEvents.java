package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.core.config.AurealConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author stal111
 * @since 2022-11-02
 */
@Mod.EventBusSubscriber
public class DeathEvents {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        MobCategory category = entity.getType().getCategory();

        if (category == MobCategory.AMBIENT || category == MobCategory.CREATURE) {
            if (event.getSource().getEntity() instanceof Player player && !(player instanceof FakePlayer)) {
                boolean aurealEntity = entity.getPersistentData().getBoolean("aureal");

                double chance = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_CHANCE.get(): AurealConfig.ENTITY_DEATH_INCREASEMENT_CHANCE.get();
                int amount = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_AMOUNT.get() : AurealConfig.ENTITY_DEATH_INCREASEMENT_AMOUNT.get();

                if (entity.getRandom().nextDouble() < chance) {
                    AurealHelper.increaseCorruption(player, amount);
                }
            }
        }
    }
}
