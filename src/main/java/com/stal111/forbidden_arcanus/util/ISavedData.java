package com.stal111.forbidden_arcanus.util;

import net.minecraft.nbt.CompoundNBT;

/**
 * Saved Data Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.util.ISavedData
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-06
 */
public interface ISavedData {
    CompoundNBT write(CompoundNBT compound);
    void read(CompoundNBT compound);
}
