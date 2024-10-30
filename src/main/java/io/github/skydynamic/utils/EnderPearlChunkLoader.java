package io.github.skydynamic.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static net.minecraft.server.level.TicketType.create;

public class EnderPearlChunkLoader {
    private static final HashMap<ServerPlayer, Set<ThrownEnderpearl>> enderPearls = new HashMap<>();

    private static final TicketType<ChunkPos> ENDER_PEARL = create(
        "ender_pearl", Comparator.comparingLong(ChunkPos::toLong), 40
    );

    public static void registerEnderPearl(ThrownEnderpearl thrownEnderpearl) {
        ServerPlayer owner = (ServerPlayer) thrownEnderpearl.getOwner();
        enderPearls.computeIfAbsent(owner, k -> new HashSet<>()).add(thrownEnderpearl);
    }

    public static void deregisterEnderPearl(ThrownEnderpearl thrownEnderpearl) {
        ServerPlayer owner = (ServerPlayer) thrownEnderpearl.getOwner();
        enderPearls.get(owner).remove(thrownEnderpearl);
    }

    public static Set<ThrownEnderpearl> getEnderPearls(ServerPlayer owner) {
        return enderPearls.get(owner);
    }

    public static long registerAndUpdateEnderPearlTicket(ThrownEnderpearl thrownEnderpearl) {
        Level var3 = thrownEnderpearl.level();
        if (var3 instanceof ServerLevel serverLevel) {
            ChunkPos chunkPos = thrownEnderpearl.chunkPosition();
            registerEnderPearl(thrownEnderpearl);
            serverLevel.resetEmptyTime();
            return placeEnderPearlTicket(serverLevel, chunkPos) - 1L;
        } else {
            return 0L;
        }
    }

    public static long placeEnderPearlTicket(ServerLevel serverLevel, ChunkPos chunkPos) {
        serverLevel.getChunkSource().addRegionTicket(ENDER_PEARL, chunkPos, 2, chunkPos);
        return ENDER_PEARL.timeout();
    }
}
