package io.github.skydynamic.utils.function;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.TicketType;


public class BlockChunkLoader {
    public static final TicketType
        BLOCK_LOADER = new TicketType(300, TicketType.FLAG_LOADING | TicketType.FLAG_SIMULATION);
}
