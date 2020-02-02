package com.tyrriel.simplerpg.systems.configurationsystem;

import com.tyrriel.simplerpg.systems.configurationsystem.config.ConfigManager;
import com.tyrriel.simplerpg.systems.configurationsystem.items.ItemsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationSystem {

    public ConfigurationSystem(JavaPlugin plugin){
        new ConfigManager(plugin);
        new ItemsManager(plugin);
    }

}
