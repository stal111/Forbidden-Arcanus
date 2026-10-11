package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public interface FlyingLabel<R extends HitResult> {

    default void render(GuiGraphicsExtractor guiGraphics, ItemStack stack, DeltaTracker deltaTracker, int centerX, int centerY, R result) {
        AtomicInteger y = new AtomicInteger(centerY);

        Consumer<Component> adder = component -> {
            int width = Minecraft.getInstance().font.width(component.getVisualOrderText());

            guiGraphics.fill(centerX - width / 2 - 2, y.get() - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 1, 0x44000000);
            guiGraphics.fill(centerX - width / 2 - 4, y.get() - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 3, 0x44000000);

            guiGraphics.text(Minecraft.getInstance().font, component, centerX - width / 2, centerY - 20, -1);

            y.addAndGet(10);
        };

        this.getComponents(Minecraft.getInstance().level, Minecraft.getInstance().player, stack, result, adder);
    }

    void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, R result, Consumer<Component> adder);
}
