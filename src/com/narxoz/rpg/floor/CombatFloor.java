package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.ArrayList;
import java.util.List;
public class CombatFloor extends TowerFloor {

    private final String floorName;
    private List<Monster> monsters;
    private int totalDamageTaken;

    public CombatFloor(String floorName, List<Monster> monsters) {
        this.floorName = floorName;
        this.monsters = monsters;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("You encounter " + monsters.size() + " monster(s)!");
        for (Monster m : monsters) {
            System.out.println("  - " + m.getName() + " (HP: " + m.getHp() + ", ATK: " + m.getAttackPower() + ")");
        }
        totalDamageTaken = 0;
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n--- Combat Begins ---");

        int round = 1;
        while (hasLivingMonsters() && hasLivingHeroes(party)) {
            System.out.println("\n[Round " + round + "]");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct() && hasLivingMonsters()) {
                    Monster target = getFirstLivingMonster();
                    hero.attack(target);
                }

                hero.onTurnEnd();
            }

            if (hasLivingMonsters()) {
                for (Monster monster : monsters) {
                    if (!monster.isAlive()) continue;

                    Hero target = getRandomLivingHero(party);
                    if (target != null) {
                        int hpBefore = target.getHp();
                        monster.attack(target);
                        totalDamageTaken += (hpBefore - target.getHp());
                    }
                }
            }

            round++;
        }

        boolean cleared = hasLivingHeroes(party);
        String summary = cleared ? "Victory! All monsters defeated." : "Defeat! All heroes have fallen.";

        System.out.println("\n" + summary);
        return new FloorResult(cleared, totalDamageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("The party finds some healing potions!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(10);
                    System.out.println("  " + hero.getName() + " heals 10 HP (now at " + hero.getHp() + "/" + hero.getMaxHp() + ")");
                }
            }
        }
    }

    private boolean hasLivingMonsters() {
        return monsters.stream().anyMatch(Monster::isAlive);
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        return party.stream().anyMatch(Hero::isAlive);
    }

    private Monster getFirstLivingMonster() {
        return monsters.stream()
                .filter(Monster::isAlive)
                .findFirst()
                .orElse(null);
    }

    private Hero getRandomLivingHero(List<Hero> party) {
        List<Hero> living = new ArrayList<>();
        for (Hero h : party) {
            if (h.isAlive()) living.add(h);
        }
        if (living.isEmpty()) return null;
        return living.get((int) (Math.random() * living.size()));
    }
}
