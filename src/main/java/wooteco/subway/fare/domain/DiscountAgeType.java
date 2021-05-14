package wooteco.subway.fare.domain;

import java.util.Arrays;
import java.util.function.DoubleFunction;
import java.util.function.Predicate;

import wooteco.subway.members.member.domain.LoginMember;

public enum DiscountAgeType {
    BABY(fare -> 0.0, age -> age > 0 && age < 6),
    CHILDREN(fare -> (fare - Constants.DEDUCTION) * 0.5, age -> age >= 6 && age < 13),
    TEENAGER(fare -> (fare - Constants.DEDUCTION) * 0.7, age -> (age >= 13 && age < 19)),
    ADULT(fare -> fare, age -> age >= 19);

    private final DoubleFunction<Double> farePerDistance;
    private final Predicate<Integer> predicate;

    private static class Constants {
        public static final double DEDUCTION = 350.0;
    }

    DiscountAgeType(
            DoubleFunction<Double> farePerDistance,
            Predicate<Integer> predicate) {
        this.farePerDistance = farePerDistance;
        this.predicate = predicate;
    }

    public static double discountByAge(LoginMember loginMember, Double fare) {
        final DiscountAgeType discountType = Arrays.stream(DiscountAgeType.values())
                .filter(discountAgeType -> discountAgeType.predicate.test(loginMember.getAge()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 연령이 없습니다."));

        return discountType.farePerDistance.apply(fare);
    }
}
