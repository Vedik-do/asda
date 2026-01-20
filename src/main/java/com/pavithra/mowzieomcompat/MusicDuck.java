package com.pavithra.mowzieomcompat;

/**
 * Global multiplier applied to OTHER music sounds (not our boss track) to create a smooth crossfade.
 *
 * Note: this cannot truly "pause" an already-playing track; it just fades it down to (near) silent and
 * fades it back up later.
 */
public final class MusicDuck {
    private static float multiplier = 1.0F;
    private static float target = 1.0F;

    private MusicDuck() {}

    public static float getMultiplier() {
        return multiplier;
    }

    public static void setBossActive(boolean bossActive) {
        target = bossActive ? 0.0F : 1.0F;
    }

    public static void tick() {
        float step = 1.0F / Math.max(1, CompatConfig.FADE_TICKS);
        if (multiplier < target) {
            multiplier = Math.min(target, multiplier + step);
        } else if (multiplier > target) {
            multiplier = Math.max(target, multiplier - step);
        }
    }
}
