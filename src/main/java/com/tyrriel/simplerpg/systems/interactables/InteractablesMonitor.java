package com.tyrriel.simplerpg.systems.interactables;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.tyrriel.simplerpg.packetwrappers.WrapperPlayServerEntityMetadata;
import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.WeakHashMap;

public class InteractablesMonitor implements Runnable {

    public static WeakHashMap<Player, Entity> glowingEntities = new WeakHashMap<>();

    public InteractablesMonitor(JavaPlugin plugin){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, 10);

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
                    WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(event.getPacket());
                    Entity entity = packet.getEntity(event);
                    Player player = event.getPlayer();
                    Entity temp = InteractablesMonitor.glowingEntities.get(player);

                    List<WrappedWatchableObject> metadatas = packet.getMetadata();
                    WrappedWatchableObject status = null;
                    for (WrappedWatchableObject metadata: metadatas){
                        try {
                            if (metadata.getIndex() == 0)
                                status = metadata;
                            break;
                        } catch (FieldAccessException e){

                        }
                    }

                    if (temp != null && temp.equals(entity)) {
                        if (status != null){
                            byte mask = (byte) status.getValue();
                            mask |= 0x40;
                            status.setValue(mask);
                        }
                    } else {
                        if (status != null){
                            byte mask = (byte) status.getValue();
                            mask &= ~0x40;
                            status.setValue(mask);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void run() {
        for (Player player : CharacterManager.characters.keySet()) {

            Entity entity = player.getTargetEntity(3);
            Entity temp = glowingEntities.get(player);
            if (temp != null && !temp.equals(entity)){
                glowingEntities.remove(player);
                temp.setGlowing(true);
                temp.setGlowing(false);
            }
            if (entity != null && entity.hasMetadata("Interactable")){
                MetadataValue metadataValue = null;
                List<MetadataValue> values = entity.getMetadata("Interactable");
                for (MetadataValue value : values){
                    metadataValue = value;
                }
                if (metadataValue == null) break;

                if (metadataValue.asString().equalsIgnoreCase("npc")){
                    player.sendActionBar("Ⓕ Talk to " + entity.getName());
                }
                if (metadataValue.asString().equalsIgnoreCase("dialogue")){
                    player.sendActionBar("Ⓕ Talk to " + entity.getName());
                }
                if (metadataValue.asString().equalsIgnoreCase("lootable")){
                    player.sendActionBar("Ⓕ Search " + entity.getName());
                }
                if (metadataValue.asString().equalsIgnoreCase("item")){
                    entity = entity.getVehicle();
                    player.sendActionBar("Ⓕ Take");
                }
                if (metadataValue.asString().equalsIgnoreCase("book")){

                }

                glowingEntities.put(player, entity);
                entity.setGlowing(true);
                entity.setGlowing(false);
            }
        }
    }
}
