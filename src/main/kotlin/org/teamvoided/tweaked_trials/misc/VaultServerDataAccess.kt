package org.teamvoided.tweaked_trials.misc

import java.util.*

interface VaultServerDataAccess {
    fun `tweakedTrails$getPlayerCooldowns`(): Map<UUID, Long>
}
