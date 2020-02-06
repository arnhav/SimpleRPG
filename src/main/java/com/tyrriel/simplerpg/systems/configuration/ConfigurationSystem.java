package com.tyrriel.simplerpg.systems.configuration;

import com.tyrriel.simplerpg.systems.configuration.config.ConfigManager;
import com.tyrriel.simplerpg.systems.configuration.items.ItemsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationSystem {

    public ConfigurationSystem(JavaPlugin plugin){
        new ConfigManager(plugin);
        new ItemsManager(plugin);
    }

}
