package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-01-04
 */
public enum EssenceType implements StringRepresentable {
    AUREAL("aureal"),
    SOULS("souls"),
    BLOOD("blood"),
    EXPERIENCE("experience");

    private final String name;
    private final MutableComponent component;

    EssenceType(String name) {
        this.name = name;
        this.component = Component.translatable("forbidden_arcanus.essence." + name);
    }

    public MutableComponent getComponent() {
        return this.component;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
