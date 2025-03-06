package org.teamvoided.tweaked_trails

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import org.teamvoided.tweaked_trails.Template.log

@Suppress("unused")
object TemplateClient {

    fun init() {
        log.info("Hello from Client")
        ParticleFactoryRegistry.getInstance().register(TTParticles.SPAWNER_BEAM_EMITTER, SpawnerBeamParticleEmitter.Factory())
    }
}
