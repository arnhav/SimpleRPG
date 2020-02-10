package com.tyrriel.simplerpg.systems.characters;

import com.tyrriel.simplerpg.commands.ClassCommand;
import com.tyrriel.simplerpg.commands.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CharactersSystem {

    public CharactersSystem(JavaPlugin plugin){
        plugin.getCommand("class").setExecutor(new ClassCommand());
        plugin.getCommand("addexp").setExecutor(new CommandHandler());
        plugin.getCommand("setmoney").setExecutor(new CommandHandler());
        plugin.getCommand("additem").setExecutor(new CommandHandler());
    }

}
