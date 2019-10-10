package com.stal111.forbidden_arcanus.util;

import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Collection;

public class VoxelShapeHelper {

    public static VoxelShape combineAll(Collection<VoxelShape> shapes) {
        VoxelShape result = VoxelShapes.empty();
        for (VoxelShape shape : shapes) {
            result = VoxelShapes.combine(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }

    public static VoxelShape combineAll(VoxelShape... shapes) {
        VoxelShape result = VoxelShapes.empty();
        for (VoxelShape shape : shapes) {
            result = VoxelShapes.combine(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }
}
