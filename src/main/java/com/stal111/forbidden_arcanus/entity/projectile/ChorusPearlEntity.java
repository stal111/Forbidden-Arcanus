package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.entity.ModEntities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ChorusPearlEntity extends EntityThrowable {

	public ChorusPearlEntity(World world) {
		super(ModEntities.TYPE_CHORUS_PEARL, world);
	}

	public ChorusPearlEntity(World world, double x, double y, double z) {
		super(ModEntities.TYPE_CHORUS_PEARL, x, y, z, world);
	}

	public ChorusPearlEntity(World world, EntityLivingBase thrower) {
		super(ModEntities.TYPE_CHORUS_PEARL, thrower, world);
	}

	@Override
	protected void onImpact(RayTraceResult hit) {
		if (hit.entity != null) {
			hit.entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
		}
		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 3);
			this.remove();
		}
	}

	@Override
	public void tick() {
		EntityLivingBase entitylivingbase = this.getThrower();
		if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isAlive()) {
			this.remove();
		} else {
			super.tick();
		}

	}

}
