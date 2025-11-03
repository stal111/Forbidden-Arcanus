package com.stal111.forbidden_arcanus.common.essence.storage;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;

import static com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage.createEmpty;
import static com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage.createFull;

public final class EssenceStorages {

    public static final EssenceStorage BLOOD_TEST_TUBE_EMPTY = createEmpty(EssenceType.BLOOD, 3000);
    public static final EssenceStorage BLOOD_TEST_TUBE_FULL = createFull(EssenceType.BLOOD, 3000);
    public static final EssenceStorage UTREM_JAR_FALLBACK = createEmpty(EssenceType.AUREAL, 10000);
    public static final EssenceStorage AUREAL_TANK_EMPTY = createEmpty(EssenceType.AUREAL, AurealTankItem.MAX_CAPACITY);
    public static final EssenceStorage ECTOPLASM_EXTRACTOR_EMPTY = createEmpty(EssenceType.SOULS, 100);
}
