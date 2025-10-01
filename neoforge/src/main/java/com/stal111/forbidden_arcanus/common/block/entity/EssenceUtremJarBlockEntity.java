package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarBlockEntity extends BlockEntity {

    public static final String TAG_AMOUNT = "essence_amount";
    public static final String TAG_LIMIT = "essence_limit";

    public static final int DEFAULT_LIMIT = 10000;

    public final AnimationState rotateAnimation = new AnimationState();

    private int tickCount = -1;

    private int amount = 0;
    private int limit = DEFAULT_LIMIT;

    public EssenceUtremJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_UTREM_JAR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, EssenceUtremJarBlockEntity blockEntity) {
        blockEntity.rotateAnimation.startIfStopped(blockEntity.tickCount);

        blockEntity.tickCount++;
    }

    public ItemStack getAsItem() {
        ItemStack stack = ModItems.ESSENCE_UTREM_JAR.get().getDefaultInstance();

        stack.applyComponents(this.collectComponents());

        return stack;
    }

    public void addEssence(int amount) {
        this.amount = Math.min(this.amount + amount, this.limit);

        this.setChanged();
    }

    public int getAmount() {
        return this.amount;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getTickCount() {
        return this.tickCount;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt(TAG_AMOUNT, this.amount);
        output.putInt(TAG_LIMIT, this.limit);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.amount = input.getIntOr(TAG_AMOUNT, 0);
        this.limit = input.getIntOr(TAG_LIMIT, DEFAULT_LIMIT);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter componentGetter) {
        super.applyImplicitComponents(componentGetter);

        EssenceStorage storage = componentGetter.get(ModDataComponents.ESSENCE_STORAGE);

        if (storage != null) {
            this.amount = storage.value().amount();
            this.limit = storage.limit();
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ModDataComponents.ESSENCE_STORAGE, new EssenceStorage(EssenceValue.of(this.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE), this.amount), this.limit, true));
    }


    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        super.removeComponentsFromTag(output);
        output.discard(TAG_AMOUNT);
        output.discard(TAG_LIMIT);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider lookupProvider) {
        return this.saveWithoutMetadata(lookupProvider);
    }
}
