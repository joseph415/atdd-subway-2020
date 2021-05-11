package wooteco.subway.fare.domain;

import java.util.List;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.map.domain.LineStationEdge;

public interface LineFareRule {
    double maxLineFare(List<Line> lines);
}
