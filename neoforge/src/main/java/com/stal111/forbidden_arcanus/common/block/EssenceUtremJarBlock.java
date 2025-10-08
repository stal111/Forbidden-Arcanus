package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarBlock extends UtremJarBlock implements EntityBlock {

    public static final EnumProperty<EssenceType> ESSENCE_TYPE = ModBlockStateProperties.ESSENCE_TYPE;

    public EssenceUtremJarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ESSENCE_TYPE, EssenceType.AUREAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EssenceUtremJarBlockEntity(pos, state);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        if (level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
            return blockEntity.getAsItem();
        }
        return super.getCloneItemStack(level, pos, state, includeData, player);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);

        if (state != null) {
            EssenceType type = EssenceHelper.getEssenceStorage(context.getItemInHand()).
                    map(essenceStorage -> essenceStorage.value().type())
                    .orElse(EssenceType.AUREAL);

            state = state.setValue(ESSENCE_TYPE, type);
        }

        return state;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
            if (blockEntity.getEssenceStorage().isFull()) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }

            for (HephaestusForgeInput input : FARegistries.FORGE_INPUT_REGISTRY) {
                EssenceValue inputValue = input.getMaxInputValue(stack, level.getRandom());

                if (inputValue != EssenceValue.EMPTY && inputValue.type() == state.getValue(ESSENCE_TYPE)) {
                    int transferredAmount = blockEntity.addEssence(inputValue.amount());

                    player.setItemInHand(hand, input.finishInput(stack, transferredAmount));

                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.ESSENCE_UTREM_JAR.get(), EssenceUtremJarBlockEntity::clientTick);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ESSENCE_TYPE);
    }
}
