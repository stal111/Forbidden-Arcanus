package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.common.entity.ModBoat;
import com.stal111.forbidden_arcanus.common.entity.ModChestBoat;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.common.entity.projectile.BoomArrow;
import com.stal111.forbidden_arcanus.common.entity.projectile.DracoArcanusArrow;
import com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall;
import com.stal111.forbidden_arcanus.common.entity.projectile.ThrownAurealBottle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.EntityRegistryHelper;

/**
 * Mod Entities
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModEntities
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class ModEntities implements RegistryClass {

    public static final EntityRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENTITY_TYPE);

    public static final RegistryEntry<EntityType<EnergyBall>> ENERGY_BALL = HELPER.register("energy_ball", EntityType.Builder.<EnergyBall>of(EnergyBall::new, MobCategory.MISC).sized(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryEntry<EntityType<BoomArrow>> BOOM_ARROW = HELPER.register("boom_arrow", EntityType.Builder.<BoomArrow>of(BoomArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryEntry<EntityType<DracoArcanusArrow>> DRACO_ARCANUS_ARROW = HELPER.register("draco_arcanus_arrow", EntityType.Builder.<DracoArcanusArrow>of(DracoArcanusArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryEntry<EntityType<CrimsonLightningBoltEntity>> CRIMSON_LIGHTNING_BOLT = HELPER.register("crimson_lightning_bolt", EntityType.Builder.of(CrimsonLightningBoltEntity::new, MobCategory.MISC).sized(0.0F, 0.0F).setTrackingRange(16).updateInterval(Integer.MAX_VALUE));
    public static final RegistryEntry<EntityType<ModBoat>> BOAT = HELPER.register("boat", EntityType.Builder.<ModBoat>of(ModBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
    public static final RegistryEntry<EntityType<ModChestBoat>> CHEST_BOAT = HELPER.register("chest_boat", EntityType.Builder.<ModChestBoat>of(ModChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
    public static final RegistryEntry<EntityType<LostSoul>> LOST_SOUL = HELPER.register("lost_soul", EntityType.Builder.<LostSoul>of(LostSoul::new, MobCategory.CREATURE).sized(0.35F, 0.6F).clientTrackingRange(8));
    public static final RegistryEntry<EntityType<ThrownAurealBottle>> AUREAL_BOTTLE = HELPER.register("aureal_bottle", EntityType.Builder.<ThrownAurealBottle>of(ThrownAurealBottle::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryEntry<EntityType<DarkTrader>> DARK_TRADER = HELPER.register("dark_trader", EntityType.Builder.of(DarkTrader::new, MobCategory.CREATURE).sized(0.35F, 0.6F).clientTrackingRange(8));

}
