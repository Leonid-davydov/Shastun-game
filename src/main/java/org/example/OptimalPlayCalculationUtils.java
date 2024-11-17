package org.example;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.CommonPlayCalculationUtils.*;

public class OptimalPlayCalculationUtils {
    private static Map<Set<Integer>, Double> TIMES_BY_POSITIONS = new HashMap<>();

    public static double calculatePositionTime(Set<Integer> position) {
        double left = 1.0;
        double right = 1.0;

        Map<Double, Integer> timeFrequency = new TreeMap<>(Comparator.naturalOrder());

        for (Dices dices : ALL_DICES) {
            Set<Set<Integer>> posiblePositions = getPosiblePositions(position, dices);

            if (posiblePositions.contains(Collections.emptySet())) {
                continue;
            }

            if (posiblePositions.isEmpty()) {
                left -= ((double) dices.getFrequency() / DICES_COMBINATIONS_COUNT);
            } else {
                Set<Integer> bestPosition = getBest(posiblePositions);
                Double positionTime = TIMES_BY_POSITIONS.get(bestPosition);
                timeFrequency.put(positionTime, timeFrequency.getOrDefault(positionTime, 0) + dices.getFrequency());
            }
        }

        if (timeFrequency.isEmpty()) {
            double time = right / left;
            TIMES_BY_POSITIONS.put(position, time);
            return time;
        }

        int lessThan = timeFrequency.values().stream().mapToInt(Integer::intValue).sum();

        List<Double> possibleValues = new ArrayList<>();
        double intervalStart = 0.0;
        double intervalEnd;

        for (Map.Entry<Double, Integer> entry : timeFrequency.entrySet()) {
            double time = calculateTime(left, lessThan, right);
            intervalEnd = entry.getKey();

            if (isMore(time, intervalStart) && isMoreOrEqual(intervalEnd, time)) {
                possibleValues.add(time);
            }

            intervalStart = intervalEnd;
            lessThan -= entry.getValue();
            right += entry.getKey() * entry.getValue() / DICES_COMBINATIONS_COUNT;
        }

        double lastIntervalTime = right / left;

        if (isMore(lastIntervalTime, intervalStart)) {
            possibleValues.add(lastIntervalTime);
        }

        double time = possibleValues.stream().mapToDouble(Double::doubleValue).min().orElseThrow();
        TIMES_BY_POSITIONS.put(position, time);
        return time;
    }

    private static Set<Integer> getBest(Set<Set<Integer>> posiblePositions) {
        double bestTime = Double.MAX_VALUE;
        Set<Integer> bestPosition = Collections.emptySet();

        for (Set<Integer> posiblePosition : posiblePositions) {
            try {
                if (TIMES_BY_POSITIONS.get(posiblePosition) < bestTime) {
                    bestTime = TIMES_BY_POSITIONS.get(posiblePosition);
                    bestPosition = posiblePosition;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return bestPosition;
    }

    private static double calculateTime(double left, int lessThan, double right) {
        left -= (double) lessThan / DICES_COMBINATIONS_COUNT;
        return right / left;
    }
}
