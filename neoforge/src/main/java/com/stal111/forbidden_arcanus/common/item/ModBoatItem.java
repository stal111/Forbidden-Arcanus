package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.CustomBoat;
import com.stal111.forbidden_arcanus.common.entity.ModBoat;
import com.stal111.forbidden_arcanus.common.entity.ModChestBoat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author stal111
 * @since 2022-06-25
 */
public class ModBoatItem extends BoatItem {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    private final ModBoat.Type woodType;
    private final boolean hasChest;

    public ModBoatItem(boolean hasChest, ModBoat.Type woodType, Properties properties) {
        //TODO
        super(null, properties);
        this.woodType = woodType;
        this.hasChest = hasChest;
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitResult.getType() != HitResult.Type.MISS) {
            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);

            if (!list.isEmpty()) {
                Vec3 vec31 = player.getEyePosition();

                for (Entity entity : list) {
                    if (entity.getBoundingBox().inflate(entity.getPickRadius()).contains(vec31)) {
                        return InteractionResult.PASS;
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                CustomBoat customBoat = this.getBoat(level, hitResult);
                customBoat.setWoodType(this.woodType);

                if (!(customBoat instanceof Boat boat)) {
                    return InteractionResult.PASS;
                }

                boat.setYRot(player.getYRot());

                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResult.FAIL;
                } else {
                    if (!level.isClientSide()) {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());

                        stack.consume(1, player);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResult.SUCCESS;
                }
            }

        }
        return InteractionResult.PASS;
    }

    private CustomBoat getBoat(Level level, HitResult hitResult) {
        return this.hasChest ? new ModChestBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) : new ModBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
    }
}
