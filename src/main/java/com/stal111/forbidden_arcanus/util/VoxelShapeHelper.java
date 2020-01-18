package com.stal111.forbidden_arcanus.util;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class VoxelShapeHelper {

    public static VoxelShape combineAll(Collection<VoxelShape> shapes) {
        VoxelShape result = VoxelShapes.empty();
        for (VoxelShape shape : shapes) {
            result = VoxelShapes.combineAndSimplify(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }

    public static VoxelShape combineAll(VoxelShape... shapes) {
        VoxelShape result = VoxelShapes.empty();
        for (VoxelShape shape : shapes) {
            result = VoxelShapes.combineAndSimplify(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }

    public static VoxelShape[] getRotatedShapes(VoxelShape shape) {
        VoxelShape[] shapes = new VoxelShape[4];
        shapes[0] = shape;
        shapes[1] = rotateShape(shape, RotationAmount.NINETY);
        shapes[2] = rotateShape(shape, RotationAmount.HUNDRED_EIGHTY);
        shapes[3] = rotateShape(shape, RotationAmount.TWO_HUNDRED_SEVENTY);
        return shapes;
    }

    public static VoxelShape rotateShape(VoxelShape shape, RotationAmount rotationAmount) {
        Set<VoxelShape> rotatedShapes = new HashSet<>();

        shape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
            x1 = (x1 * 16) - 8; x2 = (x2 * 16) - 8;
            z1 = (z1 * 16) - 8; z2 = (z2 * 16) - 8;

            if (rotationAmount == RotationAmount.NINETY) {
                rotatedShapes.add(Block.makeCuboidShape(8 - z1, y1 * 16, 8 + x1, 8 - z2, y2 * 16, 8 + x2));
            } else if (rotationAmount == RotationAmount.HUNDRED_EIGHTY) {
                rotatedShapes.add(Block.makeCuboidShape(8 - x1, y1 * 16, 8 - z1, 8 - x2, y2 * 16, 8 - z2));
            } else if (rotationAmount == RotationAmount.TWO_HUNDRED_SEVENTY) {
                rotatedShapes.add(Block.makeCuboidShape(8 + z1, y1 * 16, 8 - x1, 8 + z2, y2 * 16, 8 - x2));
            }
        });
        return rotatedShapes.stream().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }

    public static VoxelShape rotateShapeAxis(VoxelShape shape, Direction.Axis axis) {
        Set<VoxelShape> rotatedShapes = new HashSet<>();
        shape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
            if (axis == Direction.Axis.X) {
                rotatedShapes.add(Block.makeCuboidShape(y1 * 16, x1 * 16, z1 * 16, y2 * 16, x2 * 16, z2 * 16));
            } else if (axis == Direction.Axis.Z) {
                rotatedShapes.add(rotateShape(Block.makeCuboidShape(x1 * 16, z1 * 16, y1 * 16, x2 * 16, z2 * 16, y2 * 16), RotationAmount.HUNDRED_EIGHTY));
            } else {
                rotatedShapes.add(Block.makeCuboidShape(x1 * 16, y1 * 16, z1 * 16, x2 * 16, y2 * 16, z2 * 16));
            }
        });
        return rotatedShapes.stream().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }

    public static VoxelShape add(double x1, double y1, double z1, double x2, double y2, double z2, VoxelShape... shapes) {
        Set<VoxelShape> result = new HashSet<>();
        for (VoxelShape shape : shapes) {
            shape.forEachBox((x, y, z, x3, y3, z3) -> {
                x = (x * 16); x3 = (x3 * 16);
                y = (y * 16); y3 = (y3 * 16);
                z = (z * 16); z3 = (z3 * 16);

                result.add(Block.makeCuboidShape(x + x1, y + y1, z + z1, x3 + x2, y3 + y2, z3 + z2));
            });
        }
        return result.stream().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }

    public enum RotationAmount {
        NINETY,
        HUNDRED_EIGHTY,
        TWO_HUNDRED_SEVENTY
    }
}
