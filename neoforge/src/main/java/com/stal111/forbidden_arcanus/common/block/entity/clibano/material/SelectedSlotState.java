package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class SelectedSlotState implements ValueIOSerializable {

    private static final Codec<Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>>> EITHER_CODEC = Codec.either(MoltenMaterialType.CODEC, ResourceKey.codec(Registries.RECIPE));

    public static final StreamCodec<RegistryFriendlyByteBuf, SelectedSlotState> STREAM_CODEC = ByteBufCodecs.either(
            MoltenMaterialType.STREAM_CODEC,
            ResourceKey.streamCodec(Registries.RECIPE)
    ).apply(ByteBufCodecs::optional).map(either -> {
        SelectedSlotState state = new SelectedSlotState(() -> {});
        state.toggleSlot(either.orElse(null));
        return state;
    }, SelectedSlotState::getSelected);

    private final Runnable onChanged;
    public @Nullable Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> activeType = null;

    public SelectedSlotState(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    public void toggleSlot(@Nullable Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>> type) {
        this.activeType = type != null && type.equals(this.activeType) ? null : type;

        this.onChanged.run();
    }

    public Optional<Either<Holder<MoltenMaterialType>, ResourceKey<Recipe<?>>>> getSelected() {
        return Optional.ofNullable(this.activeType);
    }

    @Override
    public void serialize(ValueOutput output) {
        output.storeNullable("selected_slot", EITHER_CODEC, this.activeType);
    }

    @Override
    public void deserialize(ValueInput input) {
        this.activeType = input.read("selected_slot", EITHER_CODEC).orElse(null);
    }
}
