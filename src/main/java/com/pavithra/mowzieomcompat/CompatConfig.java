package com.pavithra.mowzieomcompat;

public final class CompatConfig {
    private CompatConfig() {}

    // Keep it dead simple: hardcoded defaults. (If you want a real Forge config file, tell me and I’ll add it.)
    public static final double DETECTION_RADIUS_BLOCKS = 64.0;
    public static final int FADE_TICKS = 40; // 2 seconds at 20 TPS
    public static final int BOSS_STICKY_TICKS = 40; // keep playing briefly after boss leaves range

    /**
     * If a boss uses multiple internal "phases" (e.g., Sculptor), this keeps the boss music stable even if
     * the entity temporarily de-spawns or becomes non-targetable for a moment.
     */
    public static final boolean INCLUDE_SCULPTOR = true;
}
