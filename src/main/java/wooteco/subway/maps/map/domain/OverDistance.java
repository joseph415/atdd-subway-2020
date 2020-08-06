package wooteco.subway.maps.map.domain;

public enum OverDistance {
    TEN(10),
    FIFTY(50);

    private final int overDistance;

    OverDistance(int overDistance) {
        this.overDistance = overDistance;
    }

    public int getOverDistance() {
        return overDistance;
    }
}
