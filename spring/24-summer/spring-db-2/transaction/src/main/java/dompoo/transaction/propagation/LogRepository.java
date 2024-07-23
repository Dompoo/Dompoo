package dompoo.transaction.propagation;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepository {
    
    private final EntityManager em;
    
    @Transactional
    public void save(Log logmessage) {
        log.info("로그 저장");
        em.persist(logmessage);
        
        if (logmessage.getMessage().contains("로그예외")) {
            log.info("로그 저장중 예외 발생");
            throw new RuntimeException("로그 저장중 예외 발생");
        }
    }
    
    @Transactional
    public Optional<Log> findByMessage(String message) {
        return em.createQuery("select m from Log m where m.message = :message", Log.class)
            .setParameter("message", message)
            .getResultList().stream().findAny();
    }
}
