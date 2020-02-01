package com.tyrriel.simplerpg.systems.playerui;

import com.tyrriel.simplerpg.SimpleRPG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

public class CompassDisplay {

    public static WeakHashMap<Player, KeyedBossBar> playersCompass = new WeakHashMap<>();

    public static void createCompass(Player player) {
        NamespacedKey key = getCompassKey(player);
        KeyedBossBar compass = Bukkit.getBossBar(key);
        if (compass == null) {
            compass = Bukkit.createBossBar(key, "Compass", BarColor.WHITE, BarStyle.SOLID);
            compass.setProgress(0);
        }
        compass.addPlayer(player);
        playersCompass.put(player, compass);
    }

    private static NamespacedKey getCompassKey(Player player) {
        String keyStr = player.getUniqueId().toString().toLowerCase();
        NamespacedKey key = new NamespacedKey(SimpleRPG.getInstance(), "compass." + keyStr);
        return key;
    }

    public static float calcYaw(Player player) {
        Location playerLoc = player.getLocation();
        float calcYaw = playerLoc.getYaw();
        if (calcYaw < 0.0F) {
            calcYaw += 360.0F;
        }
        return calcYaw;
    }

    public static String getCompassDisplay(float yaw) {
        yaw += 2.25F;
        if (yaw > 360.0F) {
            yaw -= 360.0F;
        }
        if ((yaw >= 180.0F) && (yaw < 184.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 184.5D) && (yaw < 189.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ";
        }
        if ((yaw >= 189.0F) && (yaw < 193.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ";
        }
        if ((yaw >= 193.5D) && (yaw < 198.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ";
        }
        if ((yaw >= 198.0F) && (yaw < 202.5D)) {
            return "&7    ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 202.5D) && (yaw < 207.0F)) {
            return "&7    ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 207.0F) && (yaw < 211.5D)) {
            return "&7    ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 211.5D) && (yaw < 216.0F)) {
            return "&7   &f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 216.0F) && (yaw < 220.5D)) {
            return "&7 &f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 220.5D) && (yaw < 225.0F)) {
            return "&fNW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 225.0F) && (yaw < 229.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 229.5D) && (yaw < 234.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7";
        }
        if ((yaw >= 234.0F) && (yaw < 238.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ";
        }
        if ((yaw >= 238.5D) && (yaw < 243.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ";
        }
        if ((yaw >= 243.0F) && (yaw < 247.5D)) {
            return "&7    ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 247.5D) && (yaw < 252.0F)) {
            return "&7    ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 252.0F) && (yaw < 256.5D)) {
            return "&7    ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 256.5D) && (yaw < 261.0F)) {
            return "&7    &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 261.0F) && (yaw < 265.5D)) {
            return "&7  &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 265.5D) && (yaw < 270.0F)) {
            return "&7&c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f E &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 270.0F) && (yaw < 274.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 274.5D) && (yaw < 279.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ";
        }
        if ((yaw >= 279.0F) && (yaw < 283.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ";
        }
        if ((yaw >= 283.5D) && (yaw < 288.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ";
        }
        if ((yaw >= 288.0F) && (yaw < 292.5D)) {
            return "&7    ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 292.5D) && (yaw < 297.0F)) {
            return "&7    ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 297.0F) && (yaw < 301.5D)) {
            return "&7    ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 301.5D) && (yaw < 306.0F)) {
            return "&7   &f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 306.0F) && (yaw < 310.5D)) {
            return "&7 &f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 310.5D) && (yaw < 315.0F)) {
            return "&fNE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 315.0F) && (yaw < 319.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 319.5D) && (yaw < 324.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7";
        }
        if ((yaw >= 324.0F) && (yaw < 328.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ";
        }
        if ((yaw >= 328.5D) && (yaw < 333.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ";
        }
        if ((yaw >= 333.0F) && (yaw < 337.5D)) {
            return "&7    ⋅ ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 337.5D) && (yaw < 342.0F)) {
            return "&7    ⋅ ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 342.0F) && (yaw < 346.5D)) {
            return "&7    ⋅&f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 346.5D) && (yaw < 351.0F)) {
            return "&7   &f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 351.0F) && (yaw < 355.5D)) {
            return "&7 &f E &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 355.5D) && (yaw < 360.0F)) {
            return "&fE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 0.0F) && (yaw < 4.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 4.5D) && (yaw < 9.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ";
        }
        if ((yaw >= 9.0F) && (yaw < 13.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ";
        }
        if ((yaw >= 13.5D) && (yaw < 18.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ";
        }
        if ((yaw >= 18.0F) && (yaw < 22.5D)) {
            return "&7    ⋅ ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 22.5D) && (yaw < 27.0F)) {
            return "&7    ⋅ ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 27.0F) && (yaw < 31.5D)) {
            return "&7    ⋅&f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 31.5D) && (yaw < 36.0F)) {
            return "&7   &f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 36.0F) && (yaw < 40.5D)) {
            return "&7 &f SE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 40.5D) && (yaw < 45.0F)) {
            return "&fSE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 45.0F) && (yaw < 49.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 49.5D) && (yaw < 54.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7";
        }
        if ((yaw >= 54.0F) && (yaw < 58.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ";
        }
        if ((yaw >= 58.5D) && (yaw < 63.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ";
        }
        if ((yaw >= 63.0F) && (yaw < 67.5D)) {
            return "&7    ⋅ ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 67.5D) && (yaw < 72.0F)) {
            return "&7    ⋅ ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 72.0F) && (yaw < 76.5D)) {
            return "&7    ⋅ &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 76.5D) && (yaw < 81.0F)) {
            return "&7    &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 81.0F) && (yaw < 85.5D)) {
            return "&7  &f S &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 85.5D) && (yaw < 90.0F)) {
            return "&7S  ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 90.0F) && (yaw < 94.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f&f NW &7&7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 94.5D) && (yaw < 99.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ";
        }
        if ((yaw >= 99.0F) && (yaw < 103.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ";
        }
        if ((yaw >= 103.5D) && (yaw < 108.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ";
        }
        if ((yaw >= 108.0F) && (yaw < 112.5D)) {
            return "&7    ⋅ ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 112.5D) && (yaw < 117.0F)) {
            return "&7    ⋅ ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 117.0F) && (yaw < 121.5D)) {
            return "&7    ⋅&f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 121.5D) && (yaw < 126.0F)) {
            return "&7   &f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 126.0F) && (yaw < 130.5D)) {
            return "&7 &f SW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 130.5D) && (yaw < 135.0F)) {
            return "&fSW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        if ((yaw >= 135.0F) && (yaw < 139.5D)) {
            return "&7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 139.5D) && (yaw < 144.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7";
        }
        if ((yaw >= 144.0F) && (yaw < 148.5D)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ";
        }
        if ((yaw >= 148.5D) && (yaw < 153.0F)) {
            return "&7    ⋅ ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ";
        }
        if ((yaw >= 153.0F) && (yaw < 157.5D)) {
            return "&7    ⋅ ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 157.5D) && (yaw < 162.0F)) {
            return "&7    ⋅ ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 162.0F) && (yaw < 166.5D)) {
            return "&7    ⋅ &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 166.5D) && (yaw < 171.0F)) {
            return "&7    &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 171.0F) && (yaw < 175.5D)) {
            return "&7  &f W &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ";
        }
        if ((yaw >= 175.5D) && (yaw < 180.0F)) {
            return "&fW &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NW &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ &c N &7 ⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅&f NE &7⋅ ⋅ ⋅ ⋅ ⋅ ⋅ ⋅  ";
        }
        return "⋅8Compass Error";
    }

    public static void removeCompass(Player player) {
        BossBar compass = playersCompass.get(player);
        if (compass != null) {
            compass.removeAll();
            NamespacedKey key = getCompassKey(player);
            Bukkit.removeBossBar(key);
            playersCompass.remove(player);
        }
    }

    public static void removeAllBossBars() {
        ArrayList<KeyedBossBar> removeList = new ArrayList();
        Iterator<KeyedBossBar> iterator = Bukkit.getBossBars();
        while (iterator.hasNext())
        {
            KeyedBossBar bossBar = iterator.next();
            String keyAndNamespace = bossBar.getKey().toString();
            if (keyAndNamespace.contains("compass.")) {
                removeList.add(bossBar);
            }
        }
        for (int i = 0; i < removeList.size(); i++)
        {
            KeyedBossBar bossBar = removeList.get(i);
            bossBar.removeAll();
            Bukkit.removeBossBar(bossBar.getKey());
        }
    }

}
