package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tyrriel.simplerpg.util.ItemUtil.item;

public class CharacterMenu {

    public static HashMap<Inventory, GUIType> open = new HashMap<>();

    public static ItemStack inventory(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 100, "Inventory", new ArrayList<>());
    }

    public static ItemStack inventory1(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 7, " ", new ArrayList<>());
    }

    public static ItemStack inventory2(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 8, " ", new ArrayList<>());
    }

    public static ItemStack inventory3(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 9, " ", new ArrayList<>());
    }

    public static ItemStack inventory4(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 10, " ", new ArrayList<>());
    }

    public static ItemStack inventory5(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 11, " ", new ArrayList<>());
    }

    public static ItemStack stats(RPGCharacter RPGCharacter){
        int value = 0;
        switch (RPGCharacter.getJob()){
            case FIGHTER:
                value = 2;
                break;
            case SORCERER:
                value = 3;
                break;
            case ROUGE:
                value = 4;
                break;
            case CLERIC:
                value = 5;
                break;
            case RANGER:
                value = 6;
                break;
        }
        return item(Material.BLACK_STAINED_GLASS_PANE, value, "Character", new ArrayList<>());
    }

    public static ItemStack quests(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 100, "Quests", new ArrayList<>());
    }

    public static ItemStack guild(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 101, "Guild", new ArrayList<>());
    }

    public static ItemStack options(){
        return item(Material.BLACK_STAINED_GLASS_PANE, 100, "Options", new ArrayList<>());
    }

    public static ItemStack backpack(int value){
        return item(Material.LEATHER, value, "Backpack Lvl." + value, new ArrayList<>());
    }

    public static ItemStack up(){
        return item(Material.GRAY_STAINED_GLASS_PANE, 1, ChatColor.BOLD + "∧", new ArrayList<>());
    }

    public static ItemStack down(){
        return item(Material.GRAY_STAINED_GLASS_PANE, 2, ChatColor.BOLD + "∨", new ArrayList<>());
    }

    public static ItemStack scroll_indicator(){
        return item(Material.GRAY_STAINED_GLASS_PANE, 3, " ", new ArrayList<>());
    }

    public static ItemStack right(){
        return item(Material.GRAY_STAINED_GLASS_PANE, 4, ChatColor.BOLD + ">", new ArrayList<>());
    }

    public static ItemStack left(){
        return item(Material.GRAY_STAINED_GLASS_PANE, 5, ChatColor.BOLD + "<", new ArrayList<>());
    }

    public enum GUIType{
        INVENTORY,
        STATS,
        QUESTS,
        GUILD,
        OPTIONS
    }
}
