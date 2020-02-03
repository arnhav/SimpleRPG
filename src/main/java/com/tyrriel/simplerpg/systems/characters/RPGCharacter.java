package com.tyrriel.simplerpg.systems.characters;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RPGCharacter {

    private UUID player;
    private Job job;
    private int health, mana;
    private int level, exp;
    private int money;
    private int backpack;
    // Stats
    private int unallocatedstats;
    private int[] stats = new int[6];
    // Equipped Items
    private ItemStack[] armor = new ItemStack[4];
    private ItemStack[] accessories = new ItemStack[4];
    private ItemStack weapon, offhand, consumable;
    // Unequipped Items
    private List<ItemStack> inventory = new ArrayList<>();

    public RPGCharacter(UUID player, Job job){
        this(player, job, 10, 10, 1, 0, 0, 0);

        setStrength(0);
        setDexterity(0);
        setIntelligence(0);
        setSpeed(0);
        setVitality(0);
        setLuck(0);
    }

    public RPGCharacter(UUID player, Job job, int health, int mana, int level, int exp, int money, int backpack){
        setPlayer(player);
        setJob(job);
        setHealth(health);
        setMana(mana);
        setLevel(level);
        setExp(exp);
        setMoney(money);
        setBackpack(backpack);
    }

    // Item Setters

    public void setHelm(ItemStack itemStack){
        armor[0] = itemStack;
    }

    public void setChest(ItemStack itemStack){
        armor[1] = itemStack;
    }

    public void setLegs(ItemStack itemStack){
        armor[2] = itemStack;
    }

    public void setBoots(ItemStack itemStack){
        armor[3] = itemStack;
    }

    public void setNecklace(ItemStack itemStack){
        accessories[0] = itemStack;
    }

    public void setEarrings(ItemStack itemStack){
        accessories[1] = itemStack;
    }

    public void setRing(ItemStack itemStack){
        accessories[2] = itemStack;
    }

    public void setBracers(ItemStack itemStack){
        accessories[3] = itemStack;
    }

    public void setWeapon(ItemStack weapon) {
        this.weapon = weapon;
    }

    public void setOffhand(ItemStack offhand) {
        this.offhand = offhand;
    }

    public void setConsumable(ItemStack consumable) {
        this.consumable = consumable;
    }
    
    // Item Getters

    public ItemStack getHelm(){
        return armor[0];
    }

    public ItemStack getChest(){
        return armor[1];
    }

    public ItemStack getLegs(){
        return armor[2];
    }

    public ItemStack getBoots(){
        return armor[3];
    }

    public ItemStack getNecklace(){
        return accessories[0];
    }

    public ItemStack getEarrings(){
        return accessories[1];
    }

    public ItemStack getRing(){
        return accessories[2];
    }

    public ItemStack getBracers(){
        return accessories[3];
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public ItemStack getOffhand() {
        return offhand;
    }

    public ItemStack getConsumable() {
        return consumable;
    }

    // Item Other

    public int addItemToInv(ItemStack itemStack){
        if (itemStack == null) return 0;
        if (inventory.size() > (backpack+1)*18){
            return -1;
        } else {
            inventory.add(itemStack);
            return 0;
        }
    }

    public void removeFromInv(int pos){
        inventory.remove(pos);
    }

    public List<ItemStack> getInventory() {
        return inventory;
    }

    // Setters

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setBackpack(int backpack) {
        this.backpack = backpack;
    }

    public void setUnallocatedstats(int unallocatedstats) {
        this.unallocatedstats = unallocatedstats;
    }
    
    public void setStrength(int value){
        stats[0] = value;
    }

    public void setDexterity(int value){
        stats[1] = value;
    }

    public void setIntelligence(int value){
        stats[2] = value;
    }

    public void setVitality(int value){
        stats[3] = value;
    }

    public void setSpeed(int value){
        stats[4] = value;
    }

    public void setLuck(int value){
        stats[5] = value;
    }

    // Getters

    public UUID getPlayer() {
        return player;
    }

    public Job getJob() {
        return job;
    }

    private int getMaxBaseHealth() {
        return level * 10;
    }

    private int getMaxBaseMana() {
        return level * 10;
    }

    public int getMaxHealth() {
        return (int) (getMaxBaseHealth() + (getVitality()*0.2));
    }

    public int getMaxMana() {
        return (int) (getMaxBaseMana() + (getIntelligence()*0.2));
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public double getMaxExpForLevel(){
        return ((4 * Math.pow(level, 3)) / 5) * 10;
    }

    public int getMoney() {
        return money;
    }

    public int getBackpack() {
        return backpack;
    }

    public int getUnallocatedstats() {
        return unallocatedstats;
    }

    public int getStrength(){
        return stats[0];
    }

    public int getDexterity(){
        return stats[1];
    }

    public int getIntelligence(){
        return stats[2];
    }

    public int getVitality(){
        return stats[3];
    }

    public int getSpeed(){
        return stats[4];
    }

    public int getLuck(){
        return stats[5];
    }
}
