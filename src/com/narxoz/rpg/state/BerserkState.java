package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    private int turnsRemaining;
    private static final double OUTGOING_MULTIPLIER = 1.5;
    private static final double INCOMING_MULTIPLIER = 1.4;

    public BerserkState() {
        this.turnsRemaining = 4;
    }

    @Override
    public String getName() { return "Berserk"; }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * OUTGOING_MULTIPLIER);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * INCOMING_MULTIPLIER);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  " + hero.getName() + " is BERSERK! (+50% damage, +40% vulnerability)");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        System.out.println("  " + hero.getName() + "'s berserk rage has " + turnsRemaining + " turn(s) remaining.");

        if (turnsRemaining <= 0) {
            System.out.println("  " + hero.getName() + " calms down from berserk state.");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() { return true; }
}