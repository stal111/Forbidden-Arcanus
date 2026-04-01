package com.stal111.forbidden_arcanus.common.essence.storage;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import net.minecraft.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public record EntityEssenceAccess<T extends LivingEntity>(T entity) implements EssenceAccess {

    private static final Map<EssenceType, AttachmentType<EssenceStorage>> ATTACHMENT_BY_TYPE = Util.make(new EnumMap<>(EssenceType.class), map -> {
        map.put(EssenceType.AUREAL, ModAttachmentTypes.AUREAL.get());
        map.put(EssenceType.ECTOPLASM, ModAttachmentTypes.ECTOPLASM.get());
        map.put(EssenceType.BLOOD, ModAttachmentTypes.BLOOD.get());
        map.put(EssenceType.EXPERIENCE, ModAttachmentTypes.EXPERIENCE.get());
    });

    @Override
    public EssenceStorage getEssence(EssenceType type) {
        return this.entity.getData(ATTACHMENT_BY_TYPE.get(type));
    }

    @Override
    public void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        this.entity.setData(ATTACHMENT_BY_TYPE.get(type), updater.apply(this.getEssence(type)));
    }
}
