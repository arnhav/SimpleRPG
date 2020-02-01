package com.tyrriel.simplerpg.systems.items;

import com.tyrriel.simplerpg.systems.characters.Job;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class RPGItem {

    private int id;
    private ItemStack itemStack;
    private Rarity rarity;
    private ItemType itemType;
    private Job job;
    private int level, value;

    public RPGItem(int id, ItemStack itemStack, Rarity rarity, ItemType itemType, Job job, int level, int value){
        setId(id);
        setItemStack(itemStack);
        setRarity(rarity);
        setItemType(itemType);
        setJob(job);
        setLevel(level);
        setValue(value);
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public ItemStack getItemStack() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(RPGItemUtil.i, PersistentDataType.INTEGER, id);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
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
