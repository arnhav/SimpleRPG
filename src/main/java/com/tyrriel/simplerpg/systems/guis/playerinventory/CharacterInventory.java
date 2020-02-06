package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.items.types.ItemType;
import com.tyrriel.simplerpg.systems.items.RPGItem;
import com.tyrriel.simplerpg.systems.items.Rarity;
import com.tyrriel.simplerpg.systems.items.types.OffhandType;
import com.tyrriel.simplerpg.util.SpecialChars;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tyrriel.simplerpg.systems.items.RPGItem.*;

public class CharacterInventory {
    private static NamespacedKey equipped = new NamespacedKey(SimpleRPG.getInstance(), "Equipped");
    private static NamespacedKey slot = new NamespacedKey(SimpleRPG.getInstance(), "Slot");
    private static NamespacedKey scroll = new NamespacedKey(SimpleRPG.getInstance(), "scroll");

    public static void openCharacterInventory(Player player, int page){
        RPGCharacter character = CharacterManager.characters.get(player);
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.WHITE + adjustedPlayerWealth(character) + SpecialChars.goldCoin());
        List<ItemStack> characterInventory = character.getInventory();
        inventory.setItem(0, CharacterMenu.inventory());
        inventory.setItem(9, CharacterMenu.stats(character));
        inventory.setItem(18, CharacterMenu.quests());
        inventory.setItem(27, CharacterMenu.guild());
        inventory.setItem(36, CharacterMenu.options());
        inventory.setItem(45, CharacterMenu.inventory1());
        if (page > 0){
            inventory.setItem(8, scroll(CharacterMenu.up(), page-1));
        }
        if (page < character.getBackpack()){
            inventory.setItem(53, scroll(CharacterMenu.down(), page+1));
        }
        inventory.setItem(((page+2)*9)-1, CharacterMenu.scroll_indicator());

        if (character.getBackpack() > 0){
            inventory.setItem(48, CharacterMenu.backpack(character.getBackpack()));
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
        // Set weapon
        inventory.setItem(30, equippedItem(character.getWeapon(), character));
        // Set offhand
        inventory.setItem(28, equippedItem(character.getOffhand(), character));
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
        CharacterMenu.open.put(inventory, CharacterMenu.GUIType.INVENTORY);
        player.openInventory(inventory);
    }

    public static String adjustedPlayerWealth(RPGCharacter RPGCharacter){
        String foo = RPGCharacter.getMoney() + " ";
        while (foo.length() < 14){
            foo = " " + foo;
        }
        return foo;
    }

    public static boolean isEquipped(ItemStack itemStack){
        if (itemStack == null) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(equipped, PersistentDataType.INTEGER);
    }

    public static boolean isInventory(ItemStack itemStack){
        if (itemStack == null) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(slot, PersistentDataType.INTEGER);
    }

    public static ItemStack removeEquipped(ItemStack itemStack){
        if (isEquipped(itemStack)) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.remove(equipped);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        return itemStack;
    }

