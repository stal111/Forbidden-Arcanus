package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class SelectedMaterialState implements ValueIOSerializable {

    public static final StreamCodec<RegistryFriendlyByteBuf, SelectedMaterialState> STREAM_CODEC = MoltenMaterialType.STREAM_CODEC
            .apply(ByteBufCodecs::optional)
            .map(type -> {
                SelectedMaterialState state = new SelectedMaterialState(() -> {});
                state.toggleType(type.orElse(null));
                return state;
            }, state -> Optional.ofNullable(state.getSelected()));

    private final Runnable onChanged;
    public @Nullable Holder<MoltenMaterialType> activeType = null;

    public SelectedMaterialState(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    public void toggleType(Holder<MoltenMaterialType> type) {
        this.activeType = activeType == type ? null : type;

        this.onChanged.run();
    }

    public @Nullable Holder<MoltenMaterialType> getSelected() {
        return this.activeType;
    }

    @Override
    public void serialize(ValueOutput output) {
        output.storeNullable("selected_material", MoltenMaterialType.CODEC, this.activeType);
    }

    @Override
    public void deserialize(ValueInput input) {
        this.activeType = input.read("selected_material", MoltenMaterialType.CODEC).orElse(null);
    }
}
