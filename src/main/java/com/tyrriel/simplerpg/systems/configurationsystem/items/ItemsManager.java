package com.tyrriel.simplerpg.systems.configurationsystem.items;

import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.items.ItemType;
import com.tyrriel.simplerpg.systems.items.Rarity;
import com.tyrriel.simplerpg.systems.items.WeaponType;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemsManager {

    // File Configs
    private FileConfiguration data;

    // Files
    private File itemsFolder;

    // Plugin instance
    private JavaPlugin plugin;

    private HashMap<String, ConfigurationItem> items = new HashMap<>();

    public ItemsManager(JavaPlugin plugin){
        this.plugin = plugin;

        createItemsFolder();
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
                    data.set("Material", Material.GRASS_BLOCK.toString());
                    data.set("Texture", 0);
                    data.set("Name", "Grass Block of Awesomeness");
                    data.set("Lore", Arrays.asList(" ", "A grass block."));
                    data.set("Rarity", Rarity.COMMON.toString());
                    data.set("Type", ItemType.JUNK.toString());
                    data.set("Class", Job.NONE.toString());
                    data.set("Level", 0);
                    data.set("Value", 0);
                    data.set("Stats.strengthmin", 0);
                    data.set("Stats.strengthmax", 0);
                    data.set("Stats.intellimin", 0);
                    data.set("Stats.intellimax", 0);
                    data.set("Stats.speedmin", 0);
                    data.set("Stats.speedmax", 0);
                    data.set("Stats.vitalitymin", 0);
                    data.set("Stats.vitalitymax", 0);
                    data.set("Stats.dexteritymin", 0);
                    data.set("Stats.dexteritymax", 0);
                    data.set("Stats.luckmin", 0);
                    data.set("Stats.luckmax", 0);
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

    public void loadItems(File[] files){
        for (File file : files){
            data = YamlConfiguration.loadConfiguration(file);

            Material material = Material.valueOf(data.getString("Material"));
            int texture = data.getInt("Texture");
            String name = data.getString("Name");
            List<String> lore = data.getStringList("Lore");
            Rarity rarity = Rarity.valueOf(data.getString("Rarity"));
            ItemType itemType = ItemType.valueOf(data.getString("Type"));
            Job job = Job.valueOf(data.getString("Class"));
            int level = data.getInt("Level");
            int value = data.getInt("Value");
            int[] stats = new int[12];
            ConfigurationSection configSection = data.getConfigurationSection("Stats");
            if (configSection != null) {
                int i = 0;
                for (String path: configSection.getKeys(false)){
                    stats[i] = configSection.getInt(path);
                    i++;
                }
            }

            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setCustomModelData(texture);
            itemMeta.setDisplayName(name);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            ConfigurationItem configurationItem = new ConfigurationItem(itemStack, rarity, itemType, job, level, value, stats);

            if (itemType == ItemType.WEAPON){
                WeaponType weaponType = WeaponType.valueOf(data.getString("WeaponType"));
                int cooldown = data.getInt("Cooldown");
                int minDamage = data.getInt("MinDamage");
                int maxDamage = data.getInt("MaxDamage");

                configurationItem.setWeaponType(weaponType);
                configurationItem.setCooldown(cooldown);
                configurationItem.setMinDamage(minDamage);
                configurationItem.setMaxDamage(maxDamage);
            }

            items.put(file.getName(), configurationItem);
        }
    }

}
