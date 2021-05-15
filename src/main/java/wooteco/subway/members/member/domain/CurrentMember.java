package wooteco.subway.members.member.domain;

import wooteco.security.core.userdetails.UserDetails;

public interface CurrentMember extends UserDetails {
    Long getId();

    String getEmail();

    Integer getAge();

    String getPassword();

    boolean isLogin();
}
