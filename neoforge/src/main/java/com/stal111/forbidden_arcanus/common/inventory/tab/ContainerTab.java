package com.stal111.forbidden_arcanus.common.inventory.tab;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public record ContainerTab(String name) implements StringRepresentable {

    public static final StreamCodec<ByteBuf, ContainerTab> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(ContainerTab::new, ContainerTab::name);

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
