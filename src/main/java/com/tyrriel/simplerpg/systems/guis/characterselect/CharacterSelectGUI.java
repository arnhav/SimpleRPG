package com.tyrriel.simplerpg.systems.guis.characterselect;

import com.tyrriel.simplerpg.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CharacterSelectGUI {

    public enum GUIType {
        CHARACTER,
        JOB
    }

    public static HashMap<Inventory, GUIType> open = new HashMap<>();

    public static void openCharacterSelector(Player player){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Select a Character");
        // Get the player's characters from database

        for (int i = 0; i < inventory.getSize(); i++){
            if (false){
                // Put already created characters here
            } else {
                inventory.setItem(i, createNew());
            }
        }
        open.put(inventory, GUIType.CHARACTER);
        player.openInventory(inventory);
    }

    public static void openJobSelector(Player player){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Select a Class");
        inventory.setItem(0, fighter());
        inventory.setItem(1, sorcerer());
        inventory.setItem(2, rouge());
        inventory.setItem(3, cleric());
        inventory.setItem(4, ranger());
        open.put(inventory, GUIType.JOB);
        player.openInventory(inventory);
    }

    public static ItemStack createNew(){
        return ItemUtil.item(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "[+] Create new Character", new ArrayList<>());
    }

    public static ItemStack fighter(){
        return ItemUtil.item(Material.BLACK_STAINED_GLASS_PANE, 2,
                "   " + ChatColor.WHITE + "Select " + ChatColor.RED + "Fighter",
                Arrays.asList(
                        " ",
                        ChatColor.GRAY + "A master of martial combat",
                        ChatColor.GRAY + "skilled with a variety of",
                        ChatColor.GRAY + "weapons and armor."
                ));
    }

    public static ItemStack sorcerer(){
        return ItemUtil.item(Material.BLACK_STAINED_GLASS_PANE, 3,
                "   " + ChatColor.WHITE + "Select " + ChatColor.DARK_RED + "Sorcerer",
                Arrays.asList(
                        " ",
                        ChatColor.GRAY + "A spellcaster who draws on",
                        ChatColor.GRAY + "inherent magic from a gift",
                        ChatColor.GRAY + "or bloodline."
                ));
    }

    public static ItemStack rouge(){
        return ItemUtil.item(Material.BLACK_STAINED_GLASS_PANE, 4,
                "   " + ChatColor.WHITE + "Select " + ChatColor.DARK_GRAY + "Rouge",
                Arrays.asList(
                        " ",
                        ChatColor.GRAY + "A scoundrel who uses stealth",
                        ChatColor.GRAY + "and trickery to overcome",
                        ChatColor.GRAY + "obstacles and enemies."
                ));
    }

    public static ItemStack cleric(){
        return ItemUtil.item(Material.BLACK_STAINED_GLASS_PANE, 5,
                "   " + ChatColor.WHITE + "Select " + ChatColor.GREEN + "Cleric",
                Arrays.asList(
                        " ",
                        ChatColor.GRAY + "A priestly champion who",
                        ChatColor.GRAY + "wields divine magic in",
                        ChatColor.GRAY + "service of a higher power."
                ));
    }

    public static ItemStack ranger(){
        return ItemUtil.item(Material.BLACK_STAINED_GLASS_PANE, 6,
                "   " + ChatColor.WHITE + "Select " + ChatColor.DARK_GREEN + "Ranger",
                Arrays.asList(
                        " ",
                        ChatColor.GRAY + "A warrior who combats",
                        ChatColor.GRAY + "threats on the edges of",
                        ChatColor.GRAY + "civilization."
                ));
    }

}
