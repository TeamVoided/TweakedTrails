package org.teamvoided.tweaked_trials.mixin;

import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.HolderLookup;
import net.minecraft.unmapped.C_nsbycoiv;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.tweaked_trials.VaultServerDataAccess;

import java.util.Map;
import java.util.UUID;

@Mixin(VaultBlockEntity.class)
public class VaultBlockEntityMixin {

    @Shadow
    @Final
    private C_nsbycoiv field_48866;

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void writeCooldowns(NbtCompound nbt, HolderLookup.Provider lookupProvider, CallbackInfo ci) {
        Map<UUID, Long> playerCooldowns = ((VaultServerDataAccess) field_48866).tweakedTrails$getPlayerCooldowns();
        if (!playerCooldowns.isEmpty()) {
            NbtList cooldowns = new NbtList();
            playerCooldowns.forEach((key, value) -> {
                NbtCompound compound = new NbtCompound();
                compound.putUuid("uuid", key);
                compound.putLong("time", value);
                cooldowns.add(compound);
            });
            nbt.put("tweakedTrails$playerCooldowns", cooldowns);
        }
    }

    @Inject(method = "readNbtImpl", at = @At("TAIL"))
    private void readCooldowns(NbtCompound nbt, HolderLookup.Provider lookupProvider, CallbackInfo ci) {
        if (nbt.contains("tweakedTrails$playerCooldowns")) {
            Map<UUID, Long> playerCooldowns = ((VaultServerDataAccess) field_48866).tweakedTrails$getPlayerCooldowns();
            NbtList cooldowns = nbt.getList("tweakedTrails$playerCooldowns", NbtElement.COMPOUND_TYPE);

            for (int i = 0; i < cooldowns.size(); i++) {
                NbtCompound compound = cooldowns.getCompound(i);
                playerCooldowns.put(compound.getUuid("uuid"), compound.getLong("time"));
            }
        }
    }
}
