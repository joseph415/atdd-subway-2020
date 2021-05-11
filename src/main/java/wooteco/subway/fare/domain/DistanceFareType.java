package wooteco.subway.fare.domain;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum DistanceFareType {
    DEFAULT(distance -> Constants.DEFAULT_FARE, distance -> distance <= 10),
    TEN_TO_FIFTY(distance -> Constants.DEFAULT_FARE + Math.floor((distance - 10) / 5.0) * 100.0,
            distance -> distance > 10 && distance <= 50),
    OVER_FIFTY(distance -> Constants.DEFAULT_FARE + Constants.TEN_TO_FIFTY_FULL_FARE
            + Math.floor((distance - 50) / 8.0) * 100.0, distance -> distance > 50);

    private final Function<Integer, Double> farePerDistance;
    private final Predicate<Integer> predicate;

    private static class Constants {
        public static final double DEFAULT_FARE = 1_250.0;
        public static final double TEN_TO_FIFTY_FULL_FARE = 800.0;
    }

    DistanceFareType(Function<Integer, Double> farePerDistance, Predicate<Integer> predicate) {
        this.farePerDistance = farePerDistance;
        this.predicate = predicate;
    }

    public static double calculateFare(int distance) {
        final DistanceFareType distanceType = Arrays.stream(DistanceFareType.values())
                .filter(distanceFareType -> distanceFareType.predicate.test(distance))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("잘못된 distance"));

        return distanceType.farePerDistance.apply(distance);
    }
}
