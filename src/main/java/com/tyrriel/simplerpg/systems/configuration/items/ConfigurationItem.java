package com.tyrriel.simplerpg.systems.configuration.items;

import com.tyrriel.simplerpg.systems.characters.Job;
import com.tyrriel.simplerpg.systems.items.*;
import com.tyrriel.simplerpg.systems.items.types.*;
import org.bukkit.inventory.ItemStack;

public class ConfigurationItem {

    private ItemStack itemStack;
    private Rarity rarity;
    private ItemType itemType;
    private Job job;
    private int level, value;
    // Non Junk Items
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

    public ConfigurationItem(ItemStack itemStack, Rarity rarity, ItemType itemType, Job job, int level, int value){
        setItemStack(itemStack);
        setRarity(rarity);
        setItemType(itemType);
        setJob(job);
        setLevel(level);
        setValue(value);
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

    public void setOffhandType(OffhandType offhandType) {
        this.offhandType = offhandType;
    }

    public void setArmorType(ArmorType armorType) {
        this.armorType = armorType;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setAccessoryType(AccessoryType accessoryType) {
        this.accessoryType = accessoryType;
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

    public int[] getStats() {
        int [] actual = new int[6];
        int k = 0;
        for (int i = 0; i < stats.length; i=i+2){
            int min = stats[i];
            int max = stats[i+1];
            int newVal = (int)(Math.random() * ((max - min) + 1)) + min;
            actual[k] = newVal;
            k++;
        }
        return actual;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage(){
        return maxDamage;
    }

    public OffhandType getOffhandType() {
        return offhandType;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public int getArmor() {
        return armor;
    }

    public AccessoryType getAccessoryType() {
        return accessoryType;
    }
}
