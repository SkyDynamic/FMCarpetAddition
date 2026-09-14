package io.github.skydynamic.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class EnderPearlChunkLoader {
    private static final HashMap<ServerPlayer, Set<ThrownEnderpearl>> enderPearls = new HashMap<>();

    private static final TicketType ENDER_PEARL = TicketType.ENDER_PEARL;

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
        serverLevel.getChunkSource().addTicketWithRadius(ENDER_PEARL, chunkPos, 2);
        return ENDER_PEARL.timeout();
    }
}
