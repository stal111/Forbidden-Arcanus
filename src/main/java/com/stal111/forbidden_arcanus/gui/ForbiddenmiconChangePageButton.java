package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.SoundEvents;

public class ForbiddenmiconChangePageButton extends Button {

    private final boolean field_212940_a;
    private final boolean field_212941_b;

    public ForbiddenmiconChangePageButton(int p_i51079_1_, int p_i51079_2_, boolean p_i51079_3_, Button.IPressable p_i51079_4_, boolean p_i51079_5_) {
        super((int) (p_i51079_1_ * 0.8), (int) (p_i51079_2_ * 0.8),18,13, "", p_i51079_4_);
        this.field_212940_a = p_i51079_3_;
        this.field_212941_b = p_i51079_5_;
    }

    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        int i = 239;
        int j = 209;
        if (this.isHovered()) {
            j += 13;
        }

        if (!this.field_212940_a) {
            i -= 18;
        }

        blit(this.x, this.y, this.getBlitOffset(), i, j, 18, 13, 256, 512);
    }

    public void playDownSound(SoundHandler p_playDownSound_1_) {
        if (this.field_212941_b) {
            p_playDownSound_1_.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
        }
    }
}
