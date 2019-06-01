package com.stal111.forbidden_arcanus.event;

import java.util.ArrayList;
import java.util.List;

import com.stal111.forbidden_arcanus.potion.ModPotions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PlayerTickEvent {

	private static List<String> PlayersWithFlight = new ArrayList<String>();

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		String username = event.player.getDisplayName().toString();
		if (event.player.isPotionActive(ModPotions.fly)) {
			if (!event.player.world.isRemote) {
				if (!event.player.abilities.isCreativeMode || !event.player.isSpectator()) {
					boolean shouldFly = true;
					event.player.abilities.allowFlying = shouldFly;
					if (!PlayersWithFlight.contains(username))
						PlayersWithFlight.add(username);
				} else if (PlayersWithFlight.contains(username)) {
					PlayersWithFlight.remove(username);
					event.player.abilities.allowFlying = false;
					event.player.abilities.isFlying = false;
				}
			}
		}

		if (event.player.isPotionActive(ModPotions.spectral_vision)) {
			if (!event.player.world.isRemote) {
				 double d0 = (double)(70);
				 
				double k = event.player.posX;
				double l = event.player.posY;
				double i1 = event.player.posZ;
				
		         AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))).grow(d0).expand(0.0D, (double)event.player.world.getHeight(), 0.0D);
		         List<EntityLiving> list = event.player.world.getEntitiesWithinAABB(EntityLiving.class, axisalignedbb);
		         
		         for(EntityLiving entityLiving : list) {
		        	 entityLiving.addPotionEffect(new PotionEffect(Potion.getPotionById(24), 10, 0, false, false));
		          }

			}
		}

	}

}
