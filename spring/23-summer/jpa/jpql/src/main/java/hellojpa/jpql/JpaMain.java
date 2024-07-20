package hellojpa.jpql;

import hellojpa.jpql.domain.Member;
import hellojpa.jpql.domain.MemberDTO;
import hellojpa.jpql.domain.MemberTeamDTO;
import hellojpa.jpql.domain.Team;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team1 = new Team();
            team1.setName("team1");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";

            List<Member> members = em.createQuery(query, Member.class)
                    .getResultList();
            
            for (Member member : members) {
                System.out.println("member = " + member.getUsername() + " | " + member.getTeam().getName());
            }

//            for (MemberTeamDTO dto : result) {
//                System.out.println("member = " + dto.getMember().getUsername());
//                System.out.println("team = " + dto.getTeam().getName());
//            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
