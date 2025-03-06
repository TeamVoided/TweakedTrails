package org.teamvoided.tweaked_trials

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.ParticleType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.teamvoided.tweaked_trials.TweakedTrials.id

object TTParticles {

    val SPAWNER_BEAM_EMITTER: ParticleType<SpawnerBeamEmitterParticleEffect> = FabricParticleTypes.complex(
        SpawnerBeamEmitterParticleEffect.CODEC, SpawnerBeamEmitterParticleEffect.PACKET_CODEC
    )

    fun init() {
        Registry.register(Registries.PARTICLE_TYPE, id("spawner_beam_emitter"), SPAWNER_BEAM_EMITTER)
    }
}