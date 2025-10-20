package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class ClibanoMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess levelAccess;

    public ClibanoMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ClibanoMenu(int containerId, Inventory playerInventory, ContainerLevelAccess levelAccess) {
        super(ModMenuTypes.CLIBANO.get(), containerId);
        this.levelAccess = levelAccess;

        this.addStandardInventorySlots(playerInventory, 8, 91);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.CLIBANO_MAIN_PART.get());
    }
}
