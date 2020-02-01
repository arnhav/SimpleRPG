package com.tyrriel.simplerpg.systems.guis;

import com.tyrriel.simplerpg.systems.guis.characterselect.CharacterSelectListener;
import com.tyrriel.simplerpg.systems.guis.playerinventory.PlayerInventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

public class GUISystem {

    public GUISystem(JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new CharacterSelectListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInventoryListener(), plugin);

    }

}
