package com.tyrriel.simplerpg.systems.combat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CombatListeners implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){

    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event){

    }

}
