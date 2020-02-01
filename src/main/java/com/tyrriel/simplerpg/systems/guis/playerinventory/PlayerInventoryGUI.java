package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.util.SpecialChars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.tyrriel.simplerpg.util.ItemUtil.*;

public class PlayerInventoryGUI {

    public enum GUIType{
        INVENTORY,
        STATS,
        QUESTS,
        GUILD,
        OPTIONS
    }

    public static HashMap<Inventory, GUIType> open = new HashMap<>();

    public static void openCharacterInventory(Player player, int page){
        RPGCharacter character = CharacterManager.characters.get(player);
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.WHITE + adjustedPlayerWealth(character) + SpecialChars.goldCoin());
        List<ItemStack> characterInventory = character.getInventory();
        inventory.setItem(0, inventory());
        inventory.setItem(9, stats(character));
        inventory.setItem(18, quests());
        inventory.setItem(27, guild());
        inventory.setItem(36, options());
        inventory.setItem(45, inventory1());
        if (page > 0){
            inventory.setItem(8, scroll(up(), page-1));
        }
        if (page < character.getBackpack()){
            inventory.setItem(53, scroll(down(), page+1));
        }
        inventory.setItem(((page+2)*9)-1, scroll_indicator());

        if (character.getBackpack() > 0){
            inventory.setItem(48, backpack(character.getBackpack()));
        }

        // Set armor
        inventory.setItem(11, equippedItem(character.getHelm(), character));
        inventory.setItem(20, equippedItem(character.getChest(), character));
        inventory.setItem(29, equippedItem(character.getLegs(), character));
        inventory.setItem(38, equippedItem(character.getBoots(), character));
        // Set accessories
        inventory.setItem(10, equippedItem(character.getNecklace(), character));
        inventory.setItem(12, equippedItem(character.getEarrings(), character));
        inventory.setItem(19, equippedItem(character.getRing(), character));
        inventory.setItem(21, equippedItem(character.getBracers(), character));
        inventory.setItem(28, equippedItem(character.getPendant(), character));
        // Set weapons
        inventory.setItem(30, equippedItem(character.getWeapon(), character));
        // Set consumable
        inventory.setItem(46, equippedItem(character.getConsumable(), character));
        // Set inventory
        int k = 0, p = page * 18;
        for (int i = 5; i < 53; i++){
            if (characterInventory.size() > p) {
                ItemStack temp = characterInventory.get(p);
                inventory.setItem(i, unequippedItem(temp, p, character));
                if (k == 2) {
                    k = 0;
                    i = i + 6;
                } else {
                    k++;
                }
                p++;
            }
        }
        open.put(inventory, GUIType.INVENTORY);
        player.openInventory(inventory);
    }

    public static void openCharacterStats(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, " ");
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        inventory.setMaxStackSize(1000);

        inventory.setItem(0, inventory());
        inventory.setItem(9, stats(RPGCharacter));
        inventory.setItem(18, quests());
        inventory.setItem(27, guild());
        inventory.setItem(36, options());
        inventory.setItem(45, inventory2());

        fillCharacterStats(inventory, 6, 70, RPGCharacter.getUnallocatedstats(), "Points");

        fillCharacterStats(inventory, 20, 10, RPGCharacter.getStrength(), "Strength");
        fillCharacterStats(inventory, 29, 20, RPGCharacter.getIntelligence(), "Intelligence");
        fillCharacterStats(inventory, 38, 30, RPGCharacter.getSpeed(), "Speed");
        fillCharacterStats(inventory, 24, 40, RPGCharacter.getVitality(), "Vitality");
        fillCharacterStats(inventory, 33, 50, RPGCharacter.getDexterity(), "Dexterity");
        fillCharacterStats(inventory, 42, 60, RPGCharacter.getLuck(), "Luck");

        open.put(inventory, GUIType.STATS);
        player.openInventory(inventory);
    }

    public static void openCharacterQuests(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, " ");
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        inventory.setItem(0, inventory());
        inventory.setItem(9, stats(RPGCharacter));
        inventory.setItem(18, quests());
        inventory.setItem(27, guild());
        inventory.setItem(36, options());
        inventory.setItem(45, inventory3());
        open.put(inventory, GUIType.QUESTS);
        player.openInventory(inventory);
    }

    public static void openPlayerGuild(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, " ");
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        inventory.setItem(0, inventory());
        inventory.setItem(9, stats(RPGCharacter));
        inventory.setItem(18, quests());
        inventory.setItem(27, guild());
        inventory.setItem(36, options());
        inventory.setItem(45, inventory4());
        open.put(inventory, GUIType.GUILD);
        player.openInventory(inventory);
    }

    public static void openPlayerOptions(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, " ");
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        inventory.setItem(0, inventory());
        inventory.setItem(9, stats(RPGCharacter));
        inventory.setItem(18, quests());
        inventory.setItem(27, guild());
        inventory.setItem(36, options());
        inventory.setItem(45, inventory5());
        open.put(inventory, GUIType.OPTIONS);
        player.openInventory(inventory);
    }

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

    public static String adjustedPlayerWealth(RPGCharacter RPGCharacter){
        String foo = RPGCharacter.getMoney() + " ";
        while (foo.length() < 14){
            foo = " " + foo;
        }
        return foo;
    }

}
