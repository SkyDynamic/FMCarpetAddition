package io.github.skydynamic.mixin;

import io.github.skydynamic.FuckMojangCarpetAdditionSettings;
import io.github.skydynamic.utils.function.BlockChunkLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBaseBlock.class)
public class MixinPistonBaseBlock {
    @Redirect(
        method = "triggerEvent",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"
        )
    )
    private boolean injectTriggerEvent(Level level, BlockPos pos, boolean bl) {
        if (FuckMojangCarpetAdditionSettings.fixExtendedPistonDeleteFrontBlock) {
            if (level.getBlockState(pos).getBlock() == Blocks.PISTON_HEAD) {
                return level.removeBlock(pos, false);
            }
            return false;
        } else {
            return level.removeBlock(pos, bl);
        }
    }

    @Inject(
        method = "triggerEvent",
        at = @At("HEAD")
    )
    private void onTriggerEvent(
        BlockState blockState,
        Level level,
        BlockPos blockPos,
        int i,
        int j,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (
            FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("bone_block")
            || FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("all")
            && !level.isClientSide
        ) {
            Direction direction = blockState.getValue(DirectionalBlock.FACING);
            BlockState pistonBlock = level.getBlockState(blockPos.above(1));
            if (
                FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("bone_block")
                || FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("all")
                && (pistonBlock.getBlock() == Blocks.BONE_BLOCK)
            ) {
                ChunkPos chunkPos = new ChunkPos(blockPos.relative(direction));
                ((ServerLevel) level).getChunkSource().addRegionTicket(BlockChunkLoader.BLOCK_LOADER, chunkPos, 3, chunkPos);
            }
        }

        if (
            FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("bedrock")
            || FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("all")
            && !level.isClientSide
        ) {
            Direction direction = blockState.getValue(DirectionalBlock.FACING);
            BlockState pistonBlock = level.getBlockState(blockPos.below(1));
            if (
                FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("bedrock")
                || FuckMojangCarpetAdditionSettings.pistonBlockChunkLoader.equals("all")
                && (pistonBlock.getBlock() == Blocks.BEDROCK)
            ) {
                ChunkPos chunkPos = new ChunkPos(blockPos.relative(direction).below(1));
                ((ServerLevel) level).getChunkSource().addRegionTicket(BlockChunkLoader.BLOCK_LOADER, chunkPos, 3, chunkPos);
            }
        }
    }
}
