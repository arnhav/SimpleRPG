package com.tyrriel.simplerpg.util;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.configurationsystem.items.ConfigurationItem;
import com.tyrriel.simplerpg.systems.configurationsystem.items.ItemsManager;
import com.tyrriel.simplerpg.systems.items.RPGItem;
import com.tyrriel.simplerpg.systems.items.types.ItemType;
import com.tyrriel.simplerpg.systems.items.types.OffhandType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtil {

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
                ChatColor.GRAY + "Cooldown: " + RPGItem.getCooldown(itemStack) + " sec."
        ));
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack makeOffhandItem(ItemStack itemStack){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemMeta.setCustomModelData(1);
        itemMeta.setLore(new ArrayList<>());
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static void addItemToCharacterInv(Player player, String item){
        if (CharacterManager.characters.containsKey(player)){
            RPGCharacter rpgCharacter = CharacterManager.characters.get(player);
            ConfigurationItem configItem = ItemsManager.getConfigurationItem(item + ".yml");
            ItemType itemType = configItem.getItemType();
            ItemStack itemStack = RPGItem.makeRPGItem(configItem.getItemStack(), configItem.getRarity(), configItem.getItemType(), configItem.getJob(), configItem.getLevel(), configItem.getValue());
            if (itemType != ItemType.JUNK){
                itemStack = RPGItem.notJunk(itemStack, configItem.getStats());
                if (itemType == ItemType.WEAPON){
                    itemStack = RPGItem.makeRPGWeapon(itemStack, configItem.getWeaponType(), configItem.getCooldown(), configItem.getMinDamage(), configItem.getMaxDamage());
                }
                if (itemType == ItemType.ARMOR){
                    itemStack = RPGItem.makeRPGArmor(itemStack, configItem.getArmorType(), configItem.getArmor());
                }
                if (itemType == ItemType.OFFHAND){
                    if (configItem.getOffhandType() == OffhandType.SHIELD) {
                        itemStack = RPGItem.makeRPGOffhand(itemStack, configItem.getOffhandType(), configItem.getArmor());
                    } else {
                        itemStack = RPGItem.makeRPGOffhand(itemStack, configItem.getOffhandType(), 0);
                    }
                }
                if (itemType == ItemType.ACCESSORY){
                    itemStack = RPGItem.makeRPGAccessory(itemStack, configItem.getAccessoryType());
                }
            }
            rpgCharacter.addItemToInv(itemStack);
        }
    }

}
