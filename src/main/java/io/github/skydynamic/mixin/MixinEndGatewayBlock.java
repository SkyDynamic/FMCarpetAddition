package io.github.skydynamic.mixin;

import io.github.skydynamic.FuckMojangCarpetAdditionSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndGatewayBlock;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndGatewayBlock.class)
public abstract class MixinEndGatewayBlock {
    @Shadow
    private static Vec3 calculateExitMovement(Entity entity) {
        return null;
    }

    @Inject(
        method = "getPortalDestination",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/portal/DimensionTransition;<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;FFLnet/minecraft/world/level/portal/DimensionTransition$PostDimensionTransition;)V",
            shift = At.Shift.BEFORE
        ),
        cancellable = true
    )
    private void injectGetPortalDestination(
        ServerLevel serverLevel,
        Entity entity,
        BlockPos blockPos,
        CallbackInfoReturnable<DimensionTransition> cir
    ) {
        TheEndGatewayBlockEntity blockEntity = (TheEndGatewayBlockEntity) serverLevel.getBlockEntity(blockPos);
        Vec3 vec3 = blockEntity.getPortalPosition(serverLevel, blockPos);
        DimensionTransition dimensionTransition = new DimensionTransition(
            serverLevel,
            vec3,
            calculateExitMovement(entity),
            entity.getYRot(),
            entity.getXRot(),
            DimensionTransition.DO_NOTHING
        );
        if (FuckMojangCarpetAdditionSettings.endGatewayDoNotAddLoadTicket.equals("all")) {
            cir.setReturnValue(dimensionTransition);
        } else if (FuckMojangCarpetAdditionSettings.endGatewayDoNotAddLoadTicket.equals("bone_block")) {
            BlockState blockState = serverLevel.getBlockState(blockPos.below());
            if (blockState.getBlock() == Blocks.BONE_BLOCK) {
                cir.setReturnValue(dimensionTransition);
            }
        }
    }
}
