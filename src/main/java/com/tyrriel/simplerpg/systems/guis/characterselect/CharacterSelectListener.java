package com.tyrriel.simplerpg.systems.guis.characterselect;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.configuration.config.ConfigManager;
import com.tyrriel.simplerpg.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CharacterSelectListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory top = event.getInventory();
        if (!CharacterSelectGUI.open.containsKey(top)) return;

        CharacterSelectGUI.open.remove(top);

        if (event.getReason() == InventoryCloseEvent.Reason.PLAYER) {
            Bukkit.getScheduler().runTaskLater(SimpleRPG.getInstance(),()-> CharacterSelectGUI.openCharacterSelector(player), 1);
        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        Inventory top = event.getInventory();
        if (!CharacterSelectGUI.open.containsKey(top)) return;

        event.setCancelled(true);
        CharacterSelectGUI.GUIType guiType = CharacterSelectGUI.open.get(top);
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;

        if (guiType == CharacterSelectGUI.GUIType.CHARACTER){
            if (itemStack.equals(CharacterSelectGUI.createNew())){
                CharacterSelectGUI.openJobSelector(player);
            }
        }

        if (guiType == CharacterSelectGUI.GUIType.JOB){
            if (itemStack.equals(CharacterSelectGUI.fighter())){
                createNewCharacter(player, Job.FIGHTER);
            }
            if (itemStack.equals(CharacterSelectGUI.sorcerer())){
                createNewCharacter(player, Job.SORCERER);
            }
            if (itemStack.equals(CharacterSelectGUI.rouge())){
                createNewCharacter(player, Job.ROUGE);
            }
            if (itemStack.equals(CharacterSelectGUI.cleric())){
                createNewCharacter(player, Job.CLERIC);
            }
            if (itemStack.equals(CharacterSelectGUI.ranger())){
                createNewCharacter(player, Job.RANGER);
            }
        }
    }

    public void createNewCharacter(Player player, Job job){
        RPGCharacter character = new RPGCharacter(player.getUniqueId(), job);
        CharacterManager.characters.put(player, character);
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        player.getInventory().setHeldItemSlot(2);
        player.getInventory().setItem(2, ItemUtil.makeWeaponItem(character.getWeapon()));
        player.getInventory().setItemInOffHand(ItemUtil.makeOffhandItem(character.getOffhand()));
        player.getInventory().setItem(8, character.getConsumable());
        player.getInventory().setHelmet(ItemUtil.makeArmorItem(character.getHelm()));
        player.getInventory().setChestplate(ItemUtil.makeArmorItem(character.getChest()));
        player.getInventory().setLeggings(ItemUtil.makeArmorItem(character.getLegs()));
        player.getInventory().setBoots(ItemUtil.makeArmorItem(character.getBoots()));
        Bukkit.getScheduler().runTaskLater(SimpleRPG.getInstance(), ()-> player.teleport(ConfigManager.getNewStartLocation()), 1);
    }

}
