package com.tyrriel.simplerpg.systems.mounts;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MountsListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if (CharacterManager.characters.containsKey(player)){
            event.setCancelled(true);
        }
    }

}
