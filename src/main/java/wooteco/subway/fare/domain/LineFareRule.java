package wooteco.subway.fare.domain;

import java.util.List;

import wooteco.subway.maps.line.domain.Line;

public interface LineFareRule {
    int maxLineFare(List<Line> lines);
}
