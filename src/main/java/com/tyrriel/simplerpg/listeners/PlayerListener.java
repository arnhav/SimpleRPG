package com.tyrriel.simplerpg.listeners;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.systems.configuration.config.ConfigManager;
import com.tyrriel.simplerpg.systems.guis.characterselect.CharacterSelectGUI;
import com.tyrriel.simplerpg.systems.guis.playerinventory.CharacterInventory;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.interactables.InteractableUtil;
import com.tyrriel.simplerpg.systems.playerui.CompassDisplay;
import com.tyrriel.simplerpg.systems.playerui.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;


public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage("");
        player.teleport(ConfigManager.getCharacterSelectLocation());

        Bukkit.getScheduler().runTaskLater(SimpleRPG.getInstance(), ()->
                player.setResourcePack(ConfigManager.getRPurl(), ConfigManager.getRPhash()), 1);
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
        ScoreboardManager.hideScoreboard(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (CharacterManager.characters.containsKey(player)){
            if (player.getOpenInventory().getType() == InventoryType.CRAFTING){
                event.setCancelled(true);
                clearHotbar(player);
                CharacterInventory.openCharacterInventory(player, 0);
            }
        }
    }

    public static void clearHotbar(Player player){
        for (int i = 0; i < 9; i++){
            player.getInventory().setItem(i, null);
        }
        player.getInventory().setItemInOffHand(null);
    }

}
