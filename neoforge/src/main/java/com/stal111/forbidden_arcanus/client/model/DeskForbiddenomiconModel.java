package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.client.animation.ForbiddenomiconAnimation;
import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class DeskForbiddenomiconModel<T extends Entity> extends AbstractForbiddenomiconModel<T> {

    private final KeyframeAnimation stillAnimation;
    private final KeyframeAnimation openingAnimation;
    private final KeyframeAnimation closingAnimation;
    private final KeyframeAnimation levitateAnimation;
    private final KeyframeAnimation openStillAnimation;
    private final KeyframeAnimation pageAnimation;

    public DeskForbiddenomiconModel(ModelPart root) {
        super(root);
        this.stillAnimation = ForbiddenomiconAnimation.CLOSED.bake(root);
        this.openingAnimation = ForbiddenomiconAnimation.OPENING.bake(root);
        this.closingAnimation = ForbiddenomiconAnimation.CLOSING.bake(root);
        this.levitateAnimation = ForbiddenomiconAnimation.LEVITATE_DESK.bake(root);
        this.openStillAnimation = ForbiddenomiconAnimation.OPEN_STILL.bake(root);
        this.pageAnimation = ForbiddenomiconAnimation.FLIP_PAGE.bake(root);
    }

    public void setupAnim(@NotNull ResearchDeskBlockEntity blockEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.stillAnimation.apply(blockEntity.stillAnimation, ageInTicks);
        this.openingAnimation.apply(blockEntity.openingAnimation, ageInTicks);
        this.closingAnimation.apply(blockEntity.closingAnimation, ageInTicks);
        this.levitateAnimation.apply(blockEntity.levitateAnimation, ageInTicks);
        this.openStillAnimation.apply(blockEntity.levitateAnimation, ageInTicks);
        this.pageAnimation.apply(blockEntity.pageAnimation, ageInTicks);
    }
}
