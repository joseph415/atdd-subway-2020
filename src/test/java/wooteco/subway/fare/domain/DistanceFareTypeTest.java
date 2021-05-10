package wooteco.subway.fare.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DistanceFareTypeTest {
    @DisplayName("거리당 요금 측정하는 test")
    @CsvSource(value = {"10,1250.0", "15,1350.0,", "58,2150.0", "59,2150.0"})
    @ParameterizedTest
    void should_givenDistance_thenFareBeDifferentDistance(int distance, double expect) {
        // when
        final Double fare = DistanceFareType.calculateFare(distance);
        // then
        assertThat(expect).isEqualTo(fare);
    }

}