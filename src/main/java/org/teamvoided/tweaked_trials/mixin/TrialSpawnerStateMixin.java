package org.teamvoided.tweaked_trials.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.enums.TrialSpawnerState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.TrialSpawnerLogic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.tweaked_trials.particle.SpawnerBeamEmitterParticleEffect;

import java.util.List;
import java.util.UUID;

@Mixin(TrialSpawnerState.class)
public abstract class TrialSpawnerStateMixin {

    @Shadow
    @Final
    private boolean spawnCapable;

    @Inject(method = "tick", at = @At("RETURN"))
    public void tickEntityConnection(BlockPos pos, TrialSpawnerLogic logic, ServerWorld world, CallbackInfoReturnable<TrialSpawnerState> cir) {
        RandomGenerator random = world.random;
        if (random.nextInt(100) == 0 && spawnCapable) {
            List<UUID> currentMobs = ((TrialSpawnerDataAccessor) logic.getData()).getCurrentMobs().stream().toList();

            if (!currentMobs.isEmpty()) {
                Entity entity = world.getEntity(currentMobs.get(random.nextInt(currentMobs.size())));

                if (entity != null) {
                    world.spawnParticles(
                            new SpawnerBeamEmitterParticleEffect(pos.ofCenter(), logic.isOminous()),
                            entity.getX() + (random.nextDouble() - 0.5),
                            entity.getBodyY(0.5) + (random.nextDouble() - 0.5),
                            entity.getZ() + (random.nextDouble() - 0.5),
                            0, 0, 0, 0, 1
                    );
                }
            }
        }
    }

    @ModifyReturnValue(method = "getEntityRotationSpeed", at = @At("RETURN"))
    private double modifyRotationSpeed(double speed) {
        return speed < 0 ? 100 : speed;
    }

    @ModifyReturnValue(method = "hasRotatingEntity", at = @At("RETURN"))
    private boolean setAlwaysRotating(boolean original) {
        return true;
    }
}