package org.teamvoided.tweaked_trials.misc

import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import org.teamvoided.tweaked_trials.TweakedTrials.MODID
import org.teamvoided.tweaked_trials.TweakedTrials.id

class TTConfig : Config(id(MODID)) {
    @Suppress("unused")
    var vaultGroup = ConfigGroup("vault")
    var vaultCooldown: Long = 1

    @ConfigGroup.Pop
    var timeType = TimeType.HOURS

    @Suppress("unused")
    var spawnerGroup = ConfigGroup("spawner")
    var shouldRotateWhenInactive = true

    var inactiveRotationSpeed = 50
    var spawnBeamParticles = true
    @ConfigGroup.Pop
    @Comment("Smaller means its more likely to spawn beams")
    var beamParticleChance = 100
}

enum class TimeType { TICKS, SECONDS, MINUTES, HOURS }

fun TTConfig.getCooldown(): Long = when (this.timeType) {
    TimeType.TICKS -> vaultCooldown
    TimeType.SECONDS -> vaultCooldown * 20
    TimeType.MINUTES -> vaultCooldown * 20 * 60
    TimeType.HOURS -> vaultCooldown * 20 * 60 * 60
}
