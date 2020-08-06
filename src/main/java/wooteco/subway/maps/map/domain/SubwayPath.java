package wooteco.subway.maps.map.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class SubwayPath {
    private static final double STANDARD_FARE = 1250d;
    private static final int TEN_OVER_DISTANCE_UNIT = 10;
    private static final int FIFTY_OVER_DISTANCE_UNIT = 50;

    private List<LineStationEdge> lineStationEdges;

    public SubwayPath(List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists.newArrayList(
                lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(Collectors.toList()));

        return stationIds;
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public double calculateFare(int distance) {
        return STANDARD_FARE + calculateOverFare(distance);
    }

    private int calculateOverFare(int distance) {
        int overfare = 0;

        if (distance <= TEN_OVER_DISTANCE_UNIT) {
            return 0;
        }

        if (distance > FIFTY_OVER_DISTANCE_UNIT) {
            overfare += (int)(
                    (Math.ceil(additionalFareRateUnitBy(distance - FIFTY_OVER_DISTANCE_UNIT, true)))
                            * 100);
            distance = FIFTY_OVER_DISTANCE_UNIT;
        }
        overfare += (int)((Math.ceil(additionalFareRateUnitBy(distance, false))) * 100);

        return overfare;
    }

    private int additionalFareRateUnitBy(int distance, boolean overFifty) {
        if (!overFifty) {
            distance -= TEN_OVER_DISTANCE_UNIT;
            return ((distance - 1) / 5) + 1;
        }
        return ((distance - 1) / 8) + 1;
    }
}
