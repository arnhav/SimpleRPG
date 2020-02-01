package com.tyrriel.simplerpg.listeners;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.guis.characterselect.CharacterSelectGUI;
import com.tyrriel.simplerpg.systems.guis.playerinventory.PlayerInventoryGUI;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.interactables.InteractableUtil;
import com.tyrriel.simplerpg.systems.playerui.CompassDisplay;
import com.tyrriel.simplerpg.util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;


public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage("");
        player.teleport(FileManager.getCharacterSelectLocation());

        Bukkit.getScheduler().runTaskLater(SimpleRPG.getInstance(), ()->
                player.setResourcePack(FileManager.getRPurl(), FileManager.getRPhash()), 1);
    }

    @EventHandler
    public void rpStatus(PlayerResourcePackStatusEvent event){
        Player player = event.getPlayer();
        switch (event.getStatus()){
            case DECLINED:
                player.kickPlayer("Thank you for checking out the server! Our server requires the use of a resource pack," +
                        " please enable it to play on the server.");
                break;
            case SUCCESSFULLY_LOADED:
                CharacterSelectGUI.openCharacterSelector(player);
                player.sendMessage(ChatColor.GREEN + "Thank you for using our resource pack!");
                break;
            case FAILED_DOWNLOAD:
                player.kickPlayer("Uh oh! Something went wrong. Please reconnect and try again.");
                break;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage("");
        // Save character to database

        CharacterManager.characters.remove(player);
        CompassDisplay.removeCompass(player);
        player.getInventory().clear();
        player.setLevel(0);
        player.setExp(0);
    }

    @EventHandler
    public void onChangeItem (PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        if (CharacterManager.characters.containsKey(player)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if (CharacterManager.characters.containsKey(player)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        if (CharacterManager.characters.containsKey(player)){
            event.setCancelled(true);
            InteractableUtil.checkForInteractable(player);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (CharacterManager.characters.containsKey(player)){
            if (player.getOpenInventory().getType() == InventoryType.CRAFTING){
                event.setCancelled(true);
                clearHotbar(player);
                PlayerInventoryGUI.openCharacterInventory(player, 0);
            }
        }
    }

    public static void clearHotbar(Player player){
        for (int i = 0; i < 9; i++){
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
        }
    }

}
