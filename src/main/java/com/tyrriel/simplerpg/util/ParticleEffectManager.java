package com.tyrriel.simplerpg.util;

import com.tyrriel.simplerpg.SimpleRPG;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ParticleEffectManager {

    public static void doPhysicalAttack(Player player, double distance){
        Location location = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(distance));
        location = location.add(0, -0.25, 0);
        LineEffect effect = new LineEffect(new EffectManager(SimpleRPG.getInstance()));
        effect.setLocation(location);
        effect.particle = Particle.SWEEP_ATTACK;
        effect.length = 1;
        effect.particles = 1;
        effect.start();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 0.5f);
    }

}
