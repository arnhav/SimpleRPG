package com.tyrriel.simplerpg.systems.interactables;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class InteractablesListener implements Listener {

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        if (CharacterManager.characters.containsKey(player)){
            event.setCancelled(true);
            InteractableUtil.checkForInteractable(player);
        }
    }

}
