package org.teamvoided.tweaked_trials.mixin;

import net.minecraft.block.TrialSpawnerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import java.util.UUID;

@Mixin(TrialSpawnerData.class)
public interface TrialSpawnerDataAccessor {

    @Accessor("currentMobs")
    Set<UUID> getCurrentMobs();
}
