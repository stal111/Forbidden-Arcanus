package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.core.config.ItemConfig;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.Creeper;

/**
 * @author stal111
 * @since 05.11.2023
 */
public class ChargeCreeperInteraction extends EntityInteraction {

    @Override
    public void interact(EntityInteractionContext context) {
        SynchedEntityData dataManager = context.entity().getEntityData();

        dataManager.set(Creeper.DATA_IS_POWERED, true);
    }

    @Override
    public boolean canInteract(EntityInteractionContext context) {
        return context.entity() instanceof Creeper creeper && !creeper.isPowered() && ItemConfig.MUNDABITUR_DUST_CHARGE_CREEPER.get();
    }
}
