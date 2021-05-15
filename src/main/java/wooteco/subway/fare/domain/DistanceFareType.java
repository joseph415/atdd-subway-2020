package wooteco.subway.fare.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum DistanceFareType {
    DEFAULT(distance -> Constants.DEFAULT_FARE, distance -> distance <= 10.0),
    TEN_TO_FIFTY(
            distance -> Constants.DEFAULT_FARE.add(
                    (BigDecimal.valueOf(distance).subtract(BigDecimal.valueOf(10))).divide(
                            BigDecimal.valueOf(5), BigDecimal.ROUND_DOWN)
                            .multiply(BigDecimal.valueOf(100))),
            distance -> distance > 10.0 && distance <= 50.0),
    OVER_FIFTY(distance -> Constants.DEFAULT_FARE.add(Constants.TEN_TO_FIFTY_FULL_FARE).add(
            (BigDecimal.valueOf(distance).subtract(BigDecimal.valueOf(50))).divide(
                    BigDecimal.valueOf(8), BigDecimal.ROUND_DOWN)
                    .multiply(BigDecimal.valueOf(100))),
            distance -> distance > 50.0);

    private final Function<Integer, BigDecimal> farePerDistance;
    private final Predicate<Integer> predicate;

    private static class Constants {
        public static final BigDecimal DEFAULT_FARE = BigDecimal.valueOf(1250);
        public static final BigDecimal TEN_TO_FIFTY_FULL_FARE = BigDecimal.valueOf(800);
    }

    DistanceFareType(Function<Integer, BigDecimal> farePerDistance, Predicate<Integer> predicate) {
        this.farePerDistance = farePerDistance;
        this.predicate = predicate;
    }

    public static int calculateFare(int distance) {
        final DistanceFareType distanceType = Arrays.stream(DistanceFareType.values())
                .filter(distanceFareType -> distanceFareType.predicate.test(distance))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("잘못된 distance"));

        return distanceType.farePerDistance.apply(distance).intValue();
    }
}
