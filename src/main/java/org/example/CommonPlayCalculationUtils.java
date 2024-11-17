package org.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonPlayCalculationUtils {
    public static double ACCURACY = 0.0000001;
    public static int DICES_COMBINATIONS_COUNT = 36;
    public static Set<Dices> ALL_DICES = generateDices();

    public static boolean areEqual(double d1, double d2) {
        return Math.abs(d1 - d2) < ACCURACY;
    }

    public static boolean isMore(double d1, double d2) {
        return d1 - d2 > ACCURACY;
    }

    public static boolean isMoreOrEqual(double d1, double d2) {
        return isMore(d1, d2) || areEqual(d1, d2);
    }

    public static Set<Set<Integer>> getAllCombinations(int number) {
        Set<Set<Integer>> combinations = new LinkedHashSet<>();
        combinations.add(Collections.emptySet());

        for (int i = 0; i < number; i++) {
            combinations = addOneNumber(combinations);
        }

        return combinations;
    }

    public static Set<Set<Integer>> addOneNumber(Set<Set<Integer>> old) {
        Set<Set<Integer>> newSets = new LinkedHashSet<>();

        for (Set<Integer> oldSet : old) {
            for (int i = 1; i <= 12; i++) {
                if (oldSet.contains(i)) {
                    continue;
                }
                Set<Integer> newSet = new LinkedHashSet<>(oldSet);
                newSet.add(i);
                newSets.add(newSet);
            }
        }

        return newSets;
    }

    public static Set<Set<Integer>> getPosiblePositions(Set<Integer> position, Dices dices) {
        Set<Set<Integer>> posiblePositions = new HashSet<>();

        if (position.contains(dices.getFirstDice()) && position.contains(dices.getSecondDice())) {
            posiblePositions.add(position.stream().filter(num -> num != dices.firstDice && num != dices.getSecondDice()).collect(Collectors.toSet()));
        }
        if (position.contains(dices.getFirstDice() + dices.getSecondDice())) {
            posiblePositions.add(position.stream().filter(num -> num != (dices.firstDice + dices.getSecondDice())).collect(Collectors.toSet()));
        }

        return posiblePositions;
    }

    private static Set<Dices> generateDices() {
        Set<Dices> dices = new HashSet<>();

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                dices.add(new Dices(i, j));
            }
        }

        return dices;
    }
}
