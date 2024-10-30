package io.github.skydynamic.mixin;

import io.github.skydynamic.FuckMojangCarpetAdditionSettings;
import io.github.skydynamic.utils.function.BlockChunkLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(NoteBlock.class)
public class MixinNoteBlock {
    @Inject(
        method = "playNote",
        at = @At("HEAD")
    )
    private void onPlayNote(
        Entity entity,
        BlockState blockState,
        Level level,
        BlockPos pos,
        CallbackInfo ci
    ) {
        if (FuckMojangCarpetAdditionSettings.noteBlockChunkLoader.equals("note_block") && !level.isClientSide) {
            ChunkPos chunkPos = new ChunkPos(pos);
            ((ServerLevel) level).getChunkSource().addRegionTicket(BlockChunkLoader.BLOCK_LOADER, chunkPos, 3, chunkPos);
        }
        if (FuckMojangCarpetAdditionSettings.noteBlockChunkLoader.equals("bone_block") && !level.isClientSide) {
            BlockState noteBlock = level.getBlockState(pos.above(1));
            if (
                Objects.equals(FuckMojangCarpetAdditionSettings.noteBlockChunkLoader, "bone_block")
                && (noteBlock.getBlock() == Blocks.BONE_BLOCK)
            ) {
                ChunkPos chunkPos = new ChunkPos(pos.above(1));
                ((ServerLevel) level).getChunkSource().addRegionTicket(BlockChunkLoader.BLOCK_LOADER, chunkPos, 3, chunkPos);
            }
        }
        if (FuckMojangCarpetAdditionSettings.noteBlockChunkLoader.equals("wither_skeleton_skull") && !level.isClientSide) {
            BlockState noteBlock = level.getBlockState(pos.above(1));
            if (
                FuckMojangCarpetAdditionSettings.noteBlockChunkLoader.equals("wither_skeleton_skull")
                && (noteBlock.getBlock() == Blocks.WITHER_SKELETON_SKULL)
                || (noteBlock.getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL)
            ) {
                ChunkPos chunkPos = new ChunkPos(pos.above(1));
                ((ServerLevel) level).getChunkSource().addRegionTicket(BlockChunkLoader.BLOCK_LOADER, chunkPos, 3, chunkPos);
            }
        }
    }
}
