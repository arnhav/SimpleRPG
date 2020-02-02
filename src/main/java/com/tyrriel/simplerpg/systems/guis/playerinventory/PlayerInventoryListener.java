package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.interactables.item.LootUtil;
import com.tyrriel.simplerpg.systems.items.ItemType;
import com.tyrriel.simplerpg.listeners.PlayerListener;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
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

import static com.tyrriel.simplerpg.systems.guis.playerinventory.CharacterInventory.*;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory top = event.getInventory();
        if (!CharacterMenu.open.containsKey(top)) return;

        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        if (event.getReason() == InventoryCloseEvent.Reason.PLAYER){
            player.getInventory().setItem(2, ItemUtil.makeWeaponItem(RPGCharacter.getWeapon()));
            player.getInventory().setItem(8, RPGCharacter.getConsumable());
            player.getInventory().setHelmet(ItemUtil.makeArmorItem(RPGCharacter.getHelm()));
            player.getInventory().setChestplate(ItemUtil.makeArmorItem(RPGCharacter.getChest()));
            player.getInventory().setLeggings(ItemUtil.makeArmorItem(RPGCharacter.getLegs()));
            player.getInventory().setBoots(ItemUtil.makeArmorItem(RPGCharacter.getBoots()));
        }

        CharacterMenu.open.remove(top);
    }

    @EventHandler
    public void onClick (InventoryClickEvent event) {
        Inventory top = event.getInventory();
        if (!CharacterMenu.open.containsKey(top)) return;

        event.setCancelled(true);
        CharacterMenu.GUIType guiType = CharacterMenu.open.get(top);
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);

        if (itemStack == null) return;

        if (guiType == CharacterMenu.GUIType.INVENTORY){
            if (itemStack.equals(CharacterMenu.stats(RPGCharacter))){
                CharacterStats.openCharacterStats(player);
            }
            if (itemStack.equals(CharacterMenu.quests())){
                CharacterQuests.openCharacterQuests(player);
            }
            if (itemStack.equals(CharacterMenu.guild())){
                PlayerGuild.openPlayerGuild(player);
            }
            if (itemStack.equals(CharacterMenu.options())){
                PlayerOptions.openPlayerOptions(player);
            }

            if (isScroll(itemStack)){
                int page = getScroll(itemStack);
                openCharacterInventory(player, page);
            }
            if (isEquipped(itemStack)){
                if (event.getClick() == ClickType.LEFT){
                    if (modifySlot(RPGCharacter, RPGItemUtil.getItemType(itemStack), null) == 0){
                        removeEquipped(itemStack);
                        openCharacterInventory(player, 0);
                    }
                }
                if (event.getClick() == ClickType.RIGHT){
                    // TODO: Figure something out for this
                }
            }
            if (isInventory(itemStack)){
                if (event.getClick() == ClickType.LEFT){
                    if (RPGItemUtil.getItemType(itemStack) != ItemType.JUNK){
                        if (RPGItemUtil.getValue(itemStack) <= RPGCharacter.getLevel()) {
                            int pos = getSlot(itemStack);
                            removeInventory(itemStack);
                            if (modifySlot(RPGCharacter, RPGItemUtil.getItemType(itemStack), itemStack) == 0) {
                                RPGCharacter.removeFromInv(pos);
                                openCharacterInventory(player, 0);
                            }
                        }
                    }
                }
                if (event.getClick() == ClickType.RIGHT){
                    int pos = getSlot(itemStack);
                    ItemStack temp = RPGCharacter.getInventory().get(pos);
                    removeInventory(temp);
                    RPGCharacter.removeFromInv(pos);
                    LootUtil.dropItem(player, temp);
                    openCharacterInventory(player, 0);
                }
            }

        }

        if (guiType == CharacterMenu.GUIType.STATS){
            if (itemStack.equals(CharacterMenu.quests())){
                CharacterQuests.openCharacterQuests(player);
            }
            if (itemStack.equals(CharacterMenu.guild())){
                PlayerGuild.openPlayerGuild(player);
            }
            if (itemStack.equals(CharacterMenu.options())){
                PlayerOptions.openPlayerOptions(player);
            }
            if (itemStack.equals(CharacterMenu.inventory())){
                openCharacterInventory(player, 0);
            }

            if (CharacterStats.isStrength(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setStrength(RPGCharacter.getStrength() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isIntelligence(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setIntelligence(RPGCharacter.getIntelligence() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isSpeed(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setSpeed(RPGCharacter.getSpeed() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isVitality(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setVitality(RPGCharacter.getVitality() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isDexterity(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setDexterity(RPGCharacter.getDexterity() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isLuck(itemStack)){
                if (RPGCharacter.getUnallocatedstats() > 0){
                    RPGCharacter.setLuck(RPGCharacter.getLuck() + 1);
                    RPGCharacter.setUnallocatedstats(RPGCharacter.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
        }
        
        if (guiType == CharacterMenu.GUIType.QUESTS){
            if (itemStack.equals(CharacterMenu.stats(RPGCharacter))){
                CharacterStats.openCharacterStats(player);
            }
            if (itemStack.equals(CharacterMenu.guild())){
                PlayerGuild.openPlayerGuild(player);
            }
            if (itemStack.equals(CharacterMenu.options())){
                PlayerOptions.openPlayerOptions(player);
            }
            if (itemStack.equals(CharacterMenu.inventory())){
                openCharacterInventory(player, 0);
            }
        }

        if (guiType == CharacterMenu.GUIType.GUILD){
            if (itemStack.equals(CharacterMenu.stats(RPGCharacter))){
                CharacterStats.openCharacterStats(player);
            }
            if (itemStack.equals(CharacterMenu.quests())){
                CharacterQuests.openCharacterQuests(player);
            }
            if (itemStack.equals(CharacterMenu.options())){
                PlayerOptions.openPlayerOptions(player);
            }
            if (itemStack.equals(CharacterMenu.inventory())){
                openCharacterInventory(player, 0);
            }
        }

        if (guiType == CharacterMenu.GUIType.OPTIONS){
            if (itemStack.equals(CharacterMenu.stats(RPGCharacter))){
                CharacterStats.openCharacterStats(player);
            }
            if (itemStack.equals(CharacterMenu.quests())){
                CharacterQuests.openCharacterQuests(player);
            }
            if (itemStack.equals(CharacterMenu.guild())){
                PlayerGuild.openPlayerGuild(player);
            }
            if (itemStack.equals(CharacterMenu.inventory())){
                openCharacterInventory(player, 0);
            }
        }

        PlayerListener.clearHotbar(player);
    }

    private int modifySlot(RPGCharacter RPGCharacter, ItemType itemType, ItemStack itemStack){
        int value = -1;
        switch (itemType){
            case HELMET:
                value = RPGCharacter.addItemToInv(RPGCharacter.getHelm());
                if (value == 0)
                    RPGCharacter.setHelm(itemStack);
                break;
            case CHESTPLATE:
                value = RPGCharacter.addItemToInv(RPGCharacter.getChest());
                if (value == 0)
                    RPGCharacter.setChest(itemStack);
                break;
            case LEGGINGS:
                value = RPGCharacter.addItemToInv(RPGCharacter.getLegs());
                if (value == 0)
                    RPGCharacter.setLegs(itemStack);
                break;
            case BOOTS:
                value = RPGCharacter.addItemToInv(RPGCharacter.getBoots());
                if (value == 0)
                    RPGCharacter.setBoots(itemStack);
                break;
            case WEAPON:
                value = RPGCharacter.addItemToInv(RPGCharacter.getWeapon());
                if (value == 0)
                    RPGCharacter.setWeapon(itemStack);
                break;
            case CONSUMABLE:
                value = RPGCharacter.addItemToInv(RPGCharacter.getConsumable());
                if (value == 0)
                    RPGCharacter.setConsumable(itemStack);
                break;
            case NECKLACE:
                value = RPGCharacter.addItemToInv(RPGCharacter.getNecklace());
                if (value == 0)
                    RPGCharacter.setNecklace(itemStack);
                break;
            case EARRINGS:
                value = RPGCharacter.addItemToInv(RPGCharacter.getEarrings());
                if (value == 0)
                    RPGCharacter.setEarrings(itemStack);
                break;
            case RING:
                value = RPGCharacter.addItemToInv(RPGCharacter.getRing());
                if (value == 0)
                    RPGCharacter.setRing(itemStack);
                break;
            case BRACERS:
                value = RPGCharacter.addItemToInv(RPGCharacter.getBracers());
                if (value == 0)
                    RPGCharacter.setBracers(itemStack);
                break;
        }
        return value;
    }
}
