package com.tyrriel.simplerpg.systems.mounts;

import org.bukkit.plugin.java.JavaPlugin;

public class MountsSystem {

    public MountsSystem(JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new MountsListener(), plugin);
    }

}
