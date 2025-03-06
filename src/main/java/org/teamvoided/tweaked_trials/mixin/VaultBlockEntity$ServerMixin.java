package org.teamvoided.tweaked_trials.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.unmapped.C_cudfnjix;
import net.minecraft.unmapped.C_czyoqmgb;
import net.minecraft.unmapped.C_nsbycoiv;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.tweaked_trials.VaultServerDataAccess;

import java.util.Map;
import java.util.UUID;

@Mixin(VaultBlockEntity.C_vucktnfd.class)
public class VaultBlockEntity$ServerMixin {

    @Inject(method = "method_56756", at = @At("HEAD"))
    private static void tick(ServerWorld world, BlockPos pos, BlockState state, C_cudfnjix config, C_nsbycoiv serverData, C_czyoqmgb sharedData, CallbackInfo ci) {
        if (world.getBlockEntity(pos) instanceof VaultBlockEntity blockEntity) {
            VaultServerDataAccess serverDataAccess = (VaultServerDataAccess) serverData;
            Map<UUID, Long> playerCooldowns = serverDataAccess.tweakedTrails$getPlayerCooldowns();

            Map.copyOf(playerCooldowns).forEach((uuid, time) -> {
                if (world.getTime() >= time) {
                    playerCooldowns.remove(uuid);
                }
            });
        }
    }
}
