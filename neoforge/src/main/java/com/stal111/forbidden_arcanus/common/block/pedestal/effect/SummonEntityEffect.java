package com.stal111.forbidden_arcanus.common.block.pedestal.effect;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

/**
 * @author stal111
 * @since 05.04.2024
 */
public class SummonEntityEffect<T extends LivingEntity> extends PedestalEffect {

    private final BiPredicate<ServerLevel, ItemStack> spawnCondition;
    private final RegistryEntry<EntityType<?>, EntityType<T>> entityType;
    private final int spawnRadius;
    private final boolean consumeItem;

    public SummonEntityEffect(BiPredicate<ServerLevel, ItemStack> spawnCondition, RegistryEntry<EntityType<?>, EntityType<T>> entityType, int spawnRadius, boolean consumeItem) {
        super(PedestalEffectTrigger.PLAYER_PLACE_ITEM);
        this.spawnCondition = spawnCondition;
        this.entityType = entityType;
        this.spawnRadius = spawnRadius;
        this.consumeItem = consumeItem;
    }

    @Override
    public void execute(ServerLevel level, BlockPos pos, ItemStack stack) {
        if (!this.spawnCondition.test(level, stack)) {
            return;
        }

        BlockPos spawnPos = this.findSpawnPositionNear(level, pos, this.spawnRadius);

        if (spawnPos != null) {
            T entity = this.entityType.get().create(level, null, spawnPos, MobSpawnType.MOB_SUMMONED, false, false);

            if (entity != null) {
                entity.lookAt(EntityAnchorArgument.Anchor.EYES, pos.getCenter());

                level.addFreshEntity(entity);
            }
        }

        if (this.consumeItem && level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
            blockEntity.clearStack(null, PedestalEffectTrigger.ENTITY_SUMMONED);
        }
    }

    @Nullable
    private BlockPos findSpawnPositionNear(ServerLevel level, BlockPos pos, int maxDistance) {
        BlockPos spawnPos = null;
        SpawnPlacementType spawnPlacementType = SpawnPlacements.getPlacementType(this.entityType.get());

        for (int i = 0; i < 10; ++i) {
            int j = pos.getX() + level.getRandom().nextInt(maxDistance * 2) - maxDistance;
            int k = pos.getZ() + level.getRandom().nextInt(maxDistance * 2) - maxDistance;
            int l = level.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);

            if (spawnPlacementType.isSpawnPositionOk(level, blockpos1, this.entityType.get())) {
                spawnPos = blockpos1;
                break;
            }
        }

        return spawnPos;
    }
}
