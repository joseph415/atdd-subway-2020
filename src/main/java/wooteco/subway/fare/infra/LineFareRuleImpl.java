package wooteco.subway.fare.infra;

import java.util.List;

import wooteco.subway.fare.domain.LineFareRule;
import wooteco.subway.maps.map.domain.LineStationEdge;

public class LineFareRuleImpl implements LineFareRule {
    @Override
    public double maxLineFare(List<LineStationEdge> lineStationEdges) {
        return 0;
    }
}
