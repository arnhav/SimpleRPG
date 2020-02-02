package com.tyrriel.simplerpg.systems.configurationsystem.items;

import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.items.*;
import org.bukkit.inventory.ItemStack;

public class ConfigurationItem {

    private ItemStack itemStack;
    private Rarity rarity;
    private ItemType itemType;
    private Job job;
    private int level, value;
    private int[] stats;
    // Weapon
    private WeaponType weaponType;
    private int cooldown, minDamage, maxDamage;
    // Offhand
    private OffhandType offhandType;
    // Armor
    private ArmorType armorType;
    private int armor;
    // Accessories
    private AccessoryType accessoryType;

    public ConfigurationItem(ItemStack itemStack, Rarity rarity, ItemType itemType, Job job, int level, int value, int[] stats){
        setItemStack(itemStack);
        setRarity(rarity);
        setItemType(itemType);
        setJob(job);
        setLevel(level);
        setValue(value);
        setStats(stats);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Job getJob() {
        return job;
    }

    public int getLevel() {
        return level;
    }

    public int getValue() {
        return value;
    }


}
