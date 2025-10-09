package com.stal111.forbidden_arcanus.common.essence.storage;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateEssencePayload;
import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import net.minecraft.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public record EntityEssenceAccess<T extends LivingEntity>(T entity) implements EssenceAccess {

    private static final Map<EssenceType, AttachmentType<EssenceStorage>> ATTACHMENT_BY_TYPE = Util.make(new EnumMap<>(EssenceType.class), map -> {
        map.put(EssenceType.AUREAL, ModAttachmentTypes.AUREAL.get());
        map.put(EssenceType.SOULS, ModAttachmentTypes.SOULS.get());
        map.put(EssenceType.BLOOD, ModAttachmentTypes.BLOOD.get());
        map.put(EssenceType.EXPERIENCE, ModAttachmentTypes.EXPERIENCE.get());
    });

    @Override
    public EssenceStorage getEssence(EssenceType type) {
        return this.entity.getData(ATTACHMENT_BY_TYPE.get(type));
    }

    @Override
    public void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        EssenceStorage updatedStorage = updater.apply(this.getEssence(type));
        this.entity.setData(ATTACHMENT_BY_TYPE.get(type), updatedStorage);

        if (this.entity instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new UpdateEssencePayload(updatedStorage));
        }
    }
}
