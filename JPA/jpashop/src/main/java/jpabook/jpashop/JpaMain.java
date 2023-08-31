package jpabook.jpashop;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {

            Book book = new Book();
            book.setName("JPA");
            book.setPrice(10000);
            book.setStockQuantity(100);
            book.setIsbn("123");
            book.setAuthor("김영한");

            Album album = new Album();
            album.setName("JPA");
            album.setPrice(10000);
            album.setStockQuantity(100);
            album.setArtist("김영한");
            album.setEtc("후후");

            Movie movie = new Movie();
            movie.setName("JPA");
            movie.setPrice(10000);
            movie.setStockQuantity(100);
            movie.setActor("창근");
            movie.setDirector("형주");

            em.persist(book);
            em.persist(album);
            em.persist(movie);



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
