package com.tyrriel.simplerpg.systems.interactables;

import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class InteractableUtil {

    public static void checkForInteractable(Player player){
        Entity entity = player.getTargetEntity(3);
        if (entity != null && entity.hasMetadata("Interactable")){
            MetadataValue metadataValue = null;
            List<MetadataValue> values = entity.getMetadata("Interactable");
            for (MetadataValue value : values){
                metadataValue = value;
            }
            if (metadataValue == null) return;
            if (metadataValue.asString().equalsIgnoreCase("npc")){

            }
            if (metadataValue.asString().equalsIgnoreCase("dialogue")){

            }
            if (metadataValue.asString().equalsIgnoreCase("item")){
                loot(player, entity.getVehicle());
            }
        }
    }

    private static void loot(Player player, Entity entity){
        RPGCharacter RPGCharacter = CharacterManager.characters.get(player);
        if (RPGCharacter == null) return;
        if (!(entity instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) entity;
        ItemStack itemStack = armorStand.getItem(EquipmentSlot.HEAD);
        if (RPGCharacter.addItemToInv(itemStack) == -1) return;
        for (Entity pass : armorStand.getPassengers()){
            pass.remove();
        }
        armorStand.remove();
        player.sendActionBar("+ " + itemStack.getItemMeta().getDisplayName());
    }

}
