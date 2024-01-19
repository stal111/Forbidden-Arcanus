package com.stal111.forbidden_arcanus.common.aureal;

import com.stal111.forbidden_arcanus.core.init.ModEnchantments;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 17.09.2023
 */
public class ItemAurealProvider extends AttachmentAurealProvider<ItemStack> {

    private static final String AUREAL_KEY = "Aureal";
    private static final String AUREAL_LIMIT_KEY = "AurealLimit";

    public ItemAurealProvider(@NotNull ItemStack stack, int defaultLimit) {
        super(stack, defaultLimit);
    }

    @Override
    public int getModifiedAurealLimit() {
        int limit = this.getAurealLimit();
        int aurealReservoirLevel = this.getAttachmentHolder().getEnchantmentLevel(ModEnchantments.AUREAL_RESERVOIR.get());

        if (aurealReservoirLevel == 0) {
            return limit;
        }

        return (int) (limit * (1 + aurealReservoirLevel / 10.0D));
    }
}
