package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.modifier.SoulboundInventory;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 16.01.2024
 */
public class ModAttachmentTypes {

    public static final MappedRegistryHelper<AttachmentType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);

    public static final Supplier<AttachmentType<EssenceStorage>> AUREAL = createEssenceStorage(EssenceType.AUREAL);
    public static final Supplier<AttachmentType<EssenceStorage>> SOULS = createEssenceStorage(EssenceType.SOULS);
    public static final Supplier<AttachmentType<EssenceStorage>> BLOOD = createEssenceStorage(EssenceType.BLOOD);
    public static final Supplier<AttachmentType<EssenceStorage>> EXPERIENCE = createEssenceStorage(EssenceType.EXPERIENCE);
    public static final Supplier<AttachmentType<SoulboundInventory>> SOULBOUND_INVENTORY = HELPER.register("soulbound_inventory", () -> AttachmentType.builder(SoulboundInventory::create).serialize(SoulboundInventory.CODEC, inventory -> !inventory.isEmpty()).copyOnDeath().build());

    private static Supplier<AttachmentType<EssenceStorage>> createEssenceStorage(EssenceType type) {
        return HELPER.register(type.getSerializedName(), () -> AttachmentType.builder(() -> EssenceStorage.createEmpty(type, 100)).serialize(EssenceStorage.codec(type)).build());
    }
}
