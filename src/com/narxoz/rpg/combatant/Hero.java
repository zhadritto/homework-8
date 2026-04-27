package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

public class Hero {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = new NormalState();
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }
    public HeroState getState()    { return state; }

    public void setState(HeroState newState) {
        this.state = newState;
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void onTurnStart() {
        state.onTurnStart(this);
    }

    public void onTurnEnd() {
        state.onTurnEnd(this);
    }

    public boolean canAct() {
        return state.canAct();
    }

    public void attack(Monster monster) {
        if (!isAlive()) return;
        if (!canAct()) return;

        int baseDamage = Math.max(1, attackPower - monster.getDefense());
        int finalDamage = state.modifyOutgoingDamage(baseDamage);

        System.out.println("  " + name + " attacks " + monster.getName() + " for " + finalDamage + " damage!");
        monster.takeDamage(finalDamage);

        if (!monster.isAlive()) {
            System.out.println("  " + monster.getName() + " has been defeated!");
        }
    }

    public void takeDamageFromEnemy(int rawDamage) {
        int finalDamage = state.modifyIncomingDamage(rawDamage);
        finalDamage = Math.max(1, finalDamage - defense);
        takeDamage(finalDamage);
    }
}