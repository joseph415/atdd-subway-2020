package wooteco.subway.members.member.domain;

public class EmptyMember implements CurrentMember {
    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public Integer getAge() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public boolean checkCredentials(Object credentials) {
        return false;
    }
}
