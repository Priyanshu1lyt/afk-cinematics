package dev.community.afkcinematics;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class AfkCinematicsClient implements ClientModInitializer {
    public static final CinematicDirector DIRECTOR = new CinematicDirector();
    private static final KeyBinding.Category CATEGORY =
        KeyBinding.Category.create(Identifier.of("afkcinematics_reimagined", "main"));
    public static final ModConfig CONFIG = ModConfig.load();
    private static KeyBinding settingsKey, toggleKey;
    private static long lastActivity = System.currentTimeMillis();
    private static double lastX, lastY, lastZ;
    private static float lastYaw, lastPitch;
    private static boolean snapshotReady;

    @Override public void onInitializeClient() {
        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.afkcinematics_reimagined.settings", InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O, CATEGORY));
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.afkcinematics_reimagined.toggle", InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_P, CATEGORY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick(client));
    }

    private static void tick(MinecraftClient client) {
        if (client.player == null || client.world == null) {
            DIRECTOR.stop(); snapshotReady = false; return;
        }
        if (!snapshotReady) {
            rememberPlayer(client); snapshotReady = true; lastActivity = System.currentTimeMillis();
        }
        boolean changed = client.player.getX() != lastX || client.player.getY() != lastY
            || client.player.getZ() != lastZ
            || Math.abs(client.player.getYaw() - lastYaw) > 0.15f
            || Math.abs(client.player.getPitch() - lastPitch) > 0.15f;
        if (changed) { lastActivity = System.currentTimeMillis(); DIRECTOR.stop(); }
        rememberPlayer(client);

        while (settingsKey.wasPressed()) client.setScreen(new SettingsScreen());
        while (toggleKey.wasPressed()) { CONFIG.enabled = !CONFIG.enabled; CONFIG.save(); if (!CONFIG.enabled) DIRECTOR.stop(); }
        long idleSeconds = (System.currentTimeMillis() - lastActivity) / 1000L;
        if (CONFIG.enabled && idleSeconds >= CONFIG.afkSeconds) DIRECTOR.startIfNeeded();
        if (!CONFIG.enabled || idleSeconds < CONFIG.afkSeconds) DIRECTOR.stop();
        DIRECTOR.tick();
    }
    private static void rememberPlayer(MinecraftClient c) {
        lastX=c.player.getX(); lastY=c.player.getY(); lastZ=c.player.getZ();
        lastYaw=c.player.getYaw(); lastPitch=c.player.getPitch();
    }
}
