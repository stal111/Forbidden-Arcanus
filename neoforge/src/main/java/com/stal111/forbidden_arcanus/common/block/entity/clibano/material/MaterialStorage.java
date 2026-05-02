package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class MaterialStorage {

    public static Codec<MaterialStorage> CODEC = Codec.unboundedMap(MoltenMaterialType.CODEC, Codec.intRange(0, 64)).xmap(
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
        this.map.merge(material, amount, Integer::sum);
    }

    public void extract(Holder<MoltenMaterialType> material, int amount) {
        this.map.merge(material, -amount, Integer::sum);
    }

    public List<MoltenMaterial> getAll() {
        return map.object2IntEntrySet().stream()
                .map(entry -> new MoltenMaterial(entry.getKey(), entry.getIntValue()))
                .toList();
    }
}
