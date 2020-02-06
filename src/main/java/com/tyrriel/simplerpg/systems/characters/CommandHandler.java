package com.tyrriel.simplerpg.systems.characters;

import com.tyrriel.simplerpg.util.ItemUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName().toLowerCase();
        if (cmd.equals("")){
            return false;
        }

        if (cmd.equalsIgnoreCase("addexp")){
            if (sender.isOp() && args.length == 2){
                Player player = Bukkit.getPlayerExact(args[0]);
                if (StringUtils.isNumeric(args[1]) && CharacterManager.characters.containsKey(player)){
                    CharacterManager.addExp(player, Integer.parseInt(args[1]));
                }
            }
        }
        if (cmd.equalsIgnoreCase("setmoney")){
            if (sender.isOp() && args.length == 2){
                Player player = Bukkit.getPlayerExact(args[0]);
                if (StringUtils.isNumeric(args[1]) && CharacterManager.characters.containsKey(player)){
                    CharacterManager.characters.get(player).setMoney(Integer.parseInt(args[1]));
                }
            }
        }
        if (cmd.equalsIgnoreCase("additem")){
            if (sender.isOp() && args.length == 2){
                Player player = Bukkit.getPlayerExact(args[0]);
                ItemUtil.addItemToCharacterInv(player, args[1]);
            }
        }

        return true;
    }
}
