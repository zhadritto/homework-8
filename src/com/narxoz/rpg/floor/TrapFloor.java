package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.*;
import java.util.List;

public class TrapFloor extends TowerFloor {
    private final String floorName;
    private final String trapType;

    public TrapFloor(String floorName, String trapType) {
        this.floorName = floorName;
        this.trapType = trapType;
    }

    @Override
    protected String getFloorName() { return floorName; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The floor is suspiciously quiet...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n--- TRAP TRIGGERED! ---");

        switch (trapType) {
            case "poison":
                System.out.println("Poison gas fills the room!");
                for (Hero hero : party) {
                    if (hero.isAlive()) {
                        System.out.println("  " + hero.getName() + " is poisoned!");
                        hero.setState(new PoisonedState());
                    }
                }
                break;
            case "stun":
                System.out.println("A flash of blinding light stuns everyone!");
                for (Hero hero : party) {
                    if (hero.isAlive()) {
                        System.out.println("  " + hero.getName() + " is stunned!");
                        hero.setState(new StunnedState());
                    }
                }
                break;
            case "gas":
                System.out.println("Strange vapors cloud your minds...");
                for (Hero hero : party) {
                    if (hero.isAlive() && Math.random() < 0.5) {
                        System.out.println("  " + hero.getName() + " enters a berserk rage!");
                        hero.setState(new BerserkState());
                    }
                }
                break;
            default:
                System.out.println("Unknown trap type!");
        }

        return new FloorResult(true, 0, "The party survived the trap.");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("No loot found in this trapped room.");
    }
}