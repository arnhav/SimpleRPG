package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.interactables.item.LootUtil;
import com.tyrriel.simplerpg.systems.items.ItemType;
import com.tyrriel.simplerpg.listeners.PlayerListener;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.items.RPGItem;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.items.RPGItemUtil;
import com.tyrriel.simplerpg.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.tyrriel.simplerpg.systems.guis.playerinventory.PlayerInventoryGUI.*;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory top = event.getInventory();
        if (!open.containsKey(top)) return;

        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        if (event.getReason() == InventoryCloseEvent.Reason.PLAYER){
            player.getInventory().setItem(2, ItemUtil.makeWeaponItem(RPGCharacter.getWeapon()));
            player.getInventory().setItem(8, RPGCharacter.getConsumable());
            player.getInventory().setHelmet(ItemUtil.makeArmorItem(RPGCharacter.getHelm()));
            player.getInventory().setChestplate(ItemUtil.makeArmorItem(RPGCharacter.getChest()));
            player.getInventory().setLeggings(ItemUtil.makeArmorItem(RPGCharacter.getLegs()));
            player.getInventory().setBoots(ItemUtil.makeArmorItem(RPGCharacter.getBoots()));
        }

        open.remove(top);
    }

    @EventHandler
    public void onClick (InventoryClickEvent event) {
        Inventory top = event.getInventory();
        if (!open.containsKey(top)) return;

        event.setCancelled(true);
        GUIType guiType = open.get(top);
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);

        if (itemStack == null) return;

        if (guiType == GUIType.INVENTORY){
            if (itemStack.equals(stats(RPGCharacter))){
                openCharacterStats(player);
            }
            if (itemStack.equals(quests())){
                openCharacterQuests(player);
            }
            if (itemStack.equals(guild())){
                openPlayerGuild(player);
            }
            if (itemStack.equals(options())){
                openPlayerOptions(player);
            }

            if (ItemUtil.isScroll(itemStack)){
                int page = ItemUtil.getScroll(itemStack);
                openCharacterInventory(player, page);
            }
            if (ItemUtil.isEquipped(itemStack)){
                RPGItem rpgItem = RPGItemUtil.getRPGItem(itemStack);
                if (event.getClick() == ClickType.LEFT){
                    if (modifySlot(RPGCharacter, rpgItem, null) == 0){
                        ItemUtil.removeEquipped(itemStack);
                        openCharacterInventory(player, 0);
                    }
                }
                if (event.getClick() == ClickType.RIGHT){
                    // TODO: Figure something out for this
                }
            }
            if (ItemUtil.isInventory(itemStack)){
                RPGItem rpgItem = RPGItemUtil.getRPGItem(itemStack);
                if (event.getClick() == ClickType.LEFT){
                    if (rpgItem.getItemType() != ItemType.JUNK){
                        if (rpgItem.getLevel() <= RPGCharacter.getLevel()) {
                            int pos = ItemUtil.getSlot(itemStack);
                            ItemStack temp = RPGCharacter.getInventory().get(pos).getItemStack();
                            ItemUtil.removeInventory(temp);
                            if (modifySlot(RPGCharacter, rpgItem, temp) == 0) {
                                RPGCharacter.removeFromInv(pos);
                                openCharacterInventory(player, 0);
                            }
                        }
                    }
                }
                if (event.getClick() == ClickType.RIGHT){
                    int pos = ItemUtil.getSlot(itemStack);
                    ItemStack temp = RPGCharacter.getInventory().get(pos).getItemStack();
                    ItemUtil.removeInventory(temp);
                    RPGCharacter.removeFromInv(pos);
                    LootUtil.dropItem(player, temp);
                    openCharacterInventory(player, 0);
                }
            }

        }

        if (guiType == GUIType.STATS){
            if (itemStack.equals(quests())){
                openCharacterQuests(player);
            }
            if (itemStack.equals(guild())){
                openPlayerGuild(player);
            }
            if (itemStack.equals(options())){
                openPlayerOptions(player);
            }
            if (itemStack.equals(inventory())){
                openCharacterInventory(player, 0);
            }

            if (ItemUtil.isStrength(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setStrength(RPGCharacter.getStrength() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    openCharacterStats(player);
                }
            }
            if (ItemUtil.isIntelligence(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setIntelligence(RPGCharacter.getIntelligence() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    openCharacterStats(player);
                }
            }
            if (ItemUtil.isSpeed(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setSpeed(RPGCharacter.getSpeed() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    openCharacterStats(player);
                }
            }
            if (ItemUtil.isVitality(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setVitality(RPGCharacter.getVitality() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    openCharacterStats(player);
                }
            }
            if (ItemUtil.isDexterity(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setDexterity(RPGCharacter.getDexterity() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    openCharacterStats(player);
                }
            }
            if (ItemUtil.isLuck(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setLuck(RPGCharacter.getLuck() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    openCharacterStats(player);
                }
            }
        }
        
        if (guiType == GUIType.QUESTS){
            if (itemStack.equals(stats(RPGCharacter))){
                openCharacterStats(player);
            }
            if (itemStack.equals(guild())){
                openPlayerGuild(player);
            }
            if (itemStack.equals(options())){
                openPlayerOptions(player);
            }
            if (itemStack.equals(inventory())){
                openCharacterInventory(player, 0);
            }
        }

        if (guiType == GUIType.GUILD){
            if (itemStack.equals(stats(RPGCharacter))){
                openCharacterStats(player);
            }
            if (itemStack.equals(quests())){
                openCharacterQuests(player);
            }
            if (itemStack.equals(options())){
                openPlayerOptions(player);
            }
            if (itemStack.equals(inventory())){
                openCharacterInventory(player, 0);
            }
        }

        if (guiType == GUIType.OPTIONS){
            if (itemStack.equals(stats(RPGCharacter))){
                openCharacterStats(player);
            }
            if (itemStack.equals(quests())){
                openCharacterQuests(player);
            }
            if (itemStack.equals(guild())){
                openPlayerGuild(player);
            }
            if (itemStack.equals(inventory())){
                openCharacterInventory(player, 0);
            }
        }

        PlayerListener.clearHotbar(player);
    }

    private int modifySlot(RPGCharacter RPGCharacter, RPGItem rpgItem, ItemStack itemStack){
        int value = -1;
        RPGItem returnedItem;
        switch (rpgItem.getItemType()){
            case HELMET:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getHelm());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setHelm(itemStack);
                break;
            case CHESTPLATE:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getChest());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setChest(itemStack);
                break;
            case LEGGINGS:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getLegs());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setLegs(itemStack);
                break;
            case BOOTS:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getBoots());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setBoots(itemStack);
                break;
            case WEAPON:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getWeapon());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setWeapon(itemStack);
                break;
            case CONSUMABLE:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getConsumable());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setConsumable(itemStack);
                break;
            case NECKLACE:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getNecklace());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setNecklace(itemStack);
                break;
            case EARRINGS:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getEarrings());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setEarrings(itemStack);
                break;
            case RING:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getEarrings());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setRing(itemStack);
                break;
            case BRACERS:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getBracers());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setBracers(itemStack);
                break;
            case PENDANT:
                returnedItem = RPGItemUtil.getRPGItem(RPGCharacter.getPendant());
                value = RPGCharacter.addItemToInv(returnedItem);
                if (value == 0)
                    RPGCharacter.setPendant(itemStack);
                break;
        }
        return value;
    }
}
