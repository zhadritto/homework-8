package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int turnsRemaining;
    private static final int POISON_DAMAGE = 3;
    private static final double DAMAGE_MULTIPLIER = 0.7;

    public PoisonedState() {
        this.turnsRemaining = 3;
    }

    @Override
    public String getName() { return "Poisoned"; }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * DAMAGE_MULTIPLIER);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.2);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  " + hero.getName() + " takes " + POISON_DAMAGE + " poison damage!");
        hero.takeDamage(POISON_DAMAGE);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        System.out.println("  " + hero.getName() + "'s poison has " + turnsRemaining + " turn(s) remaining.");

        if (turnsRemaining <= 0) {
            System.out.println("  " + hero.getName() + "'s poison has worn off!");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() { return true; }
}