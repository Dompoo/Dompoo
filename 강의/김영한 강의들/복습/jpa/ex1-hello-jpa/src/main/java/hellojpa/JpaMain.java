package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        // persistance.xml에 적었던 unit 명으로 emf 생성
        // emf는 애플리케이션 로딩 시점에 딱 1개 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        // emf를 통해 em 생성
        // em은 요청마다 생성
        EntityManager em = emf.createEntityManager();
        
        // JPA에서 데이터를 변경하는 모든 작업은 트랜잭션 내에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloJPA");
            em.persist(member);
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
