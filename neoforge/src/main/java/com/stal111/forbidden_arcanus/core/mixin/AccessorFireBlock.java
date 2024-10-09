package com.stal111.forbidden_arcanus.core.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface AccessorFireBlock {

    @Invoker("setFlammable")
     void forbiddenArcanus_setFireInfo(Block block, int encouragement, int flammability);
}
