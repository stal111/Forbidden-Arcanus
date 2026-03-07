package com.stal111.forbidden_arcanus.client.tooltip;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

public class ClientWandMaterialTooltip implements ClientTooltipComponent {

    private static final String DAMAGE_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.damage"));
    private static final String PROJECTILE_SPEED_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.projectile_speed"));
    private static final String ACCURACY_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.accuracy"));

    private static final Identifier DAMAGE_SPRITE = ForbiddenArcanus.identifier("icon/wand_stat/damage");
    private static final Identifier PROJECTILE_SPEED_SPRITE = ForbiddenArcanus.identifier("icon/wand_stat/projectile_speed");
    private static final Identifier ACCURACY_SPRITE = ForbiddenArcanus.identifier("icon/wand_stat/accuracy");

    private final Component damage;
    private final Component projectileSpeed;
    private final Component accuracy;

    public ClientWandMaterialTooltip(WandMaterial material) {
        this.damage = Component.translatable(DAMAGE_KEY, material.damage()).withStyle(ChatFormatting.GRAY);
        this.projectileSpeed = Component.translatable(PROJECTILE_SPEED_KEY, material.damage()).withStyle(ChatFormatting.GRAY);
        this.accuracy = Component.translatable(ACCURACY_KEY, material.damage()).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public int getHeight(Font font) {
        return 10 * 3;
    }

    @Override
    public int getWidth(Font font) {
        return Math.max(font.width(this.damage), Math.max(font.width(this.projectileSpeed), font.width(this.accuracy)));
    }

    @Override
    public void renderImage(Font font, int x, int y, int w, int h, GuiGraphics graphics) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, DAMAGE_SPRITE, x, y, 10, 10);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROJECTILE_SPEED_SPRITE, x, y + 10, 10, 10);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ACCURACY_SPRITE, x, y + 20, 10, 10);
        graphics.drawString(font, this.damage, x + 13, y + 1, -1);
        graphics.drawString(font, this.projectileSpeed, x + 13, y + 10 + 1, -1);
        graphics.drawString(font, this.accuracy, x + 13, y + 20 + 1, -1);
    }
}
