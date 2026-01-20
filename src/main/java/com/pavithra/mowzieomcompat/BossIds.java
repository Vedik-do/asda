package com.pavithra.mowzieomcompat;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Set;

/**
 * Mowzie's Mobs 1.7.3 (MC 1.20.1) boss entity IDs we care about.
 */
public final class BossIds {
    private BossIds() {}

    // Confirmed from Mowzie's assets/lang keys:
    // entity.mowziesmobs.<id>
    public static final ResourceLocation FROSTMAW = new ResourceLocation("mowziesmobs", "frostmaw");
    public static final ResourceLocation FERROUS_WROUGHTNAUT = new ResourceLocation("mowziesmobs", "ferrous_wroughtnaut");
    public static final ResourceLocation UMVUTHI = new ResourceLocation("mowziesmobs", "umvuthi");
    public static final ResourceLocation SCULPTOR = new ResourceLocation("mowziesmobs", "sculptor");

    public static final Set<ResourceLocation> BOSSES = Set.of(
            FROSTMAW,
            FERROUS_WROUGHTNAUT,
            UMVUTHI,
            SCULPTOR
    );

    // Each boss maps to one of OUR sound event IDs.
    public static final Map<ResourceLocation, ResourceLocation> BOSS_TO_SOUND = Map.of(
            FROSTMAW, new ResourceLocation(MowzieOMCompat.MODID, "boss_frostmaw"),
            FERROUS_WROUGHTNAUT, new ResourceLocation(MowzieOMCompat.MODID, "boss_wroughtnaut"),
            UMVUTHI, new ResourceLocation(MowzieOMCompat.MODID, "boss_umvuthi"),
            SCULPTOR, new ResourceLocation(MowzieOMCompat.MODID, "boss_sculptor")
    );

    /**
     * Alias used by older code paths in this project.
     */
    public static final Map<ResourceLocation, ResourceLocation> SOUND_BY_BOSS = BOSS_TO_SOUND;
}
