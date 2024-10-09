package com.stal111.forbidden_arcanus.common.inventory.research;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 09.11.2023
 */
public class ResearchDeskMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess levelAccess;

    public ResearchDeskMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, MenuCreationContext.of(inventory));
    }

    public ResearchDeskMenu(int containerId, MenuCreationContext<?, ?> context) {
        super(ModMenuTypes.RESEARCH_DESK.get(), containerId);
        this.levelAccess = context.levelAccess();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.RESEARCH_DESK.get());
    }
}
