package com.stal111.forbidden_arcanus.util;

import net.minecraft.nbt.CompoundTag;

/**
 * Saved Data Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.util.SavedData
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-06
 */
public interface SavedData {
    CompoundTag save(CompoundTag tag);
    void load(CompoundTag tag);
}
