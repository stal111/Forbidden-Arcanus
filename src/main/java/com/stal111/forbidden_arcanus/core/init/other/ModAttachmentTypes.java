package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.AurealStorage;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 16.01.2024
 */
public class ModAttachmentTypes {

    public static final MappedRegistryHelper<AttachmentType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);

    public static final RegistryEntry<AttachmentType<?>, AttachmentType<AurealStorage>> AUREAL = HELPER.register("aureal", () -> AttachmentType.builder(() -> AurealStorage.EMPTY).serialize(AurealStorage.CODEC).build());

}
