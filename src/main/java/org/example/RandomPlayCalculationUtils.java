package org.example;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.CommonPlayCalculationUtils.*;

public class RandomPlayCalculationUtils {
    private static Map<Set<Integer>, Double> TIMES_BY_POSITIONS = new HashMap<>();

    public static double calculatePositionTime(Set<Integer> position) {
        double left = 1.0;
        double right = 1.0;

        for (Dices dices : ALL_DICES) {
            Set<Set<Integer>> posiblePositions = getPosiblePositions(position, dices);

            if (posiblePositions.contains(Collections.emptySet())) {
                continue;
            }

            if (posiblePositions.isEmpty()) {
                left -= ((double) dices.getFrequency() / DICES_COMBINATIONS_COUNT);
            } else {
                double averageTime = getAverage(posiblePositions);
                right += (averageTime * dices.getFrequency() / DICES_COMBINATIONS_COUNT);
            }
        }

        double time = right / left;
        TIMES_BY_POSITIONS.put(position, time);
        return time;
    }

    private static double getAverage(Set<Set<Integer>> posiblePositions) {
        double totalTime = 0.0;

        for (Set<Integer> posiblePosition : posiblePositions) {
            totalTime += TIMES_BY_POSITIONS.get(posiblePosition);
        }

        return totalTime / posiblePositions.size();
    }


}
