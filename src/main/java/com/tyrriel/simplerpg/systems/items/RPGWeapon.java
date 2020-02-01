package com.tyrriel.simplerpg.systems.items;

public class RPGWeapon extends RPGItem {

    private int cooldown;
    private int minDamage, maxDamage;
    private WeaponType weaponType;

    public RPGWeapon(RPGItem rpgItem, WeaponType weaponType, int cooldown, int minDamage, int maxDamage) {
        super(rpgItem.getId(), rpgItem.getItemStack(), rpgItem.getRarity(), rpgItem.getItemType(), rpgItem.getJob(), rpgItem.getLevel(), rpgItem.getValue());
        setWeaponType(weaponType);
        setCooldown(cooldown);
        setMinDamage(minDamage);
        setMaxDamage(maxDamage);
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

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
