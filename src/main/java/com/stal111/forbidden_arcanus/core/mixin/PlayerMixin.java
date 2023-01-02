package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * @author stal111
 * @since 2023-01-02
 */
@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow public abstract @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot);

    @Shadow protected abstract void touch(Entity entity);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;playShoulderEntityAmbientSound(Lnet/minecraft/nbt/CompoundTag;)V"), method = "aiStep")
    public void forbiddenArcanus_aiStep(CallbackInfo ci) {
        if (this.getHealth() > 0.0F && !this.isSpectator() && ModifierHelper.getModifier(this.getItemBySlot(EquipmentSlot.FEET)) == ModItemModifiers.MAGNETIZED.get()) {
            if (this.isPassenger() && !this.getVehicle().isRemoved()) {
                return;
            }

            AABB aabb = this.getBoundingBox().inflate(2.0D, 1.5D, 2.0D);

            List<Entity> list = this.level.getEntities(this, aabb);

            for (Entity entity : list) {
                if (entity instanceof ItemEntity) {
                    this.touch(entity);
                }
            }
        }
    }
}
