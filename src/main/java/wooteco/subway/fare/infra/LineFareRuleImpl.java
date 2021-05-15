package wooteco.subway.fare.infra;

import java.util.List;

import wooteco.subway.fare.domain.LineFareRule;
import wooteco.subway.maps.line.domain.Line;

public class LineFareRuleImpl implements LineFareRule {
    @Override
    public int maxLineFare(List<Line> lines) {
        return lines.stream()
                .map(Line::getExtraFare)
                .max(Integer::compare)
                .orElseThrow(() -> new IllegalArgumentException("최대값이 존재하지 않습니다."));
    }
}
