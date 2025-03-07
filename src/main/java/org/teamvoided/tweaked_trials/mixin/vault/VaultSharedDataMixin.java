package org.teamvoided.tweaked_trials.mixin.vault;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.unmapped.C_cudfnjix;
import net.minecraft.unmapped.C_czyoqmgb;
import net.minecraft.unmapped.C_nsbycoiv;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.tweaked_trials.misc.VaultServerDataAccess;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Debug(export = true)
@Mixin(C_czyoqmgb.class)
public abstract class VaultSharedDataMixin {
    @Shadow
    private Set<UUID> field_48897;

    @Shadow
    protected abstract void method_56800();

    @Inject(method = "method_56788", at = @At("HEAD"))
    private void tick(ServerWorld world, BlockPos pos, C_nsbycoiv rawServerData, C_cudfnjix config, double radius, CallbackInfo ci) {
        var serverData = ((VaultServerDataAccess) rawServerData);
        var set = config.comp_2310().detect(world, config.entitySelector(), pos, radius, false)
                .stream()
                .filter(uuid -> {
                    var time = serverData.tweakedTrails$getPlayerCooldowns().get(uuid);
                    if (time != null && world.getTime() >= time) {
                        serverData.tweakedTrails$getPlayerCooldowns().remove(uuid);
                        time = null;
                    }
                    return time != null;
                })
                .collect(Collectors.toSet());
        if (!this.field_48897.equals(set)) {
            this.field_48897 = set;
            this.method_56800();
        }
    }
}
