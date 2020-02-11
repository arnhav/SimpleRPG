package com.tyrriel.simplerpg;

import com.tyrriel.simplerpg.commands.ClassCommand;
import com.tyrriel.simplerpg.commands.CommandHandler;
import com.tyrriel.simplerpg.systems.characters.CharactersSystem;
import com.tyrriel.simplerpg.listeners.PlayerListener;
import com.tyrriel.simplerpg.systems.combat.CombatSystem;
import com.tyrriel.simplerpg.systems.configuration.ConfigurationSystem;
import com.tyrriel.simplerpg.systems.mounts.MountsSystem;
import com.tyrriel.simplerpg.systems.playerui.PlayerUISystem;
import com.tyrriel.simplerpg.systems.guis.GUISystem;
import com.tyrriel.simplerpg.systems.interactables.InteractablesSystem;
import com.tyrriel.simplerpg.systems.playerui.CompassDisplay;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleRPG extends JavaPlugin {

    private static SimpleRPG simpleRPG;

    @Override
    public void onEnable() {
        // Plugin startup logic
        simpleRPG = this;

        // Systems
        new ConfigurationSystem(this);
        new CharactersSystem(this);
        new GUISystem(this);
        new PlayerUISystem(this);
        new InteractablesSystem(this);
        new CombatSystem(this);
        new MountsSystem(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Commands
        getCommand("class").setExecutor(new ClassCommand());
        getCommand("addexp").setExecutor(new CommandHandler());
        getCommand("setmoney").setExecutor(new CommandHandler());
        getCommand("additem").setExecutor(new CommandHandler());
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
