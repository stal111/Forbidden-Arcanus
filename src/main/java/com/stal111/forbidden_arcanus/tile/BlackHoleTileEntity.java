package com.stal111.forbidden_arcanus.tile;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlackHoleTileEntity extends TileEntity implements ITickableTileEntity {

    private final List<ItemEntity> thrownOutItems = new ArrayList<>();

    private double stored_xp;
    public int blackHoleRotation = 0;
    public int tickCounter;
    public int auraTexture = 0;

    public BlackHoleTileEntity() {
        super(ModTileEntities.BLACK_HOLE.get());
    }

    @Override
    public void tick() {
        blackHoleRotation++;
        tickCounter++;

        if (tickCounter == 5 || tickCounter == 10) {
            auraTexture++;
        } else if (tickCounter == 15) {
            tickCounter = 0;
            auraTexture = 0;
        }

        if (this.getWorld() == null || this.getWorld().isRemote) {
            return;
        }

        List<Entity> entities = this.getWorld().getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(getPos().getX() + 0.5 - 5, getPos().getY() + 0.5 - 5, getPos().getZ() + 0.5 - 5, getPos().getX() + 0.5 + 5, getPos().getY() + 0.5 + 5, getPos().getZ() + 0.5 + 5));

        for (Entity entity : entities) {
            if (entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity) {
                double distance = entity.getPositionVec().distanceTo(ModUtils.blockPosToVector(this.pos, 0.5));

                if ((entity instanceof ItemEntity && !ModTags.Items.BLACK_HOLE_UNAFFECTED.contains(((ItemEntity) entity).getItem().getItem()) && !this.thrownOutItems.contains(entity)) || entity instanceof ExperienceOrbEntity) {
                    entity.addVelocity((getPos().getX() + 0.5 - entity.getPosX()) * getMovementFactor(distance), (getPos().getY() + 0.5 - entity.getPosY() + 1.25) * getMovementFactor(distance), (getPos().getZ() + 0.5 - entity.getPosZ()) * getMovementFactor(distance));
                }

                if (distance <= 0.5 && ((entity instanceof ItemEntity && !this.thrownOutItems.contains(entity)) || entity instanceof ExperienceOrbEntity)) {
                    entity.remove();

                    if (entity instanceof ExperienceOrbEntity) {
                        this.stored_xp += ((ExperienceOrbEntity) entity).xpValue;
                    }

                    if (this.stored_xp >= 60) {
                        this.throwOutItemStack(this.getWorld(), new ItemStack(ModItems.XPETRIFIED_ORB.get()), getPos());
                        this.stored_xp = 0;
                    }
                }
            }
        }

        this.thrownOutItems.removeIf(itemEntity -> !itemEntity.isAlive());
    }

    private void throwOutItemStack(World world, ItemStack stack, BlockPos pos) {
        ItemEntity output = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);

        List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(getPos().getX() + 0.5 - 5, getPos().getY() + 0.5 - 5, getPos().getZ() + 0.5 - 5, getPos().getX() + 0.5 + 5, getPos().getY() + 0.5 + 5, getPos().getZ() + 0.5 + 5));

        PlayerEntity nearestPlayer = null;

        for (PlayerEntity player : players) {
            if (nearestPlayer == null || nearestPlayer.getPositionVec().distanceTo(ModUtils.blockPosToVector(pos, 0.5)) < player.getPositionVec().distanceTo(ModUtils.blockPosToVector(pos, 0.5))) {
                nearestPlayer = player;
            }
        }

        if (nearestPlayer != null) {
            output.addVelocity((nearestPlayer.getPosX() - output.getPosX()) * 0.09, (nearestPlayer.getPosY() - output.getPosY() + 1.25) * 0.09, (nearestPlayer.getPosZ() - output.getPosZ()) * 0.09);
        } else {
            setRandomVelocity(output, world.getRandom());
        }

        this.thrownOutItems.add(output);

        world.addEntity(output);
    }

    private double getMovementFactor(double distance) {
        return distance <= 3 ? 0.035 : 0.02;
    }

    private void setRandomVelocity(ItemEntity itemEntity, Random random) {
        double x = random.nextDouble();
        double y = random.nextDouble();
        double z = random.nextDouble();

        itemEntity.setMotion(random.nextBoolean() ? x : -x, random.nextBoolean() ? y : -y, random.nextBoolean() ? z : -z);
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
        super.read(state, compound);
        this.stored_xp = compound.getDouble("stored_xp");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putDouble("stored_xp", stored_xp);

        return compound;
    }
}
