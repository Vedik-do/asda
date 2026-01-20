package com.pavithra.mowzieomcompat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * SoundEvent registration for our boss tracks.
 */
public final class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MowzieOMCompat.MODID);

    public static final RegistryObject<SoundEvent> BOSS_FROSTMAW = register("boss_frostmaw");
    public static final RegistryObject<SoundEvent> BOSS_WROUGHTNAUT = register("boss_wroughtnaut");
    public static final RegistryObject<SoundEvent> BOSS_UMVUTHI = register("boss_umvuthi");
    public static final RegistryObject<SoundEvent> BOSS_SCULPTOR = register("boss_sculptor");

    private static RegistryObject<SoundEvent> register(String id) {
        return REGISTER.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MowzieOMCompat.MODID, id)));
    }

    private ModSounds() {}
}
