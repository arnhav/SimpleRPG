package com.tyrriel.simplerpg.systems.characters;

import org.bukkit.plugin.java.JavaPlugin;

public class CharactersSystem {

    public CharactersSystem(JavaPlugin plugin){
        plugin.getCommand("class").setExecutor(new ClassCommand());
        plugin.getCommand("addexp").setExecutor(new CommandHandler());
    }

}
