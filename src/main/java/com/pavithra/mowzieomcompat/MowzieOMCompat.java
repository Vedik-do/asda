package com.pavithra.mowzieomcompat;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MowzieOMCompat.MODID)
public class MowzieOMCompat {
    public static final String MODID = "mowzieomcompat";

    public MowzieOMCompat() {
        // Register our sound events and other mod content.
        ModSounds.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Client-only handlers
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientInit::onClientSetup);
    }
}
