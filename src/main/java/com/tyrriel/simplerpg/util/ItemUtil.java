package com.tyrriel.simplerpg.util;

import com.tyrriel.simplerpg.systems.items.RPGItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
                ChatColor.GRAY + "Cooldown: " + RPGItemUtil.getCooldown(itemStack) + " sec."
        ));
        temp.setItemMeta(itemMeta);
        return temp;
    }

}
