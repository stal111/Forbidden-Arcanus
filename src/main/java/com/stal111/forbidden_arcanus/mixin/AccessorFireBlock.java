package com.stal111.forbidden_arcanus.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface AccessorFireBlock {

    @Invoker("setFireInfo")
     void forbiddenArcanus_setFireInfo(Block block, int encouragement, int flammability);
}
