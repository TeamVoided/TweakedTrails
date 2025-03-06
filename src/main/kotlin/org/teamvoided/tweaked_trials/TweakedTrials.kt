package org.teamvoided.tweaked_trials

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object TweakedTrials {
    const val MODID = "tweaked_trials"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(TweakedTrials::class.simpleName)

    fun init() {
        log.info("Hello from Common")
        TTParticles.init()
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
