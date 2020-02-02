package com.tyrriel.simplerpg.systems.items;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.characters.Job;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RPGItemUtil {

    static NamespacedKey rpgItemKey = new NamespacedKey(SimpleRPG.getInstance(), "RPGItem");
    static NamespacedKey rarityKey = new NamespacedKey(SimpleRPG.getInstance(), "Rarity");
    static NamespacedKey itemTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "ItemType");
    static NamespacedKey jobKey = new NamespacedKey(SimpleRPG.getInstance(), "Job");
    static NamespacedKey levelKey = new NamespacedKey(SimpleRPG.getInstance(), "Level");
    static NamespacedKey valueKey = new NamespacedKey(SimpleRPG.getInstance(), "Value");
    static NamespacedKey attributeKey = new NamespacedKey(SimpleRPG.getInstance(), "Attributes");
    static NamespacedKey weaponTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "WeaponType");
    static NamespacedKey cooldownKey = new NamespacedKey(SimpleRPG.getInstance(), "Cooldown");
    static NamespacedKey damageKey = new NamespacedKey(SimpleRPG.getInstance(), "Damage");

    public static boolean isRPGItem(ItemStack itemStack){
        if (itemStack == null) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(rpgItemKey, PersistentDataType.INTEGER);
    }

    public static Object getValue(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType){
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(namespacedKey, persistentDataType);
    }

    public static Rarity getRarity(ItemStack itemStack){
        return Rarity.valueOf((String) getValue(itemStack, rarityKey, PersistentDataType.STRING));
    }

    public static ItemType getItemType(ItemStack itemStack){
        return ItemType.valueOf((String) getValue(itemStack, itemTypeKey, PersistentDataType.STRING));
    }

    public static Job getJob(ItemStack itemStack){
        return Job.valueOf((String) getValue(itemStack, jobKey, PersistentDataType.STRING));
    }

    public static int getLevel(ItemStack itemStack){
        return (int) getValue(itemStack, levelKey, PersistentDataType.INTEGER);
    }

    public static int getValue(ItemStack itemStack){
        return (int) getValue(itemStack, valueKey, PersistentDataType.INTEGER);
    }

    public static int[] getAttributes(ItemStack itemStack){
        return (int[]) getValue(itemStack, attributeKey, PersistentDataType.INTEGER_ARRAY);
    }

    public static WeaponType getWeaponType(ItemStack itemStack){
        return WeaponType.valueOf((String) getValue(itemStack, weaponTypeKey, PersistentDataType.STRING));
    }

    public static int getCooldown(ItemStack itemStack){
        return (int) getValue(itemStack, cooldownKey, PersistentDataType.INTEGER);
    }

    public static int getDamage(ItemStack itemStack){
        return (int) getValue(itemStack, damageKey, PersistentDataType.INTEGER);
    }

    public static ItemStack makeRPGItem(ItemStack itemStack, Rarity rarity, ItemType itemType, Job job, int level, int value, int[] attributes){
        if (itemStack == null) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(rpgItemKey, PersistentDataType.INTEGER, 1);
        pdc.set(rarityKey, PersistentDataType.STRING, rarity.toString());
        pdc.set(itemTypeKey, PersistentDataType.STRING, itemType.toString());
        pdc.set(jobKey, PersistentDataType.STRING, job.toString());
        pdc.set(levelKey, PersistentDataType.INTEGER, level);
        pdc.set(valueKey, PersistentDataType.INTEGER, value);
        pdc.set(attributeKey, PersistentDataType.INTEGER_ARRAY, attributes);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack makeRPGWeapon(ItemStack itemStack, WeaponType weaponType, int cooldown, int damage){
        if (itemStack == null) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(weaponTypeKey, PersistentDataType.STRING, weaponType.toString());
        pdc.set(cooldownKey, PersistentDataType.INTEGER, cooldown);
        pdc.set(damageKey, PersistentDataType.INTEGER, damage);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

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
}
