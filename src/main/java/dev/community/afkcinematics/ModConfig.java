package dev.community.afkcinematics;

import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ModConfig {
    public boolean enabled=true;
    public int afkSeconds=60;
    private static final Path FILE=FabricLoader.getInstance().getConfigDir().resolve("afk-cinematics-reimagined.properties");
    public static ModConfig load() {
        ModConfig c=new ModConfig(); Properties p=new Properties();
        if (Files.exists(FILE)) try (var in=Files.newInputStream(FILE)) {
            p.load(in); c.enabled=Boolean.parseBoolean(p.getProperty("enabled","true"));
            c.afkSeconds=Math.max(5,Math.min(3600,Integer.parseInt(p.getProperty("afkSeconds","60"))));
        } catch (Exception ignored) { }
        return c;
    }
    public void save() {
        Properties p=new Properties(); p.setProperty("enabled",Boolean.toString(enabled));
        p.setProperty("afkSeconds",Integer.toString(afkSeconds));
        try { Files.createDirectories(FILE.getParent()); try(var out=Files.newOutputStream(FILE)){p.store(out,"AFK Cinematics Reimagined");} }
        catch(IOException e){ System.err.println("[AFK Cinematics] Could not save config: "+e.getMessage()); }
    }
}
