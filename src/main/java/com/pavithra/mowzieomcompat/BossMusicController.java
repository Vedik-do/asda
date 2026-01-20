package com.pavithra.mowzieomcompat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Method;

public final class BossMusicController {
    private BossMusicController() {}

    private static BossMusicInstance current;
    private static ResourceLocation currentSoundId;
    private static int stickyTicksLeft = 0;

    public static boolean isBossMusicActive() {
        return current != null;
    }

    public static ResourceLocation getCurrentSoundId() {
        return currentSoundId;
    }

    /**
     * @param desiredSoundId Our sound event ID to play, or null when no boss is currently detected.
     * @param bossEntity     The actual boss entity object (from Mowzie's Mobs) when available; used only for best-effort
     *                       suppression of Mowzie's built-in boss music.
     */
    public static void tick(ResourceLocation desiredSoundId, Object bossEntity) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        if (desiredSoundId != null) {
            stickyTicksLeft = CompatConfig.BOSS_STICKY_TICKS;
            maybeStopMowzieBossMusic(bossEntity);

            // Crossfade: fade other music down while boss is active.
            MusicDuck.setBossActive(true);

            if (current != null && desiredSoundId.equals(currentSoundId)) {
                MusicDuck.tick();
                return;
            }

            start(desiredSoundId);
            MusicDuck.tick();
            return;
        }

        if (stickyTicksLeft > 0) {
            stickyTicksLeft--;
            MusicDuck.setBossActive(true);
            MusicDuck.tick();
            return;
        }

        // No boss -> restore other music, fade ours out.
        MusicDuck.setBossActive(false);
        MusicDuck.tick();
        stop();
    }

    private static void start(ResourceLocation soundId) {
        Minecraft mc = Minecraft.getInstance();
        SoundManager sm = mc.getSoundManager();

        SoundEvent evt = ForgeRegistries.SOUND_EVENTS.getValue(soundId);
        if (evt == null) {
            // Should never happen if our sounds registered correctly.
            return;
        }

        // Fade out old
        if (current != null) {
            current.startFadeOut();
        }

        BossMusicInstance inst = new BossMusicInstance(evt, CompatConfig.FADE_TICKS);
        current = inst;
        currentSoundId = soundId;
        sm.play(inst);
    }

    private static void stop() {
        if (current != null) {
            current.startFadeOut();
            current = null;
            currentSoundId = null;
        }
    }

    /**
     * Best-effort: ask Mowzie's BossMusicPlayer to stop its own boss music so only our track plays.
     * We do this via reflection to avoid a hard compile dependency.
     */
    private static void maybeStopMowzieBossMusic(Object bossEntity) {
        if (bossEntity == null) return;
        try {
            Class<?> playerCls = Class.forName("com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer");
            // stopBossMusic(MowzieEntity)
            for (Method m : playerCls.getMethods()) {
                if (!m.getName().equals("stopBossMusic")) continue;
                if (m.getParameterCount() != 1) continue;
                if (m.getParameterTypes()[0].isInstance(bossEntity)) {
                    m.invoke(null, bossEntity);
                    return;
                }
            }
        } catch (Throwable ignored) {
        }
    }
}
