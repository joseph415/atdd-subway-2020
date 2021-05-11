package wooteco.subway.fare.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.fare.domain.DistanceFareType;
import wooteco.subway.fare.domain.LineFareRule;
import wooteco.subway.maps.line.domain.Line;

class LineFareRuleImplTest {
    @DisplayName("라인에 추가요금이 있는 경우")
    @Test
    void should_givenLineExtraFare_farePlus() {
        // given
        Line line1 = TestObjectUtils.createLineExtraFare(1L, "2호선", "GREEN", 500);
        int distance = 10;
        // when
        LineFareRule lineFareRule = new LineFareRuleImpl();
        final double farePerDistance = DistanceFareType.calculateFare(distance);
        final double maxLineFare = lineFareRule.maxLineFare(Collections.singletonList(line1));
        // then
        assertThat(1750.0).isEqualTo(farePerDistance + maxLineFare);
    }

    @DisplayName("추가요금 라인이 여러개일 경우 최대 추가요금만 부가")
    @Test
    void should_givenLines_addMaxLineExtraFare() {
        // given
        Line line1 = TestObjectUtils.createLineExtraFare(1L, "2호선", "GREEN", 500);
        Line line2 = TestObjectUtils.createLineExtraFare(1L, "2호선", "GREEN", 900);
        int distance = 58;
        // when
        LineFareRule lineFareRule = new LineFareRuleImpl();
        final double farePerDistance = DistanceFareType.calculateFare(distance);
        final double maxLineFare = lineFareRule.maxLineFare(Arrays.asList(line1, line2));
        // then
        assertThat(3050.0).isEqualTo(farePerDistance + maxLineFare);
    }

}