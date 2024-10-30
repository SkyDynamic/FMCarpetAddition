package io.github.skydynamic.mixin;

import io.github.skydynamic.FuckMojangCarpetAdditionSettings;
import io.github.skydynamic.utils.EnderPearlChunkLoader;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public abstract class MixinThrownEnderpearl extends ThrowableItemProjectile {
    @Unique
    private long ticketTimer = 0L;

    protected MixinThrownEnderpearl(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "onHit",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/ThrownEnderpearl;discard()V"
        )
    )
    private void onHitDiscard(CallbackInfo ci) {
        ThrownEnderpearl thrownEnderpearl = (ThrownEnderpearl) (Object) this;
        if (FuckMojangCarpetAdditionSettings.enderPearlChunkLoader) {
            EnderPearlChunkLoader.deregisterEnderPearl(thrownEnderpearl);
        }
    }

    @Inject(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/ThrownEnderpearl;discard()V"
        )
    )
    private void onTickDiscard(CallbackInfo ci) {
        ThrownEnderpearl thrownEnderpearl = (ThrownEnderpearl) (Object) this;
        if (FuckMojangCarpetAdditionSettings.enderPearlChunkLoader) {
            EnderPearlChunkLoader.registerEnderPearl(thrownEnderpearl);
        }
    }

    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onTick(CallbackInfo ci) {
        ThrownEnderpearl thrownEnderpearl = (ThrownEnderpearl) (Object) this;
        if (FuckMojangCarpetAdditionSettings.enderPearlChunkLoader) {
            int i;
            int j;
            Entity entity;
            label30:
            {
                i = SectionPos.blockToSectionCoord(thrownEnderpearl.position().x());
                j = SectionPos.blockToSectionCoord(thrownEnderpearl.position().z());
                entity = thrownEnderpearl.getOwner();
                if (entity instanceof ServerPlayer serverPlayer) {
                    if (!entity.isAlive() && serverPlayer.serverLevel().getGameRules().getBoolean(GameRules.RULE_ENDER_PEARLS_VANISH_ON_DEATH)) {
                        thrownEnderpearl.discard();
                        break label30;
                    }
                }

                super.tick();
            }

            if (thrownEnderpearl.isAlive()) {
                BlockPos blockPos = BlockPos.containing(thrownEnderpearl.position());
                if ((--this.ticketTimer <= 0L || i != SectionPos.blockToSectionCoord(blockPos.getX()) || j != SectionPos.blockToSectionCoord(blockPos.getZ())) && entity instanceof ServerPlayer serverPlayer2) {
                    this.ticketTimer = EnderPearlChunkLoader.registerAndUpdateEnderPearlTicket(thrownEnderpearl);
                }
            }
            ci.cancel();
        }
    }
}
