package com.tyrriel.simplerpg.systems.playerui;

import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class PlayerUIUpdater implements Runnable {

    public PlayerUIUpdater(JavaPlugin plugin){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, 1);
    }

    @Override
    public void run() {
        for (Player player : CharacterManager.characters.keySet()){
            RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
            // Update Compass
            if (!CompassDisplay.playersCompass.containsKey(player)){
                CompassDisplay.createCompass(player);
            }
            KeyedBossBar compass = CompassDisplay.playersCompass.get(player);
            float yaw = CompassDisplay.calcYaw(player);
            String compassDisplay = CompassDisplay.getCompassDisplay(yaw);
            compass.setTitle(ChatColor.translateAlternateColorCodes('&', compassDisplay));

            // Keep player Vanilla health and hunger topped off
            player.setHealth(20);
            player.setFoodLevel(20);

            InventoryView view = player.getOpenInventory();

            if (view.getType() == InventoryType.CRAFTING) {
                //Update health
                int adjustedHealth = (int) Math.round((RPGCharacter.getHealth() * 10) / (double) RPGCharacter.getMaxHealth());
                player.getInventory().setItem(0, ItemUtil.item(Material.RED_STAINED_GLASS_PANE, adjustedHealth, " ", new ArrayList<>()));
                //Update Mana
                int adjustedMana = (int) Math.round((RPGCharacter.getMana() * 10) / (double) RPGCharacter.getMaxMana());
                player.getInventory().setItem(1, ItemUtil.item(Material.BLUE_STAINED_GLASS_PANE, adjustedMana, " ", new ArrayList<>()));
            }

            //Update Level/Exp
            player.setLevel(RPGCharacter.getLevel());
            float adjustedExp = (float) (RPGCharacter.getExp() / RPGCharacter.getMaxExpForLevel());
            player.setExp(adjustedExp);

            /*player.sendActionBar(character.getHealth() + "/" + character.getMaxHealth() + " | " +
                    character.getExp() + "/" + (int)character.getMaxExpForLevel() + " | " +
                    character.getMana() + "/" + character.getMaxMana());*/
        }
    }
}
