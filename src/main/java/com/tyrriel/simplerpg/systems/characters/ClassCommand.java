package com.tyrriel.simplerpg.systems.characters;

import com.tyrriel.simplerpg.systems.configuration.config.ConfigManager;
import com.tyrriel.simplerpg.systems.guis.characterselect.CharacterSelectGUI;
import com.tyrriel.simplerpg.systems.playerui.CompassDisplay;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName().toLowerCase();
        if (cmd.equals("")){
            return false;
        }

        if (cmd.equalsIgnoreCase("class")){
            if (sender instanceof Player){
                // Save character to database

                CharacterManager.characters.remove(sender);
                CompassDisplay.removeCompass((Player) sender);
                ((Player) sender).getInventory().clear();
                ((Player) sender).setLevel(0);
                ((Player) sender).setExp(0);
                ((Player) sender).teleport(ConfigManager.getCharacterSelectLocation());
                CharacterSelectGUI.openCharacterSelector((Player) sender);
            }
        }

        return true;
    }
}
