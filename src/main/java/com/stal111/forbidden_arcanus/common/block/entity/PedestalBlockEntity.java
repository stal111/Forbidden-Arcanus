package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Pedestal Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-06-25
 */
public class PedestalBlockEntity extends BlockEntity {

    private static final int DEFAULT_ITEM_HEIGHT = 120;

    private ItemStack stack = ItemStack.EMPTY;

    private final float hoverStart;
    private int ticksExisted;
    private int itemHeight = DEFAULT_ITEM_HEIGHT;

    private final ChangedCallback onChanged = (level, stack, player) -> {
        this.findHephaestusForge(level, this.getBlockPos()).ifPresent(forgePos -> {
            if (level.getBlockEntity(forgePos) instanceof HephaestusForgeBlockEntity blockEntity) {
                RitualManager ritualManager = blockEntity.getRitualManager();

                ritualManager.updateIngredient(this, stack, blockEntity.getEssences());

                if (blockEntity.getRitualManager().isRitualActive()) {
                    ritualManager.failRitual();
                }
            }
        });

        if (stack.is(ModItems.OMEGA_ARCOIN.get())) {
            BlockPos pos = this.findSpawnPositionNear(level, this.getBlockPos(), 10);

            if (pos != null) {
                DarkTrader darkTrader = ModEntities.DARK_TRADER.get().create(level, null, null, pos, MobSpawnType.MOB_SUMMONED, false, false);

                if (darkTrader != null) {
                    darkTrader.lookAt(EntityAnchorArgument.Anchor.EYES, this.getBlockPos().getCenter());

                    level.addFreshEntity(darkTrader);
                }
            }
        }
    };

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PEDESTAL.get(), pos, state);
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {
        blockEntity.ticksExisted++;
    }

    public void setStack(ItemStack stack, @Nullable Player player, boolean runOnChanged) {
        this.stack = stack;

        this.markUpdated();

        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(player, this.getBlockState()));

            if (runOnChanged) {
                this.onChanged.run(serverLevel, stack, null);
            }
        }
    }

    @Nullable
    private BlockPos findSpawnPositionNear(LevelReader pLevel, BlockPos pPos, int pMaxDistance) {
        BlockPos blockpos = null;

        for(int i = 0; i < 10; ++i) {
            int j = pPos.getX() + this.level.random.nextInt(pMaxDistance * 2) - pMaxDistance;
            int k = pPos.getZ() + this.level.random.nextInt(pMaxDistance * 2) - pMaxDistance;
            int l = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, pLevel, blockpos1, EntityType.WANDERING_TRADER)) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean hasStack() {
        return !this.stack.isEmpty();
    }

    public void clearStack(Level level, Player player) {
        this.clearStack(level, player, true);
    }

    public void clearStack(Level level, @Nullable Player player, boolean runOnChanged) {
        this.setItemHeight(DEFAULT_ITEM_HEIGHT);

        this.setStack(ItemStack.EMPTY, player, runOnChanged);
    }

    public float getItemHover(float partialTicks) {
        return (this.ticksExisted + partialTicks) / 20.0F + this.hoverStart;
    }

    public int getItemHeight() {
        return this.itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;

        this.markUpdated();
    }

    private Optional<BlockPos> findHephaestusForge(ServerLevel level, BlockPos pos) {
        return level.getPoiManager().getInRange(poiTypeHolder -> poiTypeHolder.get() == ModPOITypes.HEPHAESTUS_FORGE.get(), pos, 4, PoiManager.Occupancy.ANY).map(PoiRecord::getPos).findFirst();
    }

    @Override
    public void load(@Nonnull CompoundTag compound) {
        super.load(compound);

        this.stack = ItemStack.of(compound.getCompound("Stack"));
        this.itemHeight = compound.getInt("ItemHeight");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound) {
        super.saveAdditional(compound);

        compound.put("Stack", this.stack.save(new CompoundTag()));
        compound.putInt("ItemHeight", this.itemHeight);
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(this.getBlockPos()).expandTowards(0.0D, 1.0D, 0.0D);
    }

    @FunctionalInterface
    private interface ChangedCallback {
        void run(ServerLevel level, ItemStack stack, Player player);
    }
}
