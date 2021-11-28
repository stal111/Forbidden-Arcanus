package com.stal111.forbidden_arcanus.tile;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.*;

public class BlackHoleTileEntity extends BlockEntity {

    Map<ItemEntity, Vec3> ITEM_POSITION_MAP = new HashMap<>();
    private static final List<ItemEntity> BLACK_HOLE_OUT = new ArrayList<>();

    private double stored_xp;
    public int blackHoleRotation;
    public int tickCounter;
    public int auraTexture = 0;

    public BlackHoleTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLACK_HOLE.get(), pos, state);
        this.blackHoleRotation = new Random().nextInt(100000);
    }

    //@Override
    public void tick() {
        blackHoleRotation++;
        tickCounter++;

        if (tickCounter == 5 || tickCounter == 10) {
            auraTexture++;
        } else if (tickCounter == 15) {
            tickCounter = 0;
            auraTexture = 0;
        }

        List<Entity> entities = Objects.requireNonNull(getLevel()).getEntities(null, new AABB(getBlockPos().getX() + 0.5 - 5, getBlockPos().getY() + 0.5 - 5, getBlockPos().getZ() + 0.5 - 5, getBlockPos().getX() + 0.5 + 5, getBlockPos().getY() + 0.5 + 5, getBlockPos().getZ() + 0.5 + 5));

        for (Entity entity : entities) {
            if (entity instanceof ItemEntity || entity instanceof ExperienceOrb) {
                double distance = entity.position().distanceTo(ModUtils.blockPosToVector(worldPosition, 0.5));

                if (entity instanceof ItemEntity) {
                    if (!ITEM_POSITION_MAP.containsKey(entity) && distance <= 1.25 && !BLACK_HOLE_OUT.contains(entity)) {
                        ITEM_POSITION_MAP.put((ItemEntity) entity, entity.position());
                    }
                }

                if ((entity instanceof ItemEntity && !ModTags.Items.BLACK_HOLE_UNAFFECTED.contains(((ItemEntity) entity).getItem().getItem()) && !BLACK_HOLE_OUT.contains(entity)) || entity instanceof ExperienceOrb) {
                    entity.push((getBlockPos().getX() + 0.5 - entity.getX()) * getMovementFactor(distance), (getBlockPos().getY() + 0.5 - entity.getY() + 1.25) * getMovementFactor(distance), (getBlockPos().getZ() + 0.5 - entity.getZ()) * getMovementFactor(distance));
                }

                if (distance <= 0.5 && ((entity instanceof ItemEntity && !BLACK_HOLE_OUT.contains(entity)) || entity instanceof ExperienceOrb)) {
                    entity.remove(Entity.RemovalReason.KILLED);

                    if (entity instanceof ExperienceOrb) {
                        this.stored_xp += ((ExperienceOrb) entity).value;
                    }

                    if (this.stored_xp >= 60) {
                        throwOutItemStack(getLevel(), new ItemStack(ModItems.XPETRIFIED_ORB.get()), getBlockPos());
                        this.stored_xp = 0;
                    }
                }
            }
        }

        BLACK_HOLE_OUT.removeIf(itemEntity -> !itemEntity.isAlive());
    }

    private void throwOutItemStack(Level world, ItemStack stack, BlockPos pos) {
        ItemEntity output = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);

        List<Player> players = Objects.requireNonNull(getLevel()).getEntitiesOfClass(Player.class, new AABB(getBlockPos().getX() + 0.5 - 5, getBlockPos().getY() + 0.5 - 5, getBlockPos().getZ() + 0.5 - 5, getBlockPos().getX() + 0.5 + 5, getBlockPos().getY() + 0.5 + 5, getBlockPos().getZ() + 0.5 + 5));

        Player nearestPlayer = null;

        for (Player player : players) {
            if (nearestPlayer == null || nearestPlayer.position().distanceTo(ModUtils.blockPosToVector(pos, 0.5)) < player.position().distanceTo(ModUtils.blockPosToVector(pos, 0.5))) {
                nearestPlayer = player;
            }
        }

        if (nearestPlayer != null) {
            output.push((nearestPlayer.getX() - output.getX()) * 0.09, (nearestPlayer.getY() - output.getY() + 1.25) * 0.09, (nearestPlayer.getZ() - output.getZ()) * 0.09);
        } else {
            setRandomVelocity(output, getLevel().getRandom());
        }

        BLACK_HOLE_OUT.add(output);

        world.addFreshEntity(output);
    }

    private double getMovementFactor(double distance) {
        return distance <= 3 ? 0.035 : 0.02;
    }

    private void setRandomVelocity(ItemEntity itemEntity, Random random) {
        double x = random.nextDouble();
        double y = random.nextDouble();
        double z = random.nextDouble();

        itemEntity.setDeltaMovement(random.nextBoolean() ? x : -x, random.nextBoolean() ? y : -y, random.nextBoolean() ? z : -z);
    }

    @Override
    public void load(CompoundTag compoundNBT) {
        super.load(compoundNBT);
        this.stored_xp = compoundNBT.getDouble("stored_xp");
    }

    @Override
    public CompoundTag save(CompoundTag compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.putDouble("stored_xp", stored_xp);

        return compoundNBT;
    }
}
