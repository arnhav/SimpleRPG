package com.tyrriel.simplerpg.systems.combat.skills;

public class Skill {

    private String name;

    public Skill(String name){
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
