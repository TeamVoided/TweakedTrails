package org.teamvoided.tweaked_trials

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.tweaked_trials.misc.TTConfig

@Suppress("unused")
object TweakedTrials {
    const val MODID = "tweaked_trials"

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::TTConfig)

    @JvmField
    val log: Logger = LoggerFactory.getLogger(TweakedTrials::class.simpleName)
    fun init() {
        log.info("Your trials will be tweaking now!")
        TTParticles.init()
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
