package com.tyrriel.simplerpg.systems.items;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.items.types.*;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RPGItem {

    static NamespacedKey rpgItemKey = new NamespacedKey(SimpleRPG.getInstance(), "RPGItem");
    static NamespacedKey rarityKey = new NamespacedKey(SimpleRPG.getInstance(), "Rarity");
    static NamespacedKey itemTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "ItemType");
    static NamespacedKey jobKey = new NamespacedKey(SimpleRPG.getInstance(), "Job");
    static NamespacedKey levelKey = new NamespacedKey(SimpleRPG.getInstance(), "Level");
    static NamespacedKey valueKey = new NamespacedKey(SimpleRPG.getInstance(), "Value");
    static NamespacedKey attributeKey = new NamespacedKey(SimpleRPG.getInstance(), "Attributes");
    static NamespacedKey weaponTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "WeaponType");
    static NamespacedKey cooldownKey = new NamespacedKey(SimpleRPG.getInstance(), "Cooldown");
    static NamespacedKey minDamageKey = new NamespacedKey(SimpleRPG.getInstance(), "MinDamage");
    static NamespacedKey maxDamageKey = new NamespacedKey(SimpleRPG.getInstance(), "MaxDamage");
    static NamespacedKey offhandTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "OffhandType");
    static NamespacedKey armorTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "ArmorType");
    static NamespacedKey armorKey = new NamespacedKey(SimpleRPG.getInstance(), "Armor");
    static NamespacedKey accessoryTypeKey = new NamespacedKey(SimpleRPG.getInstance(), "AccessoryType");

    public static boolean isRPGItem(ItemStack itemStack){
        if (itemStack == null) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return false;
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(rpgItemKey, PersistentDataType.INTEGER);
    }

    private static Object getData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType){
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(namespacedKey, persistentDataType);
    }

    public static Rarity getRarity(ItemStack itemStack){
        return Rarity.valueOf((String) getData(itemStack, rarityKey, PersistentDataType.STRING));
    }

    public static ItemType getItemType(ItemStack itemStack){
        return ItemType.valueOf((String) getData(itemStack, itemTypeKey, PersistentDataType.STRING));
    }

    public static Job getJob(ItemStack itemStack){
        return Job.valueOf((String) getData(itemStack, jobKey, PersistentDataType.STRING));
    }

    public static int getLevel(ItemStack itemStack){
        return (int) getData(itemStack, levelKey, PersistentDataType.INTEGER);
    }

    public static int getValue(ItemStack itemStack){
        return (int) getData(itemStack, valueKey, PersistentDataType.INTEGER);
    }

    public static int[] getAttributes(ItemStack itemStack){
        return (int[]) getData(itemStack, attributeKey, PersistentDataType.INTEGER_ARRAY);
    }

    public static WeaponType getWeaponType(ItemStack itemStack){
        return WeaponType.valueOf((String) getData(itemStack, weaponTypeKey, PersistentDataType.STRING));
    }

    public static int getCooldown(ItemStack itemStack){
        return (int) getData(itemStack, cooldownKey, PersistentDataType.INTEGER);
    }

    public static int getMinDamage(ItemStack itemStack){
        return (int) getData(itemStack, minDamageKey, PersistentDataType.INTEGER);
    }

    public static int getMaxDamage(ItemStack itemStack){
        return (int) getData(itemStack, maxDamageKey, PersistentDataType.INTEGER);
    }

    public static OffhandType getOffhandType(ItemStack itemStack){
        return OffhandType.valueOf((String) getData(itemStack, offhandTypeKey, PersistentDataType.STRING));
    }

    public static ArmorType getArmorType(ItemStack itemStack){
        return ArmorType.valueOf((String) getData(itemStack, armorTypeKey, PersistentDataType.STRING));
    }

    public static int getArmor(ItemStack itemStack){
        return (int) getData(itemStack, armorKey, PersistentDataType.INTEGER);
    }

    public static AccessoryType getAccessoryType(ItemStack itemStack){
        return AccessoryType.valueOf((String) getData(itemStack, accessoryTypeKey, PersistentDataType.STRING));
    }

    public static int getDamage(ItemStack itemStack){
        int min = (int) getData(itemStack, minDamageKey, PersistentDataType.INTEGER);
        int max = (int) getData(itemStack, maxDamageKey, PersistentDataType.INTEGER);
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public static ItemStack makeRPGItem(ItemStack itemStack, Rarity rarity, ItemType itemType, Job job, int level, int value){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(rpgItemKey, PersistentDataType.INTEGER, 1);
        pdc.set(rarityKey, PersistentDataType.STRING, rarity.toString());
        pdc.set(itemTypeKey, PersistentDataType.STRING, itemType.toString());
        pdc.set(jobKey, PersistentDataType.STRING, job.toString());
        pdc.set(levelKey, PersistentDataType.INTEGER, level);
        pdc.set(valueKey, PersistentDataType.INTEGER, value);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack notJunk(ItemStack itemStack, int[] attributes){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(attributeKey, PersistentDataType.INTEGER_ARRAY, attributes);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack makeRPGWeapon(ItemStack itemStack, WeaponType weaponType, int cooldown, int minDamage, int maxDamage){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(weaponTypeKey, PersistentDataType.STRING, weaponType.toString());
        pdc.set(cooldownKey, PersistentDataType.INTEGER, cooldown);
        pdc.set(minDamageKey, PersistentDataType.INTEGER, minDamage);
        pdc.set(maxDamageKey, PersistentDataType.INTEGER, maxDamage);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack makeRPGOffhand(ItemStack itemStack, OffhandType offhandType, int armor){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(offhandTypeKey, PersistentDataType.STRING, offhandType.toString());
        pdc.set(armorKey, PersistentDataType.INTEGER, armor);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack makeRPGArmor(ItemStack itemStack, ArmorType armorType, int armor){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(armorTypeKey, PersistentDataType.STRING, armorType.toString());
        pdc.set(armorKey, PersistentDataType.INTEGER, armor);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack makeRPGAccessory(ItemStack itemStack, AccessoryType accessoryType){
        if (itemStack == null) return null;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(accessoryTypeKey, PersistentDataType.STRING, accessoryType.toString());
        temp.setItemMeta(itemMeta);
        return temp;
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
