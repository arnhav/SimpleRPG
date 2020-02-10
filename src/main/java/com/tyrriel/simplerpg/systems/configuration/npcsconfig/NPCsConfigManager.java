package com.tyrriel.simplerpg.systems.configuration.npcsconfig;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NPCsConfigManager {

    // File Configs
    private FileConfiguration data;

    // Files
    private File npcsFile;

    // Plugin instance
    private JavaPlugin plugin;

    public NPCsConfigManager(JavaPlugin plugin){
        this.plugin = plugin;
        createNPCsFile();
    }

    public void createNPCsFile(){
        try {
            npcsFile = new File(plugin.getDataFolder(), "npcs.yml");
            data = YamlConfiguration.loadConfiguration(npcsFile);
            if (!npcsFile.exists()){
                plugin.getLogger().info("npcs.yml not found, creating...");
                data.createSection("NPC");
                data.save(npcsFile);
                loadNPCs(npcsFile);
            } else {
                plugin.getLogger().info("npcs.yml found, loading...");
                loadNPCs(npcsFile);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadNPCs(File file){
        data = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configSection = data.getConfigurationSection("NPC");
        if (configSection != null) {
            for (String path: configSection.getKeys(false)){

            }
        }
    }

}
