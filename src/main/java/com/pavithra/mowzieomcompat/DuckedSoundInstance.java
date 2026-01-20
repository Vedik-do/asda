package com.pavithra.mowzieomcompat;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.resources.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * Wraps a SoundInstance and scales its volume by {@link MusicDuck#getMultiplier()}.
 *
 * We implement TickableSoundInstance so tickable sounds (including OverhauledMusic's fading tracks)
 * keep their internal logic.
 */
public final class DuckedSoundInstance implements TickableSoundInstance {
    private final SoundInstance delegate;

    public DuckedSoundInstance(SoundInstance delegate) {
        this.delegate = delegate;
    }

    public SoundInstance getDelegate() {
        return delegate;
    }

    @Override
    public ResourceLocation getLocation() {
        return delegate.getLocation();
    }

    @Override
    public SoundEvent getSoundEvent() {
        return delegate.getSoundEvent();
    }

    @Override
    public SoundSource getSource() {
        return delegate.getSource();
    }

    @Override
    public boolean isLooping() {
        return delegate.isLooping();
    }

    @Override
    public boolean isRelative() {
        return delegate.isRelative();
    }

    @Override
    public int getDelay() {
        return delegate.getDelay();
    }

    @Override
    public float getVolume() {
        return delegate.getVolume() * MusicDuck.getMultiplier();
    }

    @Override
    public float getPitch() {
        return delegate.getPitch();
    }

    @Override
    public double getX() {
        return delegate.getX();
    }

    @Override
    public double getY() {
        return delegate.getY();
    }

    @Override
    public double getZ() {
        return delegate.getZ();
    }

    @Override
    public SoundInstance.Attenuation getAttenuation() {
        return delegate.getAttenuation();
    }

    @Override
    public boolean canStartSilent() {
        return delegate.canStartSilent();
    }

    @Override
    public boolean canPlaySound() {
        return delegate.canPlaySound();
    }

    @Override
    public void tick() {
        if (delegate instanceof TickableSoundInstance t) {
            t.tick();
        }
    }

    @Override
    public boolean isStopped() {
        if (delegate instanceof TickableSoundInstance t) {
            return t.isStopped();
        }
        return false;
    }

    @Override
    public Sound getSound() {
        return delegate.getSound();
    }

    @Override
    public WeighedSoundEvents resolve(SoundManager manager) {
        return delegate.resolve(manager);
    }
}
