package com.pavithra.mowzieomcompat;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public final class ForgeClientEvents {
    private ForgeClientEvents() {}

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        Entity player = mc.player;

        AABB box = player.getBoundingBox().inflate(CompatConfig.DETECTION_RADIUS_BLOCKS);

        Entity bestBoss = null;
        ResourceLocation bestSound = null;
        double bestDist = Double.MAX_VALUE;

        for (Entity e : mc.level.getEntities(player, box)) {
            if (e == null || e.isRemoved()) continue;
            if (e instanceof LivingEntity le && !le.isAlive()) continue;

            ResourceLocation typeId = ForgeRegistries.ENTITY_TYPES.getKey(e.getType());
            if (typeId == null) continue;

            if (!CompatConfig.INCLUDE_SCULPTOR && typeId.equals(BossIds.SCULPTOR)) continue;

            ResourceLocation soundId = BossIds.SOUND_BY_BOSS.get(typeId);
            if (soundId == null) continue;

            double d = e.distanceToSqr(player);
            if (d < bestDist) {
                bestDist = d;
                bestBoss = e;
                bestSound = soundId;
            }
        }

        BossMusicController.tick(bestSound, bestBoss);
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (event.getSound() == null) return;

        ResourceLocation id = event.getSound().getLocation();

        // 1) Hard-block Mowzie's built-in boss music so only our tracks play.
        // (We block the main themes + ALL Sculptor theme segments.)
        if (id != null && "mowziesmobs".equals(id.getNamespace())) {
            String p = id.getPath();
            if ("music.frostmaw_theme".equals(p)
                    || "music.ferrous_wroughtnaut_theme".equals(p)
                    || "music.umvuthi_theme".equals(p)
                    || p.startsWith("music.sculptor_theme")) {
                event.setSound(null);
                return;
            }
        }

        // 2) Wrap other music so we can duck/crossfade while a boss is active.
        if (event.getSound() instanceof DuckedSoundInstance) return;

        if (event.getSound().getSource() == SoundSource.MUSIC) {
            // Don't duck our own boss music.
            if (id != null && MowzieOMCompat.MODID.equals(id.getNamespace())) {
                return;
            }
            event.setSound(new DuckedSoundInstance(event.getSound()));
        }
    }
}
