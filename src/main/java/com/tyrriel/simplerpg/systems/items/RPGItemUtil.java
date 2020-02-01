package com.tyrriel.simplerpg.systems.items;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.util.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RPGItemUtil {
    static NamespacedKey i = new NamespacedKey(SimpleRPG.getInstance(), "ItemID");

    public static ChatColor getRarityColor(Rarity rarity){
        switch (rarity){
            case COMMON:
                return ChatColor.WHITE;
            case UNCOMMON:
                return ChatColor.GRAY;
            case RARE:
                return ChatColor.BLUE;
            case EPIC:
                return ChatColor.LIGHT_PURPLE;
            case LEGENDARY:
                return ChatColor.GOLD;
        }
        return ChatColor.WHITE;
    }

    public static boolean isRPGItem(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(i, PersistentDataType.INTEGER);
    }

    public static RPGItem getRPGItem(ItemStack itemStack){
        if (!isRPGItem(itemStack)) return new RPGItem(-1, itemStack, Rarity.COMMON, ItemType.JUNK, Job.NONE, 0, 0);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        int id = pdc.get(i, PersistentDataType.INTEGER);
        return FileManager.getRPGItem(id);
    }
}
