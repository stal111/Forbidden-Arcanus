package com.stal111.forbidden_arcanus.client.tooltip;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.wand.WandStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ClientWandStatsTooltip implements ClientTooltipComponent {

    private static final String DAMAGE_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.damage"));
    private static final String PROJECTILE_SPEED_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.projectile_speed"));
    private static final String ACCURACY_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.accuracy"));

    private static final Identifier DAMAGE_SPRITE = ForbiddenArcanus.identifier("icon/wand_stat/damage");
    private static final Identifier PROJECTILE_SPEED_SPRITE = ForbiddenArcanus.identifier("icon/wand_stat/projectile_speed");
    private static final Identifier ACCURACY_SPRITE = ForbiddenArcanus.identifier("icon/wand_stat/accuracy");

    private final List<StatEntry> stats = new ArrayList<>();

    public ClientWandStatsTooltip(WandStats stats) {
        this.addStat(DAMAGE_SPRITE, DAMAGE_KEY, stats.damage());
        this.addStat(PROJECTILE_SPEED_SPRITE, PROJECTILE_SPEED_KEY, stats.projectileSpeed());
        this.addStat(ACCURACY_SPRITE, ACCURACY_KEY, stats.accuracy());
    }

    @Override
    public int getHeight(Font font) {
        return this.stats.size() * 10;
    }

    @Override
    public int getWidth(Font font) {
        return this.stats.stream().mapToInt(stat -> font.width(stat.text())).max().orElse(0);
    }

    private void addStat(Identifier sprite, String key, float value) {
        if (value != 0) {
            this.stats.add(new StatEntry(sprite, Component.translatable(key, value).withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    public void renderImage(Font font, int x, int y, int w, int h, GuiGraphics graphics) {
        for (StatEntry stat : this.stats) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, stat.sprite(), x, y, 10, 10);
            graphics.drawString(font, stat.text(), x + 13, y + 1, -1);
            y += 10;
        }
    }

    public record StatEntry(Identifier sprite, Component text) {

    }
}
