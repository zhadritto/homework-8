package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.ArrayList;
import java.util.List;
public class BossFloor extends TowerFloor {

    private Monster boss;
    private int totalDamageTaken;

    public BossFloor(Monster boss) {
        this.boss = boss;
    }

    @Override
    protected String getFloorName() {
        return "Boss Chamber";
    }

    @Override
    protected void announce() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   BOSS FLOOR: " + boss.getName().toUpperCase() + "   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("The air grows heavy. A powerful presence awaits...");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("\n" + boss.getName() + " emerges from the shadows!");
        System.out.println("  HP: " + boss.getHp() + " | ATK: " + boss.getAttackPower() + " | DEF: " + boss.getDefense());
        System.out.println("\nThis will be a tough fight!");
        totalDamageTaken = 0;
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n--- BOSS BATTLE BEGINS ---");

        int round = 1;
        while (boss.isAlive() && hasLivingHeroes(party)) {
            System.out.println("\n[Round " + round + "]");
            System.out.println("Boss HP: " + boss.getHp());
            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct() && boss.isAlive()) {
                    hero.attack(boss);
                }

                hero.onTurnEnd();
            }

            if (boss.isAlive()) {
                System.out.println("\n" + boss.getName() + " unleashes a devastating attack!");

                // Boss attacks all heroes
                for (Hero hero : party) {
                    if (hero.isAlive()) {
                        int hpBefore = hero.getHp();
                        boss.attack(hero);
                        totalDamageTaken += (hpBefore - hero.getHp());
                    }
                }
            }

            round++;
        }

        boolean cleared = hasLivingHeroes(party) && !boss.isAlive();
        String summary = cleared ?
                "VICTORY! " + boss.getName() + " has been defeated!" :
                "DEFEAT! The party falls to " + boss.getName() + "...";

        System.out.println("\n" + summary);
        return new FloorResult(cleared, totalDamageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("\n★ LEGENDARY LOOT ACQUIRED ★");
            System.out.println("The boss drops powerful treasures!");

            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(25);
                    System.out.println("  " + hero.getName() + " receives a Phoenix Down (+25 HP)");
                    System.out.println("  " + hero.getName() + " is now at " + hero.getHp() + "/" + hero.getMaxHp() + " HP");
                }
            }
        }
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        return party.stream().anyMatch(Hero::isAlive);
    }
}
