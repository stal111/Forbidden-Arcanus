package com.stal111.forbidden_arcanus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.spawner.WorldEntitySpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;

/**
 * World Entity Spawner Mixin
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.WorldEntitySpawnerMixin
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-28
 */
@Mixin(WorldEntitySpawner.class)
public class WorldEntitySpawnerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/MobEntity;onInitialSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;", shift = At.Shift.BEFORE), method = "performWorldGenSpawning", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void forbidden_arcanus_performWorldGenSpawning(IServerWorld world, Biome biome, int centerX, int centerZ, Random diameterX, CallbackInfo ci, MobSpawnInfo mobspawninfo, List list, int i, int j, MobSpawnInfo.Spawners mobspawninfo$spawners, int k, ILivingEntityData ilivingentitydata, int l, int i1, int j1, int k1, int l1, boolean flag, int i2, BlockPos blockpos, float f, double d0, double d1, Entity entity, MobEntity mobentity) {
        net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mobentity, world.getWorld(), (float) d0, blockpos.getY(), (float) d1, null, SpawnReason.NATURAL);
    }
}
