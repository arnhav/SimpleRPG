package com.tyrriel.simplerpg.systems.characters;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.WeakHashMap;

public class CharacterManager {

    public static WeakHashMap<Player, RPGCharacter> characters = new WeakHashMap<>();

    public static void addExp(Player player, int exp){
        RPGCharacter RPGCharacter = characters.get(player);
        if (RPGCharacter.getExp() + exp >= RPGCharacter.getMaxExpForLevel()){
            int newExp = (int) ((RPGCharacter.getExp() + exp) - RPGCharacter.getMaxExpForLevel());
            levelUp(player, RPGCharacter);
            addExp(player, newExp);
        } else {
            RPGCharacter.setExp(RPGCharacter.getExp() + exp);
        }
    }

    private static void levelUp(Player player, RPGCharacter RPGCharacter){
        RPGCharacter.setLevel(RPGCharacter.getLevel() + 1);
        RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() + 5);
        RPGCharacter.setHealth(RPGCharacter.getMaxHealth());
        RPGCharacter.setMana(RPGCharacter.getMaxMana());
        player.sendMessage(ChatColor.GRAY + "------------------------------");
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "           Level Up!");
        player.sendMessage(" ");
        player.sendMessage(ChatColor.GRAY + "+5 Stat Points");
        player.sendMessage(ChatColor.GRAY + "Unlocked: " + ChatColor.WHITE + "Blah");
        player.sendMessage(ChatColor.GRAY + "------------------------------");
    }

}
