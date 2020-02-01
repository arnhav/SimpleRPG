package com.tyrriel.simplerpg.systems.interactables.item;

import com.tyrriel.simplerpg.SimpleRPG;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class LootUtil {

    public static void dropItem(Player player, ItemStack itemStack){
        spawnLoot(player.getLocation().add(0, -1.5, 0), itemStack);
    }

    public static void spawnLoot(Location location, ItemStack itemStack){
        ArmorStand loot = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ArmorStand indicator = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        loot.setMarker(true);
        loot.setVisible(false);
        loot.setItem(EquipmentSlot.HEAD, itemStack);
        indicator.setVisible(false);
        indicator.setMetadata("Interactable", new FixedMetadataValue(SimpleRPG.getInstance(), "item"));
        loot.addPassenger(indicator);
    }

}
