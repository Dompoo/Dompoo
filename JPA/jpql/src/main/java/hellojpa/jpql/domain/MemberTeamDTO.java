package hellojpa.jpql.domain;

public class MemberTeamDTO {

    private Member member;
    private Team team;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
