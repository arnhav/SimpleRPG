package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CharacterQuests {
    public static void openCharacterQuests(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, " ");
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        inventory.setItem(0, CharacterMenu.inventory());
        inventory.setItem(9, CharacterMenu.stats(RPGCharacter));
        inventory.setItem(18, CharacterMenu.quests());
        inventory.setItem(27, CharacterMenu.guild());
        inventory.setItem(36, CharacterMenu.options());
        inventory.setItem(45, CharacterMenu.inventory3());
        CharacterMenu.open.put(inventory, CharacterMenu.GUIType.QUESTS);
        player.openInventory(inventory);
    }
}
