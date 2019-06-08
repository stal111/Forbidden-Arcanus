//package com.stal111.forbidden_arcanus.util;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import org.apache.commons.lang3.tuple.Pair;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.function.Function;
//
//public class RayTraceTools {
//
//	public static void rayTrace(Beam beam, Function<Entity, Boolean> consumer) {
//
//		Vec3d start = beam.getStart();
//		Vec3d lookVec = beam.getLookVec();
//		Vec3d end = beam.getEnd();
//		double dist = beam.getDist();
//		World world = beam.getWorld();
//		PlayerEntity player = beam.getPlayer();
//		List<Entity> targets = world.getEntitiesInAABBexcluding(player,
//				player.getBoundingBox().expand(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist).grow(1.0D, 1.0D,
//						1.0D),
//
//				EntitySelectors.NOT_SPECTATING.and(ent -> ent != null && ent.canBeCollidedWith()));
//
//		List<Pair<Entity, Double>> hitTargets = new ArrayList<>();
//		for (Entity target : targets) {
//			AxisAlignedBB targetBB = target.getBoundingBox().grow(target.getCollisionBorderSize());
//			if (targetBB.contains(start)) {
//				hitTargets.add(Pair.of(target, 0.0));
//			} else {
//				RayTraceResult targetResult = targetBB.calculateIntercept(start, end);
//				if (targetResult != null) {
//					double d3 = start.distanceTo(targetResult.getHitVec());
//					if (d3 < dist) {
//						hitTargets.add(Pair.of(target, d3));
//					}
//				}
//			}
//		}
//		hitTargets.sort(Comparator.comparing(Pair::getRight));
//		hitTargets.stream().filter(pair -> consumer.apply(pair.getLeft())).findFirst();
//	}
//
//	public static class Beam {
//		private World world;
//		private PlayerEntity player;
//		private double maxDist;
//		private Vec3d start;
//		private Vec3d lookVec;
//		private Vec3d end;
//		private double dist;
//
//		public Beam(World world, PlayerEntity player, double maxDist) {
//			this.world = world;
//			this.player = player;
//			this.maxDist = maxDist;
//			calculate();
//		}
//
//		private void calculate() {
//			start = this.player.getEyePosition(1.0f);
//			lookVec = this.player.getLookVec();
//			end = start.add(lookVec.x * this.maxDist, lookVec.y * this.maxDist, lookVec.z * this.maxDist);
//
//			RayTraceResult result = this.world.rayTraceBlocks(start, end);
//
//			dist = this.maxDist;
//
//			if (result != null && result.getType() == RayTraceResult.Type.BLOCK) {
//				dist = result.getHitVec().distanceTo(start);
//				end = start.add(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist);
//			}
//		}
//
//		public Vec3d getStart() {
//			return start;
//
//		}
//
//		public Vec3d getLookVec() {
//			return lookVec;
//		}
//
//		public Vec3d getEnd() {
//			return end;
//		}
//
//		public double getDist() {
//			return dist;
//		}
//
//		public World getWorld() {
//			return world;
//		}
//
//		public PlayerEntity getPlayer() {
//			return player;
//		}
//
//		public double getMaxDist() {
//			return maxDist;
//		}
//	}
//}
