package wooteco.subway.fare.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.members.member.domain.LoginMember;

class CalculateFareServiceTest {
    @DisplayName("요금을 계산하는 서비스 기능을 제공한다.")
    @CsvSource(value = {"5,0.0", "10,1350.0", "13,1890.0", "20,3050.0"})
    @ParameterizedTest
    void Should_givenLineAndMember_calculateFare(Integer age, double expected) {
        // given
        LoginMember loginMember = TestObjectUtils.createLoginMember(1L, "j@a.com", "1234", age);
        Line line1 = TestObjectUtils.createLineExtraFare(1L, "2호선", "GREEN", 500);
        Line line2 = TestObjectUtils.createLineExtraFare(1L, "2호선", "GREEN", 900);
        List<Line> lines = Arrays.asList(line1, line2);
        int distance = 58;

        CalculateFareService calculateFareService = new CalculateFareService();
        // when
        double fare = calculateFareService.calculateFare(loginMember, lines, distance);
        // then
        assertThat(expected).isEqualTo(fare);
    }
}