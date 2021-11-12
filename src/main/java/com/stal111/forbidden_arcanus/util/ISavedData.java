package com.stal111.forbidden_arcanus.util;

import net.minecraft.nbt.CompoundTag;

/**
 * Saved Data Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.util.ISavedData
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-06
 */
public interface ISavedData {
    CompoundTag write(CompoundTag compound);
    void read(CompoundTag compound);
}
