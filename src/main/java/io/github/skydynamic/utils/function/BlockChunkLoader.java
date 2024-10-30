package io.github.skydynamic.utils.function;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.TicketType;

import java.util.Comparator;

public class BlockChunkLoader {
    public static final TicketType<ChunkPos>
        BLOCK_LOADER = TicketType.create
        (
            "block_loader", Comparator.comparingLong(ChunkPos::toLong),
            300
        );
}
