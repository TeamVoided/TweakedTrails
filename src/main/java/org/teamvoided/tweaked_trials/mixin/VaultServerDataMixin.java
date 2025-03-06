package org.teamvoided.tweaked_trials.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.unmapped.C_nsbycoiv;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.tweaked_trials.VaultServerDataAccess;

import java.util.*;

@Mixin(C_nsbycoiv.class)
public abstract class VaultServerDataMixin implements VaultServerDataAccess {

    @Shadow
    protected abstract void method_56786();

    @Shadow
    @Final
    private Set<UUID> field_48888;

    @Unique
    private final Map<UUID, Long> tweakedTrails$playerCooldowns = new HashMap<>();

    @Inject(method = "<init>(Ljava/util/Set;JLjava/util/List;I)V", at = @At("TAIL"))
    private void init(Set<UUID> rewardedPlayers, long stateUpdatingResumesAt, List<ItemStack> ejectedItems, int totalEjections, CallbackInfo ci) {
        field_48888.clear();
    }

    @ModifyReturnValue(method = "method_56773", at = @At("RETURN"))
    private Set<UUID> replaceGetter(Set<UUID> uuids) {
        return tweakedTrails$playerCooldowns.keySet();
    }

    @ModifyReturnValue(method = "method_56769", at = @At("RETURN"))
    private boolean replaceContains(boolean original, PlayerEntity player) {
        return tweakedTrails$playerCooldowns.containsKey(player.getUuid());
    }

    @Inject(method = "method_56775", at = @At("HEAD"), cancellable = true)
    private void replaceAddRewardedPlayer(PlayerEntity player, CallbackInfo ci) {
        tweakedTrails$playerCooldowns.put(player.getUuid(), player.getWorld().getTime() + 6000);
        if (tweakedTrails$playerCooldowns.size() > 128) {
            tweakedTrails$playerCooldowns.keySet().forEach(tweakedTrails$playerCooldowns::remove);
        }

        method_56786();
        ci.cancel();
    }

    @Inject(method = "method_56771", at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"), cancellable = true)
    private void replaceCopy(C_nsbycoiv serverData, CallbackInfo ci) {
        tweakedTrails$playerCooldowns.clear();
        tweakedTrails$playerCooldowns.putAll(((VaultServerDataAccess) serverData).tweakedTrails$getPlayerCooldowns());
        field_48888.clear();
        ci.cancel();
    }

    @Override
    public Map<UUID, Long> tweakedTrails$getPlayerCooldowns() {
        return tweakedTrails$playerCooldowns;
    }
}
