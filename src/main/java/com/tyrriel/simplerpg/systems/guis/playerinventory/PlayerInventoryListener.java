package com.tyrriel.simplerpg.systems.guis.playerinventory;

import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.interactables.item.LootUtil;
import com.tyrriel.simplerpg.systems.items.types.ItemType;
import com.tyrriel.simplerpg.listeners.PlayerListener;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.items.RPGItem;
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

        RPGCharacter character = CharacterManager.characters.get(player);
        if (event.getReason() == InventoryCloseEvent.Reason.PLAYER){
            player.getInventory().setItem(2, ItemUtil.makeWeaponItem(character.getWeapon()));
            player.getInventory().setItemInOffHand(ItemUtil.makeOffhandItem(character.getOffhand()));
            player.getInventory().setItem(8, character.getConsumable());
            player.getInventory().setHelmet(ItemUtil.makeArmorItem(character.getHelm()));
            player.getInventory().setChestplate(ItemUtil.makeArmorItem(character.getChest()));
            player.getInventory().setLeggings(ItemUtil.makeArmorItem(character.getLegs()));
            player.getInventory().setBoots(ItemUtil.makeArmorItem(character.getBoots()));
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
        RPGCharacter character = CharacterManager.characters.get(player);

        if (itemStack == null) return;

        if (guiType == CharacterMenu.GUIType.INVENTORY){
            if (itemStack.equals(CharacterMenu.stats(character))){
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
                    ItemType itemType;
                    if (event.getSlot() == 28){
                        itemType = ItemType.OFFHAND;
                    } else {
                        itemType = RPGItem.getItemType(itemStack);
                    }
                    if (modifySlot(character, itemType, itemStack, null) == 0){
                        openCharacterInventory(player, 0);
                    }
                }
                if (event.getClick() == ClickType.RIGHT){
                    // TODO: Figure something out for this
                }
            }
            if (isInventory(itemStack)){
                if (event.getClick() == ClickType.LEFT){
                    if (RPGItem.getItemType(itemStack) != ItemType.JUNK){
                        if ((RPGItem.getLevel(itemStack) <= character.getLevel()) && (RPGItem.getJob(itemStack) == Job.NONE || RPGItem.getJob(itemStack) == character.getJob())) {
                            int pos = getSlot(itemStack);
                            ItemStack temp = character.getInventory().get(pos);
                            if (modifySlot(character, RPGItem.getItemType(itemStack), itemStack, temp) == 0) {
                                character.removeFromInv(pos);
                                openCharacterInventory(player, 0);
                            }
                        }
                    }
                }
                if (event.getClick() == ClickType.RIGHT){
                    int pos = getSlot(itemStack);
                    ItemStack temp = character.getInventory().get(pos).clone();
                    character.removeFromInv(pos);
                    openCharacterInventory(player, 0);
                    LootUtil.dropItem(player, removeInventory(temp));
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
                if (character.getUnallocatedstats() > 0){
                    character.setStrength(character.getStrength() + 1);
                    character.setUnallocatedstats(character.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isIntelligence(itemStack)){
                if (character.getUnallocatedstats() > 0){
                    character.setIntelligence(character.getIntelligence() + 1);
                    character.setUnallocatedstats(character.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isSpeed(itemStack)){
                if (character.getUnallocatedstats() > 0){
                    character.setSpeed(character.getSpeed() + 1);
                    character.setUnallocatedstats(character.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isVitality(itemStack)){
                if (character.getUnallocatedstats() > 0){
                    character.setVitality(character.getVitality() + 1);
                    character.setUnallocatedstats(character.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isDexterity(itemStack)){
                if (character.getUnallocatedstats() > 0){
                    character.setDexterity(character.getDexterity() + 1);
                    character.setUnallocatedstats(character.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
            if (CharacterStats.isLuck(itemStack)){
                if (character.getUnallocatedstats() > 0){
                    character.setLuck(character.getLuck() + 1);
                    character.setUnallocatedstats(character.getUnallocatedstats() - 1);
                    CharacterStats.openCharacterStats(player);
                }
            }
        }
        
        if (guiType == CharacterMenu.GUIType.QUESTS){
            if (itemStack.equals(CharacterMenu.stats(character))){
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
            if (itemStack.equals(CharacterMenu.stats(character))){
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
            if (itemStack.equals(CharacterMenu.stats(character))){
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

    private int modifySlot(RPGCharacter character, ItemType itemType, ItemStack original, ItemStack stack){
        switch (itemType){
            case WEAPON:
                if (character.getWeapon() == null || stack == null){
                    if (character.addItemToInv(removeEquipped(character.getWeapon())) == 0) {
                        character.setWeapon(removeInventory(stack));
                        return 0;
                    }
                } else {
                    if (character.getOffhand() == null){
                        return modifySlot(character, ItemType.OFFHAND, original, stack);
                    }
                }
                break;
            case OFFHAND:
                if (character.addItemToInv(removeEquipped(character.getOffhand())) == 0) {
                    character.setOffhand(removeInventory(stack));
                    return 0;
                }
                break;
            case CONSUMABLE:
                if (character.addItemToInv(removeEquipped(character.getConsumable())) == 0) {
                    character.setConsumable(removeInventory(stack));
                    return 0;
                }
                break;
            case ARMOR:
                return modifyArmorSlot(character, original, stack);
            case ACCESSORY:
                return modifyAccessorySlot(character, original, stack);
        }
        return -1;
    }

    private int modifyArmorSlot(RPGCharacter rpgCharacter, ItemStack original, ItemStack stack){
        int value = -1;
        switch (RPGItem.getArmorType(original)){
            case HELMET:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getHelm()));
                if (value == 0)
                    rpgCharacter.setHelm(removeInventory(stack));
                break;
            case CHESTPLATE:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getChest()));
                if (value == 0)
                    rpgCharacter.setChest(removeInventory(stack));
                break;
            case LEGGINGS:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getLegs()));
                if (value == 0)
                    rpgCharacter.setLegs(removeInventory(stack));
                break;
            case BOOTS:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getBoots()));
                if (value == 0)
                    rpgCharacter.setBoots(removeInventory(stack));
                break;
        }
        return value;
    }

    private int modifyAccessorySlot(RPGCharacter rpgCharacter, ItemStack original, ItemStack stack){
        int value = -1;
        switch (RPGItem.getAccessoryType(original)){
            case NECKLACE:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getNecklace()));
                if (value == 0)
                    rpgCharacter.setNecklace(removeInventory(stack));
                break;
            case EARRINGS:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getEarrings()));
                if (value == 0)
                    rpgCharacter.setEarrings(removeInventory(stack));
                break;
            case RING:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getRing()));
                if (value == 0)
                    rpgCharacter.setRing(removeInventory(stack));
                break;
            case BRACERS:
                value = rpgCharacter.addItemToInv(removeEquipped(rpgCharacter.getBracers()));
                if (value == 0)
                    rpgCharacter.setBracers(removeInventory(stack));
                break;
        }
        return value;
    }
}