    public static ItemStack removeInventory(ItemStack itemStack){
        if (isInventory(itemStack)) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.remove(slot);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        return itemStack;
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
        itemMeta.setDisplayName(RPGItem.getRarityColor(getRarity(itemStack)) + "" + ChatColor.BOLD + itemMeta.getDisplayName());
        itemMeta.setLore(itemLore(true, itemStack, itemMeta.getLore(), character.getLevel()));
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(equipped, PersistentDataType.INTEGER, 1);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    public static ItemStack unequippedItem(ItemStack itemStack, int s, RPGCharacter character){
        if (itemStack == null) return itemStack;
        ItemStack temp = itemStack.clone();
        ItemMeta itemMeta = temp.getItemMeta();
        itemMeta.setDisplayName(RPGItem.getRarityColor(getRarity(itemStack)) + "" + ChatColor.BOLD + itemMeta.getDisplayName());
        itemMeta.setLore(itemLore(false, itemStack, itemMeta.getLore(), character.getLevel()));
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(slot, PersistentDataType.INTEGER, s);
        temp.setItemMeta(itemMeta);
        return temp;
    }

    private static List<String> itemLore(boolean equipped, ItemStack itemStack, List<String> itemLore, int lvl){
        List<String> lore = new ArrayList<>();
        Rarity rarity = getRarity(itemStack);
        ItemType itemType = getItemType(itemStack);
        if (itemType == ItemType.JUNK){
            lore.add(RPGItem.getRarityColor(rarity) +
                    StringUtils.capitalize(rarity.toString().toLowerCase()) + " " + StringUtils.capitalize(itemType.toString().toLowerCase()));
        }
        if (itemType == ItemType.WEAPON){
            lore.add(RPGItem.getRarityColor(rarity) +
                    StringUtils.capitalize(rarity.toString().toLowerCase()) + " " + StringUtils.capitalize(getWeaponType(itemStack).toString().toLowerCase()));
            lore.add(" ");
            lore.add(ChatColor.WHITE + SpecialChars.swords() + ChatColor.RESET + ChatColor.WHITE + " " + getMinDamage(itemStack) + "-" + getMaxDamage(itemStack) + ChatColor.GRAY + " Damage");
        }
        if (itemType == ItemType.OFFHAND){
            lore.add(RPGItem.getRarityColor(rarity) +
                    StringUtils.capitalize(rarity.toString().toLowerCase()) + " " + StringUtils.capitalize(getOffhandType(itemStack).toString().toLowerCase()));
            if (getOffhandType(itemStack) == OffhandType.SHIELD){
                lore.add(" ");
                lore.add(ChatColor.WHITE + SpecialChars.shield() + ChatColor.RESET + ChatColor.WHITE + " " + getArmor(itemStack) + ChatColor.GRAY + " Armor");
            }
        }
        if (itemType == ItemType.ARMOR){
            lore.add(RPGItem.getRarityColor(rarity) +
                    StringUtils.capitalize(rarity.toString().toLowerCase()) + " " + StringUtils.capitalize(getArmorType(itemStack).toString().toLowerCase()));
            lore.add(" ");
            lore.add(ChatColor.WHITE + SpecialChars.shield() + ChatColor.RESET + ChatColor.WHITE + " " + getArmor(itemStack) + ChatColor.GRAY + " Armor");
        }
        if (itemType == ItemType.ACCESSORY){
            lore.add(RPGItem.getRarityColor(rarity) +
                    StringUtils.capitalize(rarity.toString().toLowerCase()) + " " + StringUtils.capitalize(getAccessoryType(itemStack).toString().toLowerCase()));
        }
        lore.add(" ");
        if (itemType != ItemType.JUNK){
            int[] stats = getAttributes(itemStack);
            for (int i = 0; i < stats.length; i++){
                int val = stats[i];
                if (val > 0){
                    lore.add(ChatColor.BLUE + "+" + val + " " + getStatName(i));
                }
                if (val < 0){
                    lore.add(ChatColor.RED + "" + val + " " + getStatName(i));
                }
            }
        }
        if (itemLore != null){
            lore.add(" ");
            lore.addAll(itemLore);
        }
        int reqLvl = getLevel(itemStack);
        String reqL;
        if (reqLvl > lvl){
            reqL = ChatColor.RED + "Required Level: " + reqLvl;
        } else {
            reqL = ChatColor.WHITE + "Required Level: " + reqLvl;
        }
        if (reqLvl < 10){
            reqL = "  " + reqL;
        }
        if (reqLvl < 100){
            reqL = " " + reqL;
        }
        if (equipped){
            lore.addAll(equippedLore(itemStack, reqL));
        } else {
            lore.addAll(unequippedLore(itemStack, reqL));
        }
        return lore;
    }

    private static List<String> equippedLore(ItemStack itemStack, String reqL){
        return Arrays.asList(
                " ",
                ChatColor.WHITE + SpecialChars.leftClick() + " Unequip",
                ChatColor.WHITE + SpecialChars.rightClick() + " Drop           " + reqL,
                ChatColor.GRAY + "------------------------------",
                ChatColor.YELLOW + "Sell Value: " + ChatColor.WHITE + getValue(itemStack) + " " + SpecialChars.goldCoin()
        );
    }

    private static List<String> unequippedLore(ItemStack itemStack, String reqL){
        List<String> lore = new ArrayList<>();
        if (getItemType(itemStack) != ItemType.JUNK) {
            lore.addAll(Arrays.asList(
                    " ",
                    ChatColor.WHITE + SpecialChars.leftClick() + " Equip",
                    ChatColor.WHITE + SpecialChars.rightClick() + " Drop           " + reqL
            ));
        }
        lore.addAll(Arrays.asList(
                ChatColor.GRAY + "------------------------------",
                ChatColor.YELLOW + "Sell Value: " + ChatColor.WHITE + getValue(itemStack) + " " + SpecialChars.goldCoin()
        ));
        return lore;
    }

    private static String getStatName(int val){
        if (val == 0) return "Strength";
        if (val == 1) return "Intelligence";
        if (val == 2) return "Agility";
        if (val == 3) return "Vitality";
        if (val == 4) return "Dexterity";
        if (val == 5) return "Luck";
        return "";
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
}
