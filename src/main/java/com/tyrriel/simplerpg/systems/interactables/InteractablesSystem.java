package com.tyrriel.simplerpg.systems.interactables;

import org.bukkit.plugin.java.JavaPlugin;

public class InteractablesSystem {

    public InteractablesSystem(JavaPlugin plugin){
        new InteractablesMonitor(plugin);
    }

}
