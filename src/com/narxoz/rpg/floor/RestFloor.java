package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.RegeneratingState;
import java.util.List;

public class RestFloor extends TowerFloor {
    @Override
    protected String getFloorName() { return "Peaceful Shrine"; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("A tranquil shrine offers respite from the dangers above.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n--- The party rests ---");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                int hpBefore = hero.getHp();
                hero.heal(15);
                int actualHealing = hero.getHp() - hpBefore;

                System.out.println("  " + hero.getName() + " rests and recovers " + actualHealing + " HP");
                System.out.println("  " + hero.getName() + " feels rejuvenated!");
                hero.setState(new RegeneratingState());
            }
        }
        return new FloorResult(true, 0, "The party is refreshed and ready to continue.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        System.out.println("(The rest itself is the reward - no additional loot)");
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("ERROR: This should not be called!");
    }
}