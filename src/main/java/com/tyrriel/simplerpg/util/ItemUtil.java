package com.tyrriel.simplerpg.util;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.items.ItemType;
import com.tyrriel.simplerpg.systems.items.RPGItem;
import com.tyrriel.simplerpg.systems.items.RPGItemUtil;
import com.tyrriel.simplerpg.systems.items.RPGWeapon;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtil {

    private static NamespacedKey equipped = new NamespacedKey(SimpleRPG.getInstance(), "Equipped");
    private static NamespacedKey slot = new NamespacedKey(SimpleRPG.getInstance(), "Slot");
    private static NamespacedKey scroll = new NamespacedKey(SimpleRPG.getInstance(), "scroll");

    public static ItemStack item (Material material, int texture, String itemName, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(texture);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * PLAYER INVENTORY
     */
    public static ItemStack makeArmorItem(ItemStack itemStack){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemMeta.setCustomModelData(1);
        itemMeta.setLore(new ArrayList<>());
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack makeWeaponItem(ItemStack itemStack){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Basic Attack");
        itemMeta.setLore(Arrays.asList(
                " ",
                ChatColor.GRAY + "Cooldown: 2 sec."
        ));
        temp.setItemMeta(itemMeta);
        return temp;
    }

    /**
     * CHARACTER INVENTORY
     */

    public static boolean isEquipped(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        return pdc.has(equipped, PersistentDataType.INTEGER);
    }

    public static boolean isInventory(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        return pdc.has(slot, PersistentDataType.INTEGER);
    }

    public static void removeEquipped(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (isEquipped(itemStack)) {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.remove(equipped);
        }
    }

    public static void removeInventory(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (isInventory(itemStack)) {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.remove(slot);
        }
    }

    public static int getSlot(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!isInventory(itemStack)) return -1;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(slot, PersistentDataType.INTEGER);
    }

    public static boolean isScroll(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        return pdc.has(scroll, PersistentDataType.INTEGER);
    }

    public static int getScroll(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return -1;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        if (!pdc.has(scroll, PersistentDataType.INTEGER)) return -1;
        return pdc.get(scroll, PersistentDataType.INTEGER);
    }

    public static ItemStack equippedItem(ItemStack itemStack, RPGCharacter character){
        if (itemStack == null) return itemStack;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        RPGItem rpgItem = RPGItemUtil.getRPGItem(temp);
        itemMeta.setDisplayName(RPGItemUtil.getRarityColor(rpgItem.getRarity()) + itemStack.getI18NDisplayName());
        itemMeta.setLore(equippedLore(rpgItem, itemMeta.getLore(), character.getLevel()));
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(equipped, PersistentDataType.INTEGER, 1);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    private static List<String> equippedLore(RPGItem rpgItem, List<String> itemLore, int lvl){
        List<String> lore = new ArrayList<>();
        lore.add(RPGItemUtil.getRarityColor(rpgItem.getRarity()) + StringUtils.capitalize(rpgItem.getItemType().toString().toLowerCase()));
        // Put Item Stats here
        if (rpgItem instanceof RPGWeapon){

        }
        if (itemLore != null){
            lore.addAll(itemLore);
        }
        int reqLvl = rpgItem.getLevel();
        String reqL;
        if (reqLvl > lvl){
            reqL = ChatColor.RED + "Required Level: " + reqLvl;
        } else {
            reqL = ChatColor.GRAY + "Required Level: " + reqLvl;
        }
        if (reqLvl < 10){
            reqL = "  " + reqL;
        }
        if (reqLvl < 100){
            reqL = " " + reqL;
        }
        lore.addAll(Arrays.asList(
                " ",
                ChatColor.WHITE + SpecialChars.leftClick() + " Unequip",
                ChatColor.WHITE + SpecialChars.rightClick() + " Drop           " + reqL,
                ChatColor.GRAY + "------------------------------",
                ChatColor.DARK_GRAY + "Sell Value: " + ChatColor.WHITE + rpgItem.getValue() + " " + SpecialChars.goldCoin()
        ));
        return lore;
    }

    public static ItemStack unequippedItem(ItemStack itemStack, int s, RPGCharacter character){
        if (itemStack == null) return itemStack;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        RPGItem rpgItem = RPGItemUtil.getRPGItem(temp);
        itemMeta.setDisplayName(RPGItemUtil.getRarityColor(rpgItem.getRarity()) + itemStack.getI18NDisplayName());
        itemMeta.setLore(unequippedLore(rpgItem, itemMeta.getLore(), character.getLevel()));
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(slot, PersistentDataType.INTEGER, s);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    private static List<String> unequippedLore(RPGItem rpgItem, List<String> itemLore, int lvl){
        List<String> lore = new ArrayList<>();
        lore.add(RPGItemUtil.getRarityColor(rpgItem.getRarity()) + StringUtils.capitalize(rpgItem.getItemType().toString().toLowerCase()));
        // Put Item Stats here
        if (rpgItem instanceof RPGWeapon){

        }
        if (itemLore != null){
            lore.addAll(itemLore);
        }
        int reqLvl = rpgItem.getLevel();
        String reqL;
        if (reqLvl > lvl){
            reqL = ChatColor.RED + "Required Level: " + reqLvl;
        } else {
            reqL = ChatColor.GRAY + "Required Level: " + reqLvl;
        }
        if (reqLvl < 10){
            reqL = "  " + reqL;
        }
        if (reqLvl < 100){
            reqL = " " + reqL;
        }
        if (rpgItem.getItemType() == ItemType.JUNK) {
            lore.addAll(Arrays.asList(
                    " ",
                    ChatColor.GRAY + "------------------------------",
                    ChatColor.DARK_GRAY + "Sell Value: " + ChatColor.WHITE + rpgItem.getValue() + " " + SpecialChars.goldCoin()
            ));
        } else {
            lore.addAll(Arrays.asList(
                    " ",
                    ChatColor.WHITE + SpecialChars.leftClick() + " Equip",
                    ChatColor.WHITE + SpecialChars.rightClick() + " Drop           " + reqL,
                    ChatColor.GRAY + "------------------------------",
                    ChatColor.DARK_GRAY + "Sell Value: " + ChatColor.WHITE + rpgItem.getValue() + " " + SpecialChars.goldCoin()
            ));
        }
        return lore;
    }

    public static ItemStack scroll(ItemStack itemStack, int s){
        if (itemStack == null) return itemStack;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(scroll, PersistentDataType.INTEGER, s);
        temp.setItemMeta(itemMeta);
        return temp;
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
