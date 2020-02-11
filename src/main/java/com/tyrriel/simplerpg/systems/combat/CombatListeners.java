package com.tyrriel.simplerpg.systems.combat;

import com.tyrriel.simplerpg.SimpleRPG;
import com.tyrriel.simplerpg.packetwrappers.WrapperPlayServerAnimation;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.systems.items.RPGItem;
import com.tyrriel.simplerpg.systems.items.types.ItemType;
import com.tyrriel.simplerpg.systems.items.types.WeaponType;
import com.tyrriel.simplerpg.util.ParticleEffectManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class CombatListeners implements Listener {

    @EventHandler
    public void onUseSkill(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        if (CharacterManager.characters.containsKey(player)){
            event.setCancelled(true);
            player.getInventory().setHeldItemSlot(2);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){

    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event){
        Entity damagedEntity = event.getEntity();
        Entity damagerEntity = event.getDamager();

        if (damagedEntity instanceof Player){
            event.setCancelled(true);
            RPGCharacter character = CharacterManager.characters.get(damagedEntity);
            if (character != null){
                damagedEntity.playEffect(EntityEffect.HURT);
            }
        } else {

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        RPGCharacter character = CharacterManager.characters.get(player);
        Material material = player.getInventory().getItemInMainHand().getType();
        if (character != null && (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) &&
            player.getCooldown(material) == 0){
            if (!player.getScoreboardTags().contains("Drop")){
                int cooldown = 1;
                if (character.getWeapon() != null){
                    WeaponType weaponType = RPGItem.getWeaponType(character.getWeapon());
                    cooldown = RPGItem.getCooldown(character.getWeapon());
                    if (weaponType == WeaponType.HAND_CROSSBOW || weaponType == WeaponType.BOW){

                    }
                    else if (weaponType == WeaponType.WAND){

                    } else {
                        if (weaponType == WeaponType.DAGGER){
                            ParticleEffectManager.doPhysicalAttack(player, 1);
                        } else {
                            ParticleEffectManager.doPhysicalAttack(player, 1.5);
                        }
                    }
                } else {
                    ParticleEffectManager.doPhysicalAttack(player, 0.75);
                }
                if (character.getOffhand() != null){
                    if (RPGItem.getItemType(character.getOffhand()) == ItemType.WEAPON){
                        Bukkit.getScheduler().runTaskLater(SimpleRPG.getInstance(), ()->{
                            WeaponType type = RPGItem.getWeaponType(character.getOffhand());
                            if (type == WeaponType.HAND_CROSSBOW){

                            } else {
                                if (type == WeaponType.DAGGER) {
                                    ParticleEffectManager.doPhysicalAttack(player, 1);
                                } else {
                                    ParticleEffectManager.doPhysicalAttack(player, 1.5);
                                }
                                WrapperPlayServerAnimation packet = new WrapperPlayServerAnimation();
                                packet.setEntityID(player.getEntityId());
                                packet.setAnimation(3);
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    packet.sendPacket(online);
                                }
                            }
                        }, 5);
                    }
                }
                player.setCooldown(material, cooldown * 20);
            } else {
                player.removeScoreboardTag("Drop");
            }
        } else {
            event.setCancelled(true);
        }
    }

}
