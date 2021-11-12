package com.stal111.forbidden_arcanus.util;

import com.google.common.collect.Maps;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.LevelReader;
import net.minecraft.server.level.ServerLevel;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ModUtils {

    protected static final Random random = new Random();

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, path);
    }

    public static boolean isShiftDown() {
        return InputConstants.isKeyDown(GLFW.glfwGetCurrentContext(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(GLFW.glfwGetCurrentContext(), GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean applyBonemeal(ItemStack stack, ServerLevel worldIn, BlockPos pos, net.minecraft.world.entity.player.Player player) {
        BlockState blockstate = worldIn.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof BonemealableBlock) {
            BonemealableBlock igrowable = (BonemealableBlock) blockstate.getBlock();
            if (igrowable.isValidBonemealTarget(worldIn, pos, blockstate, worldIn.isClientSide)) {
                if (!worldIn.isClientSide) {
                    if (igrowable.isBonemealSuccess(worldIn, worldIn.random, pos, blockstate)) {
                        igrowable.performBonemeal(worldIn, worldIn.random, pos, blockstate);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static void addStrippable(Block block, Block strippedBlock) {
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(block, strippedBlock);
    }

    public static List<?> removeLastFromList(List<?> list) {
        ArrayList list1 = new ArrayList();
        for (int i = 0; i < list.size() - 1; i++) {
            list1.add(list.get(i));
        }
        return list1;
    }

    public static void shrinkAndAddStack(Player player, InteractionHand hand, ItemStack stack, ItemStack newStack) {
        stack.shrink(1);
        if (stack.isEmpty()) {
            player.setItemInHand(hand, newStack);
        } else if (!player.getInventory().add(newStack)) {
            player.drop(newStack, false);
        }
    }

    public static PlayerTeam createTeam(Scoreboard scoreboard, String name, ChatFormatting color) {
        if (scoreboard.getTeamNames().contains(name)) {
            return scoreboard.getPlayerTeam(name);
        } else {
            PlayerTeam team = scoreboard.addPlayerTeam(name);
            team.setDisplayName(new TextComponent(name));
            team.setColor(color);
            return team;
        }
    }

    public static void removeTeam(Scoreboard scoreboard, PlayerTeam team) {
        if (scoreboard.getTeamNames().contains(team.getName())) {
            scoreboard.removePlayerTeam(team);
        }
    }

    public static Collection<Recipe<?>> getRecipesByOutput(ItemStack targetOutput, Collection<RecipeSerializer<?>> recipeSerializer) {
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream()
                .filter(r -> {
                    boolean flag = false;
                    for (RecipeSerializer<?> serializer : recipeSerializer) {
                        if (serializer == r.getSerializer()) {
                            flag = true;
                        }
                    }
                    return !r.isSpecial() && flag && ItemStack.isSameIgnoreDurability(targetOutput, r.getResultItem());
                }).collect(Collectors.toList());
    }

    public static Collection<Recipe<?>> getCraftingRecipesByOutput(@Nonnull ItemStack targetOutput) {
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream()
                .filter(r -> !r.isSpecial() && (r.getSerializer() == RecipeSerializer.SHAPED_RECIPE || r.getSerializer() == RecipeSerializer.SHAPELESS_RECIPE) && ItemStack.isSameIgnoreDurability(targetOutput, r.getResultItem())).collect(Collectors.toList());
    }

    public static Collection<Recipe<?>> getSmeltingRecipesByOutput(@Nonnull ItemStack targetOutput) {
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream()
                .filter(r -> !r.isSpecial() && r.getSerializer() == RecipeSerializer.SMELTING_RECIPE && ItemStack.isSameIgnoreDurability(targetOutput, r.getResultItem())).collect(Collectors.toList());
    }

    public static boolean hasBlockEnoughSolidSite(VoxelShape shape, LevelReader world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos);
        if (direction == Direction.DOWN && state.is(BlockTags.UNSTABLE_BOTTOM_CENTER)) {
            return false;
        } else {
            return !Shapes.joinIsNotEmpty(state.getBlockSupportShape(world, pos).getFaceShape(direction), shape, BooleanOp.ONLY_SECOND);
        }
    }

    public static Vec3 blockPosToVector(BlockPos pos) {
        return blockPosToVector(pos, 0);
    }

    public static Vec3 blockPosToVector(BlockPos pos, double offset) {
        return new Vec3(pos.getX() + offset, pos.getY() + offset, pos.getZ() + offset);
    }
}
