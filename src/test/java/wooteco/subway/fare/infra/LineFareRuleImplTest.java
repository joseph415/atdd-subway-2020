package wooteco.subway.fare.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.fare.domain.DistanceFareType;
import wooteco.subway.fare.domain.LineFareRule;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.map.domain.LineStationEdge;

class LineFareRuleImplTest {
    @DisplayName("라인에 추가요금이 있는 경우")
    @Test
    void should_givenLineExtraFare_farePlus() {
        // given
        Line line1 = TestObjectUtils.createLineExtraFare(1L, "2호선", "GREEN", 500);
        final LineStation lineStation1 = new LineStation(1L, null, 0, 0);
        final LineStation lineStation2 = new LineStation(2L, 1L, 10, 2);
        line1.addLineStation(lineStation1);
        line1.addLineStation(lineStation2);
        
        List<LineStationEdge> lineStations = Lists.newArrayList(
                new LineStationEdge(lineStation1, line1.getId()),
                new LineStationEdge(lineStation2, line1.getId())
        );
        int distance = 10;
        // when
        LineFareRule lineFareRule = new LineFareRuleImpl();
        final double farePerDistance = DistanceFareType.calculateFare(distance);
        final double maxLineFare = lineFareRule.maxLineFare(lineStations);
        // then
        assertThat(1750.0).isEqualTo(farePerDistance + maxLineFare);
    }
    
    
}