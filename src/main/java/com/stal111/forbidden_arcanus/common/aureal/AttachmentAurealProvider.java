package com.stal111.forbidden_arcanus.common.aureal;

import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

/**
 * @author stal111
 * @since 17.01.2024
 */
public class AttachmentAurealProvider<T extends IAttachmentHolder> implements AurealProvider {

    private final T attachmentHolder;

    public AttachmentAurealProvider(T attachmentHolder, int defaultLimit) {
        this.attachmentHolder = attachmentHolder;
        this.getAurealStorage().setLimit(defaultLimit);
    }

    @Override
    public AurealStorage getAurealStorage() {
        return this.attachmentHolder.getData(ModAttachmentTypes.AUREAL);
    }

    public T getAttachmentHolder() {
        return this.attachmentHolder;
    }
}
