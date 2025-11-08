package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * Black Hole Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.BlackHoleBlockEntity
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class BlackHoleBlockEntity extends BlockEntity implements BlockEntityAgeAccess {

    private static final double DAMAGE_DISTANCE = 0.6D;
    private static final int PLAYER_SEARCH_DISTANCE = 6;

    private final List<ItemEntity> thrownOutItems = new ArrayList<>();

    private double stored_xp;
    private int tickCounter;
    public int auraTexture = 0;

    public BlackHoleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLACK_HOLE.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BlackHoleBlockEntity blockEntity) {
        blockEntity.tickCounter++;

        if (blockEntity.tickCounter % 5 == 0) {
            blockEntity.auraTexture = (blockEntity.auraTexture + 1) % 3;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BlackHoleBlockEntity blockEntity) {
        List<Entity> entities = level.getEntities(null, new AABB(pos.getX() + 0.5 - 5, pos.getY() + 0.5 - 5, pos.getZ() + 0.5 - 5, pos.getX() + 0.5 + 5, pos.getY() + 0.5 + 5, pos.getZ() + 0.5 + 5));

        for (Entity entity : entities) {
            if (!entity.getType().is(ModTags.EntityTypes.BLACK_HOLE_AFFECTED)) {
                continue;
            }

            if (entity instanceof ItemEntity itemEntity && !blockEntity.isAffectedItem(itemEntity)) {
                continue;
            }

            double distance = entity.position().distanceTo(pos.getCenter());
            double movementFactor = blockEntity.getMovementFactor(distance);

            entity.push((pos.getX() + 0.5 - entity.getX()) * movementFactor, (pos.getY() + 0.5 - entity.getY() + 1.25) * movementFactor, (pos.getZ() + 0.5 - entity.getZ()) * movementFactor);

            if (distance <= DAMAGE_DISTANCE) {
                if (entity instanceof ExperienceOrb experienceOrb) {
                    blockEntity.stored_xp += experienceOrb.getValue();

                    if (blockEntity.stored_xp >= 60) {
                        blockEntity.throwOutItemStack(level, new ItemStack(ModItems.CONDENSED_EXPERIENCE.get()), pos.getCenter());
                        blockEntity.stored_xp = 0;
                    }
                    if (level instanceof ServerLevel serverLevel) {
                        experienceOrb.kill(serverLevel);
                    }
                } else {
                    entity.hurt(level.damageSources().magic(), 4);
                }
            }
        }

        blockEntity.thrownOutItems.removeIf(itemEntity -> !itemEntity.isAlive());
    }

    public boolean isAffectedItem(ItemEntity entity) {
        return !this.thrownOutItems.contains(entity) && !entity.getItem().is(ModTags.Items.BLACK_HOLE_UNAFFECTED);
    }

    private void throwOutItemStack(Level level, ItemStack stack, Vec3 pos) {
        ItemEntity item = new ItemEntity(level, pos.x(), pos.y(), pos.z(), stack);
        Player nearestPlayer = level.getNearestPlayer(pos.x(), pos.y(), pos.z(), PLAYER_SEARCH_DISTANCE, false);

        if (nearestPlayer == null) {
            this.setRandomVelocity(item, level.getRandom());
        } else {
            item.push((nearestPlayer.getX() - item.getX()) * 0.09, (nearestPlayer.getY() - item.getY() + 1.25) * 0.09, (nearestPlayer.getZ() - item.getZ()) * 0.09);
        }

        this.thrownOutItems.add(item);

        level.addFreshEntity(item);
    }

    private double getMovementFactor(double distance) {
        return distance <= 3 ? 0.035 : 0.02;
    }

    private void setRandomVelocity(ItemEntity itemEntity, RandomSource random) {
        double x = random.nextDouble();
        double y = random.nextDouble();
        double z = random.nextDouble();

        itemEntity.setDeltaMovement(random.nextBoolean() ? x : -x, random.nextBoolean() ? y : -y, random.nextBoolean() ? z : -z);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.stored_xp = input.getDoubleOr("StoredXP", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putDouble("StoredXP", this.stored_xp);
    }

    @Override
    public int getAgeInTicks() {
        return this.tickCounter;
    }
}
