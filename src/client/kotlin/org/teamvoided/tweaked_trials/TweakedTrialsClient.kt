package org.teamvoided.tweaked_trials

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import org.teamvoided.tweaked_trials.TweakedTrials.log
import org.teamvoided.tweaked_trials.particle.SpawnerBeamParticleEmitter

@Suppress("unused")
object TweakedTrialsClient {

    fun init() {
        ParticleFactoryRegistry.getInstance().register(TTParticles.SPAWNER_BEAM_EMITTER, SpawnerBeamParticleEmitter.Factory())
    }
}
