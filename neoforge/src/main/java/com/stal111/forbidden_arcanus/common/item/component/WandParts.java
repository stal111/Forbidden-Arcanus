package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.wand.WandPart;
import com.stal111.forbidden_arcanus.common.item.wand.WandStats;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record WandParts(
        WandPart tip,
        WandPart transition,
        WandPart pommel
) implements TooltipProvider {

    public static final Codec<WandParts> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WandPart.CODEC.fieldOf("tip").forGetter(WandParts::tip),
            WandPart.CODEC.fieldOf("transition").forGetter(WandParts::transition),
            WandPart.CODEC.fieldOf("pommel").forGetter(WandParts::pommel)
    ).apply(instance, WandParts::new));

    public WandStats getCombinedStats() {
        return new WandStats(
                this.tip.stats().damageBonus() + this.transition.stats().damageBonus() + this.pommel.stats().damageBonus(),
                this.tip.stats().speedBonus() + this.transition.stats().speedBonus() + this.pommel.stats().speedBonus(),
                this.tip.stats().aimBonus() + this.transition.stats().aimBonus() + this.pommel.stats().aimBonus()
        );
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
//        WandStats combinedStats = this.getCombinedStats();
//
//        tooltipAdder.accept(Component.literal("Tip: ").append(this.tip.materialName()).withStyle(ChatFormatting.GRAY));
//        tooltipAdder.accept(Component.literal("Transition: ").append(this.transition.materialName()).withStyle(ChatFormatting.GRAY));
//        tooltipAdder.accept(Component.literal("Pommel: ").append(this.pommel.materialName()).withStyle(ChatFormatting.GRAY));
//        tooltipAdder.accept(Component.empty());
//        tooltipAdder.accept(Component.literal("Damage: ").append(String.valueOf(combinedStats.damageBonus())).withStyle(ChatFormatting.GREEN));
//        tooltipAdder.accept(Component.literal("Speed: ").append(String.valueOf(combinedStats.speedBonus())).withStyle(ChatFormatting.GREEN));
//        tooltipAdder.accept(Component.literal("Aim: ").append(String.valueOf(combinedStats.aimBonus())).withStyle(ChatFormatting.GREEN));
    }
}