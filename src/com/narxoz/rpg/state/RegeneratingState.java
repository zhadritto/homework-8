package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;
public class RegeneratingState implements HeroState {

    private int turnsRemaining;
    private static final int REGEN_AMOUNT = 5;

    public RegeneratingState() {
        this.turnsRemaining = 5;
    }

    @Override
    public String getName() {
        return "Regenerating";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 0.9);
    }

    @Override
    public void onTurnStart(Hero hero) {
        int oldHp = hero.getHp();
        hero.heal(REGEN_AMOUNT);
        int actualHealing = hero.getHp() - oldHp;

        if (actualHealing > 0) {
            System.out.println("  " + hero.getName() + " regenerates " + actualHealing + " HP!");
        }
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        System.out.println("  " + hero.getName() + "'s regeneration has " + turnsRemaining + " turn(s) remaining.");

        if (turnsRemaining <= 0) {
            System.out.println("  " + hero.getName() + "'s regeneration effect fades.");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
