package wooteco.subway.fare.domain;

import java.util.List;

import wooteco.subway.fare.infra.LineFareRuleImpl;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.members.member.domain.CurrentMember;

public class CalculateFareService {
    public int calculateFare(CurrentMember currentMember, List<Line> lines, int distance) {
        final LineFareRule lineFareRule = new LineFareRuleImpl();
        final int fare = DistanceFareType.calculateFare(distance) + lineFareRule.maxLineFare(lines);

        if (currentMember.isLogin()) {
            return DiscountAgeType.discountByAge(currentMember, fare);
        }
        return fare;
    }
}
