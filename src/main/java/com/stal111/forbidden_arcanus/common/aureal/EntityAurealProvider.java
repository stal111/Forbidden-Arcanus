package com.stal111.forbidden_arcanus.common.aureal;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * @author stal111
 * @since 16.01.2024
 */
public class EntityAurealProvider extends AttachmentAurealProvider<Entity> {

    public EntityAurealProvider(Player attachmentHolder, int defaultLimit) {
        super(attachmentHolder, defaultLimit);
    }
}
