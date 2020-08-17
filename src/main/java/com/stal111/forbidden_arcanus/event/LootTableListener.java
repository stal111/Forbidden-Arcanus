package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.event.modifier.InfernumPickaxeLootModifier;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class LootTableListener {

    public static void registerGlobalModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        register("infernum_smelting", new InfernumPickaxeLootModifier.Serializer());
    }

    private static <T extends GlobalLootModifierSerializer<?>> void register(String name, T serializer) {
        serializer.setRegistryName(new ResourceLocation(Main.MOD_ID, name));
        ForgeRegistries.LOOT_MODIFIER_SERIALIZERS.register(serializer);
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        String prefix = "minecraft:chests/";
        String name = event.getName().toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file) {
                case "abandoned_mineshaft":
                case "end_city_treasure": event.getTable().addPool(getInjectPool(file)); break;
                default: break;
            }
        }

        if (name.equals("minecraft:entities/enderman")) {
            event.getTable().addPool(getInjectPool("enderman"));
        } else if (name.equals("minecraft:entities/bat")) {
            event.getTable().addPool(getInjectPool("bat"));
        } else if (name.equals("minecraft:entities/squid")) {
            event.getTable().addPool(getInjectPool("squid"));
        } else if (name.equals("minecraft:entities/ender_dragon")) {
            event.getTable().addPool(getInjectPool("ender_dragon"));
        }
    }

    private static LootPool getInjectPool(String entryName) {
        return LootPool.builder()
                .addEntry(getInjectEntry(entryName, 1))
                .bonusRolls(0, 3)
                .name("forbidden_arcanus_inject")
                .build();
    }

    private static LootEntry.Builder getInjectEntry(String name, int weight) {
        ResourceLocation table = new ResourceLocation(Main.MOD_ID, "inject/" + name);
        return TableLootEntry.builder(table).weight(weight);
    }
}
