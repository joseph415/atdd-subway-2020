package wooteco.subway.fare.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import wooteco.subway.members.member.domain.LoginMember;

public enum DiscountAgeType {
    BABY(fare -> BigDecimal.valueOf(0), age -> age > 0 && age < 6),
    CHILDREN(fare -> (BigDecimal.valueOf(fare).subtract(Constants.DEDUCTION).multiply(
            BigDecimal.valueOf(5, 1))), age -> age >= 6 && age < 13),
    TEENAGER(fare -> (BigDecimal.valueOf(fare).subtract(Constants.DEDUCTION)).multiply(
            BigDecimal.valueOf(7, 1)), age -> (age >= 13 && age < 19)),
    ADULT(BigDecimal::valueOf, age -> age >= 19);

    private final Function<Integer, BigDecimal> farePerDistance;
    private final Predicate<Integer> predicate;

    private static class Constants {
        public static final BigDecimal DEDUCTION = BigDecimal.valueOf(350);
    }

    DiscountAgeType(
            Function<Integer, BigDecimal> farePerDistance,
            Predicate<Integer> predicate) {
        this.farePerDistance = farePerDistance;
        this.predicate = predicate;
    }

    public static int discountByAge(LoginMember loginMember, int fare) {
        final DiscountAgeType discountType = Arrays.stream(DiscountAgeType.values())
                .filter(discountAgeType -> discountAgeType.predicate.test(loginMember.getAge()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 연령이 없습니다."));

        return discountType.farePerDistance.apply(fare).intValue();
    }
}
