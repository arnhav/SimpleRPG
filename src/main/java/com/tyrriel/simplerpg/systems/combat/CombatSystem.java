package com.tyrriel.simplerpg.systems.combat;

import org.bukkit.plugin.java.JavaPlugin;

public class CombatSystem {

    public CombatSystem(JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new CombatListeners(), plugin);
    }

}
