package com.tyrriel.simplerpg.systems.playerui;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayerUISystem {

    public PlayerUISystem(JavaPlugin plugin){
        new PlayerUIUpdater(plugin);
    }

}
