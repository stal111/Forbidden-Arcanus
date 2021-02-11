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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.*;

public class BlackHoleTileEntity extends TileEntity implements ITickableTileEntity {

    Map<ItemEntity, Vector3d> ITEM_POSITION_MAP = new HashMap<>();
    private static final List<ItemEntity> BLACK_HOLE_OUT = new ArrayList<>();

    private double stored_xp;
    public int blackHoleRotation;
    public int tickCounter;
    public int auraTexture = 0;

    public BlackHoleTileEntity() {
        super(ModTileEntities.BLACK_HOLE.get());
        this.blackHoleRotation = new Random().nextInt(100000);
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

        List<Entity> entities = Objects.requireNonNull(getWorld()).getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(getPos().getX() + 0.5 - 5, getPos().getY() + 0.5 - 5, getPos().getZ() + 0.5 - 5, getPos().getX() + 0.5 + 5, getPos().getY() + 0.5 + 5, getPos().getZ() + 0.5 + 5));

        for (Entity entity : entities) {
            if (entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity) {
                double distance = entity.getPositionVec().distanceTo(ModUtils.blockPosToVector(pos, 0.5));

                if (entity instanceof ItemEntity) {
                    if (!ITEM_POSITION_MAP.containsKey(entity) && distance <= 1.25 && !BLACK_HOLE_OUT.contains(entity)) {
                        ITEM_POSITION_MAP.put((ItemEntity) entity, entity.getPositionVec());
                    }
                }

                if ((entity instanceof ItemEntity && !ModTags.Items.BLACK_HOLE_UNAFFECTED.contains(((ItemEntity) entity).getItem().getItem()) && !BLACK_HOLE_OUT.contains(entity)) || entity instanceof ExperienceOrbEntity) {
                    entity.addVelocity((getPos().getX() + 0.5 - entity.getPosX()) * getMovementFactor(distance), (getPos().getY() + 0.5 - entity.getPosY() + 1.25) * getMovementFactor(distance), (getPos().getZ() + 0.5 - entity.getPosZ()) * getMovementFactor(distance));
                }

                if (distance <= 0.5 && ((entity instanceof ItemEntity && !BLACK_HOLE_OUT.contains(entity)) || entity instanceof ExperienceOrbEntity)) {
                    entity.remove();

                    if (entity instanceof ExperienceOrbEntity) {
                        this.stored_xp += ((ExperienceOrbEntity) entity).xpValue;
                    }

                    if (this.stored_xp >= 60) {
                        throwOutItemStack(getWorld(), new ItemStack(ModItems.XPETRIFIED_ORB.get()), getPos());
                        this.stored_xp = 0;
                    }
                }
            }
        }

        BLACK_HOLE_OUT.removeIf(itemEntity -> !itemEntity.isAlive());
    }

    private void throwOutItemStack(World world, ItemStack stack, BlockPos pos) {
        ItemEntity output = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);

        List<PlayerEntity> players = Objects.requireNonNull(getWorld()).getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(getPos().getX() + 0.5 - 5, getPos().getY() + 0.5 - 5, getPos().getZ() + 0.5 - 5, getPos().getX() + 0.5 + 5, getPos().getY() + 0.5 + 5, getPos().getZ() + 0.5 + 5));

        PlayerEntity nearestPlayer = null;

        for (PlayerEntity player : players) {
            if (nearestPlayer == null || nearestPlayer.getPositionVec().distanceTo(ModUtils.blockPosToVector(pos, 0.5)) < player.getPositionVec().distanceTo(ModUtils.blockPosToVector(pos, 0.5))) {
                nearestPlayer = player;
            }
        }

        if (nearestPlayer != null) {
            output.addVelocity((nearestPlayer.getPosX() - output.getPosX()) * 0.09, (nearestPlayer.getPosY() - output.getPosY() + 1.25) * 0.09, (nearestPlayer.getPosZ() - output.getPosZ()) * 0.09);
        } else {
            setRandomVelocity(output, getWorld().getRandom());
        }

        BLACK_HOLE_OUT.add(output);

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
    public void read(BlockState state, CompoundNBT compoundNBT) {
        super.read(state, compoundNBT);
        this.stored_xp = compoundNBT.getDouble("stored_xp");
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putDouble("stored_xp", stored_xp);

        return compoundNBT;
    }
}
