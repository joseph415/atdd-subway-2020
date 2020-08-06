package wooteco.subway.maps.map.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class SubwayPath {
    private static final int STANDARD_FARE = 1250;

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

    public int calculateFare(int distance, int highestExtraFare, int age) {
        int fare = STANDARD_FARE + calculateOverFare(distance);

        if (highestExtraFare > 0) {
            return fare + highestExtraFare;
        }

        return fareForSaleBy(age, fare);
    }

    private int fareForSaleBy(int age, int fare) {
        if (age >= 15 && age < 19) {
            return ((fare - 350) * (80)) / 100;
        }
        if (age >= 6 && age < 13) {
            return ((fare - 350) * (50)) / 100;
        }
        return fare;
    }

    private int calculateOverFare(int distance) {
        int overfare = 0;

        if (distance <= OverDistance.TEN.getOverDistance()) {
            return 0;
        }

        if (distance > OverDistance.FIFTY.getOverDistance()) {
            overfare += (int)(
                    (Math.ceil(additionalFareRateUnitBy(
                            distance - OverDistance.FIFTY.getOverDistance(), true)))
                            * 100);
            distance = OverDistance.FIFTY.getOverDistance();
        }
        overfare += (int)((Math.ceil(additionalFareRateUnitBy(distance, false))) * 100);

        return overfare;
    }

    private int additionalFareRateUnitBy(int distance, boolean overFifty) {
        if (!overFifty) {
            distance -= OverDistance.TEN.getOverDistance();
            return ((distance - 1) / 5) + 1;
        }
        return ((distance - 1) / 8) + 1;
    }
}
