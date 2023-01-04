package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Ritual Essences <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualEssences
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-10
 */
public record RitualEssences(int aureal, int souls, int blood, int experience) {

    public boolean checkEssences(HephaestusForgeBlockEntity tileEntity) {
        EssenceManager manager = tileEntity.getEssenceManager();

        if (manager.getAureal() < this.aureal()) {
            return false;
        } else if (manager.getSouls() < this.souls()) {
            return false;
        } else if (manager.getBlood() < this.blood()) {
            return false;
        }
        return manager.getExperience() >= this.experience();
    }

    public void reduceEssences(HephaestusForgeBlockEntity blockEntity) {
        EssenceManager manager = blockEntity.getEssenceManager();

        manager.decreaseAureal(this.aureal());
        manager.decreaseSouls(this.souls());
        manager.decreaseBlood(this.blood());
        manager.decreaseExperience(this.experience());
    }

    public int getFromName(String name) {
        return switch (name) {
            case "Aureal" -> this.aureal();
            case "Souls" -> this.souls();
            case "Blood" -> this.blood();
            case "Experience" -> this.experience();
            default -> 0;
        };
    }

    public void serializeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.aureal());
        buffer.writeVarInt(this.souls());
        buffer.writeVarInt(this.blood());
        buffer.writeVarInt(this.experience());
    }

    public static RitualEssences fromNetwork(FriendlyByteBuf buffer) {
        return new RitualEssences(buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }
}
