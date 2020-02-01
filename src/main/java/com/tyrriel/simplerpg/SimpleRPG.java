package com.tyrriel.simplerpg;

import com.tyrriel.simplerpg.systems.characters.CharactersSystem;
import com.tyrriel.simplerpg.listeners.PlayerListener;
import com.tyrriel.simplerpg.systems.playerui.PlayerUISystem;
import com.tyrriel.simplerpg.systems.guis.GUISystem;
import com.tyrriel.simplerpg.systems.interactables.InteractablesSystem;
import com.tyrriel.simplerpg.systems.playerui.CompassDisplay;
import com.tyrriel.simplerpg.util.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleRPG extends JavaPlugin {

    private static SimpleRPG simpleRPG;

    @Override
    public void onEnable() {
        // Plugin startup logic
        simpleRPG = this;

        new FileManager(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Systems
        new CharactersSystem(this);
        new GUISystem(this);
        new PlayerUISystem(this);
        new InteractablesSystem(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        CompassDisplay.removeAllBossBars();
    }

    public static SimpleRPG getInstance(){
        return simpleRPG;
    }
}