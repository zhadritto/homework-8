package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;
public class StunnedState implements HeroState {

    private int turnsRemaining;

    public StunnedState() {
        this.turnsRemaining = 1;
    }

    @Override
    public String getName() {
        return "Stunned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return 0;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.3);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  " + hero.getName() + " is stunned and cannot act!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;

        if (turnsRemaining <= 0) {
            System.out.println("  " + hero.getName() + " recovers from being stunned!");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return false;
    }
}
