package com.tyrriel.simplerpg.commands;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.configuration.items.ItemsManager;
import com.tyrriel.simplerpg.util.ItemUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CharacterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName().toLowerCase();
        if (cmd.equals("")){
            return false;
        }

        if (cmd.equals("character")){
            if (sender.isOp() && args.length == 3){
                Player player = Bukkit.getPlayerExact(args[1]);
                if (args[0].equalsIgnoreCase("addexp") && StringUtils.isNumeric(args[2]) && CharacterManager.characters.containsKey(player)){
                    CharacterManager.addExp(player, Integer.parseInt(args[2]));
                }
                if (args[0].equalsIgnoreCase("setmoney") && StringUtils.isNumeric(args[2]) && CharacterManager.characters.containsKey(player)){
                    CharacterManager.characters.get(player).setMoney(Integer.parseInt(args[2]));
                }
                if (args[0].equalsIgnoreCase("additem") && ItemsManager.getConfigurationItem(args[2]) != null && CharacterManager.characters.containsKey(player)){
                    ItemUtil.addItemToCharacterInv(player, args[2]);
                }
            }
        }

        return true;
    }
}
