package com.tyrriel.simplerpg.systems.configuration.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {

    private JavaPlugin plugin;

    private FileConfiguration config;

    private File configFile;


    private static Location charSelectLoc;
    private static Location newStartLoc;
    private static String rpurl;
    private static String rphash;

    public ConfigManager(JavaPlugin plugin){
        this.plugin = plugin;
        createConfig();
    }

    public void createConfig() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            configFile = new File(plugin.getDataFolder(), "config.yml");
            config = new YamlConfiguration();
            if (!configFile.exists()) {
                plugin.getLogger().info("config.yml not found, creating...");
                plugin.saveDefaultConfig();
                loadConfig();
            } else {
                plugin.getLogger().info("config.yml found, loading...");
                loadConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        config = plugin.getConfig();

        charSelectLoc = getLocation(config, "CharacterSelection");
        newStartLoc = getLocation(config, "NewStart");
        rpurl = config.getString("rpurl");
        rphash = config.getString("rphash");
    }

    private Location getLocation(FileConfiguration fileConfiguration, String path) {
        Location location;
        String worldName = fileConfiguration.getString(path + ".world");
        int x = fileConfiguration.getInt(path + ".x");
        int y = fileConfiguration.getInt(path + ".y");
        int z = fileConfiguration.getInt(path + ".z");

        World world = Bukkit.createWorld(new WorldCreator(worldName));
        location = new Location(world, x, y, z);
        return location;
    }

    public static Location getCharacterSelectLocation(){
        return charSelectLoc;
    }

    public static Location getNewStartLocation(){
        return newStartLoc;
    }

    public static String getRPurl() { return rpurl; }

    public static String getRPhash() {
        return rphash;
    }
}
