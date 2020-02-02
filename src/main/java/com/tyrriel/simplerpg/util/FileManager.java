package com.tyrriel.simplerpg.util;

import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.items.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    // File Configs
    private FileConfiguration config;
    private FileConfiguration data;

    // Files
    private File configFile;
    private File itemsFolder;

    // Plugin instance
    private JavaPlugin plugin;

    // File Stuff
    private static Location charSelectLoc;
    private static Location newStartLoc;
    private static String rpurl;
    private static String rphash;

    private static HashMap<Integer, RPGItem> items = new HashMap<>();

    public FileManager(JavaPlugin plugin){
        this.plugin = plugin;

        createConfig();
        createItemsFolder();
    }

    public void createConfig() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            configFile = new File(plugin.getDataFolder(), "config.yml");
            config = new YamlConfiguration();
            if (!configFile.exists()) {
                plugin.getLogger().info("config.yml not found, creating...");
                plugin.saveDefaultConfig();
                loadConfig();
            } else {
                plugin.getLogger().info("config.yml found, loading...");
                loadConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createItemsFolder(){
        try {
            itemsFolder = new File(plugin.getDataFolder(), "Items");
            if (!itemsFolder.exists()){
                plugin.getLogger().info("Items Folder not found, creating...");
                itemsFolder.mkdirs();

                File exampleItem = new File(itemsFolder, "exampleItem.yml");
                data = YamlConfiguration.loadConfiguration(exampleItem);
                if (!exampleItem.exists()){
                    data.set("ID", 0);
                    data.set("Material", Material.GRASS_BLOCK.toString());
                    data.set("Texture", 0);
                    data.set("Name", "Grass Block of Awesomeness");
                    data.set("Lore", Arrays.asList(" ", "A grass block."));
                    data.set("Rarity", Rarity.COMMON.toString());
                    data.set("Type", ItemType.JUNK.toString());
                    data.set("Class", Job.NONE.toString());
                    data.set("Level", 0);
                    data.set("Value", 0);
                    data.save(exampleItem);
                }

                File[] files = itemsFolder.listFiles();
                loadItems(files);
            } else {
                plugin.getLogger().info("Items Folder found, loading...");
                File[] files = itemsFolder.listFiles();
                loadItems(files);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        config = plugin.getConfig();

        charSelectLoc = getLocation(config, "CharacterSelection");
        newStartLoc = getLocation(config, "NewStart");
        rpurl = config.getString("rpurl");
        rphash = config.getString("rphash");
    }

    public void loadItems(File[] files){
        for (File file : files){
            data = YamlConfiguration.loadConfiguration(file);

            int id = data.getInt("ID");
            Material material = Material.valueOf(data.getString("Material"));
            int texture = data.getInt("Texture");
            String name = data.getString("Name");
            List<String> lore = data.getStringList("Lore");
            Rarity rarity = Rarity.valueOf(data.getString("Rarity"));
            ItemType itemType = ItemType.valueOf(data.getString("Type"));
            Job job = Job.valueOf(data.getString("Class"));
            int level = data.getInt("Level");
            int value = data.getInt("Value");

            RPGItem rpgItem = new RPGItem(id, ItemUtil.item(material, texture, name, lore), rarity, itemType, job, level, value);
            if (itemType == ItemType.WEAPON){
                WeaponType weaponType = WeaponType.valueOf(data.getString("WeaponType"));
                int cooldown = data.getInt("Cooldown");
                int minDamage = data.getInt("MinDamage");
                int maxDamage = data.getInt("MaxDamage");

                RPGWeapon rpgWeapon = new RPGWeapon(rpgItem, weaponType, cooldown, minDamage, maxDamage);
                items.put(id, rpgWeapon);
            } else {
                items.put(id, rpgItem);
            }
        }
    }

    private Location getLocation(FileConfiguration fileConfiguration, String path) {
        Location location;
        String worldName = fileConfiguration.getString(path + ".world");
        int x = fileConfiguration.getInt(path + ".x");
        int y = fileConfiguration.getInt(path + ".y");
        int z = fileConfiguration.getInt(path + ".z");

        World world = Bukkit.createWorld(new WorldCreator(worldName));
        location = new Location(world, x, y, z);
        return location;
    }

    // Getters

    public static Location getCharacterSelectLocation(){
        return charSelectLoc;
    }

    public static Location getNewStartLocation(){
        return newStartLoc;
    }

    public static String getRPurl() { return rpurl; }

    public static String getRPhash() {
        return rphash;
    }

    public static RPGItem getRPGItem(int id){
        return items.get(id);
    }
}
