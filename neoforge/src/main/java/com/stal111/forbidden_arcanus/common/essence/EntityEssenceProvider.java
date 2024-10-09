package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateEssencePayload;
import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import net.minecraft.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author stal111
 * @since 13.05.2024
 */
public class EntityEssenceProvider<T extends LivingEntity> implements EssenceProvider {

    private static final Map<EssenceType, AttachmentType<EssenceStorage>> ATTACHMENT_BY_TYPE = Util.make(new EnumMap<>(EssenceType.class), map -> {
        map.put(EssenceType.AUREAL, ModAttachmentTypes.AUREAL.get());
        map.put(EssenceType.SOULS, ModAttachmentTypes.SOULS.get());
        map.put(EssenceType.BLOOD, ModAttachmentTypes.BLOOD.get());
        map.put(EssenceType.EXPERIENCE, ModAttachmentTypes.EXPERIENCE.get());
    });

    T entity;

    public EntityEssenceProvider(T entity) {
        this.entity = entity;
    }

    @Override
    public EssenceStorage asStorage(EssenceType type) {
        return this.entity.getData(ATTACHMENT_BY_TYPE.get(type));
    }

    @Override
    public void setStorage(EssenceStorage storage) {
        this.entity.setData(ATTACHMENT_BY_TYPE.get(storage.data().type()), storage);

        if (this.entity instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new UpdateEssencePayload(storage));
        }
    }
}
