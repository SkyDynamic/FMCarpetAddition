package io.github.skydynamic.mixin;

import io.github.skydynamic.FuckMojangCarpetAdditionSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
}
