package org.teamvoided.tweaked_trials;

import java.util.Map;
import java.util.UUID;

public interface VaultServerDataAccess {

    Map<UUID, Long> tweakedTrails$getPlayerCooldowns();
}
