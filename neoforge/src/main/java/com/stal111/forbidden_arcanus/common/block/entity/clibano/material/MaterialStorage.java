package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;

import java.util.List;

public class MaterialStorage {

    public static final int MAX_AMOUNT = 256 * 90;

    public static Codec<MaterialStorage> CODEC = Codec.unboundedMap(MoltenMaterialType.CODEC, Codec.intRange(0, MAX_AMOUNT)).xmap(
            map -> new MaterialStorage(new Object2IntOpenHashMap<>(map)),
            storage -> storage.map
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MaterialStorage> STREAM_CODEC = ByteBufCodecs.map(
            Object2IntOpenHashMap::new,
            MoltenMaterialType.STREAM_CODEC,
            ByteBufCodecs.INT
    ).map(MaterialStorage::new, storage -> storage.map);

    private final Object2IntOpenHashMap<Holder<MoltenMaterialType>> map;

    public MaterialStorage(Object2IntOpenHashMap<Holder<MoltenMaterialType>> map) {
        this.map = map;
    }

    public static MaterialStorage createEmpty() {
        return new MaterialStorage(new Object2IntOpenHashMap<>());
    }

    public void insert(Holder<MoltenMaterialType> material, int amount) {
        int newAmount = Mth.clamp(this.map.getInt(material) + amount, 0, MAX_AMOUNT);

        this.map.put(material, newAmount);
    }

    public void insert(MoltenMaterial material) {
        int newAmount = Mth.clamp(this.map.getInt(material.type()) + material.amount(), 0, MAX_AMOUNT);

        this.map.put(material.type(), newAmount);
    }

    public boolean canFit(MoltenMaterial material) {
        return this.map.getInt(material.type()) + material.amount() <= MAX_AMOUNT;
    }

    public int getAmount(Holder<MoltenMaterialType> type) {
        return this.map.getInt(type);
    }

    public List<MoltenMaterial> getAll() {
        return map.object2IntEntrySet().stream()
                .map(entry -> new MoltenMaterial(entry.getKey(), entry.getIntValue()))
                .toList();
    }
}
