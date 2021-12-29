package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.inventory.InputType;
import com.stal111.forbidden_arcanus.common.loader.HephaestusForgeInputLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Update Forge Inputs Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateForgeInputsPacket
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-12-27
 */
public record UpdateForgeInputsPacket(Map<InputType, Map<Item, HephaestusForgeInputLoader.InputData>> inputs) {

    public static void encode(UpdateForgeInputsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeMap(packet.inputs, (byteBuf, inputType) -> byteBuf.writeUtf(inputType.getSerializedName()), (byteBuf, itemInputDataMap) -> byteBuf.writeMap(itemInputDataMap, (friendlyByteBuf, item) -> friendlyByteBuf.writeItem(new ItemStack(item)), (friendlyByteBuf, inputData) -> inputData.serializeToNetwork(friendlyByteBuf)));
    }

    public static UpdateForgeInputsPacket decode(FriendlyByteBuf buffer) {
        return new UpdateForgeInputsPacket(buffer.readMap(byteBuf -> InputType.valueOf(byteBuf.readUtf().toUpperCase(Locale.ROOT)), byteBuf -> byteBuf.readMap(friendlyByteBuf -> friendlyByteBuf.readItem().getItem(), HephaestusForgeInputLoader.InputData::fromNetwork)));
    }

    public static void consume(UpdateForgeInputsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            HephaestusForgeInputLoader.setInputs(new EnumMap<>(packet.inputs));
        });
        ctx.get().setPacketHandled(true);
    }
}
