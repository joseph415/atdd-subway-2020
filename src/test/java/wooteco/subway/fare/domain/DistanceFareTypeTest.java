package wooteco.subway.fare.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DistanceFareTypeTest {
    @DisplayName("거리당 요금 측정하는 test")
    @CsvSource(value = {"10,1250", "15,1350,", "58,2150", "59,2150"})
    @ParameterizedTest
    void should_givenDistance_thenFareBeDifferentDistance(int distance, int expect) {
        // when
        final int fare = DistanceFareType.calculateFare(distance);
        // then
        assertThat(fare).isEqualTo(expect);
    }

}