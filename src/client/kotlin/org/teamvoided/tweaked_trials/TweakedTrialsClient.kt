package org.teamvoided.tweaked_trials

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import org.teamvoided.tweaked_trials.TweakedTrials.log

@Suppress("unused")
object TweakedTrialsClient {

    fun init() {
        log.info("Hello from Client")
        ParticleFactoryRegistry.getInstance().register(TTParticles.SPAWNER_BEAM_EMITTER, SpawnerBeamParticleEmitter.Factory())
    }
}
