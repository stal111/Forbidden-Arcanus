package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 */
public class MundabiturDustItem extends Item {

    public MundabiturDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        var transformContext = TransformPatternInteraction.TransformPatternContext.of(level, context.getClickedPos(), context.getHand(), context.getClickedFace());

        return this.tryInteract(player, stack, TransformPatternInteraction.class, transformContext);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        var context = EntityInteraction.EntityInteractionContext.of(target, hand);

        return this.tryInteract(player, stack, EntityInteraction.class, context);
    }

    private <T extends MundabiturInteraction.Context> InteractionResult tryInteract(Player player, ItemStack stack, Class<? extends MundabiturInteraction<T>> clazz, T context) {
        var optional = FARegistries.MUNDABITUR_INTERACTION_REGISTRY.get().getValues().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .filter(interaction -> interaction.canInteract(context))
                .findFirst();

        if (optional.isPresent()) {
            Level level = player.level();

            optional.get().interact(context);

            ItemStackUtils.shrinkStack(player, stack);

            level.playSound(player, context.getPos(), ModSounds.MUNDABITUR_DUST_USE.get(), SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() * 0.15F + 0.9F);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
