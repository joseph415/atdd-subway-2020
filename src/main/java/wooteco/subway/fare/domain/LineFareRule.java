package wooteco.subway.fare.domain;

import java.util.List;

import wooteco.subway.maps.map.domain.LineStationEdge;

public interface LineFareRule {
    double maxLineFare(List<LineStationEdge> lineStationEdges);
}
