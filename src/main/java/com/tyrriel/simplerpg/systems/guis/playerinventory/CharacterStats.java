package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.util.SpecialChars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tyrriel.simplerpg.util.ItemUtil.item;

public class CharacterStats {
    public static void openCharacterStats(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, " ");
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        inventory.setMaxStackSize(1000);

        inventory.setItem(0, CharacterMenu.inventory());
        inventory.setItem(9, CharacterMenu.stats(RPGCharacter));
        inventory.setItem(18, CharacterMenu.quests());
        inventory.setItem(27, CharacterMenu.guild());
        inventory.setItem(36, CharacterMenu.options());
        inventory.setItem(45, CharacterMenu.inventory2());

        fillCharacterStats(inventory, 6, 70, RPGCharacter.getUnallocatedstats(), "Points");

        fillCharacterStats(inventory, 20, 10, RPGCharacter.getStrength(), "Strength");
        fillCharacterStats(inventory, 29, 20, RPGCharacter.getIntelligence(), "Intelligence");
        fillCharacterStats(inventory, 38, 30, RPGCharacter.getSpeed(), "Speed");
        fillCharacterStats(inventory, 24, 40, RPGCharacter.getVitality(), "Vitality");
        fillCharacterStats(inventory, 33, 50, RPGCharacter.getDexterity(), "Dexterity");
        fillCharacterStats(inventory, 42, 60, RPGCharacter.getLuck(), "Luck");

        CharacterMenu.open.put(inventory, CharacterMenu.GUIType.STATS);
        player.openInventory(inventory);
    }

    public static void fillCharacterStats(Inventory inventory, int startPos, int startTexture, int value, String type){
        String string = String.valueOf(value);
        char[] chars = string.toCharArray();
        if (string.length() == 1){
            int num = Character.getNumericValue(chars[0]);
            inventory.setItem(startPos, staticon(type, startTexture));
            inventory.setItem(startPos + 1, stat(String.valueOf(value), 100));
            inventory.setItem(startPos + 2, stat(" ", 90 + num));
        }
        if (string.length() == 2){
            int num1 = Character.getNumericValue(chars[0]);
            int num2 = Character.getNumericValue(chars[1]);
            inventory.setItem(startPos, staticon(type, startTexture));
            inventory.setItem(startPos + 1, stat(String.valueOf(value), 80 + num1));
            inventory.setItem(startPos + 2, stat(" ", 90 + num2));
        }
        if (string.length() == 3){
            int num1 = Character.getNumericValue(chars[0]);
            int num2 = Character.getNumericValue(chars[1]);
            int num3 = Character.getNumericValue(chars[2]);
            inventory.setItem(startPos, staticon(type, startTexture + num1));
            inventory.setItem(startPos + 1, stat(String.valueOf(value), 80 + num2));
            inventory.setItem(startPos + 2, stat(" ", 90 + num3));
        }
    }

    private static ItemStack staticon(String string, int value){
        return item(Material.LIGHT_GRAY_STAINED_GLASS_PANE, value, string,
                Arrays.asList(
                        " ",
                        ChatColor.GRAY + SpecialChars.leftClick() + " to add 1 point."
                ));
    }

    private static ItemStack stat(String name, int value){
        return item(Material.LIGHT_GRAY_STAINED_GLASS_PANE, value, name, new ArrayList<>());
    }

    /**
     * CHARACTER STATS
     */
    public static boolean isStrength(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasCustomModelData() && (itemMeta.getCustomModelData() > 9 && itemMeta.getCustomModelData() < 20);
    }

    public static boolean isIntelligence(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasCustomModelData() && (itemMeta.getCustomModelData() > 19 && itemMeta.getCustomModelData() < 30);
    }

    public static boolean isSpeed(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasCustomModelData() && (itemMeta.getCustomModelData() > 29 && itemMeta.getCustomModelData() < 40);
    }

    public static boolean isVitality(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasCustomModelData() && (itemMeta.getCustomModelData() > 39 && itemMeta.getCustomModelData() < 50);
    }

    public static boolean isDexterity(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasCustomModelData() && (itemMeta.getCustomModelData() > 49 && itemMeta.getCustomModelData() < 60);
    }

    public static boolean isLuck(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasCustomModelData() && (itemMeta.getCustomModelData() > 59 && itemMeta.getCustomModelData() < 70);
    }
}
