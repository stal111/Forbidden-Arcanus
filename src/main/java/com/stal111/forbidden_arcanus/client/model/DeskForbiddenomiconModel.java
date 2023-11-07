package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.client.animation.ForbiddenomiconAnimation;
import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class DeskForbiddenomiconModel<T extends Entity> extends AbstractForbiddenomiconModel<T> {

    public DeskForbiddenomiconModel(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void setupAnim(@NotNull ResearchDeskBlockEntity blockEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(blockEntity.stillAnimation, ForbiddenomiconAnimation.CLOSED, ageInTicks);
        this.animate(blockEntity.openingAnimation, ForbiddenomiconAnimation.OPENING, ageInTicks);
        this.animate(blockEntity.closingAnimation, ForbiddenomiconAnimation.CLOSING, ageInTicks);
        this.animate(blockEntity.levitateAnimation, ForbiddenomiconAnimation.LEVITATE_DESK, ageInTicks);
        this.animate(blockEntity.levitateAnimation, ForbiddenomiconAnimation.OPEN_STILL, ageInTicks);
        this.animate(blockEntity.pageAnimation, ForbiddenomiconAnimation.FLIP_PAGE, ageInTicks);
    }
}
