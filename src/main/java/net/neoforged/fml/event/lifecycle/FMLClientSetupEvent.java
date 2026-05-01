package net.neoforged.fml.event.lifecycle;

public class FMLClientSetupEvent { public void enqueueWork(Runnable runnable) { runnable.run(); } }
