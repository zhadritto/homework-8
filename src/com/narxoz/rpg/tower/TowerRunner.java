package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import java.util.List;

public class TowerRunner {
    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }

    public TowerRunResult run(List<Hero> party) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("   THE HAUNTED TOWER: ASCENDING THE FLOORS");
        System.out.println("═══════════════════════════════════════");

        printPartyStatus(party);

        int floorsCleared = 0;
        boolean reachedTop = false;

        for (int i = 0; i < floors.size(); i++) {
            TowerFloor floor = floors.get(i);

            System.out.println("\n\n╔═══════════════════════════════════════╗");
            System.out.println("║       FLOOR " + (i + 1) + " OF " + floors.size() + "              ║");
            System.out.println("╚═══════════════════════════════════════╝");

            FloorResult result = floor.explore(party);

            if (result.isCleared()) {
                floorsCleared++;
                System.out.println("\n ✓ Floor "  + (i + 1) + " cleared!");
            } else {
                System.out.println("\n ✗ Floor "  + (i + 1) + " failed!");
                break;
            }

            if (!hasLivingHeroes(party)) {
                System.out.println("\nAll heroes have fallen! Tower climb ends.");
                break;
            }

            printPartyStatus(party);
        }

        if (floorsCleared == floors.size() && hasLivingHeroes(party)) {
            reachedTop = true;
            System.out.println("\n\n★★★ TOWER CONQUERED! ★★★");
            System.out.println("The heroes have reached the top!");
        }

        int heroesSurviving = countLivingHeroes(party);
        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }

    private void printPartyStatus(List<Hero> party) {
        System.out.println("\n--- Party Status ---");
        for (Hero hero : party) {
            String status = hero.isAlive() ?
                    String.format("%s: %d/%d HP [%s]",
                            hero.getName(),
                            hero.getHp(),
                            hero.getMaxHp(),
                            hero.getState().getName()) :
                    hero.getName() + ": FALLEN";
            System.out.println("  " + status);
        }
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        return party.stream().anyMatch(Hero::isAlive);
    }

    private int countLivingHeroes(List<Hero> party) {
        return (int) party.stream().filter(Hero::isAlive).count();
    }
}