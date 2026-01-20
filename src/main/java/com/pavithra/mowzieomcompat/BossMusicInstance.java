package com.pavithra.mowzieomcompat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * A looping boss music instance with a clean fade-in / fade-out.
 */
public final class BossMusicInstance extends AbstractTickableSoundInstance {
    private final int fadeTicks;
    private boolean fadingOut = false;
    private int fadeInAge = 0;
    private int fadeOutAge = 0;

    public BossMusicInstance(SoundEvent event, int fadeTicks) {
        super(event, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        this.fadeTicks = Math.max(1, fadeTicks);
        this.looping = true;
        this.delay = 0;
        this.relative = true;
        this.volume = 0.0F;
        this.pitch = 1.0F;
    }

    /**
     * Starts fading the music out. When fade completes, the sound stops.
     */
    public void startFadeOut() {
        if (!this.fadingOut) {
            this.fadingOut = true;
            this.fadeOutAge = 0;
        }
    }

    public boolean isFadingOut() {
        return fadingOut;
    }

    @Override
    public void tick() {
        // Keep the sound anchored to the player (relative sound).
        var player = Minecraft.getInstance().player;
        if (player == null) {
            this.startFadeOut();
        }

        if (fadingOut) {
            fadeOutAge++;
            float t = 1.0F - (fadeOutAge / (float) fadeTicks);
            this.volume = Math.max(0.0F, t);
            if (fadeOutAge >= fadeTicks) {
                this.stop(); // protected in superclass; safe to call here.
            }
        } else {
            fadeInAge++;
            float t = Math.min(1.0F, fadeInAge / (float) fadeTicks);
            this.volume = t;
        }
    }
}
